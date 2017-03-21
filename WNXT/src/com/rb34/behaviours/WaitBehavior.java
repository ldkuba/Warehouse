package com.rb34.behaviours;

import java.io.PrintStream;

import com.rb34.message.RobotStatusMessage;
import com.rb34.robot_interface.RobotScreen;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.Sound;
import lejos.nxt.comm.RConsole;
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
		//System.out.println("Path len: " + behavior.getPath().size());
		
		if(behavior.checkIfNoRoute())
		{
			//Sound.beep();
			
			try
			{
				Thread.sleep(1000);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
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
		
		System.out.println("test");

		//screen.itemPickUp(5, 1.4f);
		//screen.updateState("Waiting");

		if (!Button.ESCAPE.isDown())
		{ 
			
		}
		
		suppress();

	}

	@Override
	public void suppress()
	{
		supressed = true;

	}

}