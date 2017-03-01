package com.rb32.behaviours;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;

public class LineFollowing implements Behavior 
{	
	private boolean supressed = true;
	
	private LightSensor lightSensorR;
	private LightSensor lightSensorL;

	protected int whiteInit;
	
	private DifferentialPilot pilot;

	protected int whiteInitL;
	
	private TurnBehavior turnBehavior;
	
	public LineFollowing(LightSensor left, LightSensor right, int whiteInitL, int whiteInitR, TurnBehavior turnBehavior) 
	{
		this.turnBehavior = turnBehavior;
		
		lightSensorR = right;
		lightSensorL = left;
		
		pilot = new DifferentialPilot(43, 171, Motor.B, Motor.C);
		
		this.whiteInit = whiteInitR;
		this.whiteInitL = whiteInitR;
		
		pilot.setTravelSpeed(100.0);
		
		Motor.B.setSpeed(100.0f);
		Motor.C.setSpeed(100.0f);
		
	}

	@Override
	public boolean takeControl() 
	{
		return true;
	}

	@Override
	public void action() 
	{
		supressed = false;
		
		this.whiteInitL = this.lightSensorL.getLightValue();//this gets the value for white at the start, sets 'base' while value.
		this.whiteInit = this.lightSensorR.getLightValue();
		
		/*if(this.turnBehavior != null)
		{
			this.turnBehavior.calibrate(this.whiteInitL, this.whiteInit);
		}*/
		
		while (!supressed) 
		{

			int reading = lightSensorR.getLightValue();
			int readingL = lightSensorL.getLightValue();

			if (whiteInit - reading > 1) {
				pilot.arcForward(-400);
			} else {
				pilot.arcForward(400);
			}

			if (Button.ESCAPE.isDown()) {
				System.exit(0);
			}

			LCD.clear();
			LCD.drawInt(reading, 8, 1);
			LCD.drawInt(whiteInit, 8, 3);
			LCD.drawInt(whiteInit - reading, 8, 5);
			LCD.drawChar('R', 8, 7);
			
			LCD.drawInt(readingL, 3, 1);
			LCD.drawInt(whiteInitL, 3, 3);
			LCD.drawInt(whiteInitL - readingL, 3, 5);
			LCD.drawChar('L', 3, 7);		
			
			Delay.msDelay(20);

	
		}
	}

	@Override
	public void suppress() {
		supressed = true;
	}

}
