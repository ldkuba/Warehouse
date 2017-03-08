package com.rb34.route_execution;

import com.rb34.route_planning.graph_entities.IVertex;
import java.util.ArrayList;

public class Execute {

	private ArrayList<String> robotInstructions;
	private ArrayList<IVertex> vertexPath;

	public ArrayList<String> runRoute(ArrayList<IVertex> path) {
		 vertexPath = path;

		convertToMovement converter = new convertToMovement();

		/*boolean debug = false;
		if (debug  == true) {
			System.out.println("");

			System.out.println("Chosen Path: ");
			for (IVertex node : path) {
				System.out.print(node.getLabel().getName() + "; ");
			}

			System.out.println("");

		}*/
		
		robotInstructions = converter.execute(vertexPath);

		
		//Replace with method that sends to bluetooth
		return robotInstructions;
	}

}
