package com.rb34.interfaces;
import java.util.ArrayList;

//Interface for Job object
public interface IJob{

	//Gets unique job ID
	String getJobId();

	//Gets list of items to be collected
	//and the number of items.
	ArrayList <IOrder> getOrders ();

}