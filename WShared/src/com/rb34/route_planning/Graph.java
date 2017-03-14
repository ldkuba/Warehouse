package com.rb34.route_planning;

import static java.lang.Float.MAX_VALUE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.function.BiFunction;

import org.apache.log4j.Logger;

import com.rb34.route_execution.Execute;
import com.rb34.route_planning.graph_entities.Heuristic;
import com.rb34.route_planning.graph_entities.IEdge;
import com.rb34.route_planning.graph_entities.IGraph;
import com.rb34.route_planning.graph_entities.IVertex;
import com.rb34.route_planning.graph_entities.Label;
import com.rb34.route_planning.graph_entities.Result;

import rp.robotics.mapping.GridMap;
import rp.robotics.mapping.MapUtils;

public class Graph implements IGraph {
	final static Logger logger = Logger.getLogger(Graph.class);
	
	Map<String, IVertex> vertices;
	GridMap gridMap;

	// Constructor with GridMap parameter
	public Graph() {
		logger.debug("Started route planning");
		vertices = new HashMap<>();
		
		// should get the real map here instead of this "real warehouse"
		gridMap = MapUtils.createRealWarehouse();
		
		// Add the junctions from the map to the collection of vertices
		for (int i = 0; i < gridMap.getXSize(); i++)
			for (int j = 0; j < gridMap.getYSize(); j++)
				if (!gridMap.isObstructed(i, j))
					addVertex(Integer.toString(i) + "|" + Integer.toString(j), new Vertex(i, j));

		// Add the edges
		for (IVertex sourceVertex : getVertices())
			for (IVertex targetVertex : getVertices()) {
				int xSource = sourceVertex.getLabel().getX();
				int ySource = sourceVertex.getLabel().getY();
				int xTarget = targetVertex.getLabel().getX();
				int yTarget = targetVertex.getLabel().getY();
				if (Math.sqrt(Math.pow(xTarget - xSource, 2) + Math.pow(yTarget - ySource, 2)) == 1)
					addEdge(sourceVertex.getLabel().getName(), targetVertex.getLabel().getName(), 1);

			}
		logger.debug("Generated graph from map");
	}
	
	public void executeRoute(String startVertexId, String endVertexId, int robotId) {
		ArrayList<IVertex> path = aStar(startVertexId, endVertexId).getPath().get();
		
		String logMessage = "";
		for (IVertex vertex : path) {
			logMessage += vertex.getLabel().getName() + " ";
		}
		logger.debug("The generated path is: " + logMessage);
		Execute execute = new Execute();
		execute.runRoute(path, robotId);
	}

	// Add vertex to graph
	@Override
	public void addVertex(String vertexId, IVertex vertex) {
		vertices.put(vertexId, vertex);
		vertex.getLabel().setName(vertexId);
	}

	// Add edge to vertex
	@Override
	public void addEdge(String vertexSrcId, String vertexTgtId, Integer cost) {
		getVertex(vertexSrcId).addEdge(new Edge(getVertex(vertexTgtId), cost));
	}

	// Get the collection of vertices
	@Override
	public Collection<IVertex> getVertices() {
		return vertices.values();
	}

	// Get the collection of vertex IDs
	@Override
	public Collection<String> getVertexIds() {
		return vertices.keySet();
	}

	// Get a specific vertex
	@Override
	public IVertex getVertex(String vertexId) {
		return vertices.get(vertexId);
	}

	// A* algorithm 
	@Override
	public Result aStar(String startVertexId, String endVertexId) {
		Result result = new Result();
		
		if (getVertex(startVertexId) == null || getVertex(endVertexId) == null) {
			logger.debug("Invalid coordinates");
			return null;
		}

		BiFunction<IVertex, IVertex, Float> heuristic = new Heuristic();
		Comparator<IVertex> comparator = new Comparator<IVertex>() {
			@Override
			public int compare(IVertex o1, IVertex o2) {
				return (int) (o1.getLabel().getCost() + heuristic.apply(o1, getVertex(endVertexId))
						- (o2.getLabel().getCost() + heuristic.apply(o2, getVertex(endVertexId))));
			}
		};
		ArrayList<IVertex> closedList = new ArrayList<>();
		PriorityQueue<IVertex> openList = new PriorityQueue<>(comparator);
		for (IVertex vertex : getVertices()) {
			vertex.getLabel().setParentVertex(null);
			vertex.getLabel().setCost(Integer.MAX_VALUE);
		}
		getVertex(startVertexId).getLabel().setCost(0);
		openList.offer(getVertex(startVertexId));
		boolean targetInClosedList = false;
		while (!openList.isEmpty()) {
			IVertex currentVertex = openList.poll();
			if (targetInClosedList) {
				Integer pathCost = getVertex(endVertexId).getLabel().getCost();
				ArrayList<IVertex> path = new ArrayList<>();
				Optional<IVertex> currentPathVertex = Optional.of(getVertex(endVertexId));
				while (currentPathVertex.get().getLabel().getParentVertex().isPresent()) {
					path.add(0, currentPathVertex.get());
					currentPathVertex = currentPathVertex.get().getLabel().getParentVertex();
				}
				path.add(0, currentPathVertex.get());
				result.setVisitedVertices(closedList);
				result.setPath(path);
				result.setPathCost(pathCost);
				logger.debug("Found path");
				break;
			}
			for (IEdge edge : currentVertex.getSuccessors()) {
				String successorName = edge.getTgt().getLabel().getName();
				IVertex successor = getVertex(successorName);
				if (closedList.contains(successor))
					continue;
				int tentativeCost = currentVertex.getLabel().getCost() + edge.getCost();
				if (!openList.contains(successor)) {
					successor.getLabel().setCost(tentativeCost);
					successor.getLabel().setParentVertex(currentVertex);
					openList.offer(successor);
				} else if (tentativeCost >= successor.getLabel().getCost())
					continue;
			}
			closedList.add(currentVertex);
			if (currentVertex.getLabel().getName().equals(endVertexId))
				targetInClosedList = true;
		}
		return result;
	}
}