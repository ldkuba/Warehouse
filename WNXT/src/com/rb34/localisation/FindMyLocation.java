package localisation;

import java.util.ArrayList;
import lejos.nxt.LightSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;
import rp.robotics.mapping.GridMap;
import rp.util.HashMap;

public class FindMyLocation {
	
	// Returns robot's location
	public static Result myLocation (GridMap gridMap, DifferentialPilot pilot, LightSensor rightSensor, LightSensor leftSensor, UltrasonicSensor ranger) {
		
		// Should not return -1, -1
		int x = -1, y = -1;
		
		// A value from the floor (white)
		int referenceValue = getReferenceValue (rightSensor, leftSensor);
		int error = 7;
		float pilotSpeed = 0.1f;
		
		// Sets travel speed
		pilot.setTravelSpeed(pilotSpeed);

		// Scanning, putting values into an array, comparing
		
		
		
		boolean foundLocation = false;
		
		while (!foundLocation) {
			
			
		}
		// Should not return -1, -1 if it returns -1, -1 something is wrong.
		return new Result (x, y);
	}

	static String putHashMapToScreen (GridMap gridMap, HashMap <Result, Integer> hashMap) {
		
		String a = "";
		for (int i = 0; i < gridMap.getXSize(); i++) {
			for (int j = 0; j < gridMap.getYSize(); j++) {
				a += i + " " + j + " " + hashMap.get(new Result (i, j)) + "\n";
			}
		}
		return a;
	}
	
	static void movesForward (GridMap gridMap, HashMap <Result, Integer> hashMap, ArrayList <Result> arrayList) {
		
	}
	
	static HashMap <Result, Float> fillHashMap (GridMap gridMap, ArrayList <Result> arrayList) {
		
		HashMap <Result, Float> hashMap = new HashMap <Result, Float> ();
		
		for (int i = 0; i < gridMap.getXSize(); i++) {
			for (int j = 0; j < gridMap.getYSize(); j++) {
				Result result = new Result (i, j);
				if (!gridMap.isObstructed(i, j)) {
					arrayList.add(result);
					hashMap.put(result, 1 / (float) possibleLocations (gridMap));
				}
			}
		}
		return hashMap;
	}
	
	static int getArrayPositionByLocation (int x, int y, ArrayList <Result> arrayList, GridMap gridMap) {
		
		for (int i = 0; i < arrayList.size(); i++) {
			if (arrayList.get(i).getX() == x && arrayList.get(i).getY() == y) {
				return i;
			}
		}
		
		// If returns -1, position was not found or it is a wall
		return -1;
	}
	
	static HashMap <Result, Float> updateHashMap (int x, int y, float probability, HashMap <Result, Float> hashMap, ArrayList <Result> arrayList, GridMap gridMap) {
		
		int arrayPosition = getArrayPositionByLocation (x, y, arrayList, gridMap);
		
		if (!hashMap.containsKey(arrayList.get(arrayPosition))) {
			if (probability == 0) {
				
				hashMap.remove(arrayPosition);
				
				if (arrayPosition != -1) {
					arrayList.remove(getArrayPositionByLocation (x, y, arrayList, gridMap));
				}
				
			} else {
				
				hashMap.remove(arrayList.get(arrayPosition));
				if (getArrayPositionByLocation (x, y, arrayList, gridMap) != -1) {
					arrayList.remove(getArrayPositionByLocation (x, y, arrayList, gridMap));
				}
				
				Result result = new Result (x, y);
				
				hashMap.put (result, probability);
				arrayList.add (result);
			}
			
		} else {
			
			hashMap.put(new Result (x, y), probability);
		}
		
		return hashMap;
	}
	
	// Checks ground's colour value
	static int getReferenceValue (LightSensor rightSensor, LightSensor leftSensor) {
		
		Delay.msDelay(250);
		int rightValue = rightSensor.readValue();
		int leftValue = leftSensor.readValue();
		Delay.msDelay(250);
		// average the values returned by both sensors
		return (rightValue + leftValue) / 2;
	}
	
	// Possible locations for a robot to be in (needs fixing)
	static int possibleLocations (GridMap gridMap) {
		
		int locations = 0;
		
		for (int i = 0; i < gridMap.getXSize(); i++) {
			for (int j = 0; j < gridMap.getYSize(); j++) {
				if (!gridMap.isObstructed(i, j)) {
					locations++;
				}
			}
		}
		return locations;
	}
	
