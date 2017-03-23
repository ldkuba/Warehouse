package com.rb34.behaviours;

import rp.config.WheeledRobotConfiguration;
import rp.systems.WheeledRobotSystem;
import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.addon.OpticalDistanceSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class DistanceKeeping implements Behavior {
	private Float maxDistance;
	private WheeledRobotConfiguration robotConfig;
	private DifferentialPilot pilot;
	private OpticalDistanceSensor irSensor;
	private double maxSpeed;
	private boolean running = true;
	private double currentRange;
	private double error;
	private double output;
	private boolean supressed = true;
	
	public DistanceKeeping(Float _maxDis, OpticalDistanceSensor _s) {
		maxDistance = _maxDis;
		irSensor = _s;
		
		robotConfig = new WheeledRobotConfiguration (0.059f, 0.115f, 0.17f, Motor.C, Motor.A);
		pilot = new WheeledRobotSystem (robotConfig).getPilot();
		pilot.setTravelSpeed((pilot.getMaxTravelSpeed()/10)*2);
		pilot.setRotateSpeed((pilot.getRotateMaxSpeed()/10)*2);
	}

	@Override
	public boolean takeControl() {
		currentRange = irSensor.getDistance();
		System.out.println("Current: "+currentRange);
		error = currentRange - maxDistance;
		System.out.println("Error: "+error);
		if (error < 200) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void action() {
		supressed  = false;
		System.out.println("In stopping action");
		pilot.stop();
		
		
		/*long delay = 100; // This is the time value that is used in the
		// constants' calculations as well as the loop.
		double kv = 15;
		double kp = (kv * 0.6); // The value of kp ki and kd were found using
		// the Ziegler�Nichols tuning method.
		double ki = (1.2 * (kv / delay));
		double kd = (0.6 * ((kv * delay) / 8));
		double error = 0;
		double previousOutput = 0;
		maxSpeed = pilot.getMaxTravelSpeed();
		
		output = Math.abs((kp * error) + (ki * (Math.abs(error) / delay))
				+ (kd * (Math.abs(error) / delay)));
		
		if (output > maxSpeed) {
			output = maxSpeed - 0.05;
		}
		// System.out.println("New speed:" + output);
		Delay.msDelay(delay);
		//pilot.setTravelSpeed(output);
		pilot.rotate(90,true);*/
		
		if (Button.ESCAPE.isDown()) { //make sure that robot will stop program if escape button is pressed.
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