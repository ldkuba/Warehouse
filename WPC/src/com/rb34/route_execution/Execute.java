package com.rb34.route_execution;

import java.util.ArrayList;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.rb34.general.PathChoices;
import com.rb34.message.NewPathMessage;
import com.rb34.network.Master;
import com.rb34.route_planning.graph_entities.IVertex;


public class Execute {

	private ArrayList<PathChoices> robotInstructions;
	private ArrayList<IVertex> vertexPath;
	private Master master;

	private static final Logger log4j = LogManager.getLogger(Execute.class.getName());
	
	
	public Execute(Master master) {
		this.master = master;
	}
	
	public Execute(){
		
	}
	
	public void runRoute(ArrayList<IVertex> path, int robotId) {
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

		
		//Replace with method that sends to bluetooth
		log4j.info("Converted Instructions" + robotInstructions);
		int robotID = robotId;
		NewPathMessage msg = new NewPathMessage();
		msg.setCommands(robotInstructions);
		master.send(msg, robotID);
	}

}
