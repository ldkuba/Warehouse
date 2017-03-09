package com.rb34.general;

import com.rb34.general.interfaces.IRobot;
import com.rb34.jobInput.Item;

import com.rb34.jobInput.Job;


import java.util.ArrayList;

public class Robot implements IRobot{

	private int xLoc;
	private int yLoc;
	private int xDrop;
	private int yDrop;
	private Status state;
	private Job job;
	private ArrayList<Item> itemList;

	public Robot(){
		this.xLoc = 0;
		this.yLoc = 0;
		this.state = Status.IDLE;
		this.job = null;
	}

	public void recievedMessge(){
		//TODO
	}

	@Override
	public Status getRobotStatus(){
		return state;
	}

	@Override
	public void setRobotStatus(Status state){
		this.state = state;
	}

	@Override
	public void setXLoc(int xLoc){
		this.xLoc = xLoc;
	}

	@Override
	public void setYLoc(int yLoc){
		this.yLoc = yLoc;
	}

	@Override
	public int getXLoc(){
		return xLoc;
	}

	@Override
	public int getYLoc(){
		return yLoc;
	}

	@Override
	public void setCurrentJob(Job job){
		this.job = job;
	}

	@Override
	public Job getCurrentJob(){
		return job;
	}

	@Override
	public void setXDropLoc(int xDropLoc){
		this.xDrop = xDropLoc;
	}

	@Override
	public void setYDropLoc(int yDropLoc){
		this.yDrop = yDropLoc;
	}

	@Override
	public int getXDropLoc(){
		return xDrop;
	}

	@Override
	public int getYDropLoc(){
		return yDrop;
	}

	@Override
	public ArrayList<Item> getItemsToPick(){
		return itemList;
	}

	@Override
	public void setItemsToPick(ArrayList<Item> itemsToPick){
		this.itemList = itemsToPick;
	}
}
