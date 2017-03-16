package com.rb34.behaviours;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;
import com.rb34.robot_interface.RobotScreen;

public class LineFollowing implements Behavior 
{	
	private boolean supressed = true;
	
	private LightSensor lightSensorR;
	private LightSensor lightSensorL;
	private DifferentialPilot pilot;
	private RobotScreen screen;
	int reading;
	int readingL;
	int whiteInitL;
	int whiteInitR;
	
	public LineFollowing(LightSensor left, LightSensor right, RobotScreen _screen) {
		this.screen = _screen;
		
		lightSensorR = right;
		lightSensorL = left;
		this.whiteInitL = whiteInitL;
		this.whiteInitR = whiteInitR;
		
		pilot = new DifferentialPilot(56, 120, Motor.A, Motor.B);
		
		pilot.setTravelSpeed(80);
		pilot.setRotateSpeed(50);
		
	}
	
	public boolean rightOnBlack() {
		if (lightSensorR.getLightValue() <= 40) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean leftOnBlack() {
		if (lightSensorL.getLightValue() <= 40) {
			return true;
		} else {
			return false;
		}
	}
	
	protected void checkLeft() {
		// If left sensor on line && right sensor is not => Go left
		while (leftOnBlack() && !rightOnBlack()) {
			//pilot.rotate(2.5, true);
			pilot.rotateLeft();

			//System.out.println("Left rotation");
			
			if (Button.ESCAPE.isDown()) { //make sure that robot will stop program if escape button is pressed.
				System.exit(0);
				suppress();
			}
			Delay.msDelay(20);
		}
	}

	protected void checkRight() {
		// If right sensor on line && left sensor is not => Go right
		while (!leftOnBlack() && rightOnBlack()) {
			//pilot.rotate(-2.5, true);
			pilot.rotateRight();

			//System.out.println("Right rotation");
			
			if (Button.ESCAPE.isDown()) { //make sure that robot will stop program if escape button is pressed.
				System.exit(0);
				suppress();
			}
			Delay.msDelay(20);
		}
	}

	@Override
	public boolean takeControl() 
	{
		return true; //always true-so will follow line until another behaviour takes control
	}

	@Override
	public void action() {
		supressed = false;
		
		
		while (!supressed) {
			
			pilot.forward();
			screen.printState("Moving foward");

			checkLeft();
			//System.out.println("Left Initial: " +whiteInitL);
			//System.out.println("New left:" +lightSensorL.getLightValue());
			checkRight();
			//System.out.println("Right: "+rightOnBlack());
			//System.out.println("Right Initial: " +whiteInitR);
			//System.out.println("New right:" +lightSensorR.getLightValue());


	/*		if (!rightOnBlack() && !leftOnBlack()) { //if greater than 1 must be on white so forward
				pilot.forward();
				System.out.println("Forward");
				Delay.msDelay(10);
			} else if (rightOnBlack() && !leftOnBlack())  {
				pilot.rotateRight(); 
				System.out.println("Recorrecting A");
				Delay.msDelay(10);
			} else if (!rightOnBlack() && leftOnBlack()) {
				pilot.rotateLeft();
				System.out.println("Recorrecting B");
				Delay.msDelay(10);
			}*/

			if (Button.ESCAPE.isDown()) { //make sure that robot will stop program if escape button is pressed.
				System.exit(0);
				suppress();
			}

		/*	LCD.clear();
			LCD.drawInt(reading, 8, 1);
			//LCD.drawInt(whiteInit, 8, 3);
			//LCD.drawInt(whiteInit - reading, 8, 5);
			LCD.drawChar('R', 8, 7);
			
			LCD.drawInt(readingL, 3, 1);
			//LCD.drawInt(whiteInitL, 3, 3);
			//LCD.drawInt(whiteInitL - readingL, 3, 5);
			LCD.drawChar('L', 3, 7);
			//checks values when trying to debug-prints on robot		
*/			
			Delay.msDelay(30);
		}
	}

	@Override
	public void suppress() {
		supressed = true;
	}

}