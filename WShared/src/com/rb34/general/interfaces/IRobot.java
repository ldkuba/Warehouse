package com.rb34.general.interfaces;

import java.util.ArrayList;

import com.rb34.jobInput.interfaces.IItem;

public interface IRobot {
	
	//Sets and gets unique robot ID
	void setRobotId(int Id);
	int getRobotId();
	
	//Sets the status of the robot(Busy/Stuck/Free...etc)
	void setOnJob(boolean onJob);
	boolean onJob();
	
	void setOnRoute(boolean onRoute);
	boolean onRoute();
	
	// Sets X and Y coordinates of specific Robot
	void setXCurrentLoc(int xLoc);
	void setYCurrentLoc(int yLoc);
	
	// Gets X and Y coordinates of specific Robot
	int getXCurrentLoc();
	int getYCurrentLoc();
	
	void setXDropLoc(int xDropLoc);
	void setYDropLoc(int yDropLoc);
	
	int getXDropLoc();
	int getYDropLoc();
	
	ArrayList <IItem> getItemsToPick();
	void setItemsToPick(ArrayList<IItem> itemsToPick);
}
