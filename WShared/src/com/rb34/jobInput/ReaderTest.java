package com.rb34.jobInput;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReaderTest {

	
	public ArrayList<Item> createItemList(){
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
		
		
	}

}
