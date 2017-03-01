package com.rb34.jobInput.implementation;

import com.rb34.jobInput.interfaces.IItem;

public class Item implements IItem {
	
	private String itemID;
	private float reward;
	private float weight;
	private int xLoc;
	private int yLoc;

	public Item(String itemID, float reward, float weight,int xLoc,int yLoc){
		this.itemID = itemID;
		this.reward = reward;
		this.weight = reward;
		this.xLoc = xLoc;
		this.yLoc = yLoc;
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

}
