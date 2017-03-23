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

public class TurnBehavior implements Behavior
{
	private LightSensor lightSensorR;
	private LightSensor lightSensorL;
	private WheeledRobotConfiguration robotConfig;
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

	public TurnBehavior(LightSensor left, LightSensor right, RobotScreen _screen, LineFollowing followLine, String head,
			ShouldMove shouldMove)
	{

		this.shouldMove = shouldMove;

		lightSensorR = right;

		lightSensorL = left;

		this.screen = _screen;
		this.followLine = followLine;

		path = new ArrayList<>();
		path.clear();

		forceFirstAction = false;
		lastAction = false;
		
		this.head = head;

		robotConfig = new WheeledRobotConfiguration(0.059f, 0.115f, 0.17f, Motor.C, Motor.A);
		pilot = new WheeledRobotSystem(robotConfig).getPilot();
		
		pilot.setTravelSpeed((pilot.getMaxTravelSpeed() / 10) * 2);
		pilot.setRotateSpeed((pilot.getRotateMaxSpeed() / 10) * 2);
	}

	public void setPath(ArrayList<PathChoices> path)
	{
		this.path = path;
	}

	public ArrayList<PathChoices> getPath()
	{
		return this.path;
	}

	public boolean rightOnBlack()
	{
		if (lightSensorR.getLightValue() <= THRESHOLD)
		{
			return true;
		} else
		{
			return false;
		}
	}

	public boolean leftOnBlack()
	{
		if (lightSensorL.getLightValue() <= THRESHOLD)
		{
			return true;
		} else
		{
			return false;
		}
	}

	@Override
	public boolean takeControl()
	{
		if ((rightOnBlack() && leftOnBlack()) || forceFirstAction)
		{
			return true;
		} else
		{
			return false;
		}
	}

	@Override
	public void action()
	{
		// turnDirection = 4;

		screen.updateState("Path size: " + path.size());
		screen.updateState("Path size: " + path.size());
		screen.updateState("Path size: " + path.size());
		supressed = false;
		pilot.stop();
		readingL = lightSensorL.getLightValue();
		readingR = lightSensorR.getLightValue();

		if (path != null)
		{
			actionDone = false;

			if (path.isEmpty())
			{
				// Sound.beep();
				actionDone = true;
				lastAction = true;

			} else
			{
				// screen.updateLocation(x, y);
				turnDirection = path.get(0).ordinal();
				path.remove(0);

			}
		}

		// System.out.println("FIRST: " + (!forceFirstAction) + " LAST: " +
		// (!lastAction));

		switch (turnDirection)
		{
		case 0:
			if (!forceFirstAction) {
				pilot.travel(0.05, true);

				while (!supressed && pilot.isMoving())
				{

					if (Button.ESCAPE.isDown())
					{
						System.exit(0);
					}
				}

				pilot.rotate(90, true);
			} else {
				pilot.rotateLeft();
				
				while (!leftOnBlack())
				{
					if (Button.ESCAPE.isDown())
					{ // make sure that robot will stop program if escape button
						// is
						// pressed.
						System.exit(0);
						suppress();
					}
					Delay.msDelay(20);
				}
				pilot.stop();
			}
	
			if (!forceFirstAction && !lastAction)
			{
				updateCo(0);
			}
			updateDirection(0);
			screen.updateState("Left");
			break;
		case 1:
			if (!forceFirstAction)
			{
				pilot.travel(0.05, true);

				while (!supressed && pilot.isMoving())
				{

					if (Button.ESCAPE.isDown())
					{
						System.exit(0);
					}
				}

				pilot.rotate(-90, true);
			} else
			{
				pilot.rotateLeft();
				
				while (!rightOnBlack())
				{

					if (Button.ESCAPE.isDown())
					{ // make sure that robot will stop program if escape button
						// is
						// pressed.
						System.exit(0);
						suppress();
					}
					Delay.msDelay(20);
				}
				
				pilot.stop();
			}

			if (!forceFirstAction && !lastAction)
			{
				updateCo(1);
			}
			updateDirection(1);
			screen.updateState("Right");
			break;
		case 2:
			pilot.travel(0.05, true);
			if (!forceFirstAction && !lastAction)
			{
				updateCo(2);
			}
			updateDirection(2);
			screen.updateState("Forward");
			break;
		case 3:
			pilot.travel(0.05, true);

			while (!supressed && pilot.isMoving())
			{

				if (Button.ESCAPE.isDown())
				{
					System.exit(0);
				}
			}

			pilot.rotate(180, true);
			if (!forceFirstAction && !lastAction)
			{
				updateCo(3);
			}
			updateDirection(3);
			screen.updateState("Rotate");
			break;
		case 4:
			try
			{
				pilot.wait(TIMEOUT);
			} catch (InterruptedException e)
			{
				System.out.println("Something wrong while in stay command.");
			}
		}
		System.out.println("Heading: " + head);

		RobotStatusMessage msg = new RobotStatusMessage();
		msg.setRobotId(JunctionFollower.RobotId);
		msg.setX(x);
		msg.setY(y);
		msg.setOnJob(true);
		msg.setOnRoute(!actionDone);
		msg.setWaitingForNewPath(false);
		TrialMainNxt.client.send(msg);

		while (!supressed && pilot.isMoving())
		{

			if (Button.ESCAPE.isDown())
			{
				System.exit(0);
			}
		}

		/*
		 * if (lastAction) { redirectHead(); lastAction = false; }
		 * 
		 * while (!supressed && pilot.isMoving()) {
		 * 
		 * if (Button.ESCAPE.isDown()) { System.exit(0); } }
		 */
		setForceFirstAction(false);
		suppress();
	}

