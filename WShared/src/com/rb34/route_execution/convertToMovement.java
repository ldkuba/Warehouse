package com.rb34.route_execution;


import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rb34.general.PathChoices;
import com.rb34.route_planning.graph_entities.IVertex;


public class convertToMovement {
		
	private static final Logger log4j = LogManager.getLogger(convertToMovement.class.getName());
	
	public ArrayList<PathChoices> execute(ArrayList<IVertex> path) {
		
		ArrayList<IVertex> vertices = path;
		ArrayList<PathChoices> movement = new ArrayList<PathChoices>();
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
						movement.add(PathChoices.FORWARD);
						Heading.setHeading("N");
					} else if(Y < prevY) {
						movement.add(PathChoices.ROTATE);
						Heading.setHeading("S");
					}	
				} else if (Y == prevY) {
					if(X > prevX) {
						movement.add(PathChoices.RIGHT);
						Heading.setHeading("E");
					} else if(X < prevX) {
						movement.add(PathChoices.LEFT);
						Heading.setHeading("W");
					}
				}		
			} else if(Heading.getHeading().equals("E")) {
				if(X == prevX){
					if(Y > prevY) {
						movement.add(PathChoices.LEFT);
						Heading.setHeading("N");
					} else if(Y < prevY) {
						movement.add(PathChoices.RIGHT);
						Heading.setHeading("S");
					}	
				} else if(Y == prevY) {
					if(X > prevX) {
						movement.add(PathChoices.FORWARD);
						Heading.setHeading("E");
					} else if(X < prevX) {
						movement.add(PathChoices.ROTATE);					
						Heading.setHeading("W");
					}
				}		
			} else if(Heading.getHeading().equals("S")) {
				if(X == prevX) {
					if(Y > prevY) {
						movement.add(PathChoices.ROTATE);
						Heading.setHeading("N");	
					} else if(Y < prevY){
						movement.add(PathChoices.FORWARD);
						Heading.setHeading("S");
					} 
				} else if(Y == prevY) {
					if(X > prevX) {
						movement.add(PathChoices.LEFT);
						Heading.setHeading("E");
					} else if(X < prevX) {
						movement.add(PathChoices.RIGHT);
						Heading.setHeading("W");
					}
				}	
			} else if(Heading.getHeading().equals("W")) {
				if(X == prevX) {	
					if(Y > prevY) {
						movement.add(PathChoices.RIGHT);
						Heading.setHeading("N");
					} else if(Y < prevY) {
						movement.add(PathChoices.LEFT);
						Heading.setHeading("S");
					}
				} else if(Y == prevY) {
						if(X > prevX) {
							movement.add(PathChoices.ROTATE);
							Heading.setHeading("E");
						} else if(X < prevX) {
							movement.add(PathChoices.FORWARD);
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
