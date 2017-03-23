package com.rb34;

import com.rb34.general.Robot;
import com.rb34.general.RobotManager;
import com.rb34.general.interfaces.IRobot.Status;
import com.rb34.jobInput.Drop;
import com.rb34.jobInput.Item;
import com.rb34.jobInput.Job;
import com.rb34.jobInput.Order;
import com.rb34.jobInput.Reader;
import com.rb34.job_assignment.JobAssigner;
import com.rb34.network.Master;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Start
{

	public static Master master;

	public static void main(String[] args)
	{

		System.out.println("0");

		Job job = new Job("1");

		Item p1 = new Item("p1", 0f, 0f);
		p1.setX(10);
		p1.setY(0);

		job.addItem("p1", new Order(p1, 1));

		Job job2 = new Job("2");

		Item p2 = new Item("p2", 0f, 0f);
		p2.setX(9);
		p2.setY(2);

		job2.addItem("p2", new Order(p2, 1));		
		
		ArrayList<Drop> drops = new ArrayList<>();
		drops.add(new Drop(9, 0));

		PriorityQueue<Job> orderedJobs = new PriorityQueue<>();

		orderedJobs.add(job);
		orderedJobs.add(job2);

		System.out.println("2");

		master = new Master();
		master.start();
		
		Robot robot = new Robot();
		robot.setXLoc(11);
		robot.setYLoc(1);
		robot.setHeading("S");
		robot.setRobotId(0);

		Robot robot2 = new Robot();
		robot2.setXLoc(9);
		robot2.setYLoc(0);
		robot2.setHeading("W");
		robot2.setRobotId(1);
		
		/*
		Robot robot3 = new Robot();
		robot3.setXLoc(1);
		robot3.setYLoc(0);
		robot3.setRobotId(2);
		*/
		
		System.out.println("3");

		while(!master.areAllConnected())
		{
			
		}
		
		RobotManager rm = new RobotManager();
		rm.addRobot(robot);
		rm.addRobot(robot2);
//		rm.addRobot(robot3);

		System.out.println("4");

		master.addListener(rm);
		
		JobAssigner jobAssigner = new JobAssigner(orderedJobs, rm, drops);
		jobAssigner.assignJobs(); // Runs Job_Assignment

		System.out.println("5");

		try
		{
			master.join();
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Run Warehouse_Interface
	}

}