package com.rb34.general.interfaces;

import java.util.ArrayList;

public interface IRobotManager {

	IRobot getRobot(int robotId);
	
	void addRobot(IRobot newRobot);
	
	ArrayList <IRobot> getRobots();
}
