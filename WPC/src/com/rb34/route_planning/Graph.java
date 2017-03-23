package com.rb34.route_planning;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.function.BiFunction;

import org.apache.log4j.Logger;

import com.rb34.general.Robot;
import com.rb34.general.RobotManager;
import com.rb34.network.Master;
import com.rb34.route_execution.Execute;
import com.rb34.route_planning.graph_entities.Heuristic;
import com.rb34.route_planning.graph_entities.IEdge;
import com.rb34.route_planning.graph_entities.IGraph;
import com.rb34.route_planning.graph_entities.IVertex;
import com.rb34.route_planning.graph_entities.ReservationInfo;
import com.rb34.route_planning.graph_entities.Result;

import rp.robotics.mapping.GridMap;
import rp.robotics.mapping.MapUtils;

public class Graph implements IGraph {
	final static Logger logger = Logger.getLogger(Graph.class);

	Map<String, IVertex> vertices;
	Map<String, ArrayList<ReservationInfo>> reservationTable;
	int[] robotTimeTracker;
	GridMap gridMap;
	Graph sampleGraph;

	// Constructor with GridMap parameter
	public Graph(RobotManager robotManager) {
		vertices = new HashMap<>();
		reservationTable = new HashMap<>();

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

		robotTimeTracker = new int[3];
		for (int i = 0; i < robotTimeTracker.length; i++)
			robotTimeTracker[i] = 0;

		for (Robot robot : robotManager.getRobots()) {
			if (!reservationTable.containsKey(robot.getXLoc() + "|" + robot.getYLoc())) {
				ReservationInfo reservation = new ReservationInfo(robot.getRobotId(), 0);
				ArrayList<ReservationInfo> reservations = new ArrayList<>();
				reservations.add(reservation);
				reservationTable.put(robot.getXLoc() + "|" + robot.getYLoc(), reservations);
			} else {
				reservationTable.get(robot.getXLoc() + "|" + robot.getYLoc())
						.add(new ReservationInfo(robot.getRobotId(), 0));
			}

		}

		String logMessage = "\nInitially reserved positions:";
		for (String location : reservationTable.keySet()) {
			logMessage += "\n" + location + " reserved at time "
					+ reservationTable.get(location).get(0).getReservationTime() + " by "
					+ reservationTable.get(location).get(0).getRobotId();
		}
		logger.debug(logMessage);

		sampleGraph = new Graph(0);
	}

	public Graph(int k) {
		vertices = new HashMap<>();
		reservationTable = new HashMap<>();

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

	}

