package com.rb34.general;

import java.util.ArrayList;

import com.rb34.general.interfaces.IRobot;
import com.rb34.jobInput.Item;

public class Robot implements IRobot {

	private int xLoc;
	private int yLoc;
	private int xDrop;
	private int yDrop;
	private int robotID;
	private boolean onJob;
	private boolean onRoute;
	private ArrayList <Item> itemList;	
	
	public Robot(){
		this.robotID = 0;
		this.xLoc = 0;
		this.yLoc = 0;
		this.onJob = false;
		this.onRoute = false;
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

	@Override
	public void setXCurrentLoc(int xLoc) {
		this.xLoc = xLoc;
		
	}

	@Override
	public void setYCurrentLoc(int yLoc) {
		this.yLoc = yLoc;
	}

	@Override
	public int getXCurrentLoc() {
		return xLoc;
	}

	@Override
	public int getYCurrentLoc() {
		// TODO Auto-generated method stub
		return yLoc;
	}

	@Override
	public void setXDropLoc(int xDropLoc) {
		this.xDrop = xDropLoc;
	}

	@Override
	public void setYDropLoc(int yDropLoc) {
		this.yDrop = yDropLoc;
	}

	@Override
	public int getXDropLoc() {
		// TODO Auto-generated method stub
		return xDrop;
	}

	@Override
	public int getYDropLoc() {
		// TODO Auto-generated method stub
		return yDrop;
	}

	@Override
	public ArrayList<Item> getItemsToPick() {
		return itemList;
	}

	@Override
	public void setItemsToPick(ArrayList<Item> itemsToPick) {
		this.itemList = itemsToPick;
	}

}
