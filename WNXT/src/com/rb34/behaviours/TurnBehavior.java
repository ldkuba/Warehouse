package com.rb34.behaviours;

import java.util.ArrayList;

import rp.config.WheeledRobotConfiguration;
import rp.systems.WheeledRobotSystem;

import com.rb34.dummy.TrialMainNxt;
import com.rb34.general.PathChoices;
import com.rb34.main.JunctionFollower;
import com.rb34.message.NewPathMessage;
import com.rb34.message.RobotStatusMessage;
import com.rb34.robot_interface.RobotScreen;

import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.Sound;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;

public class TurnBehavior implements Behavior {
	private LightSensor lightSensorR;
	private LightSensor lightSensorL;
	private DifferentialPilot pilot;
	private RobotScreen screen;
	private LineFollowing followLine;

	private final int THRESHOLD = 40;
	private int turnDirection;
	private int readingL;
	private int readingR;
	private int whiteInitR;
	private int whiteInitL;
	private int x = 0;
	private int y = 0;
	private int firstAction;

	private final long TIMEOUT = 0;

	private boolean supressed;
	private boolean actionDone;
	private boolean lastAction;
	private static boolean forceFirstAction;
	private ShouldMove shouldMove;

	private String head;
	private ArrayList<PathChoices> path;

	public TurnBehavior(LightSensor left, LightSensor right,
			RobotScreen _screen, LineFollowing followLine, ShouldMove shouldMove, DifferentialPilot pilot) {

		this.shouldMove = shouldMove;
		lightSensorR = right;
		lightSensorL = left;
		this.screen = _screen;
		this.followLine = followLine;

		path = new ArrayList<>();
		path.clear();

		forceFirstAction = false;
		lastAction = false;

		this.pilot = pilot;

		pilot.setTravelSpeed((pilot.getMaxTravelSpeed() / 10) * 2);
		pilot.setRotateSpeed((pilot.getRotateMaxSpeed() / 10) * 2);
	}


