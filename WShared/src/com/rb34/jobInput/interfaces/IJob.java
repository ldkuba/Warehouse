package com.rb34.jobInput.interfaces;
import java.util.Collection;
import java.util.HashMap;

//Interface for Job object
public interface IJob{

	//Gets unique job ID
	String getJobId();
	
	//gets numbers of Items required
	int getCount(IItem item);
	
	//Adds item to Hash Table (Key = Item , Value = count)
	void addItem(IItem item, int count);

	//Gets hashMap, where the key is the Item and the value is the number of items
	HashMap <IItem, Integer> getOrderList ();
	
	//Gets Items in HashTable
	Collection <IItem> getItems();
	
}