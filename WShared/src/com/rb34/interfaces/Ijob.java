package com.rb34.interfaces;
import java.util.ArrayList;

//Interface for Job object
public interface Ijob{

	//Gets unique job ID
	public String getJobId();

	//Gets list of items to be collected
	//and the number of items.
	public ArrayList <Iorder> getOrders ();

}