package com.rb34.behaviours;

import java.util.ArrayList;
import java.util.Random;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import com.rb34.behaviours.PathChoices;

public class TurnBehavior implements Behavior {
	private LightSensor lightSensorR;
	private LightSensor lightSensorL;
	private DifferentialPilot pilot;
	private int turnDirection;
	private boolean supressed;

	private ArrayList<PathChoices> path;
	private boolean actionDone;
	int readingL;
	int readingR;
	int whiteInitR;
	int whiteInitL;

	public TurnBehavior(LightSensor left, LightSensor right, ArrayList<PathChoices> path) {
		lightSensorR = right;
		lightSensorL = left;

		pilot = new DifferentialPilot(56, 120, Motor.A, Motor.B);

		pilot.setTravelSpeed(150);
		//pilot.setRotateSpeed(150.0);

		this.path = path;
	}

	public void calibrate(int readingR, int readingL) {
		this.readingR = readingR;
		this.readingL = readingL;
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

		if (turnDirection == 0) { // change to switch
			pilot.arc(80.5, 90, true);
			System.out.println("Action 0");
		} else if (turnDirection == 1) {
			pilot.arc(-80.5, -90, true);
			System.out.println("Action 1");
		} else if (turnDirection == 2) {
			pilot.travel(75.0, true);
			System.out.println("Action 2");
		} else if (turnDirection == 3) {
			pilot.rotate(180, true);
			System.out.println("Action 3");
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