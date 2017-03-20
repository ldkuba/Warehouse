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
import com.rb34.network.Master;
import com.rb34.route_planning.Graph;

public class JobAssigner {
	final static Logger logger = Logger.getLogger(JobAssigner.class);

	private PriorityQueue<Job> jobs;
	private RobotManager robotManager;
	private ArrayList<Drop> dropLocations;
	private Graph graph;

	private Master master;
	
	public JobAssigner(PriorityQueue<Job> jobs, RobotManager rm, ArrayList<Drop> dropLocations, Master master) {
		logger.debug("Started JobAssigner");
		this.jobs = jobs;
		logger.debug("Received jobs");
		robotManager = rm;
		logger.debug("Received robot manager");
		this.dropLocations = dropLocations;
		logger.debug("Received drop locations");
		graph = new Graph();
		
		this.master = master;
	}

	public void assignJobs() {
		// the reason for using dropIndex is to send the robots to different
		// drop locations if they are available
		int dropIndex = 0;

		// run while there are jobs in the PriorityQueue
		while (!jobs.isEmpty()) {

			// iterate through every robot and check the status
			ArrayList<Robot> robots = robotManager.getRobots();
			for (Robot robot : robots) {
				if (robot.getRobotStatus() == Status.IDLE) {
					// get the first job
					Job job = jobs.poll();

					// select the drop location
					Drop drop = dropLocations.get(dropIndex);
					dropIndex++;
					if (dropIndex == dropLocations.size())
						dropIndex = 0;

					// sort the items from the job so the path has the shortest
					// distance
					ItemSorter itemSorter = new ItemSorter(job, robot.getXLoc(), robot.getYLoc(), drop.getX(),
							drop.getY());
					ArrayList<Item> items = itemSorter.getSortedItems();

					// update the robot data
					robot.setCurrentJob(job);
					robot.setRobotStatus(Status.RUNNING);
					robot.setXDropLoc(drop.getX());
					robot.setYDropLoc(drop.getY());
					logger.debug("Gave job " + job.getJobId() + " to robot #" + robots.indexOf(robot));

					// get the first item
					Item item = items.get(0);
					items.remove(0);
					robot.setItemsToPick(items);

					// start route planning
					graph.executeRoute(robot.getXLoc() + "|" + robot.getYLoc(), item.getX() + "|" + item.getY(), robot.getRobotId(), master);
					logger.debug("Sent robot #" + robots.indexOf(robot) + " from " + robot.getXLoc() + "|"
							+ robot.getYLoc() + " to" + item.getX() + "|" + item.getY());

				}

				if (robot.getRobotStatus() == Status.AT_ITEM) {
					robot.setRobotStatus(Status.RUNNING);

					ArrayList<Item> items = robot.getItemsToPick();

					// if there are items remaining, send the robot to the next
					// item, else send it to the drop location
					if (items.size() > 0) {
						logger.debug("Robot #" + robots.indexOf(robot) + " is doing job "
								+ robot.getCurrentJob().getJobId() + " picked an item");
						Item item = items.get(0);
						items.remove(0);
						robot.setItemsToPick(items);

						graph.executeRoute(robot.getXLoc() + "|" + robot.getYLoc(), item.getX() + "|" + item.getY(), robot.getRobotId(), master);
					} else {
						logger.debug(
								"Robot doing job " + robot.getCurrentJob().getJobId() + " is heading to the drop off");
						graph.executeRoute(robot.getXLoc() + "|" + robot.getYLoc(),
								robot.getXDropLoc() + "|" + robot.getYDropLoc(), robot.getRobotId(), master);
					}
				}
			}
		}
		logger.debug("Ran out of jobs");
	}
}