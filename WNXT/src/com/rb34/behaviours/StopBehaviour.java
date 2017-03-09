package com.rb34.behaviours;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class StopBehaviour implements Behavior {
	private DifferentialPilot pilot;
	private boolean supressed;

	public StopBehaviour() {
		pilot = new DifferentialPilot(0.056f, 0.0113f, Motor.A, Motor.B);
		pilot.setTravelSpeed(1000.0);
		pilot.setAcceleration(2000);
		supressed = true;
	}

	@Override
	public boolean takeControl() {
		if (Button.ESCAPE.isDown()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void action() {
		supressed = false;
			System.exit(0);
			suppress();
	}

	@Override
	public void suppress() {
		supressed = true;

	}

}