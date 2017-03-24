package com.rb34.behaviours;

import java.io.PrintStream;

import rp.config.WheeledRobotConfiguration;
import rp.systems.WheeledRobotSystem;

import com.rb34.dummy.TrialMainNxt;
import com.rb34.main.JunctionFollower;
import com.rb34.message.RobotStatusMessage;
import com.rb34.robot_interface.RobotScreen;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.Sound;
import lejos.nxt.comm.RConsole;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;

//This behaviour is used whenever the robot needs to wait.
public class WaitBehavior implements Behavior {
	private WheeledRobotConfiguration robotConfig;
	private DifferentialPilot pilot;
	private TurnBehavior behavior;
	private RobotScreen screen;
	private boolean supressed;
	private boolean atPickup;
	private boolean atDropoff;
	private boolean dropDone;
	private boolean forcingBehav;
	private String pickingState;
	private int itemCount;
	private int releaseCounter;
	private int toRelease;
	private int itemCounter;
	
	public WaitBehavior(TurnBehavior _behavior, RobotScreen _screen) {
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

	@Override
	public boolean takeControl() { //This behaviour will take control if the robot does not have a route or if the behvaiour is being forced.
		if (behavior.checkIfNoRoute() || forcingBehav) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void action() {
		supressed = false;
		
		while (!supressed) {
			pilot.stop();

			if (Button.RIGHT.isDown()) { //Multiple times for pick up, just once for drop off
				Delay.msDelay(750);//Making sure that the presses are registered correctly
				if (atPickup) { //At a pick up point
					itemCounter += 1;
					screen.updateItemPickUpIncrease();
					screen.printPickingState();
				} else if (atDropoff) { //At a drop off point
					dropDone = true;
					RobotStatusMessage msg2 = new RobotStatusMessage(); //Sending message to PC so that robot status is up to date
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

			if (Button.ENTER.isDown() && atPickup) { //Enter button should be pressed when picking/dropping is done
				Delay.msDelay(750); //Making sure that the presses are registered correctly
				screen.printPickingState();
				
				switch (pickingState) { //what should be done depending on what the robot should be doing
				case "picking":
					if (itemCount == itemCounter && atPickup) { //If pick has been completed correctly
						RobotStatusMessage msg2 = new RobotStatusMessage(); //Message sent to PC updating robot's status
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
					} else if (itemCounter > itemCount) {
						toRelease = Math.abs(itemCount - itemCounter);
						setPickingState("dropping");
					} else {
						this.itemCount = itemCount - itemCounter;
						itemCounter = 0;
						setPickingState("picking");
					}
					break;
				case "dropping":
					if (toRelease == releaseCounter) {
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
					} else if (toRelease > releaseCounter) {
						toRelease = toRelease - releaseCounter;
						setPickingState("dropping");
					} else {
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

			if (Button.LEFT.isDown()) { // To decrease items picked up if too many have been picked
				Delay.msDelay(750);
				releaseCounter += 1;
				screen.updateItemPickUpDecrease();
				screen.printPickingState();
			}

			if (Button.ESCAPE.isDown()) {
				Delay.msDelay(750);
				System.exit(0);
				suppress();
			}
		}
	}
	
	@Override
	public void suppress() {
		supressed = true;

	}
	
	public void resetAllCounters() { //Makes sure all values are reset once picking is complete for each job.
		itemCount = 0;
		itemCounter = 0;
		releaseCounter = 0;
		toRelease = 0;
	}
	
	public void setFocingBehav(boolean b) { //For forcing/un-forcing the behaviour.
		forcingBehav = b;
	}

	public void setItemCount(int i) {
		itemCount = i;
	}

	public int getItemCount() {
		return itemCount;
	}

	public void setAtPickup(boolean b) {
		atPickup = b;
	}

	public void setatDropoff(boolean b) {
		atDropoff = b;
	}

	public void setPickingState(String s) {
		pickingState = s;
	}

}