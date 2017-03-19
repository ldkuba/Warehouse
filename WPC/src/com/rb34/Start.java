package com.rb34;

import com.rb34.general.Robot;
import com.rb34.general.RobotManager;
import com.rb34.jobInput.Drop;
import com.rb34.jobInput.Item;
import com.rb34.jobInput.Job;
import com.rb34.jobInput.Order;
import com.rb34.jobInput.Reader;
import com.rb34.job_assignment.JobAssigner;
import com.rb34.network.Master;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Start {

	public static Master master;
	
	public static void main(String[] args) {
		ArrayList<Job> jobs = new ArrayList<>();
		Job job1 = new Job("1000");
		Item item = new Item("a", 0f, 0f);
		item.setX(2);
		item.setY(2);
		job1.addItem("a", new Order(item, 1));
		jobs.add(job1);
		
		
		// Runs Job_Input
		PriorityQueue<Job> orderedJobs = new PriorityQueue<>(jobs);
		
		ArrayList<Drop> drops = new ArrayList<>();	// Runs Job_Selection
		Drop drop = new Drop(3, 3);
		drops.add(drop);
		
		
		master = new Master();
		master.start();
		
		Robot robot = new Robot();
		robot.setXLoc(0);
		robot.setYLoc(0);
		robot.setRobotId(0);
			
	
		RobotManager rm = new RobotManager();
		rm.addRobot(robot);
		
		master.addListener(rm);
	
		JobAssigner jobAssigner = new JobAssigner(orderedJobs, rm, drops);
		jobAssigner.assignJobs();	// Runs Job_Assignment
		try {
			master.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Run Warehouse_Interface
	}

}