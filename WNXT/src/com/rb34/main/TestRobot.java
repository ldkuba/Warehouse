package com.rb34.main;

import java.io.PrintStream;
import java.util.ArrayList;

import rp.config.WheeledRobotConfiguration;
import rp.systems.WheeledRobotSystem;

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
import com.rb34.network.Client;
import com.rb34.robot_interface.RobotScreen;

import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.comm.RConsole;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class TestRobot {
	private Arbitrator arbitrator;
	private ArrayList<PathChoices> path; // when we want to pre-define;
	private LightSensor lightSensorR;
	private LightSensor lightSensorL;
	private LineFollowing followLine;
	private TurnBehavior turnBehavior;
	private static RobotScreen screen;
	private WheeledRobotConfiguration ROBOT_CONFIG;
	private DifferentialPilot pilot;
	private String head;
	
	public TestRobot() {
		screen = new RobotScreen();
		lightSensorR = new LightSensor(SensorPort.S1);
		lightSensorL = new LightSensor(SensorPort.S4);
		ROBOT_CONFIG = new WheeledRobotConfiguration (0.056f, 0.115f, 0.17f, Motor.A, Motor.C);
		pilot = new WheeledRobotSystem(ROBOT_CONFIG).getPilot();
		
		path = new ArrayList<>();
		path.add(PathChoices.LEFT);
		path.add(PathChoices.FORWARD);
		path.add(PathChoices.FORWARD);
		path.add(PathChoices.RIGHT);
		
		ShouldMove shouldMove = new ShouldMove();
		
		followLine = new LineFollowing(lightSensorL, lightSensorR, screen, shouldMove, pilot);
		turnBehavior = new TurnBehavior(lightSensorL, lightSensorR, screen,
				followLine, shouldMove, pilot);
		
		Behavior[] behaviors = { followLine, turnBehavior };
		arbitrator = new Arbitrator(behaviors);
		arbitrator.start();
	}
	
	public static void main(String args[]) {
		TestRobot robot = new TestRobot();
	}
}