	// Puts x, y coordinates, north south east west distance to walls
	public ArrayList<DistanceFromJunction> distanceFromJunction (GridMap gridMap) {

		ArrayList<DistanceFromJunction> arrayList = new ArrayList<DistanceFromJunction> ();
		float PLUS_X = 1, PLUS_Y = 1, MINUS_X = 1, MINUS_Y = 1;
		// 18 43 72 100 136
		// 1  2  3  4   5
		for (int x = 0; x < gridMap.getXSize (); x++) {
			for (int y = 0; y < gridMap.getYSize (); y++) {
				for (int z = 1; z < 5; z++) {
					if (gridMap.isObstructed(x, y)) {
						arrayList.add (new DistanceFromJunction (x, y, 0, 0, 0, 0));
						z = 0;
						break;
					} else  {
						if (z + x < gridMap.getXSize() && !gridMap.isObstructed(x + 1, y) && gridMap.isObstructed(x + 2, y)) {
							PLUS_X = 2;
						} else if (z + x < gridMap.getXSize () && !gridMap.isObstructed(x + 1, y)) {
							switch (z) {
								case 1: PLUS_X = 2;
									break;
								case 2: PLUS_X = 3;
									break;
								case 3: PLUS_X = 4;
									break;
								case 4: PLUS_X = 5;
									break;
							}	
						}
						if (x - z >= 0 && !gridMap.isObstructed(x - 1, y) && gridMap.isObstructed(x - 2, y)) {
							MINUS_X = 2;
						} else if (x - z >= 0 && !gridMap.isObstructed (x - 1, y)) {
							switch (z) {
								case 1: MINUS_X = 2;
									break;
								case 2: MINUS_X = 3;
									break;
								case 3: MINUS_X = 4;
									break;
								case 4: MINUS_X = 5;
									break;
							}
						} 
						
						if (z + y < gridMap.getYSize() && !gridMap.isObstructed(x, y + 1) && gridMap.isObstructed(x, y + 2)) {
							PLUS_Y = 2;
						} else if (z + y < gridMap.getYSize () && !gridMap.isObstructed(x, y + 1)) {
							switch (z) {
							case 1: PLUS_Y = 2;
								break;
							case 2: PLUS_Y = 3;
								break;
							case 3: PLUS_Y = 4;
								break;
							case 4: PLUS_Y = 5;
								break;
							}
						}
						if (y - z >= 0 && !gridMap.isObstructed(x, y - 1) && gridMap.isObstructed(x, y - 2)) {
							MINUS_Y = 2;
						} else if (y - z >= 0 && !gridMap.isObstructed(x, y - 1)) {
							switch (z) {
							case 1: MINUS_Y = 2;
								break;
							case 2: MINUS_Y = 3;
								break;
							case 3: MINUS_Y = 4;
								break;
							case 4: MINUS_Y = 5;
								break;
							}
						}	
					}
				}
				
				if (!gridMap.isObstructed(x, y)) {
					arrayList.add (new DistanceFromJunction (x, y, PLUS_X, MINUS_X, PLUS_Y, MINUS_Y)); 
				}
				PLUS_X = 1;
				PLUS_Y = 1;
				MINUS_Y = 1;
				MINUS_X = 1;
			}
		}
		return arrayList;
	}
	
	// Returns x if a wall, o if not a wall, x, y coordinates, north south east west distances to walls
	public String putDistancesToAString (GridMap gridMap) {
		String representation = "";
		ArrayList <DistanceFromJunction> distanceArray = distanceFromJunction (gridMap);
		int locations = 0;
		
		for (int x = 0; x < gridMap.getXSize(); x++) {
			for (int y = 0; y < gridMap.getYSize(); y++) {
				if (gridMap.isObstructed(x, y)) {
					representation += " x " + "\n";
					locations++;
				} else {
					representation += " o " + distanceArray.get (locations).putToString() + "\n";
					locations++;
				}
			}
		}
		return representation;
	}
	
	public void putDistancesToTheScreen (GridMap gridMap) {
		System.out.print (putDistancesToAString(gridMap));
	}
	
	// Returns x if a wall, o if not a wall
	public String putGridMapToTheString (GridMap gridMap) {
		String representation = "";
		for (int i = gridMap.getYSize() - 1; i >= 0; i--) {
			for (int j = gridMap.getXSize() - 1; j >= 0; j--) {
				
				if (gridMap.isObstructed(j, i)) {
					representation += "x";
				} else {
					representation += "o";
				}
				
				if (j == 0) {
					representation += "\n";
				}
			}
		}
		return representation;
	}
	
	public void putToTheScreen (GridMap gridMap) {
		System.out.print (putGridMapToTheString (gridMap));
	}
	
