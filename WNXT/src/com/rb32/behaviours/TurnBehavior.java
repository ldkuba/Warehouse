package com.rb32.behaviours;

import java.util.ArrayList;
import java.util.Random;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class TurnBehavior implements Behavior {
	private LightSensor lightSensorR;
	private LightSensor lightSensorL;

	private DifferentialPilot pilot;

	private int turnDirection = 0;

	private boolean supressed = true;

	private Random rand;

	private int whiteInit;
	private int whiteInitR;

	private ArrayList<PathChoices> path;
	
	public enum PathChoices {
		LEFT, RIGHT, FORWARD
	}

	public TurnBehavior(LightSensor left, LightSensor right, int whiteInitL,
			int whiteInitR) {
		lightSensorR = right;
		lightSensorL = left;

		pilot = new DifferentialPilot(43, 171, Motor.B, Motor.C);

		pilot.setTravelSpeed(200.0);
		pilot.setRotateSpeed(100.0);

		rand = new Random();

		this.whiteInit = whiteInitL;
		this.whiteInitR = whiteInitR;

		path = null;
	}

	public TurnBehavior(LightSensor left, LightSensor right, int whiteInitL,
			int whiteInitR, ArrayList<PathChoices> path) {
		lightSensorR = right;
		lightSensorL = left;

		pilot = new DifferentialPilot(43, 171, Motor.B, Motor.C);

		pilot.setTravelSpeed(200.0);
		pilot.setRotateSpeed(100.0);

		rand = new Random();

		this.whiteInit = whiteInitL;
		this.whiteInitR = whiteInitR;

		this.path = path;
	}

	public void setTurnDirection(int dir) {
		this.turnDirection = dir;
	}

	public void calibrate(int whiteL, int whiteR) {
		this.whiteInit = whiteL;
		this.whiteInitR = whiteR;
	}

	@Override
	public boolean takeControl() {
		int reading = lightSensorL.getLightValue();

		if (whiteInit - reading > 1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void action() {
		supressed = false;

		pilot.stop();

		int reading = lightSensorL.getLightValue();
		int readingR = lightSensorR.getLightValue();

		LCD.clear();

		LCD.drawInt(reading, 3, 1);
		LCD.drawInt(whiteInit, 3, 3);
		LCD.drawInt(whiteInit - reading, 3, 5);
		LCD.drawChar('L', 3, 7);

		LCD.drawInt(readingR, 8, 1);
		LCD.drawInt(whiteInitR, 8, 3);
		LCD.drawInt(whiteInitR - readingR, 8, 5);
		LCD.drawChar('R', 8, 7);

		if (path != null) {
			if (path.isEmpty()) {
				System.exit(0);
			} else {
				turnDirection = path.get(0).ordinal();
				path.remove(0);
			}
		} else {
			turnDirection = rand.nextInt() % 3;
		}

		if (turnDirection == 0) {
			pilot.arc(85.5, 90, true);
		} else if (turnDirection == 1) {
			pilot.arc(-85.5, -90, true);
		} else if (turnDirection == 2) {
			pilot.travel(50.0, true);
		}

		while (!supressed && pilot.isMoving()) {
			// setTurnDirection(rand.nextInt()%3);

			if (Button.ESCAPE.isDown()) {
				System.exit(0);
			}
		}

		supressed = true;
	}

	@Override
	public void suppress() {
		supressed = true;
	}

}