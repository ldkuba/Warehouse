package com.rb32.main;

import java.util.ArrayList;
import java.util.Random;

import com.rb32.behaviours.LineFollowing;
import com.rb32.behaviours.TurnBehavior;

import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;
import com.rb32.main.JunctionFollower.PathChoices;

public class JunctionFollower {
	
	private boolean running = true;

	private Arbitrator arbitrator;

	public enum PathChoices {
		LEFT, RIGHT, FORWARD
	}
	
	private ArrayList<PathChoices> path1;
	
	private LightSensor lightSensorR;
	private LightSensor lightSensorL;
	
	private LineFollowing followLine;
	private TurnBehavior turnBehavior;
	
	public JunctionFollower() {
		lightSensorR = new LightSensor(SensorPort.S1);
		lightSensorL = new LightSensor(SensorPort.S4);
		
		path1 = new ArrayList<PathChoices>();
		
		path1.add(PathChoices.FORWARD);
		path1.add(PathChoices.FORWARD);
		path1.add(PathChoices.FORWARD);
		path1.add(PathChoices.RIGHT);
		path1.add(PathChoices.FORWARD);
		path1.add(PathChoices.LEFT);
		/*path1.add(PathChoices.LEFT);
		path1.add(PathChoices.RIGHT);
		path1.add(PathChoices.FORWARD);
		path1.add(PathChoices.LEFT);*/
		
		
		int whiteInitL = lightSensorL.getLightValue();
		int whiteInitR = lightSensorR.getLightValue();
		
		turnBehavior = new TurnBehavior(lightSensorL, lightSensorR,whiteInitR, path1);
		followLine = new LineFollowing(lightSensorL, lightSensorR,whiteInitR, turnBehavior);
		
		Behavior[] behaviors = {followLine, turnBehavior};
		arbitrator = new Arbitrator(behaviors);
		
		arbitrator.start();
		
	}
	
	public static void main(String args[]) {
		JunctionFollower robot = new JunctionFollower();
	}
	
}