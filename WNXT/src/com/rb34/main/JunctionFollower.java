package com.rb34.main;

import java.util.ArrayList;

import com.rb34.behaviours.LineFollowing;
import com.rb34.behaviours.ShouldMove;
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
import com.rb34.robot_interface.RobotScreen;

import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

//This is the class where all the robot behaviours are brought together. The class implements MessageListner, which allows networking to work with robot motion
public class JunctionFollower implements MessageListener {
	
	public static int RobotId;
	private static RobotScreen screen;
	private Arbitrator arbitrator;
	private ArrayList<PathChoices> path; // Received via bluetooth;
	private ArrayList<PathChoices> path1; // when we want to pre-define;
	private LightSensor lightSensorR;
	private LightSensor lightSensorL;
	private LineFollowing followLine;
	private TurnBehavior turnBehavior;
	private WaitBehavior waitBehavior;
	private String head;

	public JunctionFollower(RobotScreen _screen) {

		System.out.println("test");
		this.screen = _screen;
		lightSensorR = new LightSensor(SensorPort.S1);
		lightSensorL = new LightSensor(SensorPort.S4);
		path = new ArrayList<>();
		TrialMainNxt.client.addListener(this);
		
		ShouldMove shouldMove = new ShouldMove();
		
		head = new String();

		followLine = new LineFollowing(lightSensorL, lightSensorR, screen, shouldMove);
		turnBehavior = new TurnBehavior(lightSensorL, lightSensorR, screen,
				followLine, shouldMove);
		waitBehavior = new WaitBehavior(turnBehavior, screen);
		waitBehavior.setFocingBehav(true);

		Behavior[] behaviors = { followLine, turnBehavior, waitBehavior };
		arbitrator = new Arbitrator(behaviors);

		arbitrator.start();	
	}

	@Override
	public void recievedTestMessage(TestMessage msg) {

	}

	@Override
	public void recievedNewPathMessage(NewPathMessage msg) { //This is used by the network to set paths which the robot needs to follow
		
		System.out.println("Got path " + msg.getCommands().size());
		turnBehavior.setPathFromMessage(msg.getCommands());
		turnBehavior.setForceFirstAction(true);
		waitBehavior.setFocingBehav(false);
		waitBehavior.suppress();

		// screen.updateState("Path size2: "+msg.getCommands().size());
	}

	@Override
	public void recievedRobotStatusMessage(RobotStatusMessage msg) {

	}

	@Override
	public void recievedRobotInitMessage(RobotInitMessage msg) {
		RobotId = msg.getRobotId();
		turnBehavior.setAbsoluteX(msg.getX());
		turnBehavior.setAbsoluteY(msg.getY());
		
		if(msg.getHeading().equals("N"))
		{
			this.head = "north";
		}else if(msg.getHeading().equals("S"))
		{
			this.head = "south";
		}else if(msg.getHeading().equals("E"))
		{
			this.head = "east";
		}else if(msg.getHeading().equals("W"))
		{
			this.head = "west";
		}else
		{
			this.head = "HAHAHAAH";
		}
		turnBehavior.setHeading(this.head);
		waitBehavior.setFocingBehav(false);
		
		System.out.println("Init head: " + this.head);
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