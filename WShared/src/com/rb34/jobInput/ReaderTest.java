package com.rb34.jobInput;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReaderTest {

	
	public static ArrayList<Item> createItemList(){
		BufferedReader reader = null;
		ArrayList<Item> itemList = new ArrayList <Item>();

		try {
		    File file = new File("myDocs/items.csv");
		    reader = new BufferedReader(new FileReader(file));

		    String line;
		    while ((line = reader.readLine()) != null) {
		        String [] itemInfo = line.split(",");
		        String itemID = itemInfo[0];
		        float reward =  Float.valueOf(itemInfo[1]);
		        float weight = Float.valueOf(itemInfo[2]);
		        Item newItem = new Item(itemID,reward,weight);
		        itemList.add(newItem);
		    }
		    reader.close();
		    return itemList;

		    
		} catch (IOException e) {
		    e.printStackTrace();
		} 
		try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public static void main(String[] args) {
		//ItemList created
		ArrayList<Item> itemList = new ArrayList <Item>();
		
		Item itemA = new Item("aa",1.0f,0.2f);
		itemA.setX(1);
		itemA.setY(5);
		Item itemB = new Item("aa",1.0f,0.2f);
		itemB.setX(3);
		itemB.setY(3);
		Item itemC = new Item("aa",1.0f,0.2f);
		itemC.setX(7);
		itemC.setY(7);
		
		itemList.add(itemA);
		itemList.add(itemB);
		itemList.add(itemC);
		
		//JobList
		ArrayList<Job> jobList = new ArrayList <Job>();
		
		Job jobA = new Job("1000");
		jobA.addItem(itemA, 20);
		jobA.addItem(itemC, 5);
		jobList.add(jobA);
		
		Job jobB = new Job("1001");
		jobB.addItem(itemB, 10);
		jobList.add(jobB);
		
		System.out.println(jobList.get(0).getJobId());
		System.out.println(jobList.get(0).getCount(itemA));
		System.out.println(jobList.get(0).getCount(itemC));
		System.out.println(jobList.get(0).getItems());
		
	}

}
