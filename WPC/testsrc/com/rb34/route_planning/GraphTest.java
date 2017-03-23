package com.rb34.route_planning;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;

import org.junit.Test;

import com.rb34.general.Robot;
import com.rb34.general.RobotManager;
import com.rb34.route_planning.graph_entities.IVertex;

public class GraphTest {
	/*
	@Test
	public void testCost() {
		Graph graph = new Graph();	
		assertEquals(3, graph.aStar("2|2", "3|4").getPathCost().get(), 0f);
	}
	
	@Test
	public void testCostForSamePosition() {
		Graph graph = new Graph();
		assertEquals(0f, graph.aStar("2|2", "2|2").getPathCost().get(), 0f);
	}
	
	@Test
	public void testCostForLongPath() {
		Graph graph = new Graph();
		assertEquals(14f, graph.aStar("9|6", "0|1").getPathCost().get(), 0f);
	}
	
	@Test
	public void testCostToObstacle() {
		Graph graph = new Graph();
		assertNull(graph.aStar("0|0", "1|1"));
	}
	
	@Test
	public void testCostToInvalidLocation() {
		Graph graph = new Graph();
		assertNull(graph.aStar("0|0", "-1|-5"));
	}
	
	*/
	@Test
	public void testHCA() {
		RobotManager rm = new RobotManager();
		Robot robot0 = new Robot();
		robot0.setRobotId(0);
		robot0.setXLoc(0);
		robot0.setYLoc(3);
		Robot robot1 = new Robot();
		robot1.setRobotId(1);
		robot1.setXLoc(0);
		robot1.setYLoc(4);
		Robot robot2 = new Robot();
		robot2.setRobotId(2);
		robot2.setXLoc(0);
		robot2.setYLoc(5);
		rm.addRobot(robot0);
		rm.addRobot(robot1);
		rm.addRobot(robot2);
		
		Graph graph = new Graph(rm);	
		graph.hCooperativeAStar("0|3", "0|6", 0).getPath();

		graph.hCooperativeAStar("0|4", "0|2", 1).getPath();

		graph.hCooperativeAStar("0|5", "2|2", 2).getPath();
		//graph.hCooperativeAStar("5|6", "0|7", 0).getPath();
		//graph.hCooperativeAStar("11|7", "3|3", 2).getPath();
		//graph.hCooperativeAStar("2|6", "0|5", 2).getPath().get();
		//graph.hCooperativeAStar("3|3", "3|0", 2).getPath();

		
	}

}
