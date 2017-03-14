package com.rb34.general;

import java.util.ArrayList;

import com.rb34.general.interfaces.IRobot;
import com.rb34.general.interfaces.IRobotManager;
import com.rb34.message.MessageListener;
import com.rb34.message.NewPathMessage;
import com.rb34.message.TestMessage;

public class RobotManager implements IRobotManager {

	private ArrayList <Robot> robotList;
	
	public RobotManager(){
		robotList = new ArrayList<>();
	}
	
	@Override
	public Robot getRobot(int robotId) {
		return robotList.get(robotId);
	}

	@Override
	public void addRobot(Robot newRobot) {
		robotList.add(newRobot);
	}

	@Override
	public ArrayList<Robot> getRobots() {
		return robotList;
	}

}
