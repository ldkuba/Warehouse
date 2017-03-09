package com.rb34.behaviours;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;

public class LineFollowing implements Behavior {
	private boolean supressed = true;

	private LightSensor lightSensorR;
	private LightSensor lightSensorL;
	private DifferentialPilot pilot;
	private TurnBehavior turnBehavior;
	private final int THRESHOLD = 40;

	int reading;
	int readingL;

	public LineFollowing(LightSensor left, LightSensor right,
			TurnBehavior turnBehavior) {
		this.turnBehavior = turnBehavior;

		lightSensorR = right;
		lightSensorL = left;

		pilot = new DifferentialPilot(56, 120, Motor.A, Motor.B);

		pilot.setTravelSpeed(150);

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

	protected void checkLeft() {
		// If left sensor on line && right sensor is not : go left
		while (leftOnBlack() && !rightOnBlack()) {
			pilot.rotate(5, true);

			// System.out.println("Left rotation");
			Delay.msDelay(10);

			if (Button.ESCAPE.isDown()) { // make sure that robot will stop
											// program if escape button is
											// pressed.
				System.exit(0);
				suppress();
			}
		}
	}

	protected void checkRight() {
		// If right sensor on line && left sensor is not : go right
		while (!leftOnBlack() && rightOnBlack()) {
			pilot.rotate(-5, true);

			// System.out.println("Right rotation");
			Delay.msDelay(20);

			if (Button.ESCAPE.isDown()) { // make sure that robot will stop
											// program if escape button is
											// pressed.
				System.exit(0);
				suppress();
			}
		}
	}

	@Override
	public boolean takeControl() {
		return true; // always true-so will follow line until another behaviour
						// takes control
	}

	@Override
	public void action() {
		supressed = false;

		while (!supressed) {
			pilot.forward();
			checkLeft();
			checkRight();

			if (Button.ESCAPE.isDown()) { // ensures exit button will work even
											// while robot is moving.
				System.exit(0);
				suppress();
			}

			Delay.msDelay(30);
		}
	}

	@Override
	public void suppress() {
		supressed = true;
	}

}
