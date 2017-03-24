package com.rb34.behaviours;

import rp.config.WheeledRobotConfiguration;
import rp.systems.WheeledRobotSystem;
import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;
import com.rb34.robot_interface.RobotScreen;

//This behaviour uses the light sensors to make the robot follow a line correctly.

public class LineFollowing implements Behavior {
	private boolean supressed = true;
	private boolean doFirstAction = false;
	private LightSensor lightSensorR;
	private LightSensor lightSensorL;
	private DifferentialPilot pilot;
	private RobotScreen screen;
	private ShouldMove shouldMove;

	public LineFollowing(LightSensor left, LightSensor right,
			RobotScreen _screen, ShouldMove shouldMove, DifferentialPilot pilot) {
		this.screen = _screen;
		lightSensorR = right;
		lightSensorL = left;

		this.shouldMove = shouldMove;
		this.pilot = pilot;
		pilot.setTravelSpeed((pilot.getMaxTravelSpeed() / 10)*2);
		pilot.setRotateSpeed((pilot.getRotateMaxSpeed() / 10)*2);

	}

	public boolean rightOnBlack() { // Checks if right sensor detects black
		if (lightSensorR.getLightValue() <= 40) {
			return true;
		} else {
			return false;
		}
	}

	public boolean leftOnBlack() { // Checks if the left sensor detects black
		if (lightSensorL.getLightValue() <= 40) {
			return true;
		} else {
			return false;
		}
	}

	protected void checkLeft() { // If left sensor on line && right sensor is
									// not => Go left
		while (leftOnBlack() && !rightOnBlack()) {
			pilot.rotateLeft();

			if (Button.ESCAPE.isDown()) { // make sure that robot will stop
											// program if escape button is
				// pressed.
				System.exit(0);
				suppress();
			}
			Delay.msDelay(20);
		}
	}

	protected void checkRight() { // If right sensor on line && left sensor is
									// not => Go right
		while (!leftOnBlack() && rightOnBlack()) {
			pilot.rotateRight();

			if (Button.ESCAPE.isDown()) { // make sure that robot will stop
											// program if escape button is
				// pressed.
				System.exit(0);
				suppress();
			}
			Delay.msDelay(20);
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
		pilot.stop();

		while (!supressed) {

			if (shouldMove.isShouldMove()) {
				pilot.forward();

				screen.updateState("Moving foward");
				checkLeft();
				checkRight();

				if (Button.ESCAPE.isDown()) { // make sure that robot will stop
												// program if escape button is
					// pressed.
					suppress();
				}
				Delay.msDelay(30);
			}
		}
	}

	@Override
	public void suppress() {
		supressed = true;
	}

}
