package com.rb34.route_execution;


import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.rb34.general.PathChoices;
import com.rb34.general.Robot;
import com.rb34.route_planning.graph_entities.IVertex;


public class ConvertToMovement {
		
	final static Logger logger = Logger.getLogger(ConvertToMovement.class);
	int ID;
	
	public ArrayList<PathChoices> execute(ArrayList<IVertex> path, Robot robot) {
		
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
			
			if(robot.getHeading().equals("N")) {
				if(X == prevX) {
					if( Y > prevY) {
						movement.add(PathChoices.FORWARD);
						robot.setHeading("N");
					} else if(Y < prevY) {
						movement.add(PathChoices.ROTATE);
						robot.setHeading("S");
					}	
				} else if (Y == prevY) {
					if(X > prevX) {
						movement.add(PathChoices.RIGHT);
						robot.setHeading("E");
					} else if(X < prevX) {
						movement.add(PathChoices.LEFT);
						robot.setHeading("W");
					}
				}		
			} else if(robot.getHeading().equals("E")) {
				if(X == prevX){
					if(Y > prevY) {
						movement.add(PathChoices.LEFT);
						robot.setHeading("N");
					} else if(Y < prevY) {
						movement.add(PathChoices.RIGHT);
						robot.setHeading("S");
					}	
				} else if(Y == prevY) {
					if(X > prevX) {
						movement.add(PathChoices.FORWARD);
						robot.setHeading("E");
					} else if(X < prevX) {
						movement.add(PathChoices.ROTATE);					
						robot.setHeading("W");
					}
				}		
			} else if(robot.getHeading().equals("S")) {
				if(X == prevX) {
					if(Y > prevY) {
						movement.add(PathChoices.ROTATE);
						robot.setHeading("N");	
					} else if(Y < prevY){
						movement.add(PathChoices.FORWARD);
						robot.setHeading("S");
					} 
				} else if(Y == prevY) {
					if(X > prevX) {
						movement.add(PathChoices.LEFT);
						robot.setHeading("E");
					} else if(X < prevX) {
						movement.add(PathChoices.RIGHT);
						robot.setHeading("W");
					}
				}	
			} else if(robot.getHeading().equals("W")) {
				if(X == prevX) {	
					if(Y > prevY) {
						movement.add(PathChoices.RIGHT);
						robot.setHeading("N");
					} else if(Y < prevY) {
						movement.add(PathChoices.LEFT);
						robot.setHeading("S");
					}
					
				} 
			} else if(ID==1) {
				if(RobotHeadings.getHeading2() == Headings.PLUS_Y) {
					if(X == prevX) {
						if( Y > prevY) {
							movement.add(PathChoices.FORWARD);
							RobotHeadings.setHeading2(Headings.PLUS_Y);
						} else if(Y < prevY) {
							movement.add(PathChoices.ROTATE);
							RobotHeadings.setHeading2(Headings.MINUS_Y);
						}	
					} else if (Y == prevY) {
						if(X > prevX) {
							movement.add(PathChoices.RIGHT);
							RobotHeadings.setHeading2(Headings.PLUS_X);
						} else if(X < prevX) {
							movement.add(PathChoices.LEFT);
							RobotHeadings.setHeading2(Headings.MINUS_X);
						}
					} else if(X == prevX && Y == prevY)	{
						movement.add(PathChoices.STAY);
					}		
				} else if(RobotHeadings.getHeading2() == Headings.PLUS_X) {
					if(X == prevX){
						if(Y > prevY) {
							movement.add(PathChoices.LEFT);
							RobotHeadings.setHeading2(Headings.PLUS_Y);
						} else if(Y < prevY) {
							movement.add(PathChoices.RIGHT);
							RobotHeadings.setHeading2(Headings.MINUS_Y);
						}	
					} else if(Y == prevY) {
						if(X > prevX) {
							movement.add(PathChoices.FORWARD);
							RobotHeadings.setHeading2(Headings.PLUS_X);
						} else if(X < prevX) {
							movement.add(PathChoices.ROTATE);					
							RobotHeadings.setHeading2(Headings.MINUS_X);
						}
					} else if(X == prevX && Y == prevY)	{
						movement.add(PathChoices.STAY);
					}		
				} else if(RobotHeadings.getHeading2() == Headings.MINUS_Y) {
					if(X == prevX) {
						if(Y > prevY) {
							movement.add(PathChoices.ROTATE);
							robot.setHeading("E");
						} else if(X < prevX) {
							movement.add(PathChoices.ROTATE);					
							RobotHeadings.setHeading3(Headings.MINUS_X);
						}
					} else if(X == prevX && Y == prevY)	{
						movement.add(PathChoices.STAY);
					}		
				} else if(RobotHeadings.getHeading3() == Headings.MINUS_Y) {
					if(X == prevX) {
						if(Y > prevY) {
							movement.add(PathChoices.ROTATE);
							RobotHeadings.setHeading3(Headings.PLUS_Y);
						} else if(Y < prevY){
							movement.add(PathChoices.FORWARD);
							robot.setHeading("W");
						}
					} else if(Y == prevY) {
							if(X > prevX) {
								movement.add(PathChoices.ROTATE);
								RobotHeadings.setHeading3(Headings.PLUS_X);
							} else if(X < prevX) {
								movement.add(PathChoices.FORWARD);
								RobotHeadings.setHeading3(Headings.MINUS_X);
							}
					} else if(X == prevX && Y == prevY)	{
						movement.add(PathChoices.STAY);
					}
					
				} 
			}		
			prevX = X;
			prevY = Y;
			
			logger.trace("Current Position: " + "(" + prevX + "," + prevY + ")" );
			logger.trace("Current Heading: " + robot.getHeading());
		}
		
		
		
		return movement;
	}

}