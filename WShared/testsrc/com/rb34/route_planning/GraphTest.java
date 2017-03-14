package com.rb34.route_planning;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

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

}
