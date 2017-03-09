package com.rb34;

import com.rb34.general.Robot;
import com.rb34.general.RobotManager;
import com.rb34.jobInput.Drop;
import com.rb34.jobInput.Job;
import com.rb34.jobInput.Reader;
import com.rb34.job_assignment.JobAssigner;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Start {

	public static void main(String[] args) {
		ArrayList<Job> jobs = Reader.createSampleJobList();
		PriorityQueue<Job> orderedJobs = new PriorityQueue<>(jobs);
		ArrayList<Drop> drops = Reader.createDropList();

		Robot robot = new Robot();
		robot.setXLoc(0);
		robot.setYLoc(7);

		Robot robot2 = new Robot();
		robot2.setXLoc(6);
		robot2.setYLoc(0);

		RobotManager rm = new RobotManager();
		rm.addRobot(robot);
		rm.addRobot(robot2);

		//

		JobAssigner jobAssigner = new JobAssigner(orderedJobs, rm, drops);
		jobAssigner.assignJobs();
	}

}