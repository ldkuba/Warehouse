package com.rb34.job_assignment;

import java.util.ArrayList;
import java.util.PriorityQueue;

import org.apache.log4j.Logger;

import com.rb34.general.Robot;
import com.rb34.general.RobotManager;
import com.rb34.general.interfaces.IRobot.Status;
import com.rb34.job_input.Drop;
import com.rb34.job_input.Item;
import com.rb34.job_input.Job;
import com.rb34.route_planning.Graph;

public class JobAssigner extends Thread {
	final static Logger logger = Logger.getLogger(JobAssigner.class);

	private PriorityQueue<Job> jobs;
	private ArrayList<Drop> dropLocations;
	private Graph graph;

	@Override
	public void run() {
		assignJobs();
	}

	public JobAssigner(PriorityQueue<Job> jobs) {
		logger.debug("Started JobAssigner");
		this.jobs = jobs;
		logger.debug("Received jobs");
		logger.debug("Received robot manager");
		this.dropLocations = RobotManager.getDropoffList();
		logger.debug("Received drop locations");
		graph = new Graph();
	}

	public void assignJobs() {
		ArrayList<Robot> robots = RobotManager.getRobots();
		// run while there are jobs in the PriorityQueue
		while (!jobs.isEmpty() || !areAllIdle(robots)) {

			// iterate through every robot and check the status
			for (Robot robot : robots) {
				if (!jobs.isEmpty() && robot.getRobotStatus() == Status.IDLE) {
					// get the first job
					Job job = jobs.poll();

					logger.debug(robot.getRobotId() + " is not idle!");

					// sort the items from the job so the path has the shortest
					// distance
					ItemSorter itemSorter = new ItemSorter(job, robot.getXLoc(), robot.getYLoc(), dropLocations);
					System.out.println("robot coords " + robot.getYLoc() + "|" + robot.getYLoc());
					itemSorter.sortItems();

					ArrayList<Item> items = itemSorter.getSortedItems();
					ArrayList<String> destinations = itemSorter.getDestinations();

					// update the robot data
					robot.setCurrentJob(job);
					logger.debug("Gave job " + job.getJobId() + " to robot #" + robots.indexOf(robot));

					// Get coordinates for the first destination
					String destination = destinations.get(0);
					robot.setDestination(destination);
					destinations.remove(0);
					robot.setDestinations(destinations);

					// get the first item
					Item item = items.get(0);

					if (destination.equals(item.getX() + "|" + item.getY())) {
						robot.setCurrentlyGoingToItem(true);
						robot.setCurrentItem(item);
						items.remove(0);
						robot.setItemsToPick(items);
					} else
						robot.setCurrentlyGoingToItem(false);

					// start route planning

					graph.executeRoute(robot.getXLoc() + "|" + robot.getYLoc(), destination, robot);
					logger.debug("Sent robot #" + robots.indexOf(robot) + " from " + robot.getXLoc() + "|"
							+ robot.getYLoc() + " to" + destination);

					robot.setRobotStatus(Status.RUNNING);
				}

				if (robot.getRobotStatus() == Status.AT_ITEM) {
					logger.debug("Robot at item");

					ArrayList<Item> items = robot.getItemsToPick();

					ArrayList<String> destinations = robot.getDestinations();
					logger.debug(destinations.size());
					// if there are destinations remaining, send the robot to
					// the next one
					if (destinations.size() > 0) {
						String destination = destinations.get(0);
						robot.setDestination(destination);
						destinations.remove(0);
						robot.setDestinations(destinations);
						if (items.size() > 0) {
							Item item = items.get(0);

							if (destination.equals(item.getX() + "|" + item.getY())) {

								robot.setCurrentlyGoingToItem(true);
								robot.setCurrentItem(item);
								items.remove(0);
								robot.setItemsToPick(items);
							} else
								robot.setCurrentlyGoingToItem(false);
						} else
							robot.setCurrentlyGoingToItem(false);

						logger.debug("Sent robot #" + robots.indexOf(robot) + " from " + robot.getXLoc() + "|"
								+ robot.getYLoc() + " to" + destination);
						graph.executeRoute(robot.getXLoc() + "|" + robot.getYLoc(), destination, robot);

						robot.setRobotStatus(Status.RUNNING);
					} else {
						// logger.debug("Robot #" + robot.getRobotId() + " has
						// finished job "
						// + robot.getCurrentJob().getJobId());
					}
				}
			}
		}
		logger.debug("Ran out of jobs and all robots are idle");
	}

	private boolean areAllIdle(ArrayList<Robot> robots) {
		boolean areAllIdle = true;
		for (Robot robot : robots) {
			if (robot.getRobotStatus() != Status.IDLE)
				areAllIdle = false;
		}
		if (areAllIdle)
			System.out.println("all idle");
		return areAllIdle;
	}
}
