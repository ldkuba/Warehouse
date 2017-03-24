package com.rb34.main;

import com.rb34.behaviours.LineFollowing;
import com.rb34.behaviours.ShouldMove;
import com.rb34.behaviours.StopBehaviour;
import com.rb34.robot_interface.RobotScreen;

import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

//This is the test class that checks used for making the robot follow lines.
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
		ShouldMove shouldMove = new ShouldMove();
		shouldMove.setShouldMove(true);

		lineFollowing = new LineFollowing(lightSensorL, lightSensorR, screen, shouldMove);

		Behavior[] behaviors = {lineFollowing};
		arbitrator = new Arbitrator(behaviors);

		arbitrator.start();
	}
	
	public static void main(String args[]) {
		FollowingLine followNow = new FollowingLine(); //creating the robot;
	}
}
