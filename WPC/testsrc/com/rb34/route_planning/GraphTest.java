package com.rb34.route_planning;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;

import org.junit.Test;

import com.rb34.route_planning.graph_entities.IVertex;

public class GraphTest {
	
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
	
	
	@Test
	public void testHCA() {
		Graph graph = new Graph();	
		graph.hCooperativeAStar("0|0", "4|0", 0).getPath();
		graph.hCooperativeAStar("5|0", "1|0", 1).getPath();
		//graph.hCooperativeAStar("11|7", "3|3", 2).getPath();
		//graph.hCooperativeAStar("2|6", "0|5", 2).getPath().get();
		graph.hCooperativeAStar("3|3", "3|0", 2).getPath();

		
	}

}
