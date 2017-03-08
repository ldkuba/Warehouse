package com.rb34.general;

import com.rb34.general.interfaces.IRobot;

public class Robot implements IRobot {

	private int robotID;
	private int xLoc;
	private int yLoc;
	private boolean onJob;
	private boolean onRoute;
	
	
	public Robot(){
		this.robotID = 0;
		this.xLoc = 0;
		this.yLoc = 0;
		this.onJob = false;
		this.onRoute = false;
	}
	
	@Override
	public void setXLoc(int xLoc) {
		this.xLoc = xLoc;	
	}

	@Override
	public void setYLoc(int yLoc) {
		this.yLoc = yLoc;
	}

	@Override
	public int getXLoc() {
		return xLoc;
	}

	@Override
	public int getYLoc() {
		return yLoc;
	}

	@Override
	public void setRobotId(int Id) {
		this.robotID = Id;		
	}

	@Override
	public int getRobotId() {
		return robotID;
	}

	@Override
	public void setOnJob(boolean onJob) {
		this.onJob = onJob;
	}

	@Override
	public void setOnRoute(boolean onRoute) {
		this.onRoute = onRoute;
		
	}

	@Override
	public boolean onRoute() {
		return onRoute;
	}

	@Override
	public boolean onJob() {
		return onJob;
	}

}
