package com.rb32.main;

import com.rb32.behaviours.LineFollowing;
import com.rb32.behaviours.StopBehaviour;
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

	public FollowingLine() {
		lightSensorR = new LightSensor(SensorPort.S1);
		lightSensorL = new LightSensor(SensorPort.S4);

		int whiteInitR = lightSensorR.getLightValue();

		lineFollowing = new LineFollowing(lightSensorL, lightSensorR,
				whiteInitR, null);

		Behavior[] behaviors = {lineFollowing};
		arbitrator = new Arbitrator(behaviors);

		arbitrator.start();
	}
	
	public static void main(String args[]) {
		FollowingLine followNow = new FollowingLine(); //creating the robot;
	}
}
