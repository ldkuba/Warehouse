package com.rb34.general;

import java.util.ArrayList;

import com.rb34.Start;
import com.rb34.general.interfaces.IRobot.Status;
import com.rb34.job_input.Drop;
import com.rb34.message.LocationTypeMessage;
import com.rb34.message.MessageListener;
import com.rb34.message.NewPathMessage;
import com.rb34.message.RobotInitMessage;
import com.rb34.message.RobotStatusMessage;
import com.rb34.message.TestMessage;

public class RobotManager implements MessageListener
{

	private static ArrayList<Robot> robotList;
	private static ArrayList<Drop> dropoffList;

	public RobotManager()
	{
		robotList = new ArrayList<>();
		dropoffList = new ArrayList<>();
	}

	public static Robot getRobot(int robotId)
	{
		return robotList.get(robotId);
	}

	public static void addRobot(Robot newRobot)
	{
		robotList.add(newRobot);
	}
	
	public static void initRobots()
	{
		for(Robot newRobot : robotList)
		{
			RobotInitMessage msg = new RobotInitMessage();
			msg.setRobotId(newRobot.getRobotId());
			msg.setX(newRobot.getXLoc());
			msg.setY(newRobot.getYLoc());
			msg.setHeading(newRobot.getHeading());
			Start.master.send(msg, newRobot.getRobotId());
		}
	}

	public static ArrayList<Robot> getRobots()
	{
		return robotList;
	}

	public static void addDropoff(int x, int y){
		dropoffList.add(new Drop(x,  y));
	}
	
	public static ArrayList<Drop> getDropoffList(){
		return dropoffList;
	}
	
	
	@Override
	public void recievedTestMessage(TestMessage msg)
	{
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
				System.out.println("WAITING FOR NEW PATH");
				
				if (getRobot(msg.getRobotId()).getDestinations().isEmpty()) 
				{
					getRobot(msg.getRobotId()).setRobotStatus(Status.IDLE);
					
				}else
				{
					getRobot(msg.getRobotId()).setRobotStatus(Status.AT_ITEM);
				}
			}else
			{
				System.out.println("NOOOOOOOOOOT WAITING FOR NEW PATH");
				getRobot(msg.getRobotId()).notifyOfLocation();
			}
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
