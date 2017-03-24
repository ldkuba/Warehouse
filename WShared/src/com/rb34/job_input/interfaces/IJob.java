package com.rb34.job_input.interfaces;

import java.util.Collection;
import java.util.HashMap;

//Interface for Job object
public interface IJob {

	// Gets unique job ID
	String getJobId();

	// gets numbers of Items required
	int getCount(String itemID);

	// Adds item to Hash Table (Key = item ID , Value = order object)
	// Order object contains [Item, count];
	void addItem(String itemID, IOrder order);

	// returns true or false depending if this job was cancelled previously
	boolean getCancelled();

	// sets true or false depending if this job was cancelled previously
	void setCancelled(boolean wasCancelled);

	// Gets hashMap, where the key is the Item ID and the value is the Order
	HashMap<String, IOrder> getOrderList();

	// Gets all IDs of the items required by the job.
	Collection<String> getItemsID();

	// Gets total reward
	float getTotalReward();
}