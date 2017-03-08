package com.rb34.jobInput.test;

import com.rb34.jobInput.*;
import java.util.ArrayList;
import java.util.Iterator;
import org.junit.Test;
import com.rb34.jobInput.Job;

public class jobListTest {

	@Test
	public void printJobList() {
		ArrayList<Job> jobList = Reader.createJobList();
		for (int i = 0; i <= jobList.size() - 1; i++) {
			System.out.println("Job ID: " + jobList.get(i).getJobId());
			System.out
					.println("Cancellation: " + jobList.get(i).getCancelled());
			System.out.println("List of Items: " + jobList.get(i).getItemsID());

			System.out.print("Count for each job: [");
			Iterator<String> itr = jobList.get(i).getItemsID().iterator();
			while (itr.hasNext()) {
				Object element = itr.next();
				System.out.print(jobList.get(i).getCount((String) element)
						+ ",");
			}
			System.out.print("]\n");

			System.out.print("Item Info: [");
			itr = jobList.get(i).getItemsID().iterator();
			while (itr.hasNext()) {
				Object element = itr.next();
				System.out.print(jobList.get(i).getOrderList().get(element)
						.getItem().getItemID()
						+ "/");
				System.out.print(jobList.get(i).getOrderList().get(element)
						.getItem().getReward()
						+ "/");
				System.out.print(jobList.get(i).getOrderList().get(element)
						.getItem().getWeight()
						+ "/");
				System.out.print(jobList.get(i).getOrderList().get(element)
						.getItem().getX()
						+ ".");
				System.out.print(jobList.get(i).getOrderList().get(element)
						.getItem().getY()
						+ " <=> ");
			}

			System.out.print("]\n");
			System.out.println("---------------");
		}
	}
}
