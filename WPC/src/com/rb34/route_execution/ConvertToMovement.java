package com.rb34.route_execution;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
			
			if(readHead().equals("N")) {
				if(X == prevX) {
					if( Y > prevY) {
						movement.add(PathChoices.FORWARD);
						//Robot1Heading.setHeading(Headings.PLUS_Y);
						writeHead("N");
					} else if(Y < prevY) {
						movement.add(PathChoices.ROTATE);
						//Robot1Heading.setHeading(Headings.MINUS_Y);
						writeHead("S");
					}	
				} else if (Y == prevY) {
					if(X > prevX) {
						movement.add(PathChoices.RIGHT);
						//Robot1Heading.setHeading(Headings.PLUS_X);
						writeHead("E");
					} else if(X < prevX) {
						movement.add(PathChoices.LEFT);
						//Robot1Heading.setHeading(Headings.MINUS_X);
						writeHead("W");
					}
				}		
			} else if(readHead().equals("E")) {
				if(X == prevX){
					if(Y > prevY) {
						movement.add(PathChoices.LEFT);
						//Robot1Heading.setHeading(Headings.PLUS_Y);
						writeHead("N");
					} else if(Y < prevY) {
						movement.add(PathChoices.RIGHT);
						//Robot1Heading.setHeading(Headings.MINUS_Y);
						writeHead("S");
					}	
				} else if(Y == prevY) {
					if(X > prevX) {
						movement.add(PathChoices.FORWARD);
						//Robot1Heading.setHeading(Headings.PLUS_X);
						writeHead("E");
					} else if(X < prevX) {
						movement.add(PathChoices.ROTATE);					
						//Robot1Heading.setHeading(Headings.MINUS_X);
						writeHead("W");
					}
				}		
			} else if(readHead().equals("S")) {
				if(X == prevX) {
					if(Y > prevY) {
						movement.add(PathChoices.ROTATE);
						//Robot1Heading.setHeading(Headings.PLUS_Y);
						writeHead("N");
					} else if(Y < prevY){
						movement.add(PathChoices.FORWARD);
						//Robot1Heading.setHeading(Headings.MINUS_Y);
						writeHead("S");
					} 
				} else if(Y == prevY) {
					if(X > prevX) {
						movement.add(PathChoices.LEFT);
						//Robot1Heading.setHeading(Headings.PLUS_X);
						writeHead("E");
					} else if(X < prevX) {
						movement.add(PathChoices.RIGHT);
						//Robot1Heading.setHeading(Headings.MINUS_X);
						writeHead("W");
					}
				}	
			} else if(readHead().equals("W")) {
				if(X == prevX) {	
					if(Y > prevY) {
						movement.add(PathChoices.RIGHT);
						//Robot1Heading.setHeading(Headings.PLUS_Y);
						writeHead("N");
					} else if(Y < prevY) {
						movement.add(PathChoices.LEFT);
						//Robot1Heading.setHeading(Headings.MINUS_Y);
						writeHead("S");
					}
				} else if(Y == prevY) {
						if(X > prevX) {
							movement.add(PathChoices.ROTATE);
							//Robot1Heading.setHeading(Headings.PLUS_X);
							writeHead("E");
						} else if(X < prevX) {
							movement.add(PathChoices.FORWARD);
							//Robot1Heading.setHeading(Headings.MINUS_X);
							writeHead("W");
						}
				}		
			}
			prevX = X;
			prevY = Y;
			
			logger.trace("Current Position: " + "(" + prevX + "," + prevY + ")" );
			logger.trace("Current Heading: " + Robot1Heading.getHeading());
		}
		
		readHead();

		return movement;
	}
	
	public String readHead(){
		
			String line = "";
			if(ID==0) {
				try {
					line = Files.readAllLines(Paths.get("src/com/rb34/route_execution/heading.txt")).get(1);
				} catch (IOException e) {
					logger.error("Failed to Read from file: heading.txt");
					e.printStackTrace();
				}
			} else if(ID==1) {
				try {
					line = Files.readAllLines(Paths.get("src/com/rb34/route_execution/heading.txt")).get(3);
				} catch (IOException e) {
					logger.error("Failed to Read from file: heading.txt");
					e.printStackTrace();
				}
			} else if(ID==2) {
				try {
					line = Files.readAllLines(Paths.get("src/com/rb34/route_execution/heading.txt")).get(5);
				} catch (IOException e) {
					logger.error("Failed to Read from file: heading.txt");
					e.printStackTrace();
				}
			}
			
			return line;
	}
	
	public void writeHead(String head){
		
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get("src/com/rb34/route_execution/heading.txt"));
		} catch (IOException e) {
			logger.error("Failed to Read from file: heading.txt");
			e.printStackTrace();
		}
		
		if(ID==0) {
			lines.set(1, head);
		} else if(ID==1) {
			lines.set(3, head);
		} else if(ID==2) {
			lines.set(5, head);
		}
		try {
			Files.write(Paths.get("src/com/rb34/route_execution/heading.txt"), lines);
		} catch (IOException e) {
			logger.error("Failed to Write to file: heading.txt");
			e.printStackTrace();
		}
	}
}
