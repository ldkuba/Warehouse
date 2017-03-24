package com.rb34.main;

import rp.config.WheeledRobotConfiguration;
import rp.systems.WheeledRobotSystem;

import com.rb34.behaviours.LineFollowing;
import com.rb34.behaviours.ShouldMove;
import com.rb34.behaviours.StopBehaviour;
import com.rb34.robot_interface.RobotScreen;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;
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
	private WheeledRobotConfiguration ROBOT_CONFIG;
	private DifferentialPilot pilot;

	public FollowingLine() {
		lightSensorR = new LightSensor(SensorPort.S1);
		lightSensorL = new LightSensor(SensorPort.S4);
		screen = new RobotScreen();
		ShouldMove shouldMove = new ShouldMove();
		shouldMove.setShouldMove(true);
		ROBOT_CONFIG = new WheeledRobotConfiguration (0.056f, 0.115f, 0.17f, Motor.A, Motor.C);
		pilot = new WheeledRobotSystem(ROBOT_CONFIG).getPilot();

		lineFollowing = new LineFollowing(lightSensorL, lightSensorR, screen, shouldMove, pilot);

		Behavior[] behaviors = {lineFollowing};
		arbitrator = new Arbitrator(behaviors);

		arbitrator.start();
	}
	
	public static void main(String args[]) {
		FollowingLine followNow = new FollowingLine(); //creating the robot;
	}
}
