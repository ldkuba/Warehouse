package com.rb34.jobInput.interfaces;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

//Interface for Job object
public interface IJob{

	//Gets unique job ID
	String getJobId();
	
	//gets numbers of Items required
	int getCount(String itemID);
	
	//Adds item to Hash Table (Key = Item , Value = count)
	void addItem(String itemID, IOrder order);

	//Gets hashMap, where the key is the Item and the value is the number of items
	HashMap <String, IOrder> getOrderList ();
	
	//Gets Items in HashTable
	Collection <String> getItemsID();
	
	
}