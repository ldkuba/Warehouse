package com.rb34.route_execution;

// Maintains the Heading of the First Robot

public class RobotHeadings {

	private static Headings rb1heading = Headings.PLUS_X;
	private static Headings rb2heading = Headings.PLUS_X;
	private static Headings rb3heading = Headings.PLUS_X;
	
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
		rb1heading = val;
	}
	
	public static void setHeading2(Headings val) {
		rb2heading = val;
	}
	
	public static void setHeading3(Headings val) {
		rb3heading = val;
	}
	
}
