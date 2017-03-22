package com.rb34.behaviours;

import rp.config.WheeledRobotConfiguration;
import rp.systems.WheeledRobotSystem;
import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class StopBehaviour implements Behavior {
	private WheeledRobotConfiguration robotConfig;
	private DifferentialPilot pilot;
	private boolean supressed;

	public StopBehaviour() {
		robotConfig = new WheeledRobotConfiguration (0.059f, 0.115f, 0.17f, Motor.A, Motor.C);
		pilot = new WheeledRobotSystem (robotConfig).getPilot();
		pilot.setTravelSpeed((pilot.getMaxTravelSpeed()/10)*2);
		pilot.setRotateSpeed((pilot.getRotateMaxSpeed()/10)*2);
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