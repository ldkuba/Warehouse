package com.rb34.route_execution;

import org.apache.log4j.Logger;

// Maintains the Heading of the Three Robots

public class RobotHeadings {

	private static Headings rb1heading = Headings.PLUS_X;
	private static Headings rb2heading = Headings.PLUS_X;
	private static Headings rb3heading = Headings.PLUS_X;
	
	final static Logger log4j = Logger.getLogger(ConvertToMovement.class);
	
	public static Headings getHeading1() {
		return rb1heading;
	}
	
	public static Headings getHeading2() {
		return rb2heading;
	}
	
	public static Headings getHeading3() {
		return rb3heading;
	}

	public static void setHeading1(Headings val) {
		if(val != null) {
			rb1heading = val;
		} else {
			log4j.error("Error Setting Robot 1 Heading: Heading is null or not of type Headings");
			throw new IllegalArgumentException("Heading is Empty");
		}
	}
	
	public static void setHeading2(Headings val) {
		if(val != null) {
			rb2heading = val;
		} else {
			log4j.error("Error Setting Robot 2 Heading: Heading is null or not of type Headings");
			throw new IllegalArgumentException("Heading is Empty");
		}
	}
	
	public static void setHeading3(Headings val) {
		if(val != null) {
			rb3heading = val;
		} else {
			log4j.error("Error Setting Robot 3 Heading: Heading is null or not of type Headings");
			throw new IllegalArgumentException("Heading is Empty");
		}
	}
	
}
