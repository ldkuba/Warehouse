package com.rb34.route_execution.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import com.rb34.general.PathChoices;
import com.rb34.general.Robot;
import com.rb34.network.Master;
import com.rb34.route_execution.Execute;
import com.rb34.route_execution.Headings;
import com.rb34.route_execution.RobotHeadings;
import com.rb34.route_planning.Graph;
import com.rb34.route_planning.graph_entities.IVertex;

public class TestConverter {

	@Test
	public void testgetHeadings() {
		Robot rb = new Robot();
		assertTrue(rb.getHeading() instanceof String);
	}
	
	@Test
	public void testsetHeadings() {
		Robot rb1 = new Robot();
		rb1.setHeading("S");
		
		assertEquals(rb1.getHeading(),"S");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyPath() {
		Robot rb1 = new Robot();
		Execute exec = new Execute();
		exec.runRoute(null,rb1);
		
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testEmptyHeading() {
		RobotHeadings.setHeading1(null);
	}
	

	@Test
	public void testObjectCreation() {
		Execute exec = new Execute();
		
		
		
		assertTrue(exec instanceof Execute);
	}
	
}
