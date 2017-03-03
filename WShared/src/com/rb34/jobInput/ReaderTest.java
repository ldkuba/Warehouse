package com.rb34.jobInput;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class ReaderTest {

	//Reads drop csv files and populates a list.
	public static ArrayList <Drop> createDropList(){
		BufferedReader reader = null;
		ArrayList<Drop> dropList = new ArrayList<Drop>();
		
		try {
			File fileJob = new File("myDocs/drops.csv");
			reader = new BufferedReader(new FileReader(fileJob));

			String dropLoc;
			while ((dropLoc = reader.readLine()) != null) {
				String[] dropInfo = dropLoc.split(",");
				int xLoc = Integer.parseInt(dropInfo[0]);
				int yLoc = Integer.parseInt(dropInfo[1]);
				Drop newDropLoc = new Drop(xLoc,yLoc);
				dropList.add(newDropLoc);

			}
			reader.close();
			return dropList;

		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//populates job list
	public static ArrayList<Job> createJobList() {
		BufferedReader reader = null;
		ArrayList<Item> itemList = new ArrayList<Item>();

		
		try {
			
			//First: Reads items.csv and creates an ArrayList of Items
			File fileItems = new File("myDocs/items.csv");
			reader = new BufferedReader(new FileReader(fileItems));

			String item;
			while ((item = reader.readLine()) != null) {
				String[] itemInfo = item.split(",");
				String itemID = itemInfo[0];
				float reward = Float.valueOf(itemInfo[1]);
				float weight = Float.valueOf(itemInfo[2]);
				Item newItem = new Item(itemID, reward, weight);
				itemList.add(newItem);

			}
			reader.close();

			//Second: Reads locations.csv and creates and uses the Item set methods to add the location to each item
			File fileLoc = new File("myDocs/locations.csv");
			reader = new BufferedReader(new FileReader(fileLoc));

			String location;
			while ((location = reader.readLine()) != null) {
				String[] locInfo = location.split(",");
				String itemID = locInfo[2];
				int xLoc = Integer.parseInt(locInfo[0]);
				int yLoc = Integer.parseInt(locInfo[1]);
				
				for (int index = 0; index <= itemList.size() - 1; index++) {
					if(itemList.get(index).getItemID().equals(itemID)){
						itemList.get(index).setX(xLoc);
						itemList.get(index).setY(yLoc);
						break;
					}
				}
			}
			reader.close();
 
			//Third: Reads jobs.csv and creates a list of jobs.
			ArrayList<Job> jobList = new ArrayList<Job>();	
			File fileJobs = new File("myDocs/jobs.csv"); reader = new
			BufferedReader(new FileReader(fileJobs));
			
			int count = 9999;
			String itemID;
			int itemIndex = 9999;
			String job; 
			
			while((job = reader.readLine()) != null){
				String [] jobInfo = job.split(","); 
				String jobID = jobInfo[0];
				Job newJob = new Job(jobID);
				
				//Checks what items the specific job requires and creates an order object
				//Order contains [Item, Item Count]
				for(int i = 1; i <= jobInfo.length -1; i += 2){ 
					 itemID =  jobInfo[i];
					 count = Integer.parseInt(jobInfo[i + 1]);
					 
					 for(int j = 0; j <= itemList.size() - 1; j++){
						 if(itemList.get(j).getItemID().equals(itemID)){
							 itemIndex = i;
							 break;
						 }
					 }
					 Order newOrder = new Order(itemList.get(itemIndex),count);
					 newJob.addItem(itemID, newOrder);
				}
				jobList.add(newJob);
			}
			 
			reader.close();
			return jobList;

		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {

		//Testing jobList
		ArrayList<Job> jobList = createJobList();
		for (int i = 0; i <= jobList.size()-1 ; i++) {
			System.out.println(jobList.get(i).getJobId());
			System.out.println(jobList.get(i).getItemsID());
			System.out.print("[");
			Iterator<String>itr = jobList.get(i).getItemsID().iterator();
		     while(itr.hasNext()) {
		         Object element = itr.next();
		         System.out.print(jobList.get(i).getCount((String)element) + ",");
		      }
			System.out.print("]\n");
		}
		
		//Testing Drop locations
		System.out.println("----------Drop Location Test ----------");
		ArrayList<Drop> dropList = createDropList();
		for(int i = 0; i <= dropList.size() - 1; i++){
			System.out.print("X:" + dropList.get(i).getX());
			System.out.println(" Y:" + dropList.get(i).getY());
			System.out.println("-----------------");
		}
	}
}
