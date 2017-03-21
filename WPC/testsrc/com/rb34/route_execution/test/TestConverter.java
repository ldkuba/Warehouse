package com.rb34.route_execution.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.rb34.network.Master;
import com.rb34.route_execution.Execute;
import com.rb34.route_execution.Headings;
import com.rb34.route_execution.RobotHeadings;

public class TestConverter {
	Master master = new Master();

	@Test
	public void testgetHeadings() {
		
		assertTrue(RobotHeadings.getHeading1() instanceof Headings);
		assertTrue(RobotHeadings.getHeading2() instanceof Headings);
		assertTrue(RobotHeadings.getHeading3() instanceof Headings);
	}
	
	@Test
	public void testsetHeadings() {
		RobotHeadings.setHeading1(Headings.MINUS_Y);
		Headings val = RobotHeadings.getHeading1();
		assertEquals(val,Headings.MINUS_Y);
		
		RobotHeadings.setHeading2(Headings.MINUS_X);
		Headings val2 = RobotHeadings.getHeading2();
		assertEquals(val2,Headings.MINUS_X);
		
		RobotHeadings.setHeading3(Headings.PLUS_X);
		Headings val3 = RobotHeadings.getHeading3();
		assertEquals(val3,Headings.PLUS_X);
		

	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyPath() {
		Execute exec = new Execute(master);
		exec.runRoute(null,0);
		
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testEmptyHeading() {
		RobotHeadings.setHeading1(null);
	}
	

	@Test
	public void testObjectCreation() {
		Execute exec = new Execute(master);
		assertTrue(exec instanceof Execute);
	}
	
}
