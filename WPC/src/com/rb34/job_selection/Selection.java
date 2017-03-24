package com.rb34.job_selection;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

import org.apache.log4j.*;

import com.rb34.jobInput.Job;
import com.rb34.jobInput.Reader;
import com.rb34.jobInput.interfaces.IItem;
import com.rb34.jobInput.interfaces.IOrder;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

public class Selection {
	
	// REVERT READER CLASS BEFORE PUSH.

	private final static int trainingSetSize = 1000;
	private final static int queueSize = 1000;
	final static Logger logger = Logger.getLogger(Selection.class);
	
	public static void main(String[] args) {
		sortJobs();
	}
	
	public static <T> void doBayes(ArrayList<Job> jobs) {
		// Step One: Express the problem with features.
		// Create numerical features (count, reward and weight).
		Attribute jobItemCount = new Attribute("jobItemCount");
		Attribute jobReward = new Attribute("jobReward");
		Attribute jobWeight = new Attribute("jobWeight");
		// Create a nominal class (cancelled or not cancelled).
		List<String> nominalValues = new ArrayList<String>(2);
		nominalValues.add("wasCancelled");
		nominalValues.add("wasNotCancelled");
		Attribute wasCancelled = new Attribute("wasCancelled", nominalValues);
		// Put it all together into a feature vector.
		ArrayList<Attribute> wekaAttributes = new ArrayList<Attribute>(4);
		wekaAttributes.add(jobItemCount);
		wekaAttributes.add(jobReward);
		wekaAttributes.add(jobWeight);
		wekaAttributes.add(wasCancelled);
		// Step Two: Train the classifier.
		Instances isTrainingSet = new Instances("Relation", wekaAttributes, 1000);
		isTrainingSet.setClassIndex(3);
		// Loop through the first 1000 jobs in 
		Iterator<Job> it = jobs.iterator();
		int count = 0;
		// Add 1000 instances to the training set.
		while (it.hasNext() && count < trainingSetSize) {
			Job job = it.next();
			float itemCount = 0;
			float reward = 0;
			float weight = 0;
			boolean cancelled = job.getCancelled();
			HashMap<String, IOrder> orderList = job.getOrderList();
			Collection<String> orderIds = job.getItemsID();
			for (String o : orderIds) {
				// Extract the order.
				// Orders contain items and their count.
				IOrder order = orderList.get(o);
				// Extract each item from the order.
				// Items contain their reward and weight.
				IItem item = order.getItem();
				// Extract the count, reward and weight of the item.
				itemCount += order.getCount();
				reward += item.getReward();
				weight += item.getWeight();
			}
			Instance instance = new DenseInstance(4);
			instance.setValue(wekaAttributes.get(0), itemCount);
			instance.setValue(wekaAttributes.get(1), reward);
			instance.setValue(wekaAttributes.get(2), weight);
			if (cancelled) {
				instance.setValue(wekaAttributes.get(3), "wasCancelled");
			} else {
				instance.setValue(wekaAttributes.get(3), "wasNotCancelled");
			}
			isTrainingSet.add(instance);
		}
		// Build the classifier.
		try {
			Classifier model = (Classifier) new NaiveBayes();
			model.buildClassifier(isTrainingSet);
			Evaluation test = new Evaluation(isTrainingSet);
			test.evaluateModel(model, isTrainingSet);
			String bayesSummary = test.toSummaryString();
	        System.out.println(bayesSummary);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static PriorityQueue<Job> sortJobs() {
		// Create an empty queue to contain the sorted jobs.
		PriorityQueue<Job> queue = new PriorityQueue<Job>(queueSize, new JobValueComparator());
		// Get the list of jobs from Reader. Thanks Juliano!
		ArrayList<Job> jobs = Reader.createJobList();
		// Do Bayes training here.
		doBayes(jobs);
		// Iterate through the jobs.
		Iterator<Job> it = jobs.iterator();
		int count = 0;
		while (it.hasNext() && count < queueSize) {
			// logger.debug("Processing Job #" + (count + 1) + "...");
			// Extract the next job.
			Job job = it.next();
			
			// Test job.
			
			
			// Create a new variable, jobValue.
			// This is used to determine how valuable the total job is,
			// taking into consideration each item's reward, weight and count.
			float jobValue = 0;
			// Create an empty list to store item coordinates.
			ArrayList<Point2D.Float> coordinates = new ArrayList<Point2D.Float>();
			// Iterate through the orders that belong to this job.
			HashMap<String, IOrder> orderList = job.getOrderList();
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
				// Do this to prevent division by 0.
				if (weight == 0) {
					weight = 0.0001f;
				}
				int itemCount = order.getCount();
				// Extract coordinates and add them to our ArrayList.
				coordinates.add(new Point2D.Float(item.getX(), item.getY()));
				// Calculate an arbitrary item value.
				// This formula takes the reward and applies a weight, based on the item weight.
				// The lighter the item, the greater the weight.
				// We then multiply by the item count.
				float orderValue = reward * (1 / weight) * itemCount;
				// Add this to the total job value.
				jobValue += orderValue;
			}
			// Sort our list of coordinates in ascending order.
			coordinates.sort(new CoordinatesComparator());
			// Estimate the travel time of this job and apply a weight to the total job value.
			float travelTime = 0;
			for (int i = 1; i < coordinates.size(); i++) {
				// Pick the two coordinates to compare.
				Point2D.Float c1 = coordinates.get(i-1);
				Point2D.Float c2 = coordinates.get(i);
				// Extract the individual X- and Y-coordinates.
				double x = c1.getX() - c2.getX();
				double y = c1.getY() - c2.getY();
				// Convert coordinates to positive numbers.
				x = Math.abs(x);
				y = Math.abs(y);
				// Add these together to obtain an estimate of the number
				// of steps required between this item and the next.
				travelTime += x + y;
			}
			// Do this to prevent division by 0.
			if (travelTime == 0) {
				travelTime = 0.0001f;
			}
			jobValue = jobValue * (100 / travelTime);
			// Set the job value, add it to the queue and move onto the next job.
			job.setJobValue(jobValue);
			queue.add(job);
			// logger.debug("Job #" + count + " has been added to the queue.");
			count++;
		}
		// logger.debug("Queue has been returned.");
		return queue;
	}
	
	public static PriorityQueue<Job> sortJobsBasic() {
		return new PriorityQueue<Job>(Reader.createJobList());
	}
	
}