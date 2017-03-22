package com.rb34.general;

import java.util.ArrayList;

import com.rb34.Start;
import com.rb34.general.interfaces.IRobot.Status;
import com.rb34.general.interfaces.IRobotManager;
import com.rb34.message.LocationTypeMessage;
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
		msg.setX(newRobot.getXLoc());
		msg.setY(newRobot.getYLoc());
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
		System.out.println(msg.getText());
		System.out.println(msg.getText());
		System.out.println(msg.getText());
		System.out.println(msg.getText());
		System.out.println(msg.getText());
	}

	@Override
	public void recievedNewPathMessage(NewPathMessage msg)
	{

	}

	@Override
	public void recievedRobotStatusMessage(RobotStatusMessage msg)
	{
		System.out.println(msg.toString());
		
		getRobot(msg.getRobotId()).setXLoc(msg.getX());
		getRobot(msg.getRobotId()).setYLoc(msg.getY());
		
		if(msg.isOnRoute())
		{
			getRobot(msg.getRobotId()).setRobotStatus(Status.RUNNING);
		}else
		{			
			if(msg.isWaitingForNewPath())
			{
				getRobot(msg.getRobotId()).setRobotStatus(Status.AT_ITEM);
				System.out.println("WAITING FOR NEW PATH");
				
			}else
			{
				System.out.println("NOOOOOOOOOOT WAITING FOR NEW PATH");
				getRobot(msg.getRobotId()).notifyOfLocation();
			}
		} 
		if (!msg.isOnJob()) {
			getRobot(msg.getRobotId()).setRobotStatus(Status.IDLE);
		}
	}

	@Override
	public void recievedRobotInitMessage(RobotInitMessage msg)
	{
		
	}

	@Override
	public void recievedLocationTypeMessage(LocationTypeMessage msg)
	{
		
	}

}
