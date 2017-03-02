package com.rb34.jobInput.interfaces;

//Interface for Job object
public interface IOrder{

	//Gets unique job ID
	IItem getItem();
	
	//gets numbers of Items required
	int getCount();
}