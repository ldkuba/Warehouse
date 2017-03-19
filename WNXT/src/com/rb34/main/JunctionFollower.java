package com.rb34.main;

import java.util.ArrayList;

import com.rb34.behaviours.LineFollowing;
import com.rb34.behaviours.TurnBehavior;
import com.rb34.behaviours.WaitBehavior;
import com.rb34.dummy.TrialMainNxt;
import com.rb34.general.PathChoices;
import com.rb34.message.MessageListener;
import com.rb34.message.NewPathMessage;
import com.rb34.message.RobotInitMessage;
import com.rb34.message.RobotStatusMessage;
import com.rb34.message.TestMessage;
import com.rb34.network.Client;
import com.rb34.robot_interface.RobotScreen;

import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class JunctionFollower implements MessageListener
{
	public static int RobotId;
	
	private Arbitrator arbitrator;

	private ArrayList<PathChoices> path; // Received via bluetooth;
	private ArrayList<PathChoices> path1; // when we want to pre-define;

	private LightSensor lightSensorR;
	private LightSensor lightSensorL;

	private LineFollowing followLine;
	private TurnBehavior turnBehavior;
	private WaitBehavior waitBehavior;
	private static RobotScreen screen;

	public JunctionFollower(RobotScreen _screen)
	{
		this.screen = _screen;
		lightSensorR = new LightSensor(SensorPort.S1);
		lightSensorL = new LightSensor(SensorPort.S4);

		path = new ArrayList<>();
		path1 = new ArrayList<PathChoices>();

		path1.add(PathChoices.FORWARD);
		path1.add(PathChoices.FORWARD);
		path1.add(PathChoices.FORWARD);
		path1.add(PathChoices.LEFT);
		// path1.add(PathChoices.FORWARD);
		path1.add(PathChoices.FORWARD);
		// path1.add(PathChoices.FORWARD);
		// path1.add(PathChoices.RIGHT);
		// path1.add(PathChoices.FORWARD);
		// path1.add(PathChoices.FORWARD);
		// path1.add(PathChoices.RIGHT);
		// path1.add(PathChoices.FORWARD);
		// path1.add(PathChoices.LEFT);

		turnBehavior = new TurnBehavior(lightSensorL, lightSensorR, screen);
		
		TrialMainNxt.client.addListener(this);
		
		// turnBehavior.setPath(path1);
		followLine = new LineFollowing(lightSensorL, lightSensorR, screen);
		waitBehavior = new WaitBehavior(turnBehavior, screen);

		Behavior[] behaviors = { followLine, turnBehavior, waitBehavior };
		arbitrator = new Arbitrator(behaviors);
		arbitrator.start();
	}

	@Override
	public void recievedTestMessage(TestMessage msg)
	{
		
	}

	@Override
	public void recievedNewPathMessage(NewPathMessage msg)
	{
		turnBehavior.setPath(msg.getCommands());
	}

	@Override
	public void recievedRobotStatusMessage(RobotStatusMessage msg)
	{
		
	}

	@Override
	public void recievedRobotInitMessage(RobotInitMessage msg)
	{
		RobotId = msg.getRobotId();
	}
}