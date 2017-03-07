package com.rb34.route_execution;

import java.util.ArrayList;

import com.rb34.route_planning.graph_entities.IVertex;

public class MovementConv {
	
	
	public ArrayList<String> execute(ArrayList<IVertex> path) {

		ArrayList<String> movement = new ArrayList<String>();
		String heading = Heading.getHeading();
		String name1 = path.get(0).getLabel().getName();
		String[] parts1 = name1.split("\\|");
		String tempX = parts1[0];//startPoint.split("|")[0];
		String tempY = parts1[0];//startPoint.split("|")[2];
		int prevX = Integer.valueOf(tempX);
		int prevY = Integer.valueOf(tempY);
		movement.clear();
		ArrayList<String> returnVal = new ArrayList<String>();
		//remove start point
		
		//System.out.print("Initial Heading: " + heading + " | ");
		for (IVertex vertex : path) {
			String name = vertex.getLabel().getName();
			
			String[] parts = name.split("\\|");
			String coordX = parts[0];//vertex.getLabel().getName().split("|")[0];
			String coordY = parts[1];//vertex.getLabel().getName().split("|")[2];
			int X = Integer.valueOf(coordX);
			int Y = Integer.valueOf(coordY);
			
			if(X == prevX){

				if(Heading.getHeading().equals("E") && Y > prevY) {
					movement.add("LEFT");
					Heading.setHeading("N");
				} else if(Heading.getHeading().equals("E") && Y < prevY) {
					movement.add("RIGHT");
					Heading.setHeading("S");
				} else if(Heading.getHeading().equals("N") && Y > prevY) {
					movement.add("FORWARD");
					Heading.setHeading("N");	
				} else if(Heading.getHeading().equals("N") && Y < prevY) {
					movement.add("ROTATE");
					movement.add("FORWARD");
					Heading.setHeading("S");
				} else if(Heading.getHeading().equals("W") && Y > prevY) {
					movement.add("RIGHT");
					Heading.setHeading("N");
				} else if(Heading.getHeading().equals("W") && Y < prevY) {
					movement.add("LEFT");
					Heading.setHeading("S");
				} else if(Heading.getHeading().equals("S") && Y > prevY) {
					movement.add("ROTATE");
					movement.add("FORWARD");
					Heading.setHeading("N");		
				} else if(Heading.getHeading().equals("S") && Y < prevY) {
					movement.add("FORWARD");
					Heading.setHeading("S");
				}


			} else if(Y == prevY) {

				if(Heading.getHeading().equals("E") && X > prevX) {
					movement.add("FORWARD");
					Heading.setHeading("E");
				} else if(Heading.getHeading().equals("E") && X < prevX) {
					movement.add("ROTATE");
					movement.add("FORWARD");
					Heading.setHeading("W");
				} else if(Heading.getHeading().equals("N") && X > prevX) {
					movement.add("RIGHT");
					Heading.setHeading("E");
				} else if(Heading.getHeading().equals("N") && X < prevX) {
					movement.add("LEFT");
					Heading.setHeading("W");
				} else if(Heading.getHeading().equals("W") && X > prevX) {
					movement.add("ROTATE");
					movement.add("FORWARD");
					Heading.setHeading("E");
				} else if(Heading.getHeading().equals("W") && X < prevX) {
					movement.add("FORWARD");
					Heading.setHeading("W");
				} else if(Heading.getHeading().equals("S") && X > prevX) {
					movement.add("LEFT");
					Heading.setHeading("E");
				} else if(Heading.getHeading().equals("S") && X < prevX) {
					movement.add("RIGHT");
					Heading.setHeading("W");
				}	


			}
		
			prevX = X;
			prevY = Y;
			
			movement = returnVal;
			
		}
		
		//System.out.print(" Final Heading: " + Heading.getHeading() + "\n");
		return returnVal;
		
	}

}