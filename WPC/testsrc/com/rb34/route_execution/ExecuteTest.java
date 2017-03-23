package com.rb34.route_execution;

import java.util.ArrayList;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.rb34.general.PathChoices;
import com.rb34.route_planning.graph_entities.IVertex;

public class ExecuteTest {

	private ArrayList<PathChoices> robotInstructions;
	private ArrayList<IVertex> vertexPath;
	
	private static final Logger log4j = LogManager.getLogger(Execute.class.getName());

	public ArrayList<PathChoices> runRoute(ArrayList<IVertex> path) {
		 vertexPath = path;
		 
		 if(vertexPath == null) {
			log4j.error("Cannot convert to movement: Path is empty or in incorrect format for conversion");
			throw new IllegalArgumentException("Path is empty");
		 } 
			
		ConvertToMovement converter = new ConvertToMovement();
		log4j.trace("Initialised Movement Converting Object");
		
		String choosenPath = "";
		for (IVertex node : path) {
				choosenPath += node.getLabel().getName() + "; ";
		}
		
		log4j.trace("Receieved Following Path for Conversion: " + choosenPath);
		
		robotInstructions = converter.execute(vertexPath);
		System.out.println(robotInstructions);
		
		//Replace with method that sends to bluetooth
		log4j.trace("Converted Instructions" + robotInstructions);
		return robotInstructions;
	}

}
