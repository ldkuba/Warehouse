package com.rb34.general.interfaces;

import java.util.ArrayList;

import com.rb34.general.Robot;

public interface IRobotManager {

	Robot getRobot(int robotId);
	
	void addRobot(Robot newRobot);
	
	ArrayList <Robot> getRobots();
}
