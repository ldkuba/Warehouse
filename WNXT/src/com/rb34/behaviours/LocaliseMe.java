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

public class LocaliseMe implements Behavior {
	private boolean needsLocalising;
	private boolean suppressed;
	
	public LocaliseMe() {
		needsLocalising = true;
	}

	@Override
	public boolean takeControl() {
		return needsLocalising;
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void suppress() {
		suppressed = true;
	}
	
	public void setNeedsLocalising(boolean b) {
		needsLocalising = b;
	}
	
	public boolean getNeedsLocalising() {
		return needsLocalising;
	}
	
}