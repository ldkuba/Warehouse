package com.rb34.jobSelection.test;
import static org.junit.Assert.assertEquals;

import com.rb34.jobInput.Item;
import com.rb34.jobInput.Job;
import com.rb34.jobInput.Order;
import com.rb34.jobInput.interfaces.IOrder;
import com.rb34.jobSelection.*;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.Test;


public class rewardSortTest {
	
	private ArrayList<Job> jobList = Reader.createJobList();
	
	@Test
	
	public void sortedListSize(){
		jobList = Selection.rewardSelection(jobList);
		// assert statements
		assertEquals("Number of jobs in list should be 20000", 20000, jobList.size());
		
	}
	
	
	//@Test
	public void printSortedList(){
 		jobList = Selection.rewardSelection(jobList);
		for (int i = 0; i <= jobList.size() - 1; i++) {
			System.out.print("Job ID: " + jobList.get(i).getJobId());
			System.out.println(" -- netReward: " + jobList.get(i).getTotalReward());
			
			Iterator<IOrder> itr = jobList.get(i).getOrderList().values().iterator();
			while (itr.hasNext()) {
				IOrder element = itr.next();
				System.out.println(element.getItem().getReward() + " - " + element.getCount());
				System.out.println("---------------");
			}

			System.out.println("=========================================================");
			
		}
	}
}
