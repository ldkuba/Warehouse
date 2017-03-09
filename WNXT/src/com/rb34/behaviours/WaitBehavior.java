package com.rb34.behaviours;

import java.util.ArrayList;
import java.util.Random;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

import com.rb34.behaviours.PathChoices;
import com.rb34.behaviours.TurnBehavior;

public class WaitBehavior implements Behavior {
	private DifferentialPilot pilot;
	private TurnBehavior behavior;
	private boolean supressed;
	private int counter;

	public WaitBehavior(TurnBehavior _behavior) {
		this.behavior = _behavior;
		pilot = new DifferentialPilot(56, 120, Motor.A, Motor.B);

		pilot.setTravelSpeed(125.0);
		pilot.setRotateSpeed(150.0);

	}

	@Override
	public boolean takeControl() {
		if (behavior.checkIfNoRoute() == true) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void action() {
		supressed = false;
		pilot.stop();
		if (Button.ENTER.isDown()) {
			counter += 1;
		}

		//printState();
		
		if (Button.ESCAPE.isDown()) { // make sure that robot will stop program
										// if escape button is pressed.
			System.exit(0);
			suppress();
		}

		suppress();

	}

	@Override
	public void suppress() {
		supressed = true;

	}

}