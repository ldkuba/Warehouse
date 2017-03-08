package com.rb34.general.interfaces;

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
	void setXLoc(int xLoc);
	void setYLoc(int yLoc);
	
	// Gets X and Y coordinates of specific Robot
	int getXLoc();
	int getYLoc();
	
}
