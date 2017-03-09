package com.rb34.jobSelection;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.log4j.*;

import com.rb34.jobInput.Job;

public class Selection {
	
	final static Logger logger = Logger.getLogger(Selection.class);
	
	public static ArrayList <Job> rewardSelection(ArrayList <Job> jobList){
		logger.debug("closes cancellation.csv");
		Collections.sort(jobList,Collections.reverseOrder());
		logger.debug("Sorts jobList according to netReward of each job");
		return jobList;
	}
}
