package com.rb34.main;

import com.rb34.behaviours.LineFollowing;
import com.rb34.behaviours.StopBehaviour;

import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class FollowingLine {
	private Arbitrator arbitrator;
	private LightSensor lightSensorR;
	private LightSensor lightSensorL;
	private LineFollowing lineFollowing;
	private StopBehaviour stop;
	int whiteInitL;
	int whiteInitR;

	public FollowingLine() {
		lightSensorR = new LightSensor(SensorPort.S1);
		lightSensorL = new LightSensor(SensorPort.S4);
	
		while(whiteInitL == 0 || whiteInitR == 0) {
			whiteInitL = lightSensorL.getLightValue();
			whiteInitR = lightSensorR.getLightValue();
		}

		lineFollowing = new LineFollowing(lightSensorL, lightSensorR, null);

		Behavior[] behaviors = {lineFollowing};
		arbitrator = new Arbitrator(behaviors);

		arbitrator.start();
	}
	
	public static void main(String args[]) {
		FollowingLine followNow = new FollowingLine(); //creating the robot;
	}
}
