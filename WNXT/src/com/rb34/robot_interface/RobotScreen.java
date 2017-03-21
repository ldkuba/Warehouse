package com.rb34.robot_interface;
import lejos.nxt.LCD;
public class RobotScreen {
	
	private String state = "DEFAULT_STATE";
	private int x = -1, y = -1;
	private float weight = 0;
	private float maxWeight = 50;
	private int itemsPickedUp = 0;
	private int itemsToPickUp = 0;
	private float weightOfOneItem = 0;
	// Location, state, message
	
	// Print starting screen in initialization
	/*
	Current state:
	some_state
	X:Y
	X and Y coordinates	
	Weight:
	Weight: someWeight / maxWeight
	*/
	
	public RobotScreen () {
		printEverything();
	}
	
	// Main info when moving
	public void printEverything () {
		LCD.clear();
		LCD.drawString("Current state:", 0, 0);
		LCD.drawString(state, 0, 1);
		if (x == -1 && y == -1) {
			LCD.drawString("X:Y", 0, 2);
			LCD.drawString("On route", 0, 3);
		} else {
			LCD.drawString("X:Y", 0, 2);
			LCD.drawString("X:Y -> " + x + ":" + y, 0, 3);
		}
		LCD.drawString("Weight: " + weight + "/" + maxWeight, 0, 4);
	}
	
	public void updateLocation (int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void updateState (String state) {
		this.state = state;
	}
	
	public void setMaxWeight (float weight) {
		maxWeight = weight;
	}
	
	public void updateWeight (float weight) {
		this.weight = weight;
	}
	
	public void updateItemPickUpIncrease() {
		itemsPickedUp++;
		weight += weightOfOneItem;
	}
	
	public void updateItemPickUpDecrease() {
		itemsPickedUp--;
		weight -= weightOfOneItem;
	}
	
	public void setItemPickUpInfo(int itemsToPickUp) {
		this.itemsToPickUp = itemsToPickUp;
	}
	
	
	public void printPickingState() {
		LCD.clear();
		LCD.drawString("Pick these items", 0, 0);
		LCD.drawString(itemsToPickUp + "", 0, 1);
		LCD.drawString("Items picked up:", 0, 2);
		LCD.drawString (itemsPickedUp + "", 0, 3);
		
		if (itemsPickedUp > itemsToPickUp) {
			LCD.drawString("Too many items picked up", 0, 5);
			LCD.drawString("Remove "+Math.abs(itemsToPickUp - itemsPickedUp)+" items", 0, 6);
		} else if (itemsPickedUp < itemsToPickUp) {
			LCD.drawString("Too few items picked up", 0, 5);
			LCD.drawString("Pick up "+(itemsToPickUp - itemsPickedUp)+" items", 0, 6);
		} else {
			LCD.drawString("Picking complete", 0, 5);
			LCD.drawString("Proceeding to drop off point", 0, 6);
		}
	}
	
	public void printDropOffState() {
		LCD.clear();
		LCD.drawString("At Drop off location", 0, 0);
		LCD.drawString("Press enter button", 0, 1);
	}
}