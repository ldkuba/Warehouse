package com.rb34.route_execution;

import java.util.ArrayList;

import com.rb34.route_planning.graph_entities.IVertex;

public class MovementConv {
	
	
	public ArrayList<String> execute(ArrayList<IVertex> path) {

		ArrayList<String> movement = new ArrayList<String>();
		String startPoint = path.get(0).getLabel().getName();
		String heading = Heading.getHeading();
		String tempX = startPoint.split("|")[0];
		String tempY = startPoint.split("|")[2];
		int prevX = Integer.valueOf(tempX);
		int prevY = Integer.valueOf(tempY);
		
		//remove start point
		
		//path.remove(0);
		System.out.println("Initial: " + heading);
		for (IVertex vertex : path) {

			String coordX = vertex.getLabel().getName().split("|")[0];
			String coordY = vertex.getLabel().getName().split("|")[2];
			int X = Integer.valueOf(coordX);
			int Y = Integer.valueOf(coordY);
			
			if(X > prevX && Y == prevY && Heading.getHeading() == "E") {
				movement.add("FORWARD");
			} else if(X < prevX && Y == prevY && Heading.getHeading() == "E") {
				movement.add("ROTATE");
				movement.add("FORWARD");
				Heading.setHeading("W");
			} else if(Y > prevY && X == prevX && Heading.getHeading() == "E") {
				movement.add("LEFT");
				Heading.setHeading("N");
			} else if(Y < prevY && X == prevX && Heading.getHeading() == "E"){
				movement.add("RIGHT"); 
				Heading.setHeading("S");
			} else if(X > prevX && Y == prevY && Heading.getHeading() == "N") {
				movement.add("RIGHT");
				Heading.setHeading("E");
			} else if(X < prevX && Y == prevY && Heading.getHeading() == "N") {
				movement.add("LEFT");
				Heading.setHeading("W");
			} else if(Y > prevY && X == prevX && Heading.getHeading() == "N") {
				movement.add("FORWARD");
				Heading.setHeading("N");
			} else if(Y < prevY && X == prevX && Heading.getHeading() == "N") {
				movement.add("ROTATE");
				movement.add("FORWARD");
				Heading.setHeading("S");
			} else if(X > prevX && Y == prevY && Heading.getHeading() == "W") {
				movement.add("ROTATE");
				movement.add("FORWARD");
				Heading.setHeading("E");
			} else if(X < prevX && Y == prevY && Heading.getHeading() == "W") {
				movement.add("FORWARD");
				Heading.setHeading("W");
			} else if(Y > prevY && X == prevX && Heading.getHeading() == "W") {
				movement.add("RIGHT");
				Heading.setHeading("N");
			} else if(Y < prevY && X == prevX && Heading.getHeading() == "W") {
				movement.add("LEFT");
				Heading.setHeading("S");
			} else if(X > prevX && Y == prevY && Heading.getHeading() == "S") {
				movement.add("LEFT");
				Heading.setHeading("E");
			} else if(X < prevX && Y == prevY && Heading.getHeading() == "S") {
				movement.add("RIGHT");
				Heading.setHeading("W");
			} else if(Y > prevY && X == prevX && Heading.getHeading() == "S") {
				movement.add("ROTATE");
				movement.add("FORWARD");
				Heading.setHeading("N");
			} else if(Y < prevY && X == prevX && Heading.getHeading() == "S") {
				movement.add("FORWARD");
				Heading.setHeading("S");
			}	
			
			prevX = X;
			prevY = Y;
			
		}
		System.out.println("Final: " + Heading.getHeading());
		return movement;
		
	}

}