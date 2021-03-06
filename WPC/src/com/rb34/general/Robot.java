package com.rb34.general;

import java.util.ArrayList;

import com.rb34.Start;
import com.rb34.general.interfaces.IRobot;
import com.rb34.job_input.Item;
import com.rb34.job_input.Job;
import com.rb34.message.LocationTypeMessage;

public class Robot implements IRobot {
	
	private int robotId;
	private int xLoc;
	private int yLoc;
	
	private Status state;
	
	private Job job;
	
	private ArrayList<Item> itemList;
	private ArrayList<String> destinations;
	
	private boolean currentlyAtItem;
	
	private Item currentItem;
	
	private String heading;
	private String destination;

	public Robot() {
		this.xLoc = 0;
		this.yLoc = 0;
		this.state = Status.IDLE;
		this.job = null;
		this.destinations = new ArrayList<>();
		this.itemList = new ArrayList<>();
		heading = "E";
	}

	public void setCurrentlyGoingToItem(boolean b) {
		currentlyAtItem = b;
	}

	public String getHeading() {
		return this.heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public ArrayList<String> getDestinations() {
		return destinations;
	}

	public void setCurrentItem(Item item) {
		this.currentItem = item;
	}

	public void setDestinations(ArrayList<String> destinations) {
		this.destinations = destinations;
	}

	public int getXDes() {
		return Integer.parseInt(destination.split("\\|")[0]);
	}

	public int getYDes() {
		return Integer.parseInt(destination.split("\\|")[1]);
	}

	public void recievedMessge() {
		// TODO
	}

	@Override
	public Status getRobotStatus() {
		return state;
	}

	@Override
	public void setRobotStatus(Status state) {
		this.state = state;
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
	public void setCurrentJob(Job job) {
		this.job = job;
	}

	@Override
	public Job getCurrentJob() {
		return job;
	}

	@Override
	public ArrayList<Item> getItemsToPick() {
		return itemList;
	}

	@Override
	public void setItemsToPick(ArrayList<Item> itemsToPick) {
		System.out.println("items set");
		this.itemList.clear();
		this.itemList = new ArrayList<>(itemsToPick);

	}

	public int getRobotId() {
		return robotId;
	}

	public void setRobotId(int robotId) {
		this.robotId = robotId;
	}

	public void notifyOfLocation() {
		LocationTypeMessage msg = new LocationTypeMessage();

		if (this.currentlyAtItem) {
			msg.setLocationType(0);
			msg.setItemId(currentItem.getItemID());
			msg.setItemCount(job.getOrderList().get(currentItem.getItemID()).getCount());
		} else {
			msg.setLocationType(1);
			msg.setItemId("");
			msg.setItemCount(0);
		}

		System.out.println("ITEM: " + currentlyAtItem);

		Start.master.send(msg, robotId);
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}
}