	@Override
	public void suppress()
	{
		supressed = true;
	}

	public boolean checkIfNoRoute()
	{
		if (actionDone && path.isEmpty())
		{
			shouldMove.setShouldMove(false);
			return true;
		} else
		{
			shouldMove.setShouldMove(true);
			return false;
		}
	}
	
	public void setX(int i)
	{
		x += i;
	}

	public void setY(int i)
	{
		y += i;
	}

	public void setAbsoluteX(int x)
	{
		this.x = x;
	}

	public void setAbsoluteY(int y)
	{
		this.y = y;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public void setForceFirstAction(boolean b)
	{
		forceFirstAction = b;
	}

	public void setPathFromMessage(ArrayList<PathChoices> path)
	{

		lastAction = false;
		this.path = path;
		// setForceFirstAction(true);

		/*
		 * followLine.doAction(path.get(0).ordinal());
		 * UpdateDirectionAndCo(path.get(0).ordinal());
		 * followLine.doFirstAction(); path.remove(0);
		 */

	}

	public void setFirstAction(int i)
	{
		firstAction = i;
	}

	public void updateDirection(int move)
	{
		int movement = move;

		switch (movement)
		{
		case 0: // left
			if (head.equals("north"))
			{
				head = "west";
			} else if (head.equals("east"))
			{
				head = "north";
			} else if (head.equals("south"))
			{
				head = "east";
			} else
			{
				head = "south";
			}
			break;
		case 1:// right
			if (head.equals("north"))
			{
				head = "east";
			} else if (head.equals("east"))
			{
				head = "south";
			} else if (head.equals("south"))
			{
				head = "west";
			} else
			{
				head = "north";
			}
			break;
		case 2: // forward
			head.equals(head);
			break;
		case 3: // rotate 180
			if (head.equals("north"))
			{
				head = "south";
			} else if (head.equals("east"))
			{
				head = "west";
			} else if (head.equals("south"))
			{
				head = "north";
			} else
			{
				head = "east";
			}
		}
	}

	public void updateCo(int move)
	{
		int movement = move;

		switch (movement)
		{
		case 0: // left
			if (head.equals("north"))
			{
				setX(-1);
			} else if (head.equals("east"))
			{
				setY(1);
			} else if (head.equals("south"))
			{
				setX(1);
			} else
			{
				setY(-1);
			}
			break;
		case 1:// right
			if (head.equals("north"))
			{
				setX(1);
			} else if (head.equals("east"))
			{
				setY(-1);
			} else if (head.equals("south"))
			{
				setX(1);
			} else
			{
				setY(1);
			}
			break;
		case 2: // forward
			if (head.equals("north"))
			{
				setY(1);
			} else if (head.equals("east"))
			{
				setX(1);
			} else if (head.equals("south"))
			{
				setY(-1);
			} else
			{
				setX(-1);
			}
			break;
		case 3: // rotate 180
			// System.out.println("AKJF
			// BUSKUFHBSADUSDGJSKADGYSAGDFYSAGDSKADYGYSADGSADGFYUSADGFAKSYDGASDGSADHGY");
			if (head.equals("north"))
			{
				setY(-1);
			} else if (head.equals("east"))
			{
				setX(-1);
			} else if (head.equals("south"))
			{
				setY(1);
			} else
			{
				setX(1);
			}
			break;
		}
	}
}
