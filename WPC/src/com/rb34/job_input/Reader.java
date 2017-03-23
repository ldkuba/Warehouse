package com.rb34.job_input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

public class Reader {

	static Logger logger = Logger.getLogger(Reader.class);
	private static String FILE_PATH = "NO_PATH_SET";

	public static void setFilePath(String filePath){
		FILE_PATH = filePath;
	}
	
	public static ArrayList<Drop> createDropList() {
		BufferedReader reader = null;
		ArrayList<Drop> dropList = new ArrayList<Drop>();

		try {
			File fileDrop = new File(FILE_PATH + "drops.csv");
			reader = new BufferedReader(new FileReader(fileDrop));

			String dropLoc;
			while ((dropLoc = reader.readLine()) != null
					&& dropLoc.length() > 0) {
				String[] dropInfo = dropLoc.split(",");
				int xLoc = Integer.parseInt(dropInfo[0]);
				int yLoc = Integer.parseInt(dropInfo[1]);

				Drop newDropLoc = new Drop(xLoc, yLoc);
				dropList.add(newDropLoc);
			}
			reader.close();
			logger.debug("Drop List Complete");
			return dropList;
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.debug("Drop List failed");
		return null;
	}

	public static ArrayList<Item> readItemList() {
		BufferedReader reader = null;
		ArrayList<Item> itemList = new ArrayList<Item>();
		try {
			File fileItems = new File(FILE_PATH + "items.csv");
			reader = new BufferedReader(new FileReader(fileItems));

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
			return itemList;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return itemList;
	}

	public static void readLocations(ArrayList<Item> itemList) {
		try {
			BufferedReader reader = null;
			File fileLoc = new File(FILE_PATH + "locations.csv");
			reader = new BufferedReader(new FileReader(fileLoc));

			String location;
			int i = 0;
			while ((location = reader.readLine()) != null
					&& location.length() > 0) {
				String[] locInfo = location.split(",");
				int xLoc = Integer.parseInt(locInfo[0]);
				int yLoc = Integer.parseInt(locInfo[1]);

				itemList.get(i).setX(xLoc);
				itemList.get(i).setY(yLoc);
				i++;

			}
			logger.debug("Reading locations file complete - Locations set");
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Job> readJobs(ArrayList<Item> itemList, String fileName) {
		try {
			BufferedReader reader = null;
			ArrayList<Job> jobList = new ArrayList<Job>();
			File fileJobs = new File(FILE_PATH + fileName);
			reader = new BufferedReader(new FileReader(fileJobs));

			int count = 9999;
			String itemID;
			int itemIndex = 9999;
			String job;

			while ((job = reader.readLine()) != null && job.length() > 0) {
				String[] jobInfo = job.split(",");
				String jobID = jobInfo[0];
				Job newJob = new Job(jobID);

				for (int index = 1; index <= jobInfo.length - 1; index += 2) {
					itemID = jobInfo[index];
					count = Integer.parseInt(jobInfo[index + 1]);
					for (int j = 0; j <= itemList.size() - 1; j++) {
						if (itemList.get(j).getItemID().equals(itemID)) {
							itemIndex = j;
							Order newOrder = new Order(itemList.get(itemIndex),
									count);
							newJob.addItem(itemID, newOrder);
							break;
						}
					}
				}
				jobList.add(newJob);
			}
			reader.close();
			return jobList;
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void readCancellations(ArrayList<Job> jobList) {
		try {
			BufferedReader reader = null;
			File fileCancellations = new File(FILE_PATH + "cancellations.csv");
			reader = new BufferedReader(new FileReader(fileCancellations));

			String line;
			boolean wasCancelled = false;
			int i = 0;
			while ((line = reader.readLine()) != null && line.length() > 0) {
				String[] lineInfo = line.split(",");
				String wasCancelledStr = lineInfo[1];
				if (wasCancelledStr.equals("1")) {
					wasCancelled = true;
				} else {
					wasCancelled = false;
				}
				jobList.get(i).setCancelled(wasCancelled);
				i++;

			}

			reader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Job> createJobList() {
		ArrayList <Item> itemList = readItemList();
		logger.debug("Read item.csv - created item List");
		
		readLocations(itemList);
		logger.debug("Read locations.csv - set locations");
		
		ArrayList <Job> jobList = readJobs(itemList, "jobs.csv");
		logger.debug("Read job.csv - created job List");
		
		readCancellations(jobList);
		logger.debug("Read cancelations.csv - set cancelations boolean");
		logger.debug("job list complete");
		return jobList;
	}
	
	public static ArrayList<Job> createSampleJobList(){
		ArrayList <Item> itemList = readItemList();
		logger.debug("Read item.csv - created item List");
		
		readLocations(itemList);
		logger.debug("Read locations.csv - set locations");
		
		ArrayList <Job> jobList = readJobs(itemList, "customJobs.csv");
		logger.debug("Read customJobs.csv - created job List");
		
		for(Job job: jobList){
			job.setCancelled(false);
		}
		logger.debug("set cancelations boolean");
		logger.debug("sample Job list complete");
		return jobList;
	}
}