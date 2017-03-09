package com.rb34.jobInput.test;

import static org.junit.Assert.*;

import com.rb34.jobInput.*;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.Test;

import com.rb34.jobInput.Job;

public class jobListTest {

	
	//@Test
	public void JobIDChecks() {
		ArrayList<Job> jobList = Reader.createJobList();
		// assert statements
		assertEquals("JobID should be 10001", "10001", jobList.get(1)
				.getJobId());
		assertEquals("jobID one should be 10050", "10050", jobList.get(50)
				.getJobId());
		assertEquals("jobID one should be 12000", "12000", jobList.get(2000)
				.getJobId());
		assertEquals("jobID one should be 20000", "20000", jobList.get(10000)
				.getJobId());
		assertEquals("jobID one should be 29999", "29999", jobList.get(19999)
				.getJobId());
	}

	//@Test
	public void numOrder() {
		ArrayList<Job> jobList = Reader.createJobList();
		// assert statements
		assertEquals("Number of Orders should be 2 ", 2, jobList.get(0)
				.getOrderList().size());
		assertEquals("jobID one should be 4", 4, jobList.get(25).getOrderList()
				.size());
		assertEquals("jobID one should be 4", 4, jobList.get(75).getOrderList()
				.size());
		assertEquals("jobID one should be 5", 5, jobList.get(1000)
				.getOrderList().size());

	}

	//@Test
	public void sizeOfJobList() {
		ArrayList<Job> jobList = Reader.createJobList();
		// assert statements
		assertEquals("Number of jobs in list should be 20000", 20000,
				jobList.size());
	}

	//Not set as a test on purpose
	@Test
	public void printJobList() {
		ArrayList<Job> jobList = Reader.createSampleJobList();
		for (int i = 0; i <= jobList.size() - 1; i++) {
			System.out.println("Job ID: " + jobList.get(i).getJobId());
			System.out.println("Cancellation: " + jobList.get(i).getCancelled());
			System.out.println("List of Items: " + jobList.get(i).getItemsID());

			System.out.print("Count for each job: [");
			Iterator<String> itr = jobList.get(i).getItemsID().iterator();
			while (itr.hasNext()) {
				Object element = itr.next();
				System.out.print(jobList.get(i).getCount((String) element) + ",");
			}
			System.out.print("]\n");

			System.out.print("Item Info: [");
			itr = jobList.get(i).getItemsID().iterator();
			while (itr.hasNext()) {
				Object element = itr.next();
				System.out.print(jobList.get(i).getOrderList().get(element).getItem().getItemID()+ "/");
				System.out.print(jobList.get(i).getOrderList().get(element).getItem().getReward()+ "/");
				System.out.print(jobList.get(i).getOrderList().get(element).getItem().getWeight()+ "/");
				System.out.print(jobList.get(i).getOrderList().get(element).getItem().getX()+ ".");
				System.out.print(jobList.get(i).getOrderList().get(element).getItem().getY()+ " <=> ");
			}

			System.out.print("]\n");
			System.out.println("---------------");
		}
		
	}
}
