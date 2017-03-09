package com.rb34.route_execution;


import java.util.ArrayList;

import org.apache.log4j.*;

import com.rb34.route_planning.graph_entities.IVertex;


public class ConvertToMovement {
		
	private static final Logger log4j = LogManager.getLogger(ConvertToMovement.class.getName());
	
	public ArrayList<String> execute(ArrayList<IVertex> path) {
		
		ArrayList<IVertex> vertices = path;
		ArrayList<String> movement = new ArrayList<String>();
		String[] startPoint = vertices.get(0).getLabel().getName().split("\\|");
		log4j.trace("Starting Point of Robot: " + startPoint);
		int prevX = Integer.valueOf(startPoint[0]);
		int prevY = Integer.valueOf(startPoint[1]);
		
		for(IVertex vertex : vertices) {
			
			String name = vertex.getLabel().getName();
			String[] coords = name.split("\\|");
			int X = Integer.valueOf(coords[0]);
			int Y = Integer.valueOf(coords[1]);
			
			if(Heading.getHeading().equals("N")) {
				if(X == prevX) {
					if( Y > prevY) {
						movement.add("FORWARD");
						Heading.setHeading("N");
					} else if(Y < prevY) {
						movement.add("ROTATE");
						Heading.setHeading("S");
					}	
				} else if (Y == prevY) {
					if(X > prevX) {
						movement.add("RIGHT");
						Heading.setHeading("E");
					} else if(X < prevX) {
						movement.add("LEFT");
						Heading.setHeading("W");
					}
				}		
			} else if(Heading.getHeading().equals("E")) {
				if(X == prevX){
					if(Y > prevY) {
						movement.add("LEFT");
						Heading.setHeading("N");
					} else if(Y < prevY) {
						movement.add("RIGHT");
						Heading.setHeading("S");
					}	
				} else if(Y == prevY) {
					if(X > prevX) {
						movement.add("FORWARD");
						Heading.setHeading("E");
					} else if(X < prevX) {
						movement.add("ROTATE");					
						Heading.setHeading("W");
					}
				}		
			} else if(Heading.getHeading().equals("S")) {
				if(X == prevX) {
					if(Y > prevY) {
						movement.add("ROTATE");
						Heading.setHeading("N");	
					} else if(Y < prevY){
						movement.add("FORWARD");
						Heading.setHeading("S");
					} 
				} else if(Y == prevY) {
					if(X > prevX) {
						movement.add("LEFT");
						Heading.setHeading("E");
					} else if(X < prevX) {
						movement.add("RIGHT");
						Heading.setHeading("W");
					}
				}	
			} else if(Heading.getHeading().equals("W")) {
				if(X == prevX) {	
					if(Y > prevY) {
						movement.add("RIGHT");
						Heading.setHeading("N");
					} else if(Y < prevY) {
						movement.add("LEFT");
						Heading.setHeading("S");
					}
				} else if(Y == prevY) {
						if(X > prevX) {
							movement.add("ROTATE");
							Heading.setHeading("E");
						} else if(X < prevX) {
							movement.add("FORWARD");
							Heading.setHeading("W");
						}
				}		
			}
			prevX = X;
			prevY = Y;
			
			log4j.trace("Current Position: " + "(" + prevX + "," + prevY + ")" );
			log4j.trace("Current Heading: " + Heading.getHeading());
		}
		
		
		return movement;
	}
	
	
}