	// Returns an arrayList with positions (and distances) where a robot can be
	public ArrayList <DistanceFromJunction> compareDistances (float PLUS_X, float MINUS_X, float PLUS_Y, float MINUS_Y, GridMap gridMap) {
		ArrayList <DistanceFromJunction> arrayList = new ArrayList <DistanceFromJunction> ();
		arrayList = distanceFromJunction (gridMap);
		ArrayList <DistanceFromJunction> coordinates = new ArrayList <DistanceFromJunction> ();
		int size = arrayList.size();
		boolean ifMatches = false;
		
		for (int i = 0; i < size; i++) {
			if (!gridMap.isObstructed(arrayList.get(i).getX(), arrayList.get(i).getY())) {
				if (arrayList.get(i).getPlusX()  == PLUS_X  && arrayList.get(i).getMinusX() == MINUS_X && arrayList.get(i).getPlusY() == PLUS_Y && arrayList.get(i).getMinusY() == MINUS_Y) {
					ifMatches = true;
					coordinates.add(new DistanceFromJunction (arrayList.get(i).getX(), arrayList.get(i).getY(), arrayList.get(i).getPlusX(), arrayList.get(i).getMinusX(), arrayList.get(i).getPlusY(), arrayList.get(i).getMinusY()));
				} else if (arrayList.get(i).getPlusX()  == MINUS_X  && arrayList.get(i).getMinusX() == PLUS_X && arrayList.get(i).getPlusY() == MINUS_Y && arrayList.get(i).getMinusY() == PLUS_Y) {
					coordinates.add(new DistanceFromJunction (arrayList.get(i).getX(), arrayList.get(i).getY(), arrayList.get(i).getPlusX(), arrayList.get(i).getMinusX(), arrayList.get(i).getPlusY(), arrayList.get(i).getMinusY()));
					ifMatches = true;
				} else if (arrayList.get(i).getPlusX()  == MINUS_Y  && arrayList.get(i).getMinusX() == PLUS_Y && arrayList.get(i).getPlusY() == PLUS_X && arrayList.get(i).getMinusY() == MINUS_X) {
					coordinates.add(new DistanceFromJunction (arrayList.get(i).getX(), arrayList.get(i).getY(), arrayList.get(i).getPlusX(), arrayList.get(i).getMinusX(), arrayList.get(i).getPlusY(), arrayList.get(i).getMinusY()));					
					ifMatches = true;
				} else if (arrayList.get(i).getPlusX()  == PLUS_Y  && arrayList.get(i).getMinusX() == MINUS_Y && arrayList.get(i).getPlusY() == MINUS_X && arrayList.get(i).getMinusY() == PLUS_X) {
					coordinates.add(new DistanceFromJunction (arrayList.get(i).getX(), arrayList.get(i).getY(), arrayList.get(i).getPlusX(), arrayList.get(i).getMinusX(), arrayList.get(i).getPlusY(), arrayList.get(i).getMinusY()));					
					ifMatches = true;
				}
			}
			
			if (ifMatches) {
				ifMatches = false;
			}
		}
		
		return coordinates;
	}
	
	// Returns how many positions are similar to the one you are standing on right now
	public int compareDistancesInt (float PLUS_X, float MINUS_X, float PLUS_Y, float MINUS_Y, GridMap gridMap) {
		ArrayList <DistanceFromJunction> arrayList = new ArrayList <DistanceFromJunction> ();
		arrayList = distanceFromJunction (gridMap);
		ArrayList <DistanceFromJunction> coordinates = new ArrayList <DistanceFromJunction> ();
		int size = arrayList.size();
		boolean ifMatches = false;
		int matches = 0;
		
		for (int i = 0; i < size; i++) {
			if (!gridMap.isObstructed(arrayList.get(i).getX(), arrayList.get(i).getY())) {
				if (arrayList.get(i).getPlusX()  == PLUS_X  && arrayList.get(i).getMinusX() == MINUS_X && arrayList.get(i).getPlusY() == PLUS_Y && arrayList.get(i).getMinusY() == MINUS_Y) {
					ifMatches = true;
					coordinates.add(new DistanceFromJunction (arrayList.get(i).getX(), arrayList.get(i).getY(), arrayList.get(i).getPlusX(), arrayList.get(i).getMinusX(), arrayList.get(i).getPlusY(), arrayList.get(i).getMinusY()));
				} else if (arrayList.get(i).getPlusX()  == MINUS_X  && arrayList.get(i).getMinusX() == PLUS_X && arrayList.get(i).getPlusY() == MINUS_Y && arrayList.get(i).getMinusY() == PLUS_Y) {
					coordinates.add(new DistanceFromJunction (arrayList.get(i).getX(), arrayList.get(i).getY(), arrayList.get(i).getPlusX(), arrayList.get(i).getMinusX(), arrayList.get(i).getPlusY(), arrayList.get(i).getMinusY()));
					ifMatches = true;
				} else if (arrayList.get(i).getPlusX()  == MINUS_Y  && arrayList.get(i).getMinusX() == PLUS_Y && arrayList.get(i).getPlusY() == PLUS_X && arrayList.get(i).getMinusY() == MINUS_X) {
					coordinates.add(new DistanceFromJunction (arrayList.get(i).getX(), arrayList.get(i).getY(), arrayList.get(i).getPlusX(), arrayList.get(i).getMinusX(), arrayList.get(i).getPlusY(), arrayList.get(i).getMinusY()));					
					ifMatches = true;
				} else if (arrayList.get(i).getPlusX()  == PLUS_Y  && arrayList.get(i).getMinusX() == MINUS_Y && arrayList.get(i).getPlusY() == MINUS_X && arrayList.get(i).getMinusY() == PLUS_X) {
					coordinates.add(new DistanceFromJunction (arrayList.get(i).getX(), arrayList.get(i).getY(), arrayList.get(i).getPlusX(), arrayList.get(i).getMinusX(), arrayList.get(i).getPlusY(), arrayList.get(i).getMinusY()));					
					ifMatches = true;
				}
			}
			
			if (ifMatches) {
				ifMatches = false;
				matches++;
			}
		}
		return matches;
	}
	
