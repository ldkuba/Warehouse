package com.rb34.job_assignment;

import java.util.ArrayList;
import java.util.PriorityQueue;

import org.apache.log4j.Logger;

import com.rb34.general.Robot;
import com.rb34.general.RobotManager;
import com.rb34.general.interfaces.IRobot.Status;
import com.rb34.jobInput.Drop;
import com.rb34.jobInput.Item;
import com.rb34.jobInput.Job;
import com.rb34.route_planning.Graph;

public class JobAssigner {
	final static Logger logger = Logger.getLogger(JobAssigner.class);
	
	private PriorityQueue<Job> jobs;
	private RobotManager robotManager;
	private ArrayList<Drop> dropLocations;
	private Graph graph;

	public JobAssigner(PriorityQueue<Job> jobs, RobotManager rm, ArrayList<Drop> dropLocations) {
		logger.trace("Started JobAssigner");
		this.jobs = jobs;
		robotManager = rm;
		this.dropLocations = dropLocations;
		graph = new Graph();
	}

	public void assignJobs() {
		// the reason for using dropIndex is to send the robots to different drop locations if they are available
		int dropIndex = 0;
		
		// run while there are jobs in the PriorityQueue
		while (!jobs.isEmpty()) {
			
			// iterate through every robot and check the status
			for (Robot robot : robotManager.getRobots()) {
				if (robot.getRobotStatus() == Status.IDLE) {
					// get the first job
					Job job = jobs.poll();

					// select the drop location
					Drop drop = dropLocations.get(dropIndex);
					dropIndex++;
					if (dropIndex == dropLocations.size())
						dropIndex = 0;

					// sort the items from the job so the path has the shortest distance
					ItemSorter itemSorter = new ItemSorter(job, robot.getXLoc(), robot.getYLoc(), drop.getX(),
							drop.getY());
					ArrayList<Item> items = itemSorter.getSortedItems();

					// update the robot data
					robot.setCurrentJob(job);
					robot.setRobotStatus(Status.RUNNING);
					robot.setDropX(drop.getX());
					robot.setDropY(drop.getY());
					logger.trace("Gave job " + job.getJobId() + " to robot");
					
					// get the first item
					Item item = items.get(0);
					items.remove(0);
					robot.setItemsToPick(items);

					// start route planning
					graph.executeRoute(robot.getXLoc() + "|" + robot.getYLoc(), item.getX() + "|" + item.getY());
					

				}

				if (robot.getRobotStatus() == Status.AT_ITEM) {
					robot.setRobotStatus(Status.RUNNING);
					
					ArrayList<Item> items = robot.getItemsToPick();
					
					// if there are items remaining, send the robot to the next item, else send it to the drop location
					if (items.size() > 0) {
						logger.trace("Robot doing job " + robot.getCurrentJob().getJobId() + " picked an item");
						Item item = items.get(0);
						items.remove(0);
						robot.setItemsToPick(items);

						graph.executeRoute(robot.getXLoc() + "|" + robot.getYLoc(), item.getX() + "|" + item.getY());
					} else {
						logger.trace("Robot doing job " + robot.getCurrentJob().getJobId() + " is heading to the drop off");
						graph.executeRoute(robot.getXLoc() + "|" + robot.getYLoc(),
								robot.getDropX() + "|" + robot.getDropY());
					}
				}
			}
		}
		logger.trace("Ran out of jobs");
	}
}
