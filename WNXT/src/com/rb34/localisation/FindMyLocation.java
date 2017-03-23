package localisation;

import java.util.ArrayList;

import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.OpticalDistanceSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;
import rp.config.WheeledRobotConfiguration;
import rp.robotics.mapping.GridMap;
import rp.systems.WheeledRobotSystem;
import rp.util.HashMap;

/* IMPORTANT!
   This class needs:
  	
   	private WheeledRobotConfiguration ROBOT_CONFIG = new WheeledRobotConfiguration (0.056f, 0.115f, 0.17f, Motor.A, Motor.C);
	private DifferentialPilot pilot = new WheeledRobotSystem (ROBOT_CONFIG).getPilot();
	private LightSensor leftSensor = new LightSensor (SensorPort.S4);
	private LightSensor rightSensor = new LightSensor (SensorPort.S1);
	private OpticalDistanceSensor ranger = new OpticalDistanceSensor (SensorPort.S3);

	Map map = new Map ();
	FindMyLocation findMyLocation = new FindMyLocation ();
	public GridMap gridMap = map.createGridMap();
	
  	Usage: 
  	
  	int x;
  	int y;
  	Result result = findMyLocation.myLocation(gridMap, pilot, rightSensor, leftSensor, ranger);
  	x = result.getX();
  	y = result.getY();
  	
 */

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
		float pilotSpeed = 20f;
		
		// Sets travel speed
		pilot.setTravelSpeed(pilot.getMaxTravelSpeed() / 100f * pilotSpeed);
		
		//
		if (putMeInJunction (pilot, rightSensor, leftSensor, referenceValue)) {
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
					turnToDistanceAdvanced(pilot, 3f, distanceFromJunction, currentHeading);
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
										foundLocation = true;
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
								foundLocation = true;
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
										foundLocation = true;
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
							foundLocation = true;
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
								foundLocation = true;
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
										foundLocation = true;
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
								foundLocation = true;
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
					distanceFromJunction = new DistanceFromJunction (x, y,currentPLUS_X , currentMINUS_X , currentPLUS_Y , currentMINUS_Y);
					turnToDistanceAdvanced (pilot, 2f, distanceFromJunction, currentHeading);
					foundLocation = true;
					break;
				case 10: // Done. NOT TESTED
					turnToDistanceAdvanced (pilot, 3f, distanceFromJunction, currentHeading);
					if (putMeInJunction (pilot, rightSensor, leftSensor, referenceValue)) {
						performARegularScan (ranger, pilot);
					}
					
					listOfPossibleLocations = compareDistances (currentPLUS_X, currentMINUS_X, currentPLUS_Y, currentMINUS_Y, gridMap);
					distanceFromJunction = new DistanceFromJunction (x, y,currentPLUS_X , currentMINUS_X , currentPLUS_Y , currentMINUS_Y);
					switch (listOfPossibleLocations.size()) {
						case 1:
							x = listOfPossibleLocations.get(0).getX();
							y = listOfPossibleLocations.get(0).getY();
							foundLocation = true;
							break;
						case 4:
							turnToDistanceAdvanced (pilot, 3f, distanceFromJunction, currentHeading);
							for (int i = 1; i <= 5; i++) {
								if (putMeInJunction (pilot, rightSensor, leftSensor, referenceValue)) {
									pilot.stop();
								}
							}
							performARegularScan(ranger, pilot);
							listOfPossibleLocations = compareDistances (currentPLUS_X, currentMINUS_X, currentPLUS_Y, currentMINUS_Y, gridMap);
							x = listOfPossibleLocations.get(0).getX();
							y = listOfPossibleLocations.get(0).getY();
							foundLocation = true;
							break;
						case 12: // Done. NOT TESTED
							switch (currentHeading) {
								case PLUS_X:
									if (currentPLUS_Y == 1) {
										for (int i = 1; i <= 2; i++) {
											if (putMeInJunction (pilot, rightSensor, leftSensor, referenceValue)) {
												pilot.stop();
											}
										}
										
										performARegularScan (ranger, pilot);
										listOfPossibleLocations = compareDistances (currentPLUS_X, currentMINUS_X, currentPLUS_Y, currentMINUS_Y, gridMap);
										switch (listOfPossibleLocations.size()) {
											case 4:
												x = 1;
												y = 0;
												pilot.rotate(90);
												foundLocation = true;
												break;
											case 10:
												x = 4;
												y = 0;
												pilot.rotate(90);
												foundLocation = true;
												break;
										}
									} else if (currentPLUS_Y == 3) {
										for (int i = 1; i <= 2; i++) {
											if (putMeInJunction (pilot, rightSensor, leftSensor, referenceValue)) {
												pilot.stop();
											}
										}
										performARegularScan (ranger, pilot);
										listOfPossibleLocations = compareDistances (currentPLUS_X, currentMINUS_X, currentPLUS_Y, currentMINUS_Y, gridMap);
										switch (listOfPossibleLocations.size()) {
											case 10:
												x = 7;
												y = 0;
												pilot.rotate(-90);
												foundLocation = true;
												break;
											case 4:
												x = 10;
												y = 0;
												pilot.rotate(-90);
												foundLocation = true;
												break;
										}
										if (foundLocation) {
											break;
										}
									}
									break;
								case MINUS_X:
									if (currentMINUS_Y == 1) {
										for (int i = 1; i <= 2; i++) {
											if (putMeInJunction (pilot, rightSensor, leftSensor, referenceValue)) {
												pilot.stop();
											}
										}
										
										performARegularScan (ranger, pilot);
										listOfPossibleLocations = compareDistances (currentPLUS_X, currentMINUS_X, currentPLUS_Y, currentMINUS_Y, gridMap);
										switch (listOfPossibleLocations.size()) {
											case 4:
												x = 1;
												y = 0;
												pilot.rotate(90);
												foundLocation = true;
												break;
											case 10:
												x = 4;
												y = 0;
												pilot.rotate(90);
												foundLocation = true;
												break;
										}
									} else if (currentMINUS_Y == 3) {
										for (int i = 1; i <= 2; i++) {
											if (putMeInJunction (pilot, rightSensor, leftSensor, referenceValue)) {
												pilot.stop();
											}
										}
										performARegularScan (ranger, pilot);
										listOfPossibleLocations = compareDistances (currentPLUS_X, currentMINUS_X, currentPLUS_Y, currentMINUS_Y, gridMap);
										switch (listOfPossibleLocations.size()) {
											case 10:
												x = 7;
												y = 0;
												pilot.rotate(-90);
												foundLocation = true;
												break;
											case 4:
												x = 10;
												y = 0;
												pilot.rotate(-90);
												foundLocation = true;
												break;
										}
										if (foundLocation) {
											break;
										}
									}
									break;
								case PLUS_Y:
									if (currentPLUS_X == 1) {
										for (int i = 1; i <= 2; i++) {
											if (putMeInJunction (pilot, rightSensor, leftSensor, referenceValue)) {
												pilot.stop();
											}
										}
										
										performARegularScan (ranger, pilot);
										listOfPossibleLocations = compareDistances (currentPLUS_X, currentMINUS_X, currentPLUS_Y, currentMINUS_Y, gridMap);
										switch (listOfPossibleLocations.size()) {
											case 4:
												x = 1;
												y = 0;
												pilot.rotate(90);
												foundLocation = true;
												break;
											case 10:
												x = 4;
												y = 0;
												pilot.rotate(90);
												foundLocation = true;
												break;
										}
									} else if (currentPLUS_X == 3) {
										for (int i = 1; i <= 2; i++) {
											if (putMeInJunction (pilot, rightSensor, leftSensor, referenceValue)) {
												pilot.stop();
											}
										}
										performARegularScan (ranger, pilot);
										listOfPossibleLocations = compareDistances (currentPLUS_X, currentMINUS_X, currentPLUS_Y, currentMINUS_Y, gridMap);
										switch (listOfPossibleLocations.size()) {
											case 10:
												x = 7;
												y = 0;
												pilot.rotate(-90);
												foundLocation = true;
												break;
											case 4:
												x = 10;
												y = 0;
												pilot.rotate(-90);
												foundLocation = true;
												break;
										}
										if (foundLocation) {
											break;
										}
									}
									break;
								case MINUS_Y:
									if (currentPLUS_X == 1) {
										for (int i = 1; i <= 2; i++) {
											if (putMeInJunction (pilot, rightSensor, leftSensor, referenceValue)) {
												pilot.stop();
											}
										}
										
										performARegularScan (ranger, pilot);
										listOfPossibleLocations = compareDistances (currentPLUS_X, currentMINUS_X, currentPLUS_Y, currentMINUS_Y, gridMap);
										switch (listOfPossibleLocations.size()) {
											case 4:
												x = 1;
												y = 0;
												pilot.rotate(90);
												foundLocation = true;
												break;
											case 10:
												x = 4;
												y = 0;
												pilot.rotate(90);
												foundLocation = true;
												break;
										}
									} else if (currentPLUS_X == 3) {
										for (int i = 1; i <= 2; i++) {
											if (putMeInJunction (pilot, rightSensor, leftSensor, referenceValue)) {
												pilot.stop();
											}
										}
										performARegularScan (ranger, pilot);
										listOfPossibleLocations = compareDistances (currentPLUS_X, currentMINUS_X, currentPLUS_Y, currentMINUS_Y, gridMap);
										switch (listOfPossibleLocations.size()) {
											case 10:
												x = 7;
												y = 0;
												pilot.rotate(-90);
												foundLocation = true;
												break;
											case 4:
												x = 10;
												y = 0;
												pilot.rotate(-90);
												foundLocation = true;
												break;
										}
										if (foundLocation) {
											break;
										}
									}
									break;
							}
							break;
						case 10:
							if (putMeInJunction (pilot, rightSensor, leftSensor, referenceValue)) {
								performARegularScan (ranger, pilot);
								listOfPossibleLocations = compareDistances (currentPLUS_X, currentMINUS_X, currentPLUS_Y, currentMINUS_Y, gridMap);
								
								switch (listOfPossibleLocations.size()) {
								case 1: // Done. NOT TESTED
									x = listOfPossibleLocations.get(0).getX();
									y = listOfPossibleLocations.get(0).getY();
									foundLocation = true;
									break;
								case 4: // Done. NOT TESTED
									turnToDistanceAdvanced (pilot, 3f, distanceFromJunction, currentHeading);
									for (int i = 1; i <= 5; i++) {
										if (putMeInJunction (pilot, rightSensor, leftSensor, referenceValue)) {
											pilot.stop();
										}
									}
									performARegularScan (ranger, pilot);
									listOfPossibleLocations = compareDistances (currentPLUS_X, currentMINUS_X, currentPLUS_Y, currentMINUS_Y, gridMap);
									x = listOfPossibleLocations.get(0).getX();
									y = listOfPossibleLocations.get(0).getY();
									distanceFromJunction = new DistanceFromJunction (x, y,currentPLUS_X , currentMINUS_X , currentPLUS_Y , currentMINUS_Y); 
									turnToDistanceAdvanced (pilot, 2f, distanceFromJunction, currentHeading);
									foundLocation = true;
									break;
								case 10: // Done. NOT TESTED
									
									if (putMeInJunction (pilot, rightSensor, leftSensor, referenceValue)) {
										pilot.stop();
									}
									
									performARegularScan (ranger, pilot);
									listOfPossibleLocations = compareDistances (currentPLUS_X, currentMINUS_X, currentPLUS_Y, currentMINUS_Y, gridMap);
									
										switch (listOfPossibleLocations.size()) {
											case 1: // Done. NOT TESTED
												x = listOfPossibleLocations.get(0).getX();
												y = listOfPossibleLocations.get(0).getY();
												foundLocation = true;
												break;
											case 4: // Done. NOT TESTED
												turnToDistanceAdvanced (pilot, 3f, distanceFromJunction, currentHeading);
												for (int i = 1; i <= 5; i++) {
													if (putMeInJunction (pilot, rightSensor, leftSensor, referenceValue)) {
														pilot.stop();
													}
												}
												performARegularScan (ranger, pilot);
												listOfPossibleLocations = compareDistances (currentPLUS_X, currentMINUS_X, currentPLUS_Y, currentMINUS_Y, gridMap);
												x = listOfPossibleLocations.get(0).getX();
												y = listOfPossibleLocations.get(0).getY();
												distanceFromJunction = new DistanceFromJunction (x, y,currentPLUS_X , currentMINUS_X , currentPLUS_Y , currentMINUS_Y); 
												turnToDistanceAdvanced (pilot, 2f, distanceFromJunction, currentHeading);
												foundLocation = true;
												break;
										}
									break;
								}
							}
					}
					break;
				case 12: // Done. NOT TESTED
					distanceFromJunction = new DistanceFromJunction (x, y,currentPLUS_X , currentMINUS_X , currentPLUS_Y , currentMINUS_Y);
					turnToYAxisAndDistance (pilot, distanceFromJunction, gridMap, 3f);
					
					if (putMeInJunction (pilot, rightSensor, leftSensor, referenceValue)) {
						performARegularScan (ranger, pilot);
						listOfPossibleLocations = compareDistances (currentPLUS_X, currentMINUS_X, currentPLUS_Y, currentMINUS_Y, gridMap);
					}
					
					distanceFromJunction = new DistanceFromJunction (x, y,currentPLUS_X , currentMINUS_X , currentPLUS_Y , currentMINUS_Y);
						switch (listOfPossibleLocations.size()) {
							case 6: // Done. NOT TESTED
								turnToXAxis (pilot, distanceFromJunction, currentHeading, gridMap);
								find1 = false;
								
								while (!find1) {
									if (putMeInJunction (pilot, rightSensor, leftSensor, referenceValue)) {
										performARegularScan(ranger, pilot);
										
										listOfPossibleLocations = compareDistances (currentPLUS_X, currentMINUS_X, currentPLUS_Y, currentMINUS_Y, gridMap);
										
										if (listOfPossibleLocations.size() == 1) {
											System.out.println ("Should stop");
											x = listOfPossibleLocations.get(0).getX();
											y = listOfPossibleLocations.get(0).getY();
											find1 = true;
											foundLocation = true;
										}
									}
								}
								break;
							case 5: // Done. NOT TESTED
								for (int i = 1; i <= 5; i++) {
									if (putMeInJunction (pilot, rightSensor, leftSensor, referenceValue)) {
										pilot.stop();
									}
								}
								
								performARegularScan (ranger, pilot);
								distanceFromJunction = new DistanceFromJunction (x, y,currentPLUS_X , currentMINUS_X , currentPLUS_Y , currentMINUS_Y);
								turnToXAxis (pilot, distanceFromJunction, currentHeading, gridMap);
								find1 = false;
								
								while (!find1) {
									if (putMeInJunction (pilot, rightSensor, leftSensor, referenceValue)) {
										performARegularScan(ranger, pilot);
										
										listOfPossibleLocations = compareDistances (currentPLUS_X, currentMINUS_X, currentPLUS_Y, currentMINUS_Y, gridMap);
										
										if (listOfPossibleLocations.size() == 1) {
											System.out.println ("Should stop");
											x = listOfPossibleLocations.get(0).getX();
											y = listOfPossibleLocations.get(0).getY();
											find1 = true;
											foundLocation = true;
										}
									}
								}
								break;
						}
						break;
				case 28: // Done. NOT TESTED
					distanceFromJunction = new DistanceFromJunction (x, y,currentPLUS_X , currentMINUS_X , currentPLUS_Y , currentMINUS_Y);
					turnToDistanceAdvanced (pilot, 3f, distanceFromJunction, currentHeading);
					if (putMeInJunction (pilot, rightSensor, leftSensor, referenceValue)) {
						performARegularScan(ranger, pilot);
					}
					
					listOfPossibleLocations = compareDistances (currentPLUS_X, currentMINUS_X, currentPLUS_Y, currentMINUS_Y, gridMap);
					
					switch (listOfPossibleLocations.size()) {
						case 6: // Done. NOT TESTED
							turnToXAxis (pilot, distanceFromJunction, currentHeading, gridMap);
							find1 = false;
							
							while (!find1) {
								if (putMeInJunction (pilot, rightSensor, leftSensor, referenceValue)) {
									performARegularScan(ranger, pilot);
									
									listOfPossibleLocations = compareDistances (currentPLUS_X, currentMINUS_X, currentPLUS_Y, currentMINUS_Y, gridMap);
									
									if (listOfPossibleLocations.size() == 1) {
										x = listOfPossibleLocations.get(0).getX();
										y = listOfPossibleLocations.get(0).getY();
										find1 = true;
										foundLocation = true;
									}
								}
							}
							break;
						case 5: // Done. NOT TESTED
							distanceFromJunction = new DistanceFromJunction (x, y,currentPLUS_X , currentMINUS_X , currentPLUS_Y , currentMINUS_Y);
							turnToDistanceAdvanced (pilot, 3f, distanceFromJunction, currentHeading);
							for (int i = 1; i <= 5; i++) {
								if (putMeInJunction (pilot, rightSensor, leftSensor, referenceValue)) {
									pilot.stop();
								}
							}
							
							performARegularScan (ranger, pilot);
							distanceFromJunction = new DistanceFromJunction (x, y,currentPLUS_X , currentMINUS_X , currentPLUS_Y , currentMINUS_Y);
							turnToYAxisAndDistance (pilot, distanceFromJunction, gridMap, 3f);
							
							find1 = false;
							while (!find1) {
								if (putMeInJunction (pilot, rightSensor, leftSensor, referenceValue)) {
									performARegularScan(ranger, pilot);
									
									listOfPossibleLocations = compareDistances (currentPLUS_X, currentMINUS_X, currentPLUS_Y, currentMINUS_Y, gridMap);
									
									if (listOfPossibleLocations.size() == 1) {
										x = listOfPossibleLocations.get(0).getX();
										y = listOfPossibleLocations.get(0).getY();
										find1 = true;
										foundLocation = true;
									}
								}
							}
							break;
						case 12: // Done. NOT TESTED
							distanceFromJunction = new DistanceFromJunction (x, y,currentPLUS_X , currentMINUS_X , currentPLUS_Y , currentMINUS_Y);
							turnToYAxisAndDistance (pilot, distanceFromJunction, gridMap, 3f);
							
							if (putMeInJunction (pilot, rightSensor, leftSensor, referenceValue)) {
								performARegularScan (ranger, pilot);
								distanceFromJunction = new DistanceFromJunction (x, y,currentPLUS_X , currentMINUS_X , currentPLUS_Y , currentMINUS_Y);
								turnToXAxis (pilot, distanceFromJunction, currentHeading, gridMap);
							}
							
							find1 = false;
							while (!find1) {
								if (putMeInJunction (pilot, rightSensor, leftSensor, referenceValue)) {
									performARegularScan(ranger, pilot);
									
									listOfPossibleLocations = compareDistances (currentPLUS_X, currentMINUS_X, currentPLUS_Y, currentMINUS_Y, gridMap);
									
									if (listOfPossibleLocations.size() == 1) {
										x = listOfPossibleLocations.get(0).getX();
										y = listOfPossibleLocations.get(0).getY();
										foundLocation = true;
										find1 = true;
									}
								}
							}
							break;
						case 28: // Done. NOT TESTED
							find1 = false;
							while (!find1) {
								if (putMeInJunction (pilot, rightSensor, leftSensor, referenceValue)) {
									performARegularScan(ranger, pilot);
									
									listOfPossibleLocations = compareDistances (currentPLUS_X, currentMINUS_X, currentPLUS_Y, currentMINUS_Y, gridMap);
									
									if (listOfPossibleLocations.size() == 5 || listOfPossibleLocations.size() == 6) {
										find1 = true;
									}
								}
							}
							
							switch (listOfPossibleLocations.size()) {
								case 5: // Done. NOT TESTED
									distanceFromJunction = new DistanceFromJunction (x, y,currentPLUS_X , currentMINUS_X , currentPLUS_Y , currentMINUS_Y);
									turnToDistanceAdvanced (pilot, 3f, distanceFromJunction, currentHeading);
									for (int i = 1; i <= 5; i++) {
										if (putMeInJunction (pilot, rightSensor, leftSensor, referenceValue)) {
											pilot.stop();
										}
									}
									
									performARegularScan (ranger, pilot);
									distanceFromJunction = new DistanceFromJunction (x, y,currentPLUS_X , currentMINUS_X , currentPLUS_Y , currentMINUS_Y);
									turnToYAxisAndDistance (pilot, distanceFromJunction, gridMap, 3f);
									
									find1 = false;
									while (!find1) {
										if (putMeInJunction (pilot, rightSensor, leftSensor, referenceValue)) {
											performARegularScan(ranger, pilot);
											
											listOfPossibleLocations = compareDistances (currentPLUS_X, currentMINUS_X, currentPLUS_Y, currentMINUS_Y, gridMap);
											
											if (listOfPossibleLocations.size() == 1) {
												x = listOfPossibleLocations.get(0).getX();
												y = listOfPossibleLocations.get(0).getY();
												find1 = true;
												foundLocation = true;
											}
										}
									}
									break;
								case 6: // Done. NOT TESTED
									turnToXAxis (pilot, distanceFromJunction, currentHeading, gridMap);
									find1 = false;
									
									while (!find1) {
										if (putMeInJunction (pilot, rightSensor, leftSensor, referenceValue)) {
											performARegularScan(ranger, pilot);
											
											listOfPossibleLocations = compareDistances (currentPLUS_X, currentMINUS_X, currentPLUS_Y, currentMINUS_Y, gridMap);
											
											if (listOfPossibleLocations.size() == 1) {
												System.out.println ("I am at 1");
												x = listOfPossibleLocations.get(0).getX();
												y = listOfPossibleLocations.get(0).getY();
												find1 = true;
												foundLocation = true;
											}
										}
									}
									break;
							}
							
							break;
					}
					break;
			}
		}
		// Should not return -1, -1 if it returns -1, -1 something is wrong.
		return new Result (x, y);
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
	
	// Checks if possible to determine xy axis
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
	
	// Returns a number of difficulty (how many positions are out there that share the same distances)
	public static int levelOfDifficulty (float PLUS_X, float MINUS_X, float PLUS_Y, float MINUS_Y, GridMap gridMap) {
		
		return compareDistancesInt (PLUS_X, MINUS_X, PLUS_Y, MINUS_Y, gridMap);
		
	}
	
	// Simply goes forward until it hits the function. Goes forward a little just to be in the middle of the junction
	public static boolean putMeInJunction (DifferentialPilot pilot, LightSensor rightSensor, LightSensor leftSensor, int referenceValue) {
		int error = 7;
		
		boolean onJunction = false;
		boolean rightBlack = false;
		boolean leftBlack = false;
		boolean reset = false;
		
		pilot.forward();
		
		while (!onJunction) {
			// pilot.setTravelSpeed(pilot.getMaxTravelSpeed() / 5);
			
			int rightValue = rightSensor.readValue();
			int leftValue = leftSensor.readValue();
			
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
			pilot.travel(0.065);
		}
		Delay.msDelay(100);
		return true;
	}
	
	// Sets heading (PLUS_X, MINUS_X, PLUS_Y, MINUS_Y)
	public static void setHeading (Heading heading) {

		currentHeading = heading;
	}
	
	// Gets heading (PLUS_X, MINUS_X, PLUS_Y, MINUS_Y)
	public Heading getHeading () {

		return currentHeading;
	}
	
	// Puts robot into position to look at PLUS_Y axis
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

	// Scans for the first time into 4 directions
	public static void performAFirstScan (OpticalDistanceSensor ranger, DifferentialPilot pilot) {
		
		int numberOfScans = 5;
		float sumOfAllDistances = 0;
		float scannedDistance = 0;
		for (int y = 1; y <= 4; y++) {
			for (int i = 0; i < numberOfScans; i++) {
				scannedDistance = ranger.getRange();
				sumOfAllDistances += scannedDistance;
			}
			
			// Average distance
			sumOfAllDistances /= numberOfScans;
			
			if (10 <= sumOfAllDistances && sumOfAllDistances <= 37) {
				sumOfAllDistances = 1;
			} else if (40 <= sumOfAllDistances && sumOfAllDistances <= 60) {
				sumOfAllDistances = 2;
			} else {
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
			Delay.msDelay(100);
		}
	}
	
	// Scans any other times (3 directions)
	public static void performARegularScan (OpticalDistanceSensor ranger, DifferentialPilot pilot) {
		
		int numberOfScans = 5;
		float sumOfAllDistances = 0;
		float scannedDistance = 0;
		for (int y = 1; y <= 4; y++) {
			for (int i = 0; i < numberOfScans; i++) {
				scannedDistance = ranger.getRange();
				sumOfAllDistances += scannedDistance;
			}
			
			// Average distance
			sumOfAllDistances /= numberOfScans;
			
			if (10 <= sumOfAllDistances && sumOfAllDistances <= 37) {
				sumOfAllDistances = 1;
			} else if (40 <= sumOfAllDistances && sumOfAllDistances <= 60) {
				sumOfAllDistances = 2;
			} else {
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
	
	// Improved method for turning to a desired distance
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
	
	// Turns robot to the x axis. 
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
	
	// Turns robot to the y axis with a desired distance
	public static void turnToYAxisAndDistance (DifferentialPilot pilot, DistanceFromJunction distanceFromJunction, GridMap gridMap, float distance) {
		Heading compareHeading = currentHeading;
		if (ifPossibleToDetermineXYAxis (currentPLUS_X, currentMINUS_X, currentPLUS_Y, currentMINUS_Y, gridMap)) {
			ArrayList <DistanceFromJunction> compareDistance = compareDistances (currentPLUS_X, currentMINUS_X, currentPLUS_Y, currentMINUS_Y, gridMap);
			switch (currentHeading) {
				case PLUS_X:
					if ((currentPLUS_X == compareDistance.get(0).getPlusY() && currentMINUS_X == compareDistance.get(0).getMinusY()) ||
						(currentMINUS_X == compareDistance.get(0).getPlusY() && currentPLUS_X == compareDistance.get(0).getMinusY())) {
						if (currentPLUS_X == distance) {
							// Do nothing
						} else {
							pilot.rotate(180);
							setHeading (Heading.MINUS_X);
						}
					} else if ((currentPLUS_Y == compareDistance.get(0).getPlusY() && currentMINUS_Y == compareDistance.get(0).getPlusY()) ||
							   (currentMINUS_Y == compareDistance.get(0).getPlusY() && currentPLUS_Y == compareDistance.get(0).getMinusY())) {
						if (currentPLUS_Y == distance) {
							pilot.rotate(-90);
							setHeading (Heading.PLUS_Y);
						} else {
							pilot.rotate(90);
							setHeading (Heading.MINUS_Y);
						}
						
					}
					break;
				case MINUS_X:
					if ((currentPLUS_X == compareDistance.get(0).getPlusY() && currentMINUS_X == compareDistance.get(0).getMinusY()) ||
							(currentMINUS_X == compareDistance.get(0).getPlusY() && currentPLUS_X == compareDistance.get(0).getMinusY())) {
							if (currentPLUS_X == distance) {
								pilot.rotate(180);
								setHeading (Heading.PLUS_X);
							} else {
								// Do nothing
							}
						} else if ((currentPLUS_Y == compareDistance.get(0).getPlusY() && currentMINUS_Y == compareDistance.get(0).getPlusY()) ||
								   (currentMINUS_Y == compareDistance.get(0).getPlusY() && currentPLUS_Y == compareDistance.get(0).getMinusY())) {
							if (currentPLUS_Y == distance) {
								pilot.rotate(90);
								setHeading (Heading.PLUS_Y);
							} else {
								pilot.rotate(-90);
								setHeading (Heading.MINUS_Y);
							}
						}
					break;
				case PLUS_Y:
					if ((currentPLUS_X == compareDistance.get(0).getPlusY() && currentMINUS_X == compareDistance.get(0).getMinusY()) ||
							(currentMINUS_X == compareDistance.get(0).getPlusY() && currentPLUS_X == compareDistance.get(0).getMinusY())) {
							if (currentPLUS_X == distance) {
								pilot.rotate(90);
								setHeading (Heading.PLUS_X);
							} else {
								pilot.rotate(-90);
								setHeading (Heading.MINUS_X);
							}
						} else if ((currentPLUS_Y == compareDistance.get(0).getPlusY() && currentMINUS_Y == compareDistance.get(0).getPlusY()) ||
								   (currentMINUS_Y == compareDistance.get(0).getPlusY() && currentPLUS_Y == compareDistance.get(0).getMinusY())) {
							if (currentPLUS_Y == distance) {
								// Do nothing
							} else {
								pilot.rotate(180);
								setHeading (Heading.MINUS_Y);
							}
						}
					break;
				case MINUS_Y:
					if ((currentPLUS_X == compareDistance.get(0).getPlusY() && currentMINUS_X == compareDistance.get(0).getMinusY()) ||
							(currentMINUS_X == compareDistance.get(0).getPlusY() && currentPLUS_X == compareDistance.get(0).getMinusY())) {
							if (currentPLUS_X == distance) {
								pilot.rotate(-90);
								setHeading (Heading.PLUS_X);
							} else {
								pilot.rotate(90);
								setHeading (Heading.MINUS_X);
							}
						} else if ((currentPLUS_Y == compareDistance.get(0).getPlusY() && currentMINUS_Y == compareDistance.get(0).getPlusY()) ||
								   (currentMINUS_Y == compareDistance.get(0).getPlusY() && currentPLUS_Y == compareDistance.get(0).getMinusY())) {
							if (currentPLUS_Y == distance) {
								pilot.rotate(180);
								setHeading (Heading.PLUS_Y);
							} else {
								// Do nothing
							}
						}
			}
		}
	}
}