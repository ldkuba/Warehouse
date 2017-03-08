package com.rb34.route_execution;

import com.rb34.route_planning.graph_entities.Heuristic;
import com.rb34.route_planning.graph_entities.IVertex;
import com.rb34.route_planning.graph_entities.Result;
import com.rb34.route_planning.graph_implementation.Graph;
import rp.robotics.mapping.GridMap;
import rp.robotics.mapping.MapUtils;

import java.util.ArrayList;

public class Execute {

	private ArrayList<String> pathMap;

	public ArrayList<String> runRoute(String start, String end) {
		GridMap map = MapUtils.createRealWarehouse();
		Graph graph = new Graph(map);

		Result result = graph.aStar(start, end, new Heuristic<>());

		ArrayList<IVertex> path = result.getPath().get();

		convertToMovement converter = new convertToMovement();

		/*
		 * System.out.println("Visited Nodes: "); ArrayList<IVertex> visited =
		 * result.getVisitedVertices().get(); for (IVertex node : visited) {
		 * System.out.print(node.getLabel().getName() + "; "); }
		 */

		boolean debug = true;
		if (debug  == true) {
			System.out.println("");

			System.out.println("Chosen Path: ");
			for (IVertex node : path) {
				System.out.print(node.getLabel().getName() + "; ");
			}

			System.out.println("");

		}
		pathMap = converter.execute(path);
		// System.out.println(pathMap);

		return pathMap;
	}

}
