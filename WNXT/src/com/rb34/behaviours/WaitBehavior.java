package com.rb34.behaviours;

import com.rb34.message.RobotStatusMessage;
import com.rb34.robot_interface.RobotScreen;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.Sound;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class WaitBehavior implements Behavior
{
	private DifferentialPilot pilot;
	private TurnBehavior behavior;
	private RobotScreen screen;
	private boolean supressed;
	private int counter;

	public WaitBehavior(TurnBehavior _behavior, RobotScreen _screen)
	{
		this.behavior = _behavior;
		this.screen = _screen;
		pilot = new DifferentialPilot(56, 120, Motor.A, Motor.B);

		pilot.setTravelSpeed(125.0);
		pilot.setRotateSpeed(150.0);

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
		pilot.stop();
		if (Button.ENTER.isDown())
		{
			counter += 1;
		}

		screen.itemPickUp(5, 1.4f);
		screen.updateState("Waiting");

		while (!Button.ESCAPE.isDown())
		{ // make sure that robot will stop program
			// if escape button is pressed.
			Sound.beep();
		}
		
		System.exit(0);
		suppress();

	}

	@Override
	public void suppress()
	{
		supressed = true;

	}

}