package com.rb34;

import com.rb34.general.Robot;
import com.rb34.general.RobotManager;
import com.rb34.jobInput.Drop;
import com.rb34.jobInput.Job;
import com.rb34.job_assignment.JobAssigner;
import com.rb34.job_input.Reader;
import com.rb34.network.Master;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Start {

	public static void main(String[] args) {
		ArrayList<Job> jobs = Reader.createSampleJobList();	// Runs Job_Input
		PriorityQueue<Job> orderedJobs = new PriorityQueue<>(jobs);
		ArrayList<Drop> drops = Reader.createDropList();	// Runs Job_Selection

	//	Master master = new Master();
	//	master.start();
		
		Robot robot = new Robot();
		robot.setXLoc(0);
		robot.setYLoc(7);
		robot.setRobotId(1);
		
	
		RobotManager rm = new RobotManager();
		rm.addRobot(robot);
		
		//master.addListener(rm);
	
		JobAssigner jobAssigner = new JobAssigner(orderedJobs, rm, drops);
		jobAssigner.assignJobs();	// Runs Job_Assignment
		/*try {
			master.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		// Run Warehouse_Interface
	}

}