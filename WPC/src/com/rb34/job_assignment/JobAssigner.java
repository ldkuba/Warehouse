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

public class JobAssigner
{
	final static Logger logger = Logger.getLogger(JobAssigner.class);

	private PriorityQueue<Job> jobs;
	private RobotManager robotManager;
	private ArrayList<Drop> dropLocations;
	private Graph graph;

	public JobAssigner(PriorityQueue<Job> jobs, RobotManager rm, ArrayList<Drop> dropLocations)
	{
		//System.out.println("HELOOO");
		logger.debug("Started JobAssigner");
		this.jobs = jobs;
		logger.debug("Received jobs");
		robotManager = rm;
		logger.debug("Received robot manager");
		this.dropLocations = dropLocations;
		logger.debug("Received drop locations");
		graph = new Graph();
	}

	public void assignJobs() {
		ArrayList<Robot> robots = robotManager.getRobots();
		
		// run while there are jobs in the PriorityQueue
		while (!jobs.isEmpty()) {

			// iterate through every robot and check the status
			for (Robot robot : robots) {
				if (robot.getRobotStatus() == Status.IDLE) {
					// get the first job
					Job job = jobs.poll();
					
					logger.debug(robot.getRobotId() + " is not idle!!!!!!!!!!!!!!!!!");

					// sort the items from the job so the path has the shortest
					// distance
					ItemSorter itemSorter = new ItemSorter(job, robot.getXLoc(), robot.getYLoc(), dropLocations);
					itemSorter.sortItems();

					ArrayList<Item> items = itemSorter.getSortedItems();
					ArrayList<String> destinations = itemSorter.getDestinations();

					// update the robot data
					robot.setCurrentJob(job);
					robot.setRobotStatus(Status.RUNNING);
					logger.debug("Gave job " + job.getJobId() + " to robot #" + robots.indexOf(robot));

					// Get coordinates for the first destination
					String destination = destinations.get(0);
					logger.debug("destination 0 " + destination);
					destinations.remove(0);
					robot.setDestinations(destinations);
					
					// get the first item
					Item item = items.get(0);
					if (destination.equals(item.getX() + "|" + item.getY())) {
						robot.setCurrentlyGoingToItem(true);
						robot.setCurrentItem(item);
						items.remove(0);
						robot.setItemsToPick(items);
					} else robot.setCurrentlyGoingToItem(false);

					// start route planning

					graph.executeRoute(robot.getXLoc() + "|" + robot.getYLoc(), destination, robot.getRobotId());

					logger.debug("Sent robot #" + robot.getRobotId() + " from " + robot.getXLoc() + "|"
							+ robot.getYLoc() + " to" + destination);

				}

				if (robot.getRobotStatus() == Status.AT_ITEM) {
					
					ArrayList<Item> items = robot.getItemsToPick();

					ArrayList<String> destinations = robot.getDestinations();

					// if there are destinations remaining, send the robot to the next one
					if (destinations.size() > 0) {
						robot.setRobotStatus(Status.RUNNING);
						
						String destination = destinations.get(0);
						destinations.remove(0);
						robot.setDestinations(destinations);

						if (items.size() > 0) {
							Item item = items.get(0);
							if (destination.equals(item.getX() + "|" + item.getY())) {
								robot.setCurrentlyGoingToItem(true);
								robot.setCurrentItem(item);
								items.remove(0);
								robot.setItemsToPick(items);
							} else robot.setCurrentlyGoingToItem(false);
						}  else robot.setCurrentlyGoingToItem(false);
						
						logger.debug("Sent robot #" + robots.indexOf(robot) + " from " + robot.getXLoc() + "|"
								+ robot.getYLoc() + " to" + destination);
						graph.executeRoute(robot.getXLoc() + "|" + robot.getYLoc(), destination, robot.getRobotId());
					} else {
						//robot.setRobotStatus(Status.IDLE);
						logger.debug(
								"Robot #" + robot.getRobotId() + " has finished job " + robot.getCurrentJob().getJobId());
					}
				}
			}
		}
		logger.debug("Ran out of jobs");
	}
}
