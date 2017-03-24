package com.rb34.behaviours;

import com.rb34.dummy.TrialMainNxt;
import com.rb34.main.JunctionFollower;
import com.rb34.message.RobotStatusMessage;
import com.rb34.robot_interface.RobotScreen;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;
import rp.config.WheeledRobotConfiguration;
import rp.systems.WheeledRobotSystem;

public class WaitBehavior implements Behavior
{
	private WheeledRobotConfiguration robotConfig;
	private DifferentialPilot pilot;
	private TurnBehavior behavior;
	private RobotScreen screen;
	private boolean supressed;
	private int itemCount;
	private int releaseCounter;
	private int toRelease;
	private int itemCounter;
	private boolean atPickup;
	private boolean atDropoff;
	private String pickingState;
	private boolean dropDone;
	private boolean forcingBehav;
	
	public WaitBehavior(TurnBehavior _behavior, RobotScreen _screen)
	{
		this.behavior = _behavior;
		this.screen = _screen;
		robotConfig = new WheeledRobotConfiguration(0.059f, 0.115f, 0.17f, Motor.C, Motor.A);
		pilot = new WheeledRobotSystem(robotConfig).getPilot();

		pilot.setTravelSpeed((pilot.getMaxTravelSpeed() / 10) * 2);
		pilot.setRotateSpeed((pilot.getRotateMaxSpeed() / 10) * 2);

		
		itemCounter = 0;
		itemCount = 0;
		atPickup = false;
		atDropoff = false;
		forcingBehav = true;

	}

	public void resetAllCounters()
	{
		itemCount = 0;
		itemCounter = 0;
		releaseCounter = 0;
		toRelease = 0;
	}

	@Override
	public boolean takeControl()
	{
		//System.out.println(forcingBehav);
		if (behavior.checkIfNoRoute() || forcingBehav)
		{
			return true;
		} else
		{
			return false;
		}
	}

	public void setFocingBehav(boolean b)
	{
		forcingBehav = b;
	}

	@Override
	public void action()
	{
		supressed = false;

	//	System.out.println("RUNNING WAIT BEHAVIOR");
		
		while (!supressed)
		{
			
			// System.out.println("Running " + supressed);

			pilot.stop();

			if (Button.RIGHT.isDown())
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
					msg2.setHeading(behavior.getHeading());
					msg2.setWaitingForNewPath(true);
					msg2.setRobotId(JunctionFollower.RobotId);
					TrialMainNxt.client.send(msg2);
					setPickingState("done");
					atDropoff = false;
					resetAllCounters();
					screen.resetCounter();
				}
			}

			if (Button.ENTER.isDown() && atPickup)
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
						msg2.setHeading(behavior.getHeading());
						msg2.setWaitingForNewPath(true);
						msg2.setRobotId(JunctionFollower.RobotId);
						TrialMainNxt.client.send(msg2);
						setAtPickup(false);
						setPickingState("done");
						atPickup = false;
						resetAllCounters();
						screen.resetCounter();
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
						msg2.setHeading(behavior.getHeading());
						msg2.setWaitingForNewPath(true);
						msg2.setRobotId(JunctionFollower.RobotId);
						TrialMainNxt.client.send(msg2);
						setAtPickup(false);
						setPickingState("done");
						atPickup = false;
						resetAllCounters();
						screen.resetCounter();
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
				screen.printPickingState();  // 175:7 75:41
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