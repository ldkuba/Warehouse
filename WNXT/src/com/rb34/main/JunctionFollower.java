package com.rb34.main;

import java.io.PrintStream;
import java.util.ArrayList;
import com.rb34.behaviours.LineFollowing;
import com.rb34.behaviours.TurnBehavior;
import com.rb34.behaviours.WaitBehavior;
import com.rb34.dummy.TrialMainNxt;
import com.rb34.general.PathChoices;
import com.rb34.message.LocationTypeMessage;
import com.rb34.message.MessageListener;
import com.rb34.message.NewPathMessage;
import com.rb34.message.RobotInitMessage;
import com.rb34.message.RobotStatusMessage;
import com.rb34.message.TestMessage;
import com.rb34.network.Client;
import com.rb34.robot_interface.RobotScreen;
import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.comm.RConsole;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class JunctionFollower implements MessageListener {
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

	public JunctionFollower(RobotScreen _screen) {

		System.out.println("test");
		this.screen = _screen;
		lightSensorR = new LightSensor(SensorPort.S1);
		lightSensorL = new LightSensor(SensorPort.S4);
		path = new ArrayList<>();
		//TrialMainNxt.client.addListener(this);
		
		path1.add(PathChoices.RIGHT);
		path1.add(PathChoices.FORWARD);
		path1.add(PathChoices.FORWARD);
		

		followLine = new LineFollowing(lightSensorL, lightSensorR, screen);
		turnBehavior = new TurnBehavior(lightSensorL, lightSensorR, screen,
				followLine);
		
		turnBehavior.setPath(path1);
		turnBehavior.setForceFirstAction(true);
		waitBehavior = new WaitBehavior(turnBehavior, screen, RobotId);

		Behavior[] behaviors = { followLine, turnBehavior, waitBehavior };
		arbitrator = new Arbitrator(behaviors);
		arbitrator.start();
	}

	@Override
	public void recievedTestMessage(TestMessage msg) {

	}

	@Override
	public void recievedNewPathMessage(NewPathMessage msg) {
		// System.exit(-1);
		
		System.out.println("Got path");
		
		turnBehavior.setPathFromMessage(msg.getCommands());
		turnBehavior.setForceFirstAction(true);
		waitBehavior.suppress();

		// screen.updateState("Path size2: "+msg.getCommands().size());
	}

	@Override
	public void recievedRobotStatusMessage(RobotStatusMessage msg) {

	}

	@Override
	public void recievedRobotInitMessage(RobotInitMessage msg) {
		RobotId = msg.getRobotId();
		turnBehavior.setX(msg.getX());
		turnBehavior.setY(msg.getY());
	}

	@Override
	public void recievedLocationTypeMessage(LocationTypeMessage msg) {

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (msg.getLocationType() == 0) {
			this.screen.updateState("AT ITEM " + msg.getItemId());
			screen.setItemPickUpInfo(msg.getItemCount());
			waitBehavior.setAtPickup(true);
			waitBehavior.setItemCount(msg.getItemCount());
			waitBehavior.setPickingState("picking");
			this.screen.printPickingState();
		} else {
			waitBehavior.setatDropoff(true);
			this.screen.printDropOffState();
		}
		
		
	}
}