package com.rb34.jobInput.interfaces;

//Interface for Item object
public interface IItem{

	//Gets unique Item ID
	String getItemID();

	//Gets Reward of item
	float getReward();
	
	//Gets Weight of item
	float getWeight();

	//Gets X and Y locations of item
	int getX();
	int getY();
}