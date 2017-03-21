package com.rb34.behaviours;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;
import com.rb34.robot_interface.RobotScreen;

public class LineFollowing implements Behavior {	
	private boolean supressed = true;
	private boolean doFirstAction = false;
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
			pilot.rotateLeft();
			
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
			pilot.rotateRight();
			
			if (Button.ESCAPE.isDown()) { //make sure that robot will stop program if escape button is pressed.
				System.exit(0);
				suppress();
			}
			Delay.msDelay(20);
		}
	}

	@Override
	public boolean takeControl() {
		return true; //always true-so will follow line until another behaviour takes control
	}

	@Override
	public void action() {
		supressed = false;
		
		pilot.stop();
		while (!supressed) {
			
			
			pilot.forward();
			screen.updateState("Moving foward");
			checkLeft();
			checkRight();


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

	public void doFirstAction() {
		doFirstAction = true;
	}
	
	public void doAction(int i) {
		switch (i) {
		case 0:
			pilot.arc(80.5, 90, true);
			//UpdateDirectionAndCo(0);
			screen.updateState("Left");
			break;
		case 1:
			pilot.arc(-80.5, -90, true);
			//UpdateDirectionAndCo(1);
			screen.updateState("Right");
			break;
		case 2:
			pilot.travel(75.0, true);
			//UpdateDirectionAndCo(2); 
			screen.updateState("Forward");
			break;
		case 3:
			pilot.rotate(180, true);
			//UpdateDirectionAndCo(3);
			screen.updateState("Rotate");
			break;
		}
	}

	@Override
	public void suppress() {
		supressed = true;
	}

}
