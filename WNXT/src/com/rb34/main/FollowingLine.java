package com.rb34.main;

import com.rb34.behaviours.LineFollowing;
import com.rb34.behaviours.StopBehaviour;
import com.rb34.robot_interface.RobotScreen;

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
	private RobotScreen screen;

	public FollowingLine() {
		lightSensorR = new LightSensor(SensorPort.S1);
		lightSensorL = new LightSensor(SensorPort.S4);
		screen = new RobotScreen();

		lineFollowing = new LineFollowing(lightSensorL, lightSensorR, screen);

		Behavior[] behaviors = {lineFollowing};
		arbitrator = new Arbitrator(behaviors);

		arbitrator.start();
	}
	
	public static void main(String args[]) {
		FollowingLine followNow = new FollowingLine(); //creating the robot;
	}
}
