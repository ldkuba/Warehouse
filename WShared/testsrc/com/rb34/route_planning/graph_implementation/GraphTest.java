package com.rb34.route_planning.graph_implementation;

import static org.junit.Assert.*;

import org.junit.Test;

import com.rb34.route_planning.graph_entities.Heuristic;

import rp.robotics.mapping.MapUtils;

public class GraphTest {

	@Test
	public void testCost() {
		Graph graph = new Graph(MapUtils.createRealWarehouse());	
		assertEquals(5f, graph.aStar("0|0", "0|5", new Heuristic()).getPathCost().get(), 0f);
	}
	
	@Test
	public void testCostForSamePosition() {
		Graph graph = new Graph(MapUtils.createRealWarehouse());
		assertEquals(0f, graph.aStar("0|0", "0|0", new Heuristic()).getPathCost().get(), 0f);
	}
	
	@Test
	public void testPathToObstacle() {
		Graph graph = new Graph(MapUtils.createRealWarehouse());
		assertNull(graph.aStar("0|0", "1|1", new Heuristic()));
	}
	
	@Test
	public void testPathToInvalidLocation() {
		Graph graph = new Graph(MapUtils.createRealWarehouse());
		assertNull(graph.aStar("0|0", "-1|-5", new Heuristic()));
	}

}
