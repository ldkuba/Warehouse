package com.rb34.dummy;

import com.rb34.behaviours.*;
import com.rb34.robot_interface.*;
import com.rb34.main.JunctionFollower;

public class TrialMainNxt {
	private JunctionFollower robotMovement;
	private RobotScreen screen;
	
	TrialMainNxt() {
		screen = new RobotScreen(0, 0, "Starting");
		robotMovement = new JunctionFollower(screen);
	}
	
	public static void main(String[] args) {
		TrialMainNxt robot = new TrialMainNxt();
	}
}

