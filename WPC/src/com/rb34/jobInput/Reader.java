package com.rb34.jobInput;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

public class Reader {

	final static Logger logger = Logger.getLogger(Reader.class);
	
	//Reads drop csv files and populates a list.
	public static ArrayList <Drop> createDropList(){
		BufferedReader reader = null;
		ArrayList<Drop> dropList = new ArrayList<Drop>();
		final String FILE_PATH = "myDocs/";
		
		try {
			File fileDrop = new File(FILE_PATH + "drops.csv");
			reader = new BufferedReader(new FileReader(fileDrop));

			String dropLoc;
			int logCount = 0;
			logger.debug("Starts reading each line");
			while ((dropLoc = reader.readLine()) != null && dropLoc.length() > 0) {
				logger.debug("reads a new line" + logCount++);
				String[] dropInfo = dropLoc.split(",");
				int xLoc = Integer.parseInt(dropInfo[0]);
				int yLoc = Integer.parseInt(dropInfo[1]);
				
				Drop newDropLoc = new Drop(xLoc,yLoc);
				dropList.add(newDropLoc);
				logger.debug("Adds need drop into dropList");
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
		final String FILE_PATH = "myDocs/";

		try {			
			//First: Reads items.csv and creates an ArrayList of Items
			File fileItems = new File(FILE_PATH + "items.csv");
			reader = new BufferedReader(new FileReader(fileItems));
			logger.debug("opens items.csv");

			String item;
			while ((item = reader.readLine()) != null && item.length() > 0) {
				String[] itemInfo = item.split(",");
				String itemID = itemInfo[0];
				float reward = Float.valueOf(itemInfo[1]);
				float weight = Float.valueOf(itemInfo[2]);
				Item newItem = new Item(itemID, reward, weight);
				itemList.add(newItem);

			}
			reader.close();
			logger.debug("closes items.csv");

			//Second: Reads locations.csv and creates and uses the Item set methods to add the location to each item
			File fileLoc = new File(FILE_PATH + "locations.csv");
			reader = new BufferedReader(new FileReader(fileLoc));
			logger.debug("opens locations.csv");

			String location;
			int i = 0;
			while ((location = reader.readLine()) != null && location.length() > 0) {
				String[] locInfo = location.split(",");
				int xLoc = Integer.parseInt(locInfo[0]);
				int yLoc = Integer.parseInt(locInfo[1]);
				
				itemList.get(i).setX(xLoc);
				itemList.get(i).setY(yLoc);
				i++;
				
			}
			reader.close();
			logger.debug("closes locations.csv");
 
			//Third: Reads jobs.csv and creates a list of jobs.
			ArrayList<Job> jobList = new ArrayList<Job>();	
			File fileJobs = new File(FILE_PATH + "jobs.csv");
			reader = new BufferedReader(new FileReader(fileJobs));
			logger.debug("opens jobs.csv");
			
			int count = 9999;
			String itemID;
			int itemIndex = 9999;
			String job; 
			
			while((job = reader.readLine()) != null && job.length() > 0){
				String [] jobInfo = job.split(","); 
				String jobID = jobInfo[0];
				Job newJob = new Job(jobID);
				
				//Checks what items the specific job requires and creates an order object
				//Order contains [Item, Item Count]
				for(int index = 1; index <= jobInfo.length -1; index += 2){ 
					 itemID =  jobInfo[index];
					 count = Integer.parseInt(jobInfo[index + 1]);

					 
					 for(int j = 0; j <= itemList.size() - 1; j++){
						 
						 if(itemList.get(j).getItemID().equals(itemID)){
							 itemIndex = j;
							 Order newOrder = new Order(itemList.get(itemIndex),count);
							 newJob.addItem(itemID, newOrder);
							 break;
						 }
					 }
				}
				jobList.add(newJob);
			}
			 
			reader.close();
			logger.debug("closes jobs.csv");
			
			File fileCancellations = new File(FILE_PATH + "cancellations.csv");
			reader = new BufferedReader(new FileReader(fileCancellations));
			logger.debug("opens cancellations.csv");
			
			String line;
			boolean wasCancelled = false;
			i = 0;
			while((line = reader.readLine()) != null && line.length() > 0){
				String [] lineInfo = line.split(","); 
				String wasCancelledStr = lineInfo[1];
				if(wasCancelledStr.equals("1")){
					wasCancelled = true;
				}
				else{
					wasCancelled = false;
				}
				jobList.get(i).setCancelled(wasCancelled);
				i++; 
				
			}
			 
			reader.close();
			logger.debug("closes cancellation.csv");
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
	public static ArrayList<Job> createSampleJobList() {
		BufferedReader reader = null;
		ArrayList<Item> itemList = new ArrayList<Item>();
		final String FILE_PATH = "myDocs/";

		try {			
			//First: Reads items.csv and creates an ArrayList of Items
			File fileItems = new File(FILE_PATH + "items.csv");
			reader = new BufferedReader(new FileReader(fileItems));
			logger.debug("opens items.csv");

			String item;
			while ((item = reader.readLine()) != null && item.length() > 0) {
				String[] itemInfo = item.split(",");
				String itemID = itemInfo[0];
				float reward = Float.valueOf(itemInfo[1]);
				float weight = Float.valueOf(itemInfo[2]);
				Item newItem = new Item(itemID, reward, weight);
				itemList.add(newItem);

			}
			reader.close();
			logger.debug("closes items.csv");

			//Second: Reads locations.csv and creates and uses the Item set methods to add the location to each item
			File fileLoc = new File(FILE_PATH + "locations.csv");
			reader = new BufferedReader(new FileReader(fileLoc));
			logger.debug("opens locations.csv");

			String location;
			int i = 0;
			while ((location = reader.readLine()) != null && location.length() > 0) {
				String[] locInfo = location.split(",");
				int xLoc = Integer.parseInt(locInfo[0]);
				int yLoc = Integer.parseInt(locInfo[1]);
				
				itemList.get(i).setX(xLoc);
				itemList.get(i).setY(yLoc);
				i++;
				
			}
			reader.close();
			logger.debug("closes locations.csv");
 
			//Third: Reads jobs.csv and creates a list of jobs.
			ArrayList<Job> jobList = new ArrayList<Job>();	
			File fileJobs = new File(FILE_PATH + "jobs.csv");
			reader = new BufferedReader(new FileReader(fileJobs));
			logger.debug("opens jobs.csv");
			
			int count = 9999;
			String itemID;
			int itemIndex = 9999;
			String job; 
			
			while((job = reader.readLine()) != null && job.length() > 0 && jobList.size() < 4){
				String [] jobInfo = job.split(","); 
				String jobID = jobInfo[0];
				Job newJob = new Job(jobID);
				
				//Checks what items the specific job requires and creates an order object
				//Order contains [Item, Item Count]
				for(int index = 1; index <= jobInfo.length -1; index += 2){ 
					 itemID =  jobInfo[index];
					 count = Integer.parseInt(jobInfo[index + 1]);

					 
					 for(int j = 0; j <= itemList.size() - 1; j++){
						 
						 if(itemList.get(j).getItemID().equals(itemID)){
							 itemIndex = j;
							 Order newOrder = new Order(itemList.get(itemIndex),count);
							 newJob.addItem(itemID, newOrder);
							 break;
						 }
					 }
				}
				jobList.add(newJob);
			}
			 
			reader.close();
			logger.debug("closes jobs.csv");
			
			File fileCancellations = new File(FILE_PATH + "cancellations.csv");
			reader = new BufferedReader(new FileReader(fileCancellations));
			logger.debug("opens cancellations.csv");
			
			String line;
			boolean wasCancelled = false;
			i = 0;
			while((line = reader.readLine()) != null && line.length() > 0 && i <= 3){
				String [] lineInfo = line.split(","); 
				String wasCancelledStr = lineInfo[1];
				if(wasCancelledStr.equals("1")){
					wasCancelled = true;
				}
				else{
					wasCancelled = false;
				}
				jobList.get(i).setCancelled(wasCancelled);
				i++; 
				
				
			}
			 
			reader.close();
			logger.debug("closes cancellation.csv");
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
	
	
	
}
