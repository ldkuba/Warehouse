package com.rb34.interfaces;

//Interface for Job object
public interface IOrder{

	//Gets Item ID that needs to be collected
	IItem getItem();

	//Gets numbers of Items to be collected
	int getCount ();

}