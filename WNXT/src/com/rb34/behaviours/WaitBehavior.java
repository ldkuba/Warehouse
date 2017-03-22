package com.rb34.behaviours;

import java.io.PrintStream;

import com.rb34.dummy.TrialMainNxt;
import com.rb34.message.RobotStatusMessage;
import com.rb34.robot_interface.RobotScreen;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.Sound;
import lejos.nxt.comm.RConsole;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;

public class WaitBehavior implements Behavior
{
	private DifferentialPilot pilot;
	private TurnBehavior behavior;
	private RobotScreen screen;
	private boolean supressed;
	private int itemCount;
	private int releaseCounter;
	private int toRelease;
	private int itemCounter;
	private int robotId;
	private boolean atPickup;
	private boolean atDropoff;
	private String pickingState;
	private boolean dropDone;
	
	public WaitBehavior(TurnBehavior _behavior, RobotScreen _screen, int RobotId)
	{
	
		this.behavior = _behavior;
		this.screen = _screen;
		this.robotId = RobotId;
		pilot = new DifferentialPilot(56, 120, Motor.A, Motor.B);

		pilot.setTravelSpeed(125.0);
		pilot.setRotateSpeed(150.0);

		itemCounter = 0;
		itemCount = 0;
		atPickup = false;
		atDropoff = false;

	}

	@Override
	public boolean takeControl()
	{

		return behavior.checkIfNoRoute();
	}

	@Override
	public void action()
	{
		supressed = false;

		while (!supressed)
		{

			// System.out.println("Running " + supressed);

			pilot.stop();

			if (Button.ENTER.isDown())
			{ // multiple times for pick up, just
				// once for drop off
				Delay.msDelay(750);
				if (atPickup)
				{
					itemCounter += 1;
					screen.updateItemPickUpIncrease();
					screen.printPickingState();
				} else if (atDropoff)
				{
					dropDone = true;
					RobotStatusMessage msg2 = new RobotStatusMessage();
					msg2.setX(behavior.getX());
					msg2.setY(behavior.getY());
					msg2.setOnJob(false);
					msg2.setOnRoute(false);
					msg2.setWaitingForNewPath(true);
					msg2.setRobotId(robotId);
					TrialMainNxt.client.send(msg2);
					setPickingState("done");
				}
			}

			if (Button.RIGHT.isDown() && atPickup)
			{ // right button should be
				// pressed when
				// picking/dropping is
				// done
				Delay.msDelay(750);
				screen.printPickingState();

				switch (pickingState)
				{
				case "picking":
					if (itemCount == itemCounter && atPickup)
					{
						RobotStatusMessage msg2 = new RobotStatusMessage();
						msg2.setX(behavior.getX());
						msg2.setY(behavior.getY());
						msg2.setOnJob(true);
						msg2.setOnRoute(false);
						msg2.setWaitingForNewPath(true);
						msg2.setRobotId(robotId);
						TrialMainNxt.client.send(msg2);
						setAtPickup(false);
						setPickingState("done");
					} else if (itemCounter > itemCount)
					{
						toRelease = Math.abs(itemCount - itemCounter);
						setPickingState("dropping");
					} else
					{
						this.itemCount = itemCount - itemCounter;
						itemCounter = 0;
						setPickingState("picking");
					}
					break;
				case "dropping":
					if (toRelease == releaseCounter)
					{
						RobotStatusMessage msg2 = new RobotStatusMessage();
						msg2.setX(behavior.getX());
						msg2.setY(behavior.getY());
						msg2.setOnJob(true);
						msg2.setOnRoute(false);
						msg2.setWaitingForNewPath(true);
						msg2.setRobotId(robotId);
						TrialMainNxt.client.send(msg2);
						setAtPickup(false);
						setPickingState("done");
					} else if (toRelease > releaseCounter)
					{
						toRelease = toRelease - releaseCounter;
						setPickingState("dropping");
					} else
					{
						itemCount = Math.abs(releaseCounter - toRelease);
						setPickingState("picking");
					}
					break;
				case "done":
					break;
				default:
					break;
				}
			}

			if (Button.LEFT.isDown())
			{ // to decrease items picked up
				Delay.msDelay(750);
				releaseCounter += 1;
				screen.updateItemPickUpDecrease();
				screen.printPickingState();
			}

			if (Button.ESCAPE.isDown())
			{
				Delay.msDelay(750);
				System.exit(0);
				suppress();
			}
		}
	}

	public void setItemCount(int i)
	{
		itemCount = i;
	}

	public int getItemCount()
	{
		return itemCount;
	}

	public void setAtPickup(boolean b)
	{
		atPickup = b;
	}

	public void setatDropoff(boolean b)
	{
		atDropoff = b;
	}

	public void setPickingState(String s)
	{
		pickingState = s;
	}

	@Override
	public void suppress()
	{
		supressed = true;

	}

}