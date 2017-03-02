package com.rb34.jobInput;

import com.rb34.jobInput.interfaces.IItem;

public class Item implements IItem {
	
	private String itemID;
	private float reward;
	private float weight;
	private int xLoc;
	private int yLoc;

	public Item(String itemID, float reward, float weight){
		this.itemID = itemID;
		this.reward = reward;
		this.weight = weight;
	}
	@Override
	public String getItemID() {
		return itemID;
	}

	@Override
	public float getReward() {
		return reward;
	}

	@Override
	public float getWeight() {
		return weight;
	}

	@Override
	public int getX() {
		return xLoc;
	}

	@Override
	public int getY() {
		return yLoc;
	}
	@Override
	public void setX(int xLoc) {
		this.xLoc = xLoc;
	}
	@Override
	public void setY(int yLoc) {
		this.yLoc = yLoc;
	}

}
