package com.rb34.main;



import java.util.ArrayList;

import rp.config.WheeledRobotConfiguration;
import rp.systems.WheeledRobotSystem;

import com.rb34.behaviours.DistanceKeeping;
import com.rb34.behaviours.LineFollowing;
import com.rb34.behaviours.TurnBehavior;
import com.rb34.general.PathChoices;
import com.rb34.robot_interface.RobotScreen;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.OpticalDistanceSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

//This is the test class putting together lineFollowing, turning at junctions and keeping a particular distance from obstacles
public class AvoidingObs {
	private Arbitrator arbitrator;
	private OpticalDistanceSensor irSensor;
	private final float MAX_DISTANCE = 10;

	private LightSensor lightSensorR;
	private LightSensor lightSensorL;

	private LineFollowing followLine;
	private TurnBehavior turnBehavior;
	private DistanceKeeping keepDistance;
	private RobotScreen screen;
	private String head;
	private WheeledRobotConfiguration ROBOT_CONFIG;
	private DifferentialPilot pilot;
	

	private ArrayList<PathChoices> path1;

	public AvoidingObs(String head) {
		lightSensorR = new LightSensor(SensorPort.S1);
		lightSensorL = new LightSensor(SensorPort.S4);
		irSensor = new OpticalDistanceSensor(SensorPort.S2);
		ROBOT_CONFIG = new WheeledRobotConfiguration (0.056f, 0.115f, 0.17f, Motor.A, Motor.C);
		pilot = new WheeledRobotSystem(ROBOT_CONFIG).getPilot();
		
		this.head = head;

		path1 = new ArrayList<PathChoices>();

		path1.add(PathChoices.FORWARD);
		path1.add(PathChoices.FORWARD);
		path1.add(PathChoices.FORWARD);
		path1.add(PathChoices.RIGHT);
		path1.add(PathChoices.FORWARD);
		path1.add(PathChoices.LEFT);

		followLine = new LineFollowing(lightSensorL, lightSensorR, screen, null, pilot);
		turnBehavior = new TurnBehavior(lightSensorL, lightSensorR, screen, followLine, null, pilot);
		keepDistance = new DistanceKeeping(MAX_DISTANCE, irSensor);

		Behavior[] behaviors = { followLine, turnBehavior, keepDistance };
		arbitrator = new Arbitrator(behaviors);

		arbitrator.start();
	}

	public static void main(String[] args) {
		String head = "east";
		AvoidingObs robot = new AvoidingObs(head);
	}
}