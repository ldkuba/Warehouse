package com.rb34.behaviours;

import java.util.ArrayList;

import com.rb34.general.PathChoices;
import com.rb34.message.MessageListener;
import com.rb34.message.NewPathMessage;
import com.rb34.message.RobotStatusMessage;
import com.rb34.message.TestMessage;
import com.rb34.robot_interface.RobotScreen;

import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class TurnBehavior implements Behavior, MessageListener {
	private LightSensor lightSensorR;
	private LightSensor lightSensorL;
	private DifferentialPilot pilot;
	private RobotScreen screen;
	private int turnDirection;
	private boolean supressed;
	private final int THRESHOLD = 40;
	private String head = "east";
	private int x = 0;
	private int y = 0;
	// final static Logger logger = Logger.getLogger(TurnBehavior.class);

	private ArrayList<PathChoices> path;
	private boolean actionDone;
	int readingL;
	int readingR;
	int whiteInitR;
	int whiteInitL;

	public TurnBehavior(LightSensor left, LightSensor right, RobotScreen _screen) {
		lightSensorR = right;
		lightSensorL = left;
		this.screen = _screen;

		path = new ArrayList<>();
		path.clear();
		
		pilot = new DifferentialPilot(56, 120, Motor.A, Motor.B);

		pilot.setTravelSpeed(150);
	}

	public void setPath(ArrayList<PathChoices> path) {
		this.path = path;
	}

	public boolean rightOnBlack() {
		if (lightSensorR.getLightValue() <= THRESHOLD) {
			return true;
		} else {
			return false;
		}
	}

	public boolean leftOnBlack() {
		if (lightSensorL.getLightValue() <= THRESHOLD) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean takeControl() {
		if (rightOnBlack() && leftOnBlack()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void action() {
		screen.printLocation(x, y);
		supressed = false;
		pilot.stop();

		readingL = lightSensorL.getLightValue();
		readingR = lightSensorR.getLightValue();

		if (path != null) {
			if (path.isEmpty()) {
				actionDone = true;
			} else if (!path.isEmpty()) {
				turnDirection = path.get(0).ordinal();
				path.remove(0);
				actionDone = false;
			}
		}

		switch (turnDirection) {
		case 0:
			pilot.arc(80.5, 90, true);
			UpdateDirectionAndCo(0);
			screen.printState("Left");
			break;
		case 1:
			pilot.arc(-80.5, -90, true);
			UpdateDirectionAndCo(1);
			screen.printState("Right");
			break;
		case 2:
			pilot.travel(75.0, true);
			UpdateDirectionAndCo(2);
			screen.printState("Forward");
			break;
		case 3:
			pilot.rotate(180, true);
			UpdateDirectionAndCo(3);
			screen.printState("Rotate");
			break;
		}

		while (!supressed && pilot.isMoving()) {

			if (Button.ESCAPE.isDown()) {
				System.exit(0);
			}
		}

		actionDone = true;
		suppress();
	}

	@Override
	public void suppress() {
		supressed = true;
	}

	public boolean checkIfNoRoute() {
		if (actionDone && path.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
	
	public void setX(int i) {
		x += i;
	}
	
	public void setY(int i) {
		y += i;
	}
	
	public void UpdateDirectionAndCo(int move) {
		int movement = move;
		
		switch (movement) {
		case 0: //left
			if (head.equals("north")) {
				head = "west";
				setX(-1);
			} else if (head.equals("east")) {
				head = "north";
				setY(1);
			} else if (head.equals("south")) {
				head = "east";
				setX(1);
			} else {
				head = "south";
				setY(-1);
			}
			break;
		case 1://right
			if (head.equals("north")) {
				head = "east";
				setX(1);
			} else if (head.equals("east")) {
				head = "south";
				setY(-1);
			} else if (head.equals("south")) {
				head = "west";
				setX(1);
			} else {
				head = "north";
				setY(1);
			}
			break;
		case 2: //forward
			if (head.equals("north")) {
				setY(1);
			} else if (head.equals("east")) {
				setX(1);
			} else if (head.equals("south")) {
				setY(-1);
			} else {
				setX(-1);
			}
			break;
		case 3: //rotate 180
			if (head.equals("north")) {
				head = "south";
			} else if (head.equals("east")) {
				head = "west";
			} else if (head.equals("south")) {
				head = "north";
			} else {
				head = "east";
			}
		}	
	}
	
	public void forceFirstCommand(int i) {
		switch (i) {
		case 0:
			pilot.arc(80.5, 90, true);
			UpdateDirectionAndCo(0);
			screen.printState("Left");
			break;
		case 1:
			pilot.arc(-80.5, -90, true);
			UpdateDirectionAndCo(1);
			screen.printState("Right");
			break;
		case 2:
			pilot.travel(75.0, true);
			UpdateDirectionAndCo(2);
			screen.printState("Forward");
			break;
		case 3:
			pilot.rotate(180, true);
			UpdateDirectionAndCo(3);
			screen.printState("Rotate");
			break;
		}
	}

	@Override
	public void receivedTestMessage(TestMessage msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void recievedNewPathMessage(NewPathMessage msg) {
		this.path = msg.getCommands();
		
	}

	@Override
	public void recievedRobotStatusMessage(RobotStatusMessage msg) {
		// TODO Auto-generated method stub
		
	}
}