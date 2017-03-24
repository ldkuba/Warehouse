package com.rb34.behaviours;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.addon.OpticalDistanceSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import rp.config.WheeledRobotConfiguration;
import rp.systems.WheeledRobotSystem;

// This behaviour will allow the robot to keep a specific distance away from obstacles/other robots. 

public class DistanceKeeping implements Behavior {

	private WheeledRobotConfiguration robotConfig;
	private DifferentialPilot pilot;
	private OpticalDistanceSensor irSensor;
	private final int THRESHOLD = 200;
	private Float maxDistance;
	private double maxSpeed;
	private double currentRange;
	private double error;
	private double output;
	private boolean running = true;
	private boolean supressed = true;

	public DistanceKeeping(Float _maxDis, OpticalDistanceSensor _s) { // The
																		// distance
																		// is
																		// passed
																		// in as
																		// a
																		// parameter
																		// so
																		// can
																		// be
																		// defined
																		// by
																		// user.
		maxDistance = _maxDis;
		irSensor = _s;

		robotConfig = new WheeledRobotConfiguration(0.059f, 0.115f, 0.17f,
				Motor.C, Motor.A);
		pilot = new WheeledRobotSystem(robotConfig).getPilot();
		pilot.setTravelSpeed((pilot.getMaxTravelSpeed() / 10) * 2);
		pilot.setRotateSpeed((pilot.getRotateMaxSpeed() / 10) * 2);
	}

	@Override
	public boolean takeControl() {
		currentRange = irSensor.getDistance();
		System.out.println("Current: " + currentRange);
		error = currentRange - maxDistance;
		System.out.println("Error: " + error);
		if (error < THRESHOLD) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void action() {
		supressed = false;
		System.out.println("In stopping action");
		
		pilot.stop(); // This means that while something is in the range i.e.
						// too close the robot will just wait where it is.

		if (Button.ESCAPE.isDown()) { // make sure that robot will stop program
										// if escape button is pressed.
			System.exit(0);
			suppress();
		}

		suppress();

	}

	@Override
	public void suppress() {
		supressed = true;

	}

}