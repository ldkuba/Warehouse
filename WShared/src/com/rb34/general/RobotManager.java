package com.rb34.general;

import java.util.ArrayList;

import com.rb34.general.interfaces.IRobot;
import com.rb34.general.interfaces.IRobotManager;

public class RobotManager implements IRobotManager {

	private ArrayList <IRobot> robotList;
	
	public RobotManager(){
		robotList = new ArrayList<IRobot>();
	}
	
	@Override
	public IRobot getRobot(int robotId) {
		return robotList.get(robotId);
	}

	@Override
	public void addRobot(IRobot newRobot) {
		robotList.add(newRobot);
		
	}

}