	// Puts to the screen all locations with a number of similar locations
	public void putPossiblePositionsToTheScreen (GridMap gridMap) {

		ArrayList <DistanceFromJunction> arrayList = new ArrayList <DistanceFromJunction> ();
		arrayList = distanceFromJunction (gridMap);
		
		int arraySize = arrayList.size();
		
		for (int i = 0; i < arraySize; i++) {
			if (gridMap.isObstructed(arrayList.get(i).getX(), arrayList.get(i).getY())) {
				System.out.println (arrayList.get(i).getX() + ":" + arrayList.get(i).getY() + " " + " x");
			} else {
				System.out.println (arrayList.get(i).getX() + ":" + arrayList.get(i).getY() + " " + compareDistancesInt (arrayList.get(i).getPlusX(), arrayList.get(i).getMinusX(), arrayList.get(i).getPlusY(), arrayList.get(i).getMinusY(), gridMap) + " " + ifPossibleToDetermineXYAxis(arrayList.get(i).getPlusX(), arrayList.get(i).getMinusX(), arrayList.get(i).getPlusY(), arrayList.get(i).getMinusY(), gridMap ));
			}
		}
	}
	
	public ArrayList <DistanceFromJunction> locationsForThePosition (float PLUS_X, float MINUS_X, float PLUS_Y, float MINUS_Y, GridMap gridMap) {
		ArrayList <DistanceFromJunction> arrayList = new ArrayList <DistanceFromJunction> ();
		arrayList = distanceFromJunction (gridMap);
		ArrayList <DistanceFromJunction> arrayAnswer = new ArrayList <DistanceFromJunction> ();
		boolean ifMatches = false;
		
		for (int i = 0; i < gridMap.getXSize() * gridMap.getYSize(); i++) {
			if (!gridMap.isObstructed(arrayList.get(i).getX(), arrayList.get(i).getY())) {
				if (arrayList.get(i).getPlusX()  == PLUS_X  && arrayList.get(i).getMinusX() == MINUS_X && arrayList.get(i).getPlusY() == PLUS_Y && arrayList.get(i).getMinusY() == MINUS_Y) {
					ifMatches = true;
					arrayAnswer.add(new DistanceFromJunction (arrayList.get(i).getX(), arrayList.get(i).getY(), arrayList.get(i).getPlusX(), arrayList.get(i).getMinusX(), arrayList.get(i).getPlusY(), arrayList.get(i).getMinusY()));
				} else if (arrayList.get(i).getPlusX()  == MINUS_X  && arrayList.get(i).getMinusX() == PLUS_X && arrayList.get(i).getPlusY() == MINUS_Y && arrayList.get(i).getMinusY() == PLUS_Y) {
					arrayAnswer.add(new DistanceFromJunction (arrayList.get(i).getX(), arrayList.get(i).getY(), arrayList.get(i).getPlusX(), arrayList.get(i).getMinusX(), arrayList.get(i).getPlusY(), arrayList.get(i).getMinusY()));
					ifMatches = true;
				} else if (arrayList.get(i).getPlusX()  == MINUS_Y  && arrayList.get(i).getMinusX() == PLUS_Y && arrayList.get(i).getPlusY() == PLUS_X && arrayList.get(i).getMinusY() == MINUS_X) {
					arrayAnswer.add(new DistanceFromJunction (arrayList.get(i).getX(), arrayList.get(i).getY(), arrayList.get(i).getPlusX(), arrayList.get(i).getMinusX(), arrayList.get(i).getPlusY(), arrayList.get(i).getMinusY()));					
					ifMatches = true;
				} else if (arrayList.get(i).getPlusX()  == PLUS_Y  && arrayList.get(i).getMinusX() == MINUS_Y && arrayList.get(i).getPlusY() == MINUS_X && arrayList.get(i).getMinusY() == PLUS_X) {
					arrayAnswer.add(new DistanceFromJunction (arrayList.get(i).getX(), arrayList.get(i).getY(), arrayList.get(i).getPlusX(), arrayList.get(i).getMinusX(), arrayList.get(i).getPlusY(), arrayList.get(i).getMinusY()));					
					ifMatches = true;
				}
			}
			
			if (ifMatches) {
				ifMatches = false;
			}
		}
		
		return arrayAnswer;
		
	}
	
