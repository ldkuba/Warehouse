package com.rb34.warehouse_interface;

import java.util.Random;

import com.rb34.route_execution.Headings;
import com.rb34.route_execution.Robot1Heading;

import lejos.robotics.RangeFinder;
import rp.robotics.mapping.GridMap;
import rp.robotics.navigation.GridPilot;
import rp.robotics.navigation.GridPose;
import rp.robotics.navigation.Heading;
import rp.robotics.simulation.MovableRobot;
import rp.systems.StoppableRunnable;

/**
 * Performs a random walk on a grid map, avoid things ahead of it within limits.
 * 
 * @author nah
 *
 */
public class SimulationController implements StoppableRunnable {

	private final GridMap m_map;
	private final GridPilot m_pilot;

	private boolean m_running = true;
	private final RangeFinder m_ranger;
	private final MovableRobot m_robot;

	public SimulationController(MovableRobot _robot, GridMap _map, GridPose _start,
			RangeFinder _ranger) {
		m_map = _map;
		m_pilot = new GridPilot(_robot.getPilot(), _map, _start);
		m_ranger = _ranger;
		m_robot = _robot;
	}

	/**
	 * Is there enough space in front of the robot to move into?
	 * 
	 * @return
	 */
	private boolean enoughSpace() {
		return m_ranger.getRange() > m_map.getCellSize()
				+ m_robot.getRobotLength() / 2f;
	}

	/**
	 * Is the grid junction ahead really a junction and clear of obstructions?
	 * 
	 * @return
	 */
	private boolean moveAheadClear() {
		GridPose current = m_pilot.getGridPose();
		GridPose moved = current.clone();
		moved.moveUpdate();
		return m_map.isValidTransition(current.getPosition(),
				moved.getPosition())
				&& enoughSpace();
	}

	@Override
	public void run() {

		Random rand = new Random();
		Heading head = null;
		while (m_running) {

			int X = WarehouseWindow.getX();
			int Y = WarehouseWindow.getY();
			
			
			
					
			
			//System.out.println(X);
			GridPose pos = m_pilot.getGridPose();
			GridPose p1 = new GridPose(X, Y, Heading.PLUS_X);
			
			int currentX = pos.getX();
			int currentY = pos.getY();
			
			
			/* Implement a way to update the simulation viewer based on 
			 real life position of robot
			
			if(X > currentX) {
			
			} */
			

		}
	}

	@Override
	public void stop() {
		m_running = false;
	}

}
