package com.rb34.robot_interface;

import lejos.nxt.LCD;

public class RobotScreen {
	
	private String state;
	private int x, y;
	
	// Location, state, message
	// 99, 63
	
	public RobotScreen (int x, int y, String state) {
		this.x = x;
		this.y = y;
		this.state = state;
	}
	
	public void printEverything () {
		LCD.clear();
		LCD.drawString("X:Y ->" + x + ":" + y, LCD.CELL_WIDTH / 4, 4 * LCD.CELL_HEIGHT / 6);
		LCD.drawString("State: " + state, LCD.CELL_WIDTH / 4, 3 * LCD.CELL_HEIGHT / 6);
	}
	
	public void printLocation (int x, int y) {
		this.x = x;
		this.y = y;
		printEverything();
	}
	
	public void printState (String state) {
		this.state = state;
		printEverything();
	}
}
