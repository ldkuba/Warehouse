package com.rb34.general.interfaces;

import com.rb34.jobInput.Job;

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
}
