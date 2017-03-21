package localisation;

import java.util.ArrayList;

import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.addon.OpticalDistanceSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;
import rp.robotics.mapping.GridMap;
import rp.util.HashMap;

public class FindMyLocation {
	
	private static Heading currentHeading = Heading.UNKNOWN;
	private static float currentPLUS_X;
	private static float currentMINUS_X;
	private static float currentPLUS_Y;
	private static float currentMINUS_Y;
	private static int difficulty = 0;
	// Returns robot's location
	public static Result myLocation (GridMap gridMap, DifferentialPilot pilot, LightSensor rightSensor, LightSensor leftSensor, OpticalDistanceSensor ranger) {
		
		// Holds a list of possible locations
		ArrayList <DistanceFromJunction> listOfPossibleLocations = new ArrayList <DistanceFromJunction> ();
		ArrayList <DistanceFromJunction> listOfPreviousLocations = new ArrayList <DistanceFromJunction> ();
		// Should not return -1, -1
		int x = -1, y = -1;
		// Number 1, 4, 5, 6, 10, 12, 28
		
		
		// A value from the floor (white)
		int referenceValue = getReferenceValue (rightSensor, leftSensor);
		int error = 7;
		float pilotSpeed = 0.1f;
		
		// Sets travel speed
		pilot.setTravelSpeed(pilotSpeed);
		
		//
		if (putMeInJunction (pilot, rightSensor, leftSensor, referenceValue)) {
			Delay.msDelay(200);
			// Scanning, putting values into an array, comparing
			performAFirstScan (ranger, pilot);
		}
		
		DistanceFromJunction distanceFromJunction = new DistanceFromJunction (x, y, currentPLUS_X, currentMINUS_X, currentPLUS_Y, currentMINUS_Y);
		listOfPreviousLocations.add(distanceFromJunction);
		
		System.out.println (currentPLUS_X + "");
		System.out.println (currentMINUS_Y + "");
		System.out.println (currentMINUS_X + "");
		System.out.println (currentPLUS_Y + "");
		
		// If measurements are correct, set heading to PLUS_Y
		if (currentPLUS_X <= 3 || currentPLUS_Y <= 3 || currentMINUS_X <= 3 || currentMINUS_Y <= 3) {
			difficulty = levelOfDifficulty(currentPLUS_X, currentMINUS_X, currentPLUS_Y, currentMINUS_Y, gridMap);
			System.out.println (difficulty);
			setHeading(Heading.PLUS_Y);
		}
		
		boolean foundLocation = false;
		
		while (!foundLocation) {
			
			// Get a list of similar locations
			listOfPossibleLocations = compareDistances(currentPLUS_X, currentMINUS_X, currentPLUS_Y, currentMINUS_Y, gridMap);
			setHeading (Heading.PLUS_Y);
			difficulty = listOfPossibleLocations.size();
			
			switch (difficulty) {
				case 1: // Done. Fully tested.
					putMeIntoCorrectHeading(listOfPossibleLocations, currentPLUS_X, currentMINUS_X, currentPLUS_Y, currentMINUS_Y, pilot);
					x = listOfPossibleLocations.get(0).getX();
					y = listOfPossibleLocations.get(0).getY();
					foundLocation = true;
					break;
				case 4: // Done. Fully tested.
					distanceFromJunction = new DistanceFromJunction (x, y,currentPLUS_X, currentMINUS_X, currentPLUS_Y, currentMINUS_Y);
					turnToDistance(pilot, 3f, distanceFromJunction);
					if (putMeInJunction (pilot, rightSensor, leftSensor, referenceValue)) {
						performARegularScan (ranger, pilot);
						LCD.clear();
						System.out.println (currentPLUS_X + "");
						System.out.println (currentMINUS_Y + "");
						System.out.println (currentMINUS_X + "");
						System.out.println (currentPLUS_Y + "");
						// System.out.println (compareDistances (currentPLUS_X, currentMINUS_X, currentPLUS_Y, currentMINUS_Y, gridMap).size());
						distanceFromJunction = new DistanceFromJunction (x, y,currentPLUS_X, currentMINUS_X, currentPLUS_Y, currentMINUS_Y);
						listOfPreviousLocations.add (0, distanceFromJunction);
						switch (compareDistances (currentPLUS_X, currentMINUS_X, currentPLUS_Y, currentMINUS_Y, gridMap).size()) {
							case 1: // Done
								listOfPossibleLocations = compareDistances(currentPLUS_X, currentMINUS_X, currentPLUS_Y, currentMINUS_Y, gridMap);
								pilot.rotate(180);
								x = listOfPossibleLocations.get(0).getX();
								y = listOfPossibleLocations.get(0).getY();
								foundLocation = true;
								break;
							case 4: // Done
								if (putMeInJunction (pilot, rightSensor, leftSensor, referenceValue)) {
									performARegularScan (ranger, pilot);
									listOfPossibleLocations = compareDistances (currentPLUS_X, currentMINUS_X, currentPLUS_Y, currentMINUS_Y, gridMap);
									
									switch (listOfPossibleLocations.size()) {
									case 10:
										for (int i = 1; i <= 4; i++) {
											if (putMeInJunction (pilot, rightSensor, leftSensor, referenceValue)) {
												// performARegularScan (ranger, pilot);
												pilot.stop();
											}
										}
										performARegularScan (ranger, pilot);
										listOfPossibleLocations = compareDistances (currentPLUS_X, currentMINUS_X, currentPLUS_Y, currentMINUS_Y, gridMap);
										x = listOfPossibleLocations.get(0).getX();
										y = listOfPossibleLocations.get(0).getY();
										foundLocation = true;
										break;
									case 12: // Done
										switch (currentHeading) {
										case PLUS_X: 
											if (currentMINUS_Y == 1) {
												x = 2;
												y = 0;
												foundLocation = true;
											} else {
												x = 9;
												y = 0;
												foundLocation = true;
											}
											break;
										case MINUS_Y:
											if (currentMINUS_X == 1) {
												x = 2;
												y = 0;
												foundLocation = true;
											} else {
												x = 9;
												y = 0;
												foundLocation = true;
											}
											break;
										case PLUS_Y:
											if (currentPLUS_X == 1) {
												x = 2;
												y = 0;
												foundLocation = true;
											} else {
												x = 9;
												y = 0;
												foundLocation = true;
											}
											break;
										case MINUS_X:
											if (currentPLUS_Y == 1) {
												x = 2;
												y = 0;
												foundLocation = true;
											} else {
												x = 9;
												y = 0;
												foundLocation = true;
											}
											break;
										}
										distanceFromJunction = new DistanceFromJunction (x, y,currentPLUS_X , currentMINUS_X , currentPLUS_Y , currentMINUS_Y);
										turnToDistanceAdvanced(pilot, 1f, distanceFromJunction, currentHeading);
										pilot.rotate(180);
										break;
									}
								}
								break;
							case 5: // Done
								switch (currentHeading) {
									case PLUS_X: 
										if (currentPLUS_Y == 1) {
											x = 1;
											y = 7;
											foundLocation = true;
										} else if (currentMINUS_Y == 1) {
											x = 10;
											y = 7;
										}
										break;
									case MINUS_Y:
										if (currentPLUS_X == 1) {
											x = 1;
											y = 7;
											foundLocation = true;
										} else if (currentMINUS_X == 1){
											x = 10;
											y = 7;
											foundLocation = true;
										}
										break;
									case PLUS_Y:
										if (currentMINUS_X == 1) {
											x = 1;
											y = 7;
											foundLocation = true;
										} else if (currentPLUS_X == 1){
											x = 10;
											y = 7;
											foundLocation = true;
										}
										break;
									case MINUS_X:
										if (currentMINUS_Y == 1) {
											x = 1;
											y = 7;
											foundLocation = true;
										} else if (currentPLUS_Y == 1){
											x = 10;
											y = 7;
											foundLocation = true;
										}
								}
								distanceFromJunction = new DistanceFromJunction (x, y,currentPLUS_X , currentMINUS_X , currentPLUS_Y , currentMINUS_Y);
								turnToDistanceAdvanced(pilot, 1f, distanceFromJunction, currentHeading);
								break;
							case 12: // Done
								switch (currentHeading) {
								case PLUS_X: 
									if (currentMINUS_Y == 1) {
										x = 2;
										y = 0;
										foundLocation = true;
									} else {
										x = 9;
										y = 0;
										foundLocation = true;
									}
									break;
								case MINUS_Y:
									if (currentMINUS_X == 1) {
										x = 2;
										y = 0;
										foundLocation = true;
									} else {
										x = 9;
										y = 0;
										foundLocation = true;
									}
									break;
								case PLUS_Y:
									if (currentPLUS_X == 1) {
										x = 2;
										y = 0;
										foundLocation = true;
									} else {
										x = 9;
										y = 0;
										foundLocation = true;
									}
									break;
								case MINUS_X:
									if (currentPLUS_Y == 1) {
										x = 2;
										y = 0;
										foundLocation = true;
									} else {
										x = 9;
										y = 0;
										foundLocation = true;
									}
									break;
								}
								distanceFromJunction = new DistanceFromJunction (x, y,currentPLUS_X , currentMINUS_X , currentPLUS_Y , currentMINUS_Y);
								turnToDistanceAdvanced(pilot, 1f, distanceFromJunction, currentHeading);
								pilot.rotate(180);
								break;
							case 10: // Done
								for (int i = 1; i <= 4; i++) {
									if (putMeInJunction (pilot, rightSensor, leftSensor, referenceValue)) {
										System.out.println (i + "");
										// performARegularScan (ranger, pilot);
										pilot.stop();
									}
								}
								performARegularScan (ranger, pilot);
								listOfPossibleLocations = compareDistances (currentPLUS_X, currentMINUS_X, currentPLUS_Y, currentMINUS_Y, gridMap);
								x = listOfPossibleLocations.get(0).getX();
								y = listOfPossibleLocations.get(0).getY();
								foundLocation = true;
								break;
						}
					}
					foundLocation = true;
					break;
				case 5: // Done. Fully tested
					turnToDistanceAdvanced(pilot, 3f, distanceFromJunction, currentHeading);
					System.out.println ("Turn");
					if (putMeInJunction (pilot, rightSensor, leftSensor, referenceValue)) {
						performARegularScan (ranger, pilot);
						listOfPossibleLocations = compareDistances (currentPLUS_X, currentMINUS_X, currentPLUS_Y, currentMINUS_Y, gridMap);
						switch (listOfPossibleLocations.size()) {
							case 12: // Done
								switch (currentHeading) {
								case PLUS_X: 
									if (currentPLUS_Y == 1) {
										x = 2;
										y = 7;
										foundLocation = true;
									} else if (currentMINUS_Y == 1) {
										x = 9;
										y = 7;
									}
									break;
								case MINUS_Y:
									if (currentPLUS_X == 1) {
										x = 2;
										y = 7;
										foundLocation = true;
									} else if (currentMINUS_X == 1){
										x = 9;
										y = 7;
										foundLocation = true;
									}
									break;
								case PLUS_Y:
									if (currentMINUS_X == 1) {
										x = 2;
										y = 7;
										foundLocation = true;
									} else if (currentPLUS_X == 1){
										x = 9;
										y = 7;
										foundLocation = true;
									}
									break;
								case MINUS_X:
									if (currentMINUS_Y == 1) {
										x = 2;
										y = 7;
										foundLocation = true;
									} else if (currentPLUS_Y == 1){
										x = 9;
										y = 7;
										foundLocation = true;
									}
								}
							distanceFromJunction = new DistanceFromJunction (x, y, currentPLUS_X, currentMINUS_X, currentPLUS_Y, currentMINUS_Y);
							turnToDistanceAdvanced (pilot, 1f, distanceFromJunction, currentHeading);
								break;
							case 28: // Done
								for (int i = 1; i <= 4; i++) {
									if (putMeInJunction (pilot, rightSensor, leftSensor, referenceValue)) {
										pilot.stop();
									}
								}
								distanceFromJunction = new DistanceFromJunction (-1, -1, currentPLUS_X, currentMINUS_X, currentPLUS_Y, currentMINUS_Y);
								pilot.rotate(90);
								switch (currentHeading) {
									case PLUS_X:
										setHeading (Heading.MINUS_Y);
										break;
									case MINUS_X:
										setHeading (Heading.PLUS_Y);
										break;
									case PLUS_Y:
										setHeading (Heading.PLUS_X);
										break;
									case MINUS_Y:
										setHeading (Heading.MINUS_X);
										break;
								}
								
								boolean find1 = false;
								
								while (!find1) {
									if (putMeInJunction (pilot, rightSensor, leftSensor, referenceValue)) {
										performARegularScan(ranger, pilot);
										
										listOfPossibleLocations = compareDistances (currentPLUS_X, currentMINUS_X, currentPLUS_Y, currentMINUS_Y, gridMap);
										
										if (listOfPossibleLocations.size() == 1) {
											x = listOfPossibleLocations.get(0).getX();
											y = listOfPossibleLocations.get(0).getY();
											find1 = true;
										}
									}
								}
								
								break;
							case 6: // Done
								switch (currentHeading) {
								case PLUS_X: 
									if (currentPLUS_Y == 2) {
										x = 2;
										y = 6;
										foundLocation = true;
									} else if (currentMINUS_Y == 2) {
										x = 9;
										y = 6;
									}
									break;
								case MINUS_Y:
									if (currentPLUS_X == 2) {
										x = 2;
										y = 6;
										foundLocation = true;
									} else if (currentMINUS_X == 2){
										x = 9;
										y = 6;
										foundLocation = true;
									}
									break;
								case PLUS_Y:
									if (currentMINUS_X == 2) {
										x = 2;
										y = 6;
										foundLocation = true;
									} else if (currentPLUS_X == 2){
										x = 9;
										y = 6;
										foundLocation = true;
									}
									break;
								case MINUS_X:
									if (currentMINUS_Y == 2) {
										x = 2;
										y = 6;
										foundLocation = true;
									} else if (currentPLUS_Y == 2){
										x = 9;
										y = 6;
										foundLocation = true;
									}
								}
								distanceFromJunction = new DistanceFromJunction (x, y,currentPLUS_X , currentMINUS_X , currentPLUS_Y , currentMINUS_Y);
								turnToDistanceAdvanced(pilot, 2f, distanceFromJunction, currentHeading);
									break;
						}
					}
					break;
				case 6: // Done. Fully tested
					turnToXAxis (pilot, distanceFromJunction, currentHeading, gridMap);
					boolean find1 = false;
					
					while (!find1) {
						if (putMeInJunction (pilot, rightSensor, leftSensor, referenceValue)) {
							performARegularScan(ranger, pilot);
							
							listOfPossibleLocations = compareDistances (currentPLUS_X, currentMINUS_X, currentPLUS_Y, currentMINUS_Y, gridMap);
							
							if (listOfPossibleLocations.size() == 1) {
								x = listOfPossibleLocations.get(0).getX();
								y = listOfPossibleLocations.get(0).getY();
								find1 = true;
							}
						}
					}
					break;
				case 10:
					break;
				case 12:
					break;
				case 28:
					break;
			}
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
	public static ArrayList<DistanceFromJunction> distanceFromJunction (GridMap gridMap) {

		ArrayList<DistanceFromJunction> arrayList = new ArrayList<DistanceFromJunction> ();
		float PLUS_X = 1, PLUS_Y = 1, MINUS_X = 1, MINUS_Y = 1;
		// 18 43 72 
		// 1  2  3  
		for (int x = 0; x < gridMap.getXSize (); x++) {
			for (int y = 0; y < gridMap.getYSize (); y++) {
				for (int z = 1; z < 3; z++) {
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
	public static ArrayList <DistanceFromJunction> compareDistances (float PLUS_X, float MINUS_X, float PLUS_Y, float MINUS_Y, GridMap gridMap) {
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
	public static int compareDistancesInt (float PLUS_X, float MINUS_X, float PLUS_Y, float MINUS_Y, GridMap gridMap) {
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
	
	
	public static boolean ifPossibleToDetermineXYAxis (float PLUS_X, float MINUS_X, float PLUS_Y, float MINUS_Y, GridMap gridMap) {
		
		// 1, 4, 5, 6, 10, 12, 28
		switch (levelOfDifficulty (PLUS_X, MINUS_X, PLUS_Y, MINUS_Y, gridMap)) {
			case 1: 
				return true;
			case 4:
				return false;
			case 5:
				return false;
			case 6:
				return true;
			case 10:
				return true;
			case 12:
				return true;
			case 28:
				return false;
			default:
				return false;
		}
	}
	
	
	public static int levelOfDifficulty (float PLUS_X, float MINUS_X, float PLUS_Y, float MINUS_Y, GridMap gridMap) {
		
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
	
	
	public int checkPositions (int firstPossibilityDistribution, int secondPossibilityDistribution, GridMap gridMap) {
		
		ArrayList <DistanceFromJunction> arrayList = new ArrayList <DistanceFromJunction> ();
		arrayList = distanceFromJunction (gridMap);
		
		int answer = 0;
		// X axis, first - second
		for (int x = 0; x <= 10;  x++) {
			for (int y = 0; y <= 7; y++) {
				if (getIndexByCoordinates (x, y, gridMap) != -1 && getIndexByCoordinates (x + 1, y, gridMap) != -1) {
						if (compareDistancesInt (arrayList.get(getIndexByCoordinates (x, y, gridMap)).getPlusX(), 
												 arrayList.get(getIndexByCoordinates (x, y, gridMap)).getMinusX(), 
												 arrayList.get(getIndexByCoordinates (x, y, gridMap)).getPlusY(), 
												 arrayList.get(getIndexByCoordinates (x, y, gridMap)).getMinusY(), gridMap) == firstPossibilityDistribution && 
							compareDistancesInt (arrayList.get(getIndexByCoordinates (x + 1, y, gridMap)).getPlusX(), 
												 arrayList.get(getIndexByCoordinates (x + 1, y, gridMap)).getMinusX(), 
												 arrayList.get(getIndexByCoordinates (x + 1, y, gridMap)).getPlusY(), 
												 arrayList.get(getIndexByCoordinates (x + 1, y, gridMap)).getMinusY(), gridMap) == secondPossibilityDistribution) {
							answer++;
					}
				}
			}
		}
		
		// Y axis, first - second
		for (int x = 0; x <= 11;  x++) {
			for (int y = 0; y <= 6; y++) {
				if (getIndexByCoordinates (x, y, gridMap) != -1 && getIndexByCoordinates (x, y + 1, gridMap) != -1) {
						if (compareDistancesInt (arrayList.get(getIndexByCoordinates (x, y + 1, gridMap)).getPlusX(), 
												 arrayList.get(getIndexByCoordinates (x, y + 1, gridMap)).getMinusX(), 
												 arrayList.get(getIndexByCoordinates (x, y + 1, gridMap)).getPlusY(), 
												 arrayList.get(getIndexByCoordinates (x, y + 1, gridMap)).getMinusY(), gridMap) == firstPossibilityDistribution && 
							compareDistancesInt (arrayList.get(getIndexByCoordinates (x, y, gridMap)).getPlusX(), 
												 arrayList.get(getIndexByCoordinates (x, y, gridMap)).getMinusX(), 
												 arrayList.get(getIndexByCoordinates (x, y, gridMap)).getPlusY(), 
												 arrayList.get(getIndexByCoordinates (x, y, gridMap)).getMinusY(), gridMap) == secondPossibilityDistribution) {
							answer++;
					}
				}
			}
		}
		
		if (firstPossibilityDistribution != secondPossibilityDistribution) {
			// X axis, second - first
			for (int x = 0; x <= 10;  x++) {
				for (int y = 0; y <= 7; y++) {
					if (getIndexByCoordinates (x, y, gridMap) != -1 && getIndexByCoordinates (x + 1, y, gridMap) != -1) {
						if (compareDistancesInt (arrayList.get(getIndexByCoordinates (x, y, gridMap)).getPlusX(), 
												 arrayList.get(getIndexByCoordinates (x, y, gridMap)).getMinusX(), 
												 arrayList.get(getIndexByCoordinates (x, y, gridMap)).getPlusY(), 
												 arrayList.get(getIndexByCoordinates (x, y, gridMap)).getMinusY(), gridMap) == secondPossibilityDistribution && 
							compareDistancesInt (arrayList.get(getIndexByCoordinates (x + 1, y, gridMap)).getPlusX(), 
												 arrayList.get(getIndexByCoordinates (x + 1, y, gridMap)).getMinusX(), 
												 arrayList.get(getIndexByCoordinates (x + 1, y, gridMap)).getPlusY(), 
												 arrayList.get(getIndexByCoordinates (x + 1, y, gridMap)).getMinusY(), gridMap) == firstPossibilityDistribution) {
							answer++;
						}
					}
				}
			}
			
			// Y axis, second - first
			for (int x = 0; x <= 11;  x++) {
				for (int y = 0; y <= 7; y++) {
					if (getIndexByCoordinates (x, y, gridMap) != -1 && getIndexByCoordinates (x, y + 1, gridMap) != -1) {
							if (compareDistancesInt (arrayList.get(getIndexByCoordinates (x, y + 1, gridMap)).getPlusX(), 
													 arrayList.get(getIndexByCoordinates (x, y + 1, gridMap)).getMinusX(), 
													 arrayList.get(getIndexByCoordinates (x, y + 1, gridMap)).getPlusY(), 
													 arrayList.get(getIndexByCoordinates (x, y + 1, gridMap)).getMinusY(), gridMap) == secondPossibilityDistribution && 
								compareDistancesInt (arrayList.get(getIndexByCoordinates (x, y, gridMap)).getPlusX(), 
													 arrayList.get(getIndexByCoordinates (x, y, gridMap)).getMinusX(), 
													 arrayList.get(getIndexByCoordinates (x, y, gridMap)).getPlusY(), 
													 arrayList.get(getIndexByCoordinates (x, y, gridMap)).getMinusY(), gridMap) == firstPossibilityDistribution) {
								answer++;
						}
					}
				}
			}
		}
		
		return answer;
	}
	
	/*
	public ArrayList <DistanceFromJunction> checkPositionsToArrayList (int firstPossibilityDistribution, int secondPossibilityDistribution, GridMap gridMap) {
		
		ArrayList <DistanceFromJunction> arrayList = new ArrayList <DistanceFromJunction> ();
		arrayList = distanceFromJunction (gridMap);
		ArrayList <DistanceFromJunction> answerArrayList = new ArrayList <DistanceFromJunction> ();
		
		int answer = 0;
		
		for (int x = 0; x <= 10;  x+= 2) {
			for (int y = 0; y <= 7; y++) {
				if (getIndexByCoordinates (x, y, gridMap) != -1 && getIndexByCoordinates (x + 1, y, gridMap) != -1) {
						if (compareDistancesInt (arrayList.get(getIndexByCoordinates (x, y, gridMap)).getPlusX(), arrayList.get(getIndexByCoordinates (x, y, gridMap)).getMinusX(), arrayList.get(getIndexByCoordinates (x, y, gridMap)).getPlusY(), arrayList.get(getIndexByCoordinates (x, y, gridMap)).getMinusY(), gridMap) == firstPossibilityDistribution && compareDistancesInt (arrayList.get(getIndexByCoordinates (x + 1, y, gridMap)).getPlusX(), arrayList.get(getIndexByCoordinates (x + 1, y, gridMap)).getMinusX(), arrayList.get(getIndexByCoordinates (x + 1, y, gridMap)).getPlusY(), arrayList.get(getIndexByCoordinates (x + 1, y, gridMap)).getMinusY(), gridMap) == secondPossibilityDistribution) {
							answerArrayList.add(new DistanceFromJunction (x, y, arrayList.get));
					}
				}
			}
		}
		
		return answer;
	}
	*/
	
	public int getIndexByCoordinates (int xCoordinate, int yCoordinate, GridMap gridMap) {
		ArrayList <DistanceFromJunction> arrayList = new ArrayList <DistanceFromJunction> ();
		arrayList = distanceFromJunction (gridMap);
		for (int size = 0; size < (gridMap.getXSize() * gridMap.getYSize()); size++) {
			if (!gridMap.isObstructed(arrayList.get(size).getX(), arrayList.get(size).getY())) {
				if (arrayList.get(size).getX() == xCoordinate && arrayList.get(size).getY() == yCoordinate) {
					return size;
				}
			}
		}
		return -1;
	}
	
	public void moveMeAroundTheMap (DifferentialPilot pilot, LightSensor rightSensor, LightSensor leftSensor,int referenceValue) {
		
		for (int i = 0; i < 7; i++) {
			putMeInJunction(pilot, rightSensor, leftSensor, referenceValue);
		}
		
		pilot.rotate(90);
		for (int i = 0; i < 2; i++) {
			putMeInJunction(pilot, rightSensor, leftSensor, referenceValue);
		}
		
		pilot.rotate(90);
		for (int i = 0; i < 7; i++) {
			putMeInJunction(pilot, rightSensor, leftSensor, referenceValue);
		}
		pilot.rotate(90);
		for (int i = 0; i < 2; i++) {
			putMeInJunction(pilot, rightSensor, leftSensor, referenceValue);
		}
		
		pilot.rotate(90);
	}
	
	public static boolean putMeInJunction (DifferentialPilot pilot, LightSensor rightSensor, LightSensor leftSensor, int referenceValue) {
		int error = 7;
		
		boolean onJunction = false;
		boolean rightBlack = false;
		boolean leftBlack = false;
		boolean reset = false;
		
		pilot.forward();
		
		while (!onJunction) {
			pilot.setTravelSpeed(pilot.getMaxTravelSpeed() / 5);
			
			int rightValue = rightSensor.readValue();
			int leftValue = leftSensor.readValue();
			// System.out.println (rightValue + " " + leftValue + " " + referenceValue + " " + (referenceValue - rightValue) + " " + (referenceValue - leftValue));
			
			if (Math.abs(referenceValue - rightValue) > error && Math.abs(referenceValue - leftValue) > error) {
				onJunction = true;
				break;
			} else {
				if (Math.abs(referenceValue - rightValue) > error)
					rightBlack = true;
				while (rightBlack) {
					pilot.rotateLeft();
					leftValue = leftSensor.readValue();

					// check if, while rotating, the other sensor is on the line
					// if it is, it means that there is a junction and one sensor detected the line faster than the other
					// it stops trying to correct itself
					if (Math.abs(referenceValue - leftValue) > error) {
						rightBlack = false;
						reset = true;
					}
					if (Math.abs(referenceValue - rightSensor.readValue()) < error) {
						rightBlack = false;
						reset = true;
					}

				}
				if (reset) {
					pilot.forward();
					rightBlack = false;
					leftBlack = false;
					reset = false;
				}
				if (Math.abs(referenceValue - leftValue) > error)
					leftBlack = true;
				
				while (leftBlack) {
					pilot.rotateRight();
					rightValue = rightSensor.readValue();
					if (Math.abs(referenceValue - rightValue) > error) {
						leftBlack = false;
						reset = true;
					}
					if (Math.abs(referenceValue - leftSensor.readValue()) < error) {
						leftBlack = false;
						reset = true;
					}
				}
				if (reset) {
					pilot.forward();
					rightBlack = false;
					leftBlack = false;
					reset = false;
				}
			}	
		}
		pilot.stop();
		if (onJunction) {
			pilot.travel(0.085);
		}
		Delay.msDelay(200);
		return true;
	}
	
	public static void turnToDistance (DifferentialPilot pilot, float distance, DistanceFromJunction distanceFromJunction) {
		
		if (distanceFromJunction.getPlusX() == distance) {
			pilot.rotate(90);
			setHeading (Heading.PLUS_X);
		} else if (distanceFromJunction.getMinusX() == distance) {
			pilot.rotate(-90);
			setHeading (Heading.MINUS_X);
		} else if (distanceFromJunction.getMinusY() == distance) {
			pilot.rotate(180);
			setHeading (Heading.MINUS_Y);
		}
	}
	
	public static void setHeading (Heading heading) {

		currentHeading = heading;
	}
	
	public Heading getHeading () {

		return currentHeading;
	}
	
	public static void putMeIntoCorrectHeading (ArrayList <DistanceFromJunction> arrayList, float PLUS_X, float MINUS_X, float PLUS_Y, float MINUS_Y, DifferentialPilot pilot) {
		if (arrayList.get(0).getPlusX() == PLUS_X &&
			arrayList.get(0).getMinusX() == MINUS_X &&
			arrayList.get(0).getPlusY() == PLUS_Y &&
			arrayList.get(0).getMinusY() == MINUS_Y) {
		} else if (arrayList.get(0).getPlusX() == PLUS_Y &&
				arrayList.get(0).getMinusX() == MINUS_Y &&
				arrayList.get(0).getPlusY() == MINUS_X &&
				arrayList.get(0).getMinusY() == PLUS_X) {
			pilot.rotate(-90);
		} else if (arrayList.get(0).getPlusX() == MINUS_X &&
				arrayList.get(0).getMinusX() == PLUS_X &&
				arrayList.get(0).getPlusY() == MINUS_Y &&
				arrayList.get(0).getMinusY() == PLUS_Y) {
			pilot.rotate(180);
		} else {
			pilot.rotate(90);
		}
	}
	
	public static void performAFirstScan (OpticalDistanceSensor ranger, DifferentialPilot pilot) {
		
		Delay.msDelay(200);
		
		int numberOfScans = 5;
		float sumOfAllDistances = 0;
		float scannedDistance = 0;
		for (int y = 1; y <= 4; y++) {
			for (int i = 0; i < numberOfScans; i++) {
				scannedDistance = ranger.getRange();
				sumOfAllDistances += scannedDistance;
			}
			
			Delay.msDelay(200);
			
			// Average distance
			sumOfAllDistances /= numberOfScans;
			
			if (10 <= sumOfAllDistances && sumOfAllDistances <= 37) {
				sumOfAllDistances = 1;
			} else if (40 <= sumOfAllDistances && sumOfAllDistances <= 60) {
				sumOfAllDistances = 2;
			} else if (sumOfAllDistances >= 70) {
				sumOfAllDistances = 3;
			}
			
			if (y == 1) {
				currentPLUS_X = sumOfAllDistances;
			} else if (y == 2) {
				currentMINUS_Y = sumOfAllDistances;
			} else if (y == 3) {
				currentMINUS_X = sumOfAllDistances;
			} else if (y == 4) {
				currentPLUS_Y = sumOfAllDistances;
			}

			if (y != 4) {
				pilot.rotate(90);
			}
			Delay.msDelay(200);
		}
	}
	
	public static void performARegularScan (OpticalDistanceSensor ranger, DifferentialPilot pilot) {
		
		Delay.msDelay(200);
		
		int numberOfScans = 5;
		float sumOfAllDistances = 0;
		float scannedDistance = 0;
		for (int y = 1; y <= 4; y++) {
			for (int i = 0; i < numberOfScans; i++) {
				scannedDistance = ranger.getRange();
				sumOfAllDistances += scannedDistance;
			}
			
			Delay.msDelay(200);
			
			// Average distance
			sumOfAllDistances /= numberOfScans;
			
			if (10 <= sumOfAllDistances && sumOfAllDistances <= 37) {
				sumOfAllDistances = 1;
			} else if (40 <= sumOfAllDistances && sumOfAllDistances <= 60) {
				sumOfAllDistances = 2;
			} else if (sumOfAllDistances >= 70) {
				sumOfAllDistances = 3;
			}
			
			if (y == 1) {
				switch (currentHeading) {
				case PLUS_X: currentPLUS_X = sumOfAllDistances;
					setHeading (Heading.PLUS_Y);
					if (currentMINUS_X != 3) {
						currentMINUS_X++;
					}
					pilot.rotate(-90);
						break;
				case MINUS_X: currentMINUS_X = sumOfAllDistances;
					setHeading (Heading.MINUS_Y);
					if (currentPLUS_X != 3) {
						currentPLUS_X++;
					}
					pilot.rotate(-90);
						break;
				case PLUS_Y: currentPLUS_Y = sumOfAllDistances;
					setHeading (Heading.MINUS_X);
					if (currentMINUS_Y != 3) {
						currentMINUS_Y++;
					}
					pilot.rotate(-90);
						break;
				case MINUS_Y: currentMINUS_Y = sumOfAllDistances;
					setHeading (Heading.PLUS_X);
					if (currentPLUS_Y != 3) {
						currentPLUS_Y++;
					}
					pilot.rotate(-90);
						break;
				default:
					break;
				}
			} else if (y == 2) {
				switch (currentHeading) {
				case PLUS_X: currentPLUS_X = sumOfAllDistances;
					setHeading (Heading.MINUS_X);
					pilot.rotate(180);
						break;
				case MINUS_X: currentMINUS_X = sumOfAllDistances;
					setHeading (Heading.PLUS_X);
					pilot.rotate(180);
						break;
				case PLUS_Y: currentPLUS_Y = sumOfAllDistances;
					setHeading (Heading.MINUS_Y);
					pilot.rotate(180);
						break;
				case MINUS_Y: currentMINUS_Y = sumOfAllDistances;
					setHeading (Heading.PLUS_Y);
					pilot.rotate(180);
						break;
				default:
						break;
				}
			} else if (y == 3) {
				switch (currentHeading) {
				case PLUS_X: currentPLUS_X = sumOfAllDistances;
					setHeading (Heading.PLUS_Y);
					pilot.rotate(-90);
						break;
				case MINUS_X: currentMINUS_X = sumOfAllDistances;
					setHeading (Heading.MINUS_Y);
					pilot.rotate(-90);
						break;
				case PLUS_Y: currentPLUS_Y = sumOfAllDistances;
					setHeading (Heading.MINUS_X);
					pilot.rotate(-90);
						break;
				case MINUS_Y: currentMINUS_Y = sumOfAllDistances;
					setHeading (Heading.PLUS_X);
					pilot.rotate(-90);
						break;
				default:
					break;
				}
			}
		}
	}
	
	public static void turnToDistanceAdvanced (DifferentialPilot pilot, float distance, DistanceFromJunction distanceFromJunction, Heading heading) {
		switch (heading) {
			case PLUS_X:
				if (distanceFromJunction.getMinusX() == distance) {
					pilot.rotate(180);
					setHeading (Heading.MINUS_X);
				} else if (distanceFromJunction.getPlusY() == distance) {
					pilot.rotate(-90);
					setHeading (Heading.PLUS_Y);
				} else if (distanceFromJunction.getMinusY() == distance) {
					pilot.rotate(90);
					setHeading (Heading.MINUS_Y);
				}
				break;
			case MINUS_X:
				if (distanceFromJunction.getPlusX() == distance) {
					pilot.rotate(180);
					setHeading (Heading.PLUS_X);
				} else if (distanceFromJunction.getPlusY() == distance) {
					pilot.rotate(90);
					setHeading (Heading.PLUS_Y);
				} else if (distanceFromJunction.getMinusY() == distance) {
					pilot.rotate(-90);
					setHeading (Heading.MINUS_Y);
				}
				break;
			case PLUS_Y:
				if (distanceFromJunction.getMinusY() == distance) {
					pilot.rotate (180);
					setHeading (Heading.MINUS_Y);
				} else if (distanceFromJunction.getPlusX() == distance) {
					pilot.rotate (90);
					setHeading (Heading.PLUS_X);
				} else if (distanceFromJunction.getMinusX() == distance) {
					pilot.rotate(-90);
					setHeading (Heading.MINUS_X);
				}
				break;
			case MINUS_Y:
				if (distanceFromJunction.getPlusY() == distance) {
					pilot.rotate (180);
					setHeading (Heading.PLUS_Y);
				} else if (distanceFromJunction.getPlusX() == distance) {
					pilot.rotate (-90);
					setHeading (Heading.PLUS_X);
				} else if (distanceFromJunction.getMinusX() == distance) {
					pilot.rotate (90);
					setHeading (Heading.MINUS_X);
				}
				break;
		}
	}
	
	public static void turnToXAxis (DifferentialPilot pilot, DistanceFromJunction distanceFromJunction, Heading heading, GridMap gridMap) {
		if (ifPossibleToDetermineXYAxis (currentPLUS_X, currentMINUS_X, currentPLUS_Y, currentMINUS_Y, gridMap)) {
			ArrayList <DistanceFromJunction> compareDistance = compareDistances (currentPLUS_X, currentMINUS_X, currentPLUS_Y, currentMINUS_Y, gridMap);
			switch (currentHeading) {
				case PLUS_X:
					if ((currentPLUS_X != compareDistance.get(0).getPlusX() || currentMINUS_X != compareDistance.get(0).getMinusX()) ||
						(currentMINUS_X != compareDistance.get(0).getPlusX() || currentPLUS_X != compareDistance.get(0).getMinusX())) {
						pilot.rotate(90);
						setHeading (Heading.MINUS_Y);
					}
					break;
				case MINUS_X:
					if ((currentMINUS_X != compareDistance.get(0).getPlusX() || currentPLUS_X != compareDistance.get(0).getMinusX()) ||
						(currentPLUS_X != compareDistance.get(0).getPlusX() || currentMINUS_X != compareDistance.get(0).getMinusX())) {
						pilot.rotate(90);
						setHeading (Heading.PLUS_Y);
					}
					break;
				case PLUS_Y:
					if ((currentPLUS_Y != compareDistance.get(0).getPlusX() || currentMINUS_Y != compareDistance.get(0).getMinusX()) ||
						(currentMINUS_Y != compareDistance.get(0).getPlusX() || currentPLUS_Y != compareDistance.get(0).getMinusX())) {
						pilot.rotate(90);
						setHeading (Heading.PLUS_X);
					}
					break;
				case MINUS_Y:
					if ((currentMINUS_Y != compareDistance.get(0).getPlusX() || currentPLUS_Y != compareDistance.get(0).getMinusX()) ||
						(currentPLUS_Y != compareDistance.get(0).getPlusX() || currentMINUS_Y != compareDistance.get(0).getMinusX())) {
						pilot.rotate(90);
						setHeading (Heading.MINUS_X);
					}
			}
		}
	}
	
	public static void rotateNTimes (LightSensor leftSensor, LightSensor rightSensor, DifferentialPilot pilot, int timesToRotatate) {
		
	}
	
}