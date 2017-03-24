package com.rb34.job_selection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;

import org.apache.log4j.Logger;

import com.rb34.job_input.Job;
import com.rb34.job_input.Reader;
import com.rb34.job_input.interfaces.IItem;
import com.rb34.job_input.interfaces.IOrder;

public class Selection {

	// For testing.
	final static Logger logger = Logger.getLogger(Selection.class);

	public static PriorityQueue<Job> sortJobs() {
		// Create an empty queue to contain the sorted jobs.
		PriorityQueue<Job> queue = new PriorityQueue<Job>(10, new JobValueComparator());
		// Create a new variable, jobValue.
		// This is used to determine how valuable the total job is,
		// taking into consideration each item's reward, weight and count.
		float jobValue = 0.0f;
		// Get the list of jobs from Reader. Thanks Juliano!
		ArrayList<Job> jobs = Reader.createJobList();
		// Iterate through the jobs.
		Iterator<Job> it = jobs.iterator();
		int count = 0;
		while (it.hasNext()) {
			//jobValue = 0.0f;
			// Extract the next job.
			// Jobs contain a list of orders.
			Job job = (Job) it.next();
			// Extract the list of orders from this job.
			// The list of orders, surprise surprise, contains orders.
			HashMap<String, IOrder> orderList = job.getOrderList();
			// Extract the orderIds so we can access items from the orderList.
			Collection<String> orderIds = job.getItemsID();
			for (String o : orderIds) {
				// Extract the order.
				// Orders contain items and their count.
				IOrder order = orderList.get(o);
				// Extract each item from the order.
				// Items contain their reward, weight and coordinates.
				IItem item = order.getItem();
				// Extract the reward, weight and count of the item.
				float reward = item.getReward();
				float weight = item.getWeight();
				int itemCount = order.getCount();
				// Calculate an arbitrary item value.
				// This formula takes the reward and applies a weight, based on
				// the item weight.
				// The lighter the item, the greater the weight.
				// We then multiply by the item count.
				// TO IMPROVE:
				// Consider the robot travel time in the formula.
				float orderValue = reward * (1 / weight) * itemCount;
				// Add this to the total job value.
				jobValue += orderValue;
			}
			// Set the job value, add it to the queue and move onto the next
			// job.
			job.setJobValue(jobValue);
			queue.offer(job);
			count++;
			logger.debug("Job number " + count + " has been added to the queue.");
		}
		// Return the sorted list of jobs.
		logger.debug("Queue has been returned.");
		return queue;
	}
}
