package com.rb34.warehouse_interface;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.rb34.general.Robot;
import com.rb34.general.RobotManager;
import com.rb34.general.interfaces.IRobot.Status;
import com.rb34.jobInput.Item;
import com.rb34.jobInput.Job;

public class WarehouseUITest {

	public static void main(String[] args) {
		Robot robot1 = new Robot();
		Robot robot2 = new Robot();
		Robot robot3 = new Robot();
		robot1.setXLoc(0);
		robot1.setYLoc(0);
		robot1.setRobotId(0);
		
		robot2.setXLoc(0);
		robot2.setYLoc(7);
		robot2.setRobotId(1);
		
		robot3.setXLoc(5);
		robot3.setYLoc(3);
		robot3.setRobotId(2);
		
		robot2.setRobotStatus(Status.IDLE);
		robot3.setRobotStatus(Status.AT_ITEM);
	
		RobotManager rm = new RobotManager();
		rm.addRobot(robot1);
		rm.addRobot(robot2);
		rm.addRobot(robot3);
		
		WarehouseWindow view = new WarehouseWindow(rm);
		view.launch();
		
		Job rb1Job = new Job("Test Job");
		robot1.setCurrentJob(rb1Job);
		
		Item testItem = new Item("401", 10, 5);
		Item testItem2 = new Item("361", 3, 10);
		ArrayList<Item> items = new ArrayList<Item>();
		items.add(testItem);
		items.add(testItem2);
		robot1.setItemsToPick(items);
		String dest1 = "2|3";
		String dest2 = "6|5";
		ArrayList<String> dest = new ArrayList<String>();
		dest.add(dest1);
		dest.add(dest2);
		robot1.setDestinations(dest);
		
		while(robot1.getXLoc() < 11) {
			int val = robot1.getXLoc() + 1;
			robot1.setRobotStatus(Status.RUNNING);
			robot1.setXLoc(val);
			
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		while(robot1.getYLoc() < 7) {
			int val = robot1.getYLoc() + 1;
			robot1.setRobotStatus(Status.RUNNING);
			robot1.setYLoc(val);
			if(robot1.getXLoc() == 11 && robot1.getYLoc() == 7) {
				robot1.setRobotStatus(Status.AT_DROPOFF);
			}
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//robot1.setRobotStatus(Status.AT_DROPOFF);

	}

}
