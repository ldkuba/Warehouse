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
		
		Job rb1Job = new Job("Test Job 1");
		Job rb2Job = new Job("Test Job 2");
		Job rb3Job = new Job("Test Job 3");
		
		robot1.setCurrentJob(rb1Job);
		robot2.setCurrentJob(rb2Job);
		robot3.setCurrentJob(rb3Job);
		
		Item testItem = new Item("401", 10, 5);
		Item testItem2 = new Item("361", 3, 10);
		ArrayList<Item> items = new ArrayList<Item>();
		items.add(testItem);
		items.add(testItem2);
		
		Item testItem3 = new Item("643", 9, 8);
		Item testItem4 = new Item("831", 12, 15);
		ArrayList<Item> items2 = new ArrayList<Item>();
		items2.add(testItem3);
		items2.add(testItem4);
		
		Item testItem5 = new Item("245", 14, 10);
		Item testItem6 = new Item("182", 24, 30);
		ArrayList<Item> items3 = new ArrayList<Item>();
		items3.add(testItem5);
		items3.add(testItem6);
		
		robot1.setItemsToPick(items);
		robot2.setItemsToPick(items2);
		robot3.setItemsToPick(items3);
		
		String dest1 = "2|3";
		String dest2 = "6|5";
		ArrayList<String> dest = new ArrayList<String>();
		dest.add(dest1);
		dest.add(dest2);
		
		String dest3 = "11|4";
		String dest4 = "3|2";
		ArrayList<String> destrb2 = new ArrayList<String>();
		destrb2.add(dest3);
		destrb2.add(dest4);
		
		String dest5 = "0|5";
		String dest6 = "2|2";
		ArrayList<String> destrb3 = new ArrayList<String>();
		destrb3.add(dest5);
		destrb3.add(dest6);
		
		robot1.setDestinations(dest);
		robot2.setDestinations(destrb2);
		robot3.setDestinations(destrb3);
		
		
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
		


	}

}
