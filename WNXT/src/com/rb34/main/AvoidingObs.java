package com.rb34.main;

import java.util.ArrayList;
import java.util.Random;

import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;

import com.rb34.behaviours.DistanceKeeping;
import com.rb34.behaviours.LineFollowing;
import com.rb34.behaviours.PathChoices;
import com.rb34.behaviours.TurnBehavior;

import lejos.nxt.addon.OpticalDistanceSensor;

public class AvoidingObs {
	private Arbitrator arbitrator;
	private OpticalDistanceSensor irSensor;
	private final float MAX_DISTANCE = 10;

	private LightSensor lightSensorR;
	private LightSensor lightSensorL;

	private LineFollowing followLine;
	private TurnBehavior turnBehavior;
	private DistanceKeeping keepDistance;

	private ArrayList<PathChoices> path1;

	public AvoidingObs() {
		lightSensorR = new LightSensor(SensorPort.S1);
		lightSensorL = new LightSensor(SensorPort.S4);
		irSensor = new OpticalDistanceSensor(SensorPort.S2);

		path1 = new ArrayList<PathChoices>();

		path1.add(PathChoices.FORWARD);
		path1.add(PathChoices.FORWARD);
		path1.add(PathChoices.FORWARD);
		path1.add(PathChoices.RIGHT);
		path1.add(PathChoices.FORWARD);
		path1.add(PathChoices.LEFT);

		int whiteInitL = lightSensorL.getLightValue();
		int whiteInitR = lightSensorR.getLightValue();

		turnBehavior = new TurnBehavior(lightSensorL, lightSensorR);
		turnBehavior.setPath(path1);
		followLine = new LineFollowing(lightSensorL, lightSensorR, turnBehavior);
		keepDistance = new DistanceKeeping(MAX_DISTANCE, irSensor);

		Behavior[] behaviors = { followLine, turnBehavior, keepDistance };
		arbitrator = new Arbitrator(behaviors);

		arbitrator.start();
	}

	public static void main(String[] args) {
		AvoidingObs robot = new AvoidingObs();
	}
}