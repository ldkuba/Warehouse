package com.rb34.general;

import java.util.ArrayList;

import com.rb34.Start;
import com.rb34.general.interfaces.IRobot.Status;
import com.rb34.general.interfaces.IRobotManager;
import com.rb34.message.MessageListener;
import com.rb34.message.NewPathMessage;
import com.rb34.message.RobotInitMessage;
import com.rb34.message.RobotStatusMessage;
import com.rb34.message.TestMessage;

public class RobotManager implements IRobotManager, MessageListener
{

	private ArrayList<Robot> robotList;

	public RobotManager()
	{
		robotList = new ArrayList<>();
	}

	@Override
	public Robot getRobot(int robotId)
	{
		return robotList.get(robotId);
	}

	@Override
	public void addRobot(Robot newRobot)
	{
		robotList.add(newRobot);
		RobotInitMessage msg = new RobotInitMessage();
		msg.setRobotId(newRobot.getRobotId());
		Start.master.send(msg, newRobot.getRobotId());
	}

	@Override
	public ArrayList<Robot> getRobots()
	{
		return robotList;
	}

	@Override
	public void recievedTestMessage(TestMessage msg)
	{

	}

	@Override
	public void recievedNewPathMessage(NewPathMessage msg)
	{

	}

	@Override
	public void recievedRobotStatusMessage(RobotStatusMessage msg)
	{
		getRobot(msg.getRobotId()).setXLoc(msg.getX());
		getRobot(msg.getRobotId()).setYLoc(msg.getY());
		
		if(msg.isOnRoute())
		{
			getRobot(msg.getRobotId()).setRobotStatus(Status.RUNNING);
		}else
		{
			getRobot(msg.getRobotId()).setRobotStatus(Status.IDLE);
				
		}
		
		
		//getRobot(msg.getRobotId()).checkPickup();
	}

	@Override
	public void recievedRobotInitMessage(RobotInitMessage msg)
	{
		
	}

}
