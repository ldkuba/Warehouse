package com.rb34.general.interfaces;

import com.rb34.jobInput.Job;
import java.util.ArrayList;

import com.rb34.jobInput.Item;
import com.rb34.jobInput.interfaces.IItem;

public interface IRobot {
	//Sets the status of the robot(Idle, Running, Stuck...)
	enum Status{
		IDLE,
		RUNNING,
		STUCK,
		AT_ITEM,
		AT_DROPOFF
	};
	Status getRobotStatus();
	void setRobotStatus(Status state);

	// Sets X and Y coordinates of specific Robot
	void setXLoc(int xLoc);
	void setYLoc(int yLoc);
	
	// Gets X and Y coordinates of specific Robot
	int getXLoc();
	int getYLoc();

	void setCurrentJob(Job job);
	Job getCurrentJob();

	void setXDropLoc(int xDropLoc);
	void setYDropLoc(int yDropLoc);
	
	int getXDropLoc();
	int getYDropLoc();
	
	ArrayList <Item> getItemsToPick();
	void setItemsToPick(ArrayList<Item> itemsToPick);
}