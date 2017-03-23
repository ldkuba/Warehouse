package com.rb34.job_input.interfaces;

//Interface for Item object
public interface IItem{

	//Gets unique ID of item
	String getItemID();

	//Gets Reward of item
	float getReward();
	
	//Gets Weight of item
	float getWeight();

	//Sets X and Y pick-up Locations of item
	void setX(int x);
	void setY(int y);
	
	//Gets X and Y pick-up locations of item
	int getX();
	int getY();
}