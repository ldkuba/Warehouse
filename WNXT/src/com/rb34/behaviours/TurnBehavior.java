package com.rb34.behaviours;

import java.util.ArrayList;

import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class TurnBehavior implements Behavior {
	private LightSensor lightSensorR;
	private LightSensor lightSensorL;
	private DifferentialPilot pilot;
	private int turnDirection;
	private boolean supressed;
	private final int THRESHOLD = 40;
	// final static Logger logger = Logger.getLogger(TurnBehavior.class);

	private ArrayList<PathChoices> path;
	private boolean actionDone;
	int readingL;
	int readingR;
	int whiteInitR;
	int whiteInitL;

	public TurnBehavior(LightSensor left, LightSensor right) {
		lightSensorR = right;
		lightSensorL = left;

		pilot = new DifferentialPilot(56, 120, Motor.A, Motor.B);

		pilot.setTravelSpeed(150);
	}

	public void setPath(ArrayList<PathChoices> path) {
		this.path = path;
	}

	public boolean rightOnBlack() {
		if (lightSensorR.getLightValue() <= THRESHOLD) {
			return true;
		} else {
			return false;
		}
	}

	public boolean leftOnBlack() {
		if (lightSensorL.getLightValue() <= THRESHOLD) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean takeControl() {
		if (rightOnBlack() && leftOnBlack()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void action() {
		supressed = false;
		pilot.stop();

		readingL = lightSensorL.getLightValue();
		readingR = lightSensorR.getLightValue();

		if (path != null) {
			if (!path.isEmpty()) {
				turnDirection = path.get(0).ordinal();
				path.remove(0);
				actionDone = false;
			}
		}

		switch (turnDirection) {
		case 0:
			pilot.arc(80.5, 90, true);
			break;
		case 1:
			pilot.arc(-80.5, -90, true);
			break;
		case 2:
			pilot.travel(75.0, true);
			break;
		case 3:
			pilot.rotate(180, true);
			break;
		}

		while (!supressed && pilot.isMoving()) {

			if (Button.ESCAPE.isDown()) {
				System.exit(0);
			}
		}

		actionDone = true;
		suppress();
	}

	@Override
	public void suppress() {
		supressed = true;
	}

	public boolean checkIfNoRoute() {
		if (actionDone && path.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

}