	public void executeRoute(String startVertexId, String endVertexId, Robot robot) {
		ArrayList<IVertex> path = hCooperativeAStar(startVertexId, endVertexId, robot.getRobotId()).getPath().get();

		String logMessage = "";
		for (IVertex vertex : path) {
			logMessage += vertex.getLabel().getName() + " ";
		}
		logger.debug("The generated path is: " + logMessage);
		Execute execute = new Execute();
		execute.runRoute(path, robot);
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

	// A* algorithm for job assignment
	@Override
	public Result aStar(String startVertexId, String endVertexId) {
		Result result = new Result();

		if (getVertex(startVertexId) == null || getVertex(endVertexId) == null) {
			logger.debug("Invalid coordinates");
			return null;
		}

		if (startVertexId.equals(endVertexId)) {
			result.setPathCost(0);
			ArrayList<IVertex> path = new ArrayList<>();
			path.add(getVertex(startVertexId));
			result.setPath(path);
			result.setVisitedVertices(path);
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

	// A* for route planning
	public Result aStar(String startVertexId, String endVertexId, int robotId) {
		Result result = new Result();

		if (getVertex(startVertexId) == null || getVertex(endVertexId) == null) {
			logger.debug("Invalid coordinates");
			return null;
		}

		Comparator<IVertex> comparator = new Comparator<IVertex>() {
			@Override
			public int compare(IVertex o1, IVertex o2) {
				return sampleGraph.aStar(o1.getLabel().getName(), endVertexId).getPathCost().get()
						+ o1.getLabel().getTimestep()
						- sampleGraph.aStar(o2.getLabel().getName(), endVertexId).getPathCost().get()
						- o2.getLabel().getTimestep();
			}
		};
		ArrayList<IVertex> closedList = new ArrayList<>();
		PriorityQueue<IVertex> openList = new PriorityQueue<>(comparator);
		for (IVertex vertex : getVertices()) {
			vertex.getLabel().setParentVertex(null);
			vertex.getLabel().setCost(Integer.MAX_VALUE);
		}

		int virtualTime = robotTimeTracker[robotId];
		getVertex(startVertexId).getLabel().setCost(0);
		getVertex(startVertexId).getLabel().setTimestep(virtualTime);

		openList.offer(getVertex(startVertexId));
		boolean targetInClosedList = false;

		while (!openList.isEmpty()) {
			IVertex currentVertex = openList.poll();

			boolean occupiedNode = false;
			if (reservationTable.containsKey(currentVertex.getLabel().getName())) {
				for (ReservationInfo resInfo : reservationTable.get(currentVertex.getLabel().getName())) {
					if (robotId != resInfo.getRobotId()) {
						if (Math.abs(resInfo.getReservationTime() - (currentVertex.getLabel().getTimestep())) < 2) {
							logger.debug("Reserved: " + currentVertex.getLabel().getName() + " at time "
									+ currentVertex.getLabel().getTimestep() + " by " + resInfo.getRobotId());
							closedList.add(currentVertex);
							occupiedNode = true;
							if (!openList.isEmpty())
								virtualTime = openList.peek().getLabel().getTimestep();
						}
					}
				}
			}

			if (occupiedNode)
				continue;

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
				logger.debug("Found path for robot " + robotId);
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
					successor.getLabel().setTimestep(currentVertex.getLabel().getTimestep() + 1);
					openList.offer(successor);
				} else if (tentativeCost >= successor.getLabel().getCost())
					continue;
			}

			closedList.add(currentVertex);
			if (currentVertex.getLabel().getName().equals(endVertexId))
				targetInClosedList = true;

			virtualTime++;

		}

		return result;

	}

	public Result hCooperativeAStar(String startVertexId, String endVertexId, int robotId) {
		Result result = this.aStar(startVertexId, endVertexId, robotId);
		ArrayList<IVertex> path = new ArrayList<>();
		String logMessage = "\nPath for robot #" + robotId;
		int stationaryFor = 0;

		while (!result.getPath().isPresent()) {
			robotTimeTracker[robotId]++;
			result = this.aStar(startVertexId, endVertexId, robotId);
			path.add(getVertex(startVertexId));
			logMessage += "\n" + startVertexId + " at time " + stationaryFor;
			stationaryFor++;
		}

		for (IVertex pathV : result.getPath().get()) {
			path.add(pathV);
		}

		for (int i = 0; i < path.size(); i++) {
			IVertex vertex = path.get(i);

			int time = vertex.getLabel().getTimestep();

			if (i >= stationaryFor)
				logMessage += "\n" + vertex.getLabel().getName() + " at time " + time;

			if (!reservationTable.containsKey(vertex.getLabel().getName())) {
				ReservationInfo reservation = new ReservationInfo(robotId, vertex.getLabel().getTimestep());
				ArrayList<ReservationInfo> reservations = new ArrayList<>();
				reservations.add(reservation);
				reservationTable.put(vertex.getLabel().getName(), reservations);
				
				// reserve the position where it stops for 3 additional units of time
				if (i == path.size() - 1) {
					for (int j = 1; j <= 3; j++) {
						reservation = new ReservationInfo(robotId, vertex.getLabel().getTimestep() + j);
						reservations = new ArrayList<>();
						reservations.add(reservation);
						reservationTable.put(vertex.getLabel().getName(), reservations);
					}
				}
			} else {
				reservationTable.get(vertex.getLabel().getName())
						.add(new ReservationInfo(robotId, vertex.getLabel().getTimestep()));
				
				// reserve the position where it stops for 3 additional units of time
				if (i == path.size() - 1) {
					reservationTable.get(vertex.getLabel().getName())
							.add(new ReservationInfo(robotId, vertex.getLabel().getTimestep() + 1));
					reservationTable.get(vertex.getLabel().getName())
							.add(new ReservationInfo(robotId, vertex.getLabel().getTimestep() + 2));
					reservationTable.get(vertex.getLabel().getName())
							.add(new ReservationInfo(robotId, vertex.getLabel().getTimestep() + 3));
				}
			}
		}

		logger.debug(logMessage);
		robotTimeTracker[robotId] += path.size();

		return result;
	}

}
