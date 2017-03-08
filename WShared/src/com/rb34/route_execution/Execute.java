package com.rb34.route_execution;

import com.rb34.route_planning.graph_entities.IVertex;
import java.util.ArrayList;
import org.apache.logging.log4j.Logger; 
import org.apache.logging.log4j.LogManager;

public class Execute {

	private ArrayList<String> robotInstructions;
	private ArrayList<IVertex> vertexPath;

	private static final Logger log4j = LogManager.getLogger(Execute.class.getName());
	
	public ArrayList<String> runRoute(ArrayList<IVertex> path) {
		 vertexPath = path;

	if(vertexPath == null) {
		log4j.error("Cannot convert to movement: Path is empty or in incorrect format for conversion");
		throw new IllegalArgumentException("Path is empty");
		
	} 
		 
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
		log4j.info("Converted Instructions" + robotInstructions);
		return robotInstructions;
	}

}
