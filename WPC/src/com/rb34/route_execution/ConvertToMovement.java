package com.rb34.route_execution;


import java.util.ArrayList;

import org.apache.log4j.*;

import com.rb34.general.PathChoices;
import com.rb34.route_planning.graph_entities.IVertex;


public class ConvertToMovement {
		
	final static Logger logger = Logger.getLogger(ConvertToMovement.class);
	
	public ArrayList<PathChoices> execute(ArrayList<IVertex> path) {
		
		ArrayList<IVertex> vertices = path;
		ArrayList<PathChoices> movement = new ArrayList<PathChoices>();
		String[] startPoint = vertices.get(0).getLabel().getName().split("\\|");
		logger.trace("Starting Point of Robot: " + startPoint);
		int prevX = Integer.valueOf(startPoint[0]);
		int prevY = Integer.valueOf(startPoint[1]);
		
		for(IVertex vertex : vertices) {
			
			String name = vertex.getLabel().getName();
			String[] coords = name.split("\\|");
			int X = Integer.valueOf(coords[0]);
			int Y = Integer.valueOf(coords[1]);
			
			if(Robot1Heading.getHeading() ==  Headings.PLUS_Y) {
				if(X == prevX) {
					if( Y > prevY) {
						movement.add(PathChoices.FORWARD);
						Robot1Heading.setHeading(Headings.PLUS_Y);
					} else if(Y < prevY) {
						movement.add(PathChoices.ROTATE);
						Robot1Heading.setHeading(Headings.MINUS_Y);
					}	
				} else if (Y == prevY) {
					if(X > prevX) {
						movement.add(PathChoices.RIGHT);
						Robot1Heading.setHeading(Headings.PLUS_X);
					} else if(X < prevX) {
						movement.add(PathChoices.LEFT);
						Robot1Heading.setHeading(Headings.MINUS_X);
					}
				}		
			} else if(Robot1Heading.getHeading() == Headings.PLUS_X) {
				if(X == prevX){
					if(Y > prevY) {
						movement.add(PathChoices.LEFT);
						Robot1Heading.setHeading(Headings.PLUS_Y);
					} else if(Y < prevY) {
						movement.add(PathChoices.RIGHT);
						Robot1Heading.setHeading(Headings.MINUS_Y);
					}	
				} else if(Y == prevY) {
					if(X > prevX) {
						movement.add(PathChoices.FORWARD);
						Robot1Heading.setHeading(Headings.PLUS_X);
					} else if(X < prevX) {
						movement.add(PathChoices.ROTATE);					
						Robot1Heading.setHeading(Headings.MINUS_X);
					}
				}		
			} else if(Robot1Heading.getHeading() == Headings.MINUS_Y) {
				if(X == prevX) {
					if(Y > prevY) {
						movement.add(PathChoices.ROTATE);
						Robot1Heading.setHeading(Headings.PLUS_Y);	
					} else if(Y < prevY){
						movement.add(PathChoices.FORWARD);
						Robot1Heading.setHeading(Headings.MINUS_Y);
					} 
				} else if(Y == prevY) {
					if(X > prevX) {
						movement.add(PathChoices.LEFT);
						Robot1Heading.setHeading(Headings.PLUS_X);
					} else if(X < prevX) {
						movement.add(PathChoices.RIGHT);
						Robot1Heading.setHeading(Headings.MINUS_X);
					}
				}	
			} else if(Robot1Heading.getHeading() == Headings.MINUS_X) {
				if(X == prevX) {	
					if(Y > prevY) {
						movement.add(PathChoices.RIGHT);
						Robot1Heading.setHeading(Headings.PLUS_Y);
					} else if(Y < prevY) {
						movement.add(PathChoices.LEFT);
						Robot1Heading.setHeading(Headings.MINUS_Y);
					}
				} else if(Y == prevY) {
						if(X > prevX) {
							movement.add(PathChoices.ROTATE);
							Robot1Heading.setHeading(Headings.PLUS_X);
						} else if(X < prevX) {
							movement.add(PathChoices.FORWARD);
							Robot1Heading.setHeading(Headings.MINUS_X);
						}
				}		
			}
			prevX = X;
			prevY = Y;
			
			logger.trace("Current Position: " + "(" + prevX + "," + prevY + ")" );
			logger.trace("Current Heading: " + Robot1Heading.getHeading());
		}
		
		
		return movement;
	}
	
	
}
