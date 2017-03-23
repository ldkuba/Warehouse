package com.rb34.job_input.interfaces;

//Interface for Job object
public interface IOrder{

	//Gets Item in that particular order
	IItem getItem();
	
	//gets numbers of those particular Items required
	int getCount();
}