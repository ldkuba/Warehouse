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
		log4j.trace("Initialised Movement Converting Object");
		
		String choosenPath = "";
		for (IVertex node : path) {
				choosenPath += node.getLabel().getName() + "; ";
		}
		
		log4j.trace("Receieved Following Path for Conversion: " + choosenPath);
		
		robotInstructions = converter.execute(vertexPath);

		
		//Replace with method that sends to bluetooth
		log4j.info("Converted Instructions" + robotInstructions);
		return robotInstructions;
	}

}
