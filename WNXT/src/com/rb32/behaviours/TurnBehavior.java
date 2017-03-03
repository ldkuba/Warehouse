package com.rb32.behaviours;

import java.util.ArrayList;
import java.util.Random;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import com.rb32.main.JunctionFollower.PathChoices;

public class TurnBehavior implements Behavior {
	private LightSensor lightSensorR;
	private LightSensor lightSensorL;

	private DifferentialPilot pilot;

	private int turnDirection;

	private boolean supressed;

	private Random rand;

	private int whiteInitL;
	private int whiteInitR;

	private ArrayList<PathChoices> path;

/*	public TurnBehavior(LightSensor left, LightSensor right, int whiteInitL,
			int whiteInitR) {
		lightSensorR = right;
		lightSensorL = left;

		pilot = new DifferentialPilot(56, 26, Motor.A, Motor.B);

		pilot.setTravelSpeed(125.0);
		pilot.setRotateSpeed(150.0);

		rand = new Random();

		this.whiteInitL = whiteInitL;
		this.whiteInitR = whiteInitR;

		path = null;
		turnDirection = 0;
		supressed = true;
		
		no longer need this constrctor??
	}*/

	public TurnBehavior(LightSensor left, LightSensor right,
			int whiteInitR, ArrayList<PathChoices> path) {
		lightSensorR = right;
		lightSensorL = left;

		pilot = new DifferentialPilot(56, 120, Motor.A, Motor.B);

		pilot.setTravelSpeed(125.0);
		pilot.setRotateSpeed(150.0);

		rand = new Random();

		this.whiteInitR = whiteInitR;

		this.path = path;
	}

	public void setTurnDirection(int dir) {
		this.turnDirection = dir;
	}

	public void calibrate(int whiteL, int whiteR) {
		this.whiteInitL = whiteL;
		this.whiteInitR = whiteR;
	}

	@Override
	public boolean takeControl() {
		int reading = lightSensorL.getLightValue();

		if (whiteInitL - reading > 1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void action() {
		supressed = false;

		pilot.stop();

		int readingL = lightSensorL.getLightValue();
		int readingR = lightSensorR.getLightValue();

		/*
		 * LCD.clear();
		 * 
		 * LCD.drawInt(readingL, 3, 1); LCD.drawInt(whiteInitL, 3, 3);
		 * LCD.drawInt(whiteInitL - readingL, 3, 5); LCD.drawChar('L', 3, 7);
		 * 
		 * LCD.drawInt(readingR, 8, 1); LCD.drawInt(whiteInitR, 8, 3);
		 * LCD.drawInt(whiteInitR - readingR, 8, 5); LCD.drawChar('R', 8, 7);
		 */

		if (path != null) {
			if (path.isEmpty()) {
				System.exit(0);
			} else {
				turnDirection = path.get(0).ordinal();
				path.remove(0);
			}
		} else {
			System.out.println("Waiting for directions.");
		}

		if (turnDirection == 0) { //change to switch 
			pilot.arc(80.5, 90, true);
		} else if (turnDirection == 1) {
			pilot.arc(-80.5, -90, true);
		} else if (turnDirection == 2) {
			pilot.travel(50.0, true);
		}

		while (!supressed && pilot.isMoving()) {

			if (Button.ESCAPE.isDown()) {
				System.exit(0);
			}
		}

		suppress();
	}

	@Override
	public void suppress() {
		supressed = true;
	}

}