	public boolean isPositionSimilar (int x, int y,float PLUS_X, float MINUS_X, float PLUS_Y, float MINUS_Y, GridMap gridMap) {
		return false;
	}
	
	public ArrayList <DistanceFromJunction> coordinatesToDistances (int x, int y, GridMap gridMap) {
		
		ArrayList <DistanceFromJunction> arrayList = new ArrayList <DistanceFromJunction> ();
		
		for (int i = 0; i < distanceFromJunction(gridMap).size(); i++) {
			if (x == distanceFromJunction(gridMap).get(i).getX() && y == distanceFromJunction(gridMap).get(i).getY()) {
				arrayList.add(new DistanceFromJunction (x, y, distanceFromJunction (gridMap).get(i).getPlusX(), distanceFromJunction (gridMap).get(i).getMinusX(), distanceFromJunction (gridMap).get(i).getPlusY(), distanceFromJunction (gridMap).get(i).getMinusY()));
			}
		}
		
		return arrayList;
	}
	
	public boolean ifPossibleToDetermineXYAxis (float PLUS_X, float MINUS_X, float PLUS_Y, float MINUS_Y, GridMap gridMap) {
		
		switch (levelOfDifficulty (PLUS_X, MINUS_X, PLUS_Y, MINUS_Y, gridMap)) {
			case 1: 
				return true;
			case 2:
				if (PLUS_X == MINUS_X && PLUS_Y == MINUS_X) {
					return false;
				} else {
					return true;
				}
			case 4:
				if (PLUS_X == MINUS_X && MINUS_X== PLUS_Y || PLUS_X == PLUS_Y && PLUS_Y == MINUS_Y || PLUS_X == MINUS_X && MINUS_X == MINUS_Y || PLUS_Y == MINUS_Y && MINUS_Y == MINUS_X || PLUS_X == MINUS_Y && PLUS_Y == MINUS_X || PLUS_X == PLUS_Y && MINUS_X == MINUS_Y) {
					return false;
				} else {
					return true;
				}
			case 5:
				return false;
			case 6:
				return true;
			default:
				return false;
		}
	}
	
	public int levelOfDifficulty (float PLUS_X, float MINUS_X, float PLUS_Y, float MINUS_Y, GridMap gridMap) {
		return compareDistancesInt (PLUS_X, MINUS_X, PLUS_Y, MINUS_Y, gridMap);
	}
	
	public boolean ifPositionsShareXYAxis (ArrayList <DistanceFromJunction> arrayList) {
		
		boolean checking = true;
		
		if (arrayList.size() == 0) {
			return true;
		}
		
		float PLUS_X  = arrayList.get(0).getPlusX();
		float MINUS_X = arrayList.get(0).getMinusX();
		float PLUS_Y  = arrayList.get(0).getPlusY();
		float MINUS_Y = arrayList.get(0).getMinusY();
		
		for (int i = 0; i < arrayList.size(); i++) {
			if (!(arrayList.get(i).getPlusX() == PLUS_X && arrayList.get(i).getMinusX() == MINUS_X) || !(arrayList.get(i).getPlusX() == MINUS_X && arrayList.get(i).getMinusX() == PLUS_X)) {
				if (!(arrayList.get(i).getPlusY() == PLUS_Y && arrayList.get(i).getMinusY() == MINUS_Y) || !(arrayList.get(i).getPlusY() == MINUS_Y && arrayList.get(i).getMinusY() == PLUS_Y)) {
					checking = false;
				}
			}
		}
		return checking;
	}

	//
	
}