	// This behaviour will take control if both sensors see black, meaning that
	// the robot is at a junction, or if the first action is being forced. First
	// action is forced when a new path is given.
	@Override
	public boolean takeControl() {
		if ((rightOnBlack() && leftOnBlack()) || forceFirstAction) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void action() {
		supressed = false;
		pilot.stop();
		readingL = lightSensorL.getLightValue();
		readingR = lightSensorR.getLightValue();

		if (path != null) {
			actionDone = false;
			if (path.isEmpty()) {
				actionDone = true;
				lastAction = true;

			} else {
				turnDirection = path.get(0).ordinal();
				path.remove(0);
			}
		}

		switch (turnDirection) {
		case 0: // turning left
			if (!forceFirstAction) { // if this is not the first action robot
										// will move forward a little and then
										// turn 90 degrees to the left. If this
										// is the first
										// action then robot will turn left
										// until left sensor sees black.
				pilot.travel(0.05, true);

				while (!supressed && pilot.isMoving()) {
					if (Button.ESCAPE.isDown()) {
						System.exit(0);
					}
				}

				pilot.rotate(90, true);
			} else
			{
				pilot.travel(0.05, true);

				while (!supressed && pilot.isMoving())
				{

					if (Button.ESCAPE.isDown())
					{
						System.exit(0);
					}
				}

				pilot.rotateLeft();

				while (!leftOnBlack()) {
					if (Button.ESCAPE.isDown()) { // make sure that robot will
													// stop program if escape
													// button
						// is
						// pressed.
						System.exit(0);
						suppress();
					}
					Delay.msDelay(20);
				}
				pilot.stop();
			}


			if (!lastAction) { // Makes sure that coordinates are updated,
								// unless this is the last action, as update is
								// not needed at that point.
				updateCo(0);
				updateDirection(0);
			}
			updateDirection(0); // Updates the heading of the robot after every
								// action so that coordinates can be updated
								// correctly
			screen.updateState("Left");
			break;
		case 1: // turning right
			if (!forceFirstAction) { // if this is not the first action robot
										// will move forward a little and then
										// turn 90 degrees to the right. If this
										// is the first action then robot will
										// turn right until right sensor sees
										// black.

				pilot.travel(0.05, true);

				while (!supressed && pilot.isMoving()) {
					if (Button.ESCAPE.isDown()) {
						System.exit(0);
					}
				}

				pilot.rotate(-90, true);
			} else {
				pilot.rotateRight();

				while (!rightOnBlack()) {
					if (Button.ESCAPE.isDown()) { // make sure that robot will
													// stop program if escape
													// button
													// is
													// pressed.
						System.exit(0);
						suppress();
					}
					Delay.msDelay(20);
				}

				pilot.stop();
			}

			if (!lastAction) {
				updateCo(1);
				updateDirection(1);
			}

			screen.updateState("Right");
			break;
		case 2: // moving forward
			pilot.travel(0.05, true);
			if (!lastAction) {
				updateCo(2);
				updateDirection(2);
			}

			screen.updateState("Forward");
			break;
		case 3:

			System.out.println("180");

			if (!forceFirstAction)
			{
				pilot.travel(0.05, true);
			}

			while (!supressed && pilot.isMoving()) {
				if (Button.ESCAPE.isDown()) {
					System.exit(0);
				}
			}
			pilot.rotate(195, true);

			if (!lastAction)
			{
				updateCo(3);
				updateDirection(3);
			}

			screen.updateState("Rotate");
			break;
		case 4: // waiting for 1 time unit. (1 time unit is time taken to go
				// from one junction to another)
			try {
				pilot.wait(TIMEOUT);
			} catch (InterruptedException e) {
				System.out.println("Something wrong while in stay command.");
			}
		}

		RobotStatusMessage msg = new RobotStatusMessage(); // Sending message to
															// PC over
															// bluetooth.
		msg.setRobotId(JunctionFollower.RobotId);
		msg.setX(x);
		msg.setY(y);
		msg.setHeading(this.head);
		msg.setOnJob(true);
		msg.setOnRoute(!actionDone);
		msg.setWaitingForNewPath(false);
		TrialMainNxt.client.send(msg);

		while (!supressed && pilot.isMoving()) {
			if (Button.ESCAPE.isDown()) {
				System.exit(0);
			}
		}
		setForceFirstAction(false); // makes sure forceFirstAction is set to
									// false if it was true before the action
									// was done.
		suppress();
	}

	@Override
	public void suppress() {
		supressed = true;
	}

	public void setPathFromMessage(ArrayList<PathChoices> path) { // This method
																	// is used
																	// to set
																	// the
																	// robots
																	// path
																	// which is
																	// being
																	// sent via
																	// bluetooth
		lastAction = false;
		this.path = path;
	}

	public ArrayList<PathChoices> getPath() { // Will return the current path
												// that the robot is working on.
		return this.path;
	}

	public void setHeading(String heading) {
		this.head = heading;
	}

	public String getHeading() { // This will return the heading that the robot
									// currently has. Heading is where the robot
									// is facing: north, south, east or west.
		return this.head;
	}

	public boolean rightOnBlack() { // This will return true if the right sensor
									// is detecting black.
		if (lightSensorR.getLightValue() <= THRESHOLD) {
			return true;
		} else {
			return false;
		}
	}

	public boolean leftOnBlack() { // This will return true if the left sensor
									// is detecting black.
		if (lightSensorL.getLightValue() <= THRESHOLD) {
			return true;
		} else {
			return false;
		}
	}

	// This will return true if the robot is not meant to move.
	public boolean checkIfNoRoute() {
		if (actionDone && path.isEmpty()) {
			shouldMove.setShouldMove(false);
			return true;
		} else {
			shouldMove.setShouldMove(true);
			return false;
		}
	}

	public void setX(int i) {
		x += i;
	}

	public void setY(int i) {
		y += i;
	}

	public void setAbsoluteX(int x) { // This is used to set the x coordinate
										// every time a new path is sent over
										// bluetooth
		this.x = x;
	}

	public void setAbsoluteY(int y) { // This is used to set the y coordinate
										// every time a new path is sent over
										// bluetooth
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setForceFirstAction(boolean b) {
		forceFirstAction = b;
	}

	public void setFirstAction(int i) {
		firstAction = i;
	}

	public void updateDirection(int move) { // Will update the direction of the
											// robot depending the previous head
											// and the move it just made.
		int movement = move;

		switch (movement) {
		case 0: // left
			if (head.equals("north")) {
				head = "west";
			} else if (head.equals("east")) {
				head = "north";
			} else if (head.equals("south")) {
				head = "east";
			} else {
				head = "south";
			}
			break;
		case 1:// right
			if (head.equals("north")) {
				head = "east";
			} else if (head.equals("east")) {
				head = "south";
			} else if (head.equals("south")) {
				head = "west";
			} else {
				head = "north";
			}
			break;
		case 2: // forward
			head.equals(head);
			break;
		case 3: // rotate 180
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

	public void updateCo(int move) { // Will update the coordinates of the robot
										// depending on its head and the move it
										// just made.
		int movement = move;

		switch (movement) {
		case 0: // left
			if (head.equals("north")) {
				setX(-1);
			} else if (head.equals("east")) {
				setY(1);
			} else if (head.equals("south")) {
				setX(1);
			} else {
				setY(-1);
			}
			break;
		case 1:// right
			if (head.equals("north")) {
				setX(1);
			} else if (head.equals("east")) {
				setY(-1);
			} else if (head.equals("south")) {
				setX(-1);
			} else {
				setY(1);
			}
			break;
		case 2: // forward
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
		case 3: // rotate 180
			if (head.equals("north")) {
				setY(-1);
			} else if (head.equals("east")) {
				setX(-1);
			} else if (head.equals("south")) {
				setY(1);
			} else {
				setX(1);
			}
			break;
		}
	}
}
