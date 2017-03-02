package com.rb34.jobInput;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class ReaderTest {

	public static ArrayList<Job> createJobList() {
		BufferedReader reader = null;
		ArrayList<Item> itemList = new ArrayList<Item>();
		// ArrayList<Job> jobList = new ArrayList <Job>();

		try {
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

			File fileLoc = new File("myDocs/locations.csv");
			reader = new BufferedReader(new FileReader(fileLoc));

			String location;
			while ((location = reader.readLine()) != null) {
				String[] locInfo = location.split(",");
				String itemID = locInfo[2];
				int xLoc = Integer.parseInt(locInfo[0]);
				int yLoc = Integer.parseInt(locInfo[1]);
				
				for (int index = 0; index < itemList.size() - 1; index++) {
					if(itemList.get(index).getItemID().equals(itemID)){
						itemList.get(index).setX(xLoc);
						itemList.get(index).setY(yLoc);
						break;
					}
				}
			}
			reader.close();
 
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
				
				for(int i = 1; i < jobInfo.length -1; i += 2){ 
					 itemID =  jobInfo[i];
					 count = Integer.parseInt(jobInfo[i + 1]);
					 
					 for(int j = 0; j < itemList.size() - 1; j++){
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

		
		ArrayList<Job> jobList = createJobList();
		for (int i = 0; i < 10; i++) {
			System.out.println(jobList.get(i).getJobId());
			System.out.println(jobList.get(i).getItemsID());
			
			//Got a nullPointerException when trying to get Count for a specific Item
			for(int j = 0; j < jobList.get(j).getItemsID().size() - 1; j++){
				String [] itemNames = new String [jobList.get(j).getItemsID().size() - 1];
				jobList.get(i).getOrderList().keySet().toArray(itemNames);
				System.out.println(jobList.get(i).getOrderList().get(itemNames[j]));
			}
		}
	}
}
