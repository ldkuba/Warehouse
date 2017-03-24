package com.rb34.behaviours;

import rp.config.WheeledRobotConfiguration;
import rp.robotics.mapping.GridMap;
import rp.systems.WheeledRobotSystem;
import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.addon.OpticalDistanceSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;

import com.rb34.localisation.FindMyLocation;
import com.rb34.localisation.Result;
import com.rb34.robot_interface.RobotScreen;

public class LocaliseMe implements Behavior {
	private boolean needsLocalising;
	private boolean suppressed;
	private GridMap gridMap;
	private DifferentialPilot pilot;
	FindMyLocation findMyLocation;
	LightSensor rightSensor;
	LightSensor leftSensor;
	OpticalDistanceSensor ranger;
	int x;
	int y;
	
	
	public LocaliseMe(GridMap gridMap, DifferentialPilot pilot, LightSensor rightSensor, LightSensor leftSensor, OpticalDistanceSensor ranger) {
		needsLocalising = true;
		this.gridMap = gridMap;
		this.pilot = pilot;
		this.rightSensor = rightSensor;
		this.leftSensor = leftSensor;
		this.ranger = ranger;
	}

	@Override
	public boolean takeControl() {
		return needsLocalising;
	}

	@Override
	public void action() {
		findMyLocation = new FindMyLocation ();
		Result result = findMyLocation.myLocation(gridMap, pilot, rightSensor, leftSensor, ranger);
	  	x = result.getX();
	  	y = result.getY();
	  	//SEND RESULT MESSAGE
	  	needsLocalising = false;
	  	suppress();
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
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
}