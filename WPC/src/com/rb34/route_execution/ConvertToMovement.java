package com.rb34.route_execution;


import java.util.ArrayList;
import org.apache.log4j.*;

import com.rb34.general.PathChoices;
import com.rb34.route_planning.graph_entities.IVertex;


public class ConvertToMovement {
		
	final static Logger logger = Logger.getLogger(ConvertToMovement.class);
	int ID;
	
	public ArrayList<PathChoices> execute(ArrayList<IVertex> path, int robotId) {
		ID = robotId;
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
			
			if(ID==0) {
				if(RobotHeadings.getHeading1() == Headings.PLUS_Y) {
					if(X == prevX) {
						if( Y > prevY) {
							movement.add(PathChoices.FORWARD);
							RobotHeadings.setHeading1(Headings.PLUS_Y);
						} else if(Y < prevY) {
							movement.add(PathChoices.ROTATE);
							RobotHeadings.setHeading1(Headings.MINUS_Y);
						} 
					} else if (Y == prevY) {
						if(X > prevX) {
							movement.add(PathChoices.RIGHT);
							RobotHeadings.setHeading1(Headings.PLUS_X);
						} else if(X < prevX) {
							movement.add(PathChoices.LEFT);
							RobotHeadings.setHeading1(Headings.MINUS_X);
						}
					} else if(X == prevX && Y == prevY)	{
						movement.add(PathChoices.STAY);
					}
				} else if(RobotHeadings.getHeading1() == Headings.PLUS_X) {
					if(X == prevX){
						if(Y > prevY) {
							movement.add(PathChoices.LEFT);
							RobotHeadings.setHeading1(Headings.PLUS_Y);
						} else if(Y < prevY) {
							movement.add(PathChoices.RIGHT);
							RobotHeadings.setHeading1(Headings.MINUS_Y);
						}	
					} else if(Y == prevY) {
						if(X > prevX) {
							movement.add(PathChoices.FORWARD);
							RobotHeadings.setHeading1(Headings.PLUS_X);
						} else if(X < prevX) {
							movement.add(PathChoices.ROTATE);					
							RobotHeadings.setHeading1(Headings.MINUS_X);
						}
					} else if(X == prevX && Y == prevY)	{
						movement.add(PathChoices.STAY);
					}		
				} else if(RobotHeadings.getHeading1() == Headings.MINUS_Y) {
					if(X == prevX) {
						if(Y > prevY) {
							movement.add(PathChoices.ROTATE);
							RobotHeadings.setHeading1(Headings.PLUS_Y);
						} else if(Y < prevY){
							movement.add(PathChoices.FORWARD);
							RobotHeadings.setHeading1(Headings.MINUS_Y);
						} 
					} else if(Y == prevY) {
						if(X > prevX) {
							movement.add(PathChoices.LEFT);
							RobotHeadings.setHeading1(Headings.PLUS_X);
						} else if(X < prevX) {
							movement.add(PathChoices.RIGHT);
							RobotHeadings.setHeading1(Headings.MINUS_X);
						}
					} else if(X == prevX && Y == prevY)	{
						movement.add(PathChoices.STAY);
					}	
				} else if(RobotHeadings.getHeading1() == Headings.MINUS_X) {
					if(X == prevX) {	
						if(Y > prevY) {
							movement.add(PathChoices.RIGHT);
							RobotHeadings.setHeading1(Headings.PLUS_Y);
						} else if(Y < prevY) {
							movement.add(PathChoices.LEFT);
							RobotHeadings.setHeading1(Headings.MINUS_Y);
						}
					} else if(Y == prevY) {
						if(X > prevX) {
							movement.add(PathChoices.ROTATE);
							RobotHeadings.setHeading1(Headings.PLUS_X);
						} else if(X < prevX) {
							movement.add(PathChoices.FORWARD);
							RobotHeadings.setHeading1(Headings.MINUS_X);
						}
					} else if(X == prevX && Y == prevY)	{
						movement.add(PathChoices.STAY);
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
							RobotHeadings.setHeading2(Headings.PLUS_Y);
						} else if(Y < prevY){
							movement.add(PathChoices.FORWARD);
							RobotHeadings.setHeading2(Headings.MINUS_Y);
						} 
					} else if(Y == prevY) {
						if(X > prevX) {
							movement.add(PathChoices.LEFT);
							RobotHeadings.setHeading2(Headings.PLUS_X);
						} else if(X < prevX) {
							movement.add(PathChoices.RIGHT);
							RobotHeadings.setHeading2(Headings.MINUS_X);
						}
					} else if(X == prevX && Y == prevY)	{
						movement.add(PathChoices.STAY);
					}	
				} else if(RobotHeadings.getHeading2() == Headings.MINUS_X) {
					if(X == prevX) {	
						if(Y > prevY) {
							movement.add(PathChoices.RIGHT);
							RobotHeadings.setHeading2(Headings.PLUS_Y);
						} else if(Y < prevY) {
							movement.add(PathChoices.LEFT);
							RobotHeadings.setHeading2(Headings.MINUS_Y);
						}
					} else if(Y == prevY) {
							if(X > prevX) {
								movement.add(PathChoices.ROTATE);
								RobotHeadings.setHeading2(Headings.PLUS_X);
							} else if(X < prevX) {
								movement.add(PathChoices.FORWARD);
								RobotHeadings.setHeading2(Headings.MINUS_X);
							}
					} else if(X == prevX && Y == prevY)	{
						movement.add(PathChoices.STAY);
					}
					
				} 
			} else if(ID==2) {
				if(RobotHeadings.getHeading3() == Headings.PLUS_Y) {
					if(X == prevX) {
						if( Y > prevY) {
							movement.add(PathChoices.FORWARD);
							RobotHeadings.setHeading3(Headings.PLUS_Y);
						} else if(Y < prevY) {
							movement.add(PathChoices.ROTATE);
							RobotHeadings.setHeading3(Headings.MINUS_Y);
						}	
					} else if (Y == prevY) {
						if(X > prevX) {
							movement.add(PathChoices.RIGHT);
							RobotHeadings.setHeading3(Headings.PLUS_X);
						} else if(X < prevX) {
							movement.add(PathChoices.LEFT);
							RobotHeadings.setHeading3(Headings.MINUS_X);
						}
					} else if(X == prevX && Y == prevY)	{
						movement.add(PathChoices.STAY);
					}		
				} else if(RobotHeadings.getHeading3() == Headings.PLUS_X) {
					if(X == prevX){
						if(Y > prevY) {
							movement.add(PathChoices.LEFT);
							RobotHeadings.setHeading3(Headings.PLUS_Y);
						} else if(Y < prevY) {
							movement.add(PathChoices.RIGHT);
							RobotHeadings.setHeading3(Headings.MINUS_Y);
						}	
					} else if(Y == prevY) {
						if(X > prevX) {
							movement.add(PathChoices.FORWARD);
							RobotHeadings.setHeading3(Headings.PLUS_X);
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
							RobotHeadings.setHeading3(Headings.MINUS_Y);
						} 
					} else if(Y == prevY) {
						if(X > prevX) {
							movement.add(PathChoices.LEFT);
							RobotHeadings.setHeading3(Headings.PLUS_X);
						} else if(X < prevX) {
							movement.add(PathChoices.RIGHT);
							RobotHeadings.setHeading3(Headings.MINUS_X);
						}
					} else if(X == prevX && Y == prevY)	{
						movement.add(PathChoices.STAY);
					}	
				} else if(RobotHeadings.getHeading3() == Headings.MINUS_X) {
					if(X == prevX) {	
						if(Y > prevY) {
							movement.add(PathChoices.RIGHT);
							RobotHeadings.setHeading3(Headings.PLUS_Y);
						} else if(Y < prevY) {
							movement.add(PathChoices.LEFT);
							RobotHeadings.setHeading3(Headings.MINUS_Y);
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
			
		}
		
		if(ID==0) {
			logger.info(" Robot 1 Final Heading: " + RobotHeadings.getHeading1());
		} else if(ID==1) {
			logger.info("Robot 2 Final Heading: " + RobotHeadings.getHeading2());
		} else if(ID==2) {
			logger.info("Robot 3 Final Heading: " + RobotHeadings.getHeading3());
		}
		
		
		
		return movement;
	}

}