package com.rb34.main;

import java.util.ArrayList;

import com.rb34.behaviours.LineFollowing;
import com.rb34.behaviours.PathChoices;
import com.rb34.behaviours.TurnBehavior;
import com.rb34.behaviours.WaitBehavior;

import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import com.rb34.robot_interface.RobotScreen;

public class JunctionFollower {

	private Arbitrator arbitrator;
	
	private ArrayList<PathChoices> path1;
	
	private LightSensor lightSensorR;
	private LightSensor lightSensorL;
	
	private LineFollowing followLine;
	private TurnBehavior turnBehavior;
	private WaitBehavior waitBehavior;
	private static RobotScreen screen;
	
	public JunctionFollower(RobotScreen _screen) {
		this.screen = _screen;
		lightSensorR = new LightSensor(SensorPort.S1);
		lightSensorL = new LightSensor(SensorPort.S4);
		
		
		path1 = new ArrayList<PathChoices>();
		
		path1.add(PathChoices.FORWARD);
		//path1.add(PathChoices.FORWARD);
		//path1.add(PathChoices.FORWARD);
		//path1.add(PathChoices.RIGHT);
		path1.add(PathChoices.FORWARD);
		path1.add(PathChoices.RIGHT);
		path1.add(PathChoices.FORWARD);
		path1.add(PathChoices.FORWARD);
		path1.add(PathChoices.LEFT);
		/*path1.add(PathChoices.LEFT);
		path1.add(PathChoices.RIGHT);
		path1.add(PathChoices.FORWARD);
		path1.add(PathChoices.LEFT);*/
		
		
		turnBehavior = new TurnBehavior(lightSensorL, lightSensorR, screen);
		turnBehavior.setPath(path1);
		followLine = new LineFollowing(lightSensorL, lightSensorR, screen);
		waitBehavior = new WaitBehavior(turnBehavior, screen);
		
		Behavior[] behaviors = {followLine, turnBehavior, waitBehavior};
		arbitrator = new Arbitrator(behaviors);
		
		arbitrator.start();
		
	}
	
	public static void main(String args[]) {
		JunctionFollower robot = new JunctionFollower(screen);
	}
	
}