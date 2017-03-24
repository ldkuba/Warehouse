package com.rb34;

import java.util.ArrayList;
import java.util.PriorityQueue;

import com.rb34.general.Robot;
import com.rb34.general.RobotManager;
import com.rb34.job_assignment.JobAssigner;
import com.rb34.job_input.Drop;
import com.rb34.job_input.Item;
import com.rb34.job_input.Job;
import com.rb34.job_input.Order;
import com.rb34.job_input.Reader;
import com.rb34.job_selection.Selection;
import com.rb34.network.Master;

public class Start
{

	public static Master master;

	public static void main(String[] args)
	{
		Reader.setFilePath("myDocs/");
		
		master = new Master();
		master.start();
		
		Robot robot = new Robot();
		robot.setXLoc(0);
		robot.setYLoc(0);
		robot.setHeading("E");
		robot.setRobotId(0);
		/*
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
		
		ArrayList<Drop> drops = Reader.createDropList();

		System.out.println(drops.size());
		//System.out.println(Selection.sortJobs().size());
		
		for (Job job : Selection.sortJobs()) {
			System.out.println(job.getJobId());
		}
		
		while(!master.areAllConnected())
		{
			
		}
		
		RobotManager rm = new RobotManager();
		rm.addRobot(robot);
	//	rm.addRobot(robot2);
//		rm.addRobot(robot3);

		master.addListener(rm);
		
		JobAssigner jobAssigner = new JobAssigner(Selection.sortJobs(), rm, drops);
		jobAssigner.assignJobs(); // Runs Job_Assignment

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