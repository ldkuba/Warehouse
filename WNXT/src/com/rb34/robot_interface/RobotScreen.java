package localisation;

import lejos.nxt.LCD;

public class RobotScreen {
	
	private String state = "DEFAULT_STATE";
	private int x = -1, y = -1;
	private float weight = 0;
	private float maxWeight = 50;
	private float itemsPickedUp = 0;
	private float itemsToPickUp = 0;
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
			LCD.drawString("Still searching", 0, 3);
		} else {
			LCD.drawString("X:Y", 0, 2);
			LCD.drawString("X:Y -> " + x + ":" + y, 0, 3);
		}
		LCD.drawString("Weight: " + weight + "/" + maxWeight, 0, 4);
	}
	
	public void updateLocation (int x, int y) {
		this.x = x;
		this.y = y;
		printEverything();
	}
	
	public void updateState (String state) {
		this.state = state;
		printEverything();
	}
	
	public void setMaxWeight (float weight) {
		maxWeight = weight;
		printEverything();
	}
	
	public void updateWeight (float weight) {
		this.weight = weight;
	}
	
	public void updateItemPickUp () {
		itemsPickedUp++;
		weight += weightOfOneItem;
		itemPickUp();
	}
	
	public void itemPickUp (float itemsToPickUp, float weightOfOneItem) {
		this.itemsToPickUp = itemsToPickUp;
		this.weightOfOneItem = weightOfOneItem;
		
		LCD.clear();
		LCD.drawString("Pick these items", 0, 0);
		LCD.drawString(itemsToPickUp + "", 0, 1);
		LCD.drawString("Items picked up:", 0, 2);
		LCD.drawString (itemsPickedUp + "", 0, 3);
		
		if (weight > itemsToPickUp * weightOfOneItem) {
			LCD.drawString("Too much", 0, 4);
		}
	}
	
	private void itemPickUp () {
		LCD.clear();
		LCD.drawString("Pick these items", 0, 0);
		LCD.drawString(itemsToPickUp + "", 0, 1);
		LCD.drawString("Items picked up:", 0, 2);
		LCD.drawString (itemsPickedUp + "", 0, 3);
	}
}

