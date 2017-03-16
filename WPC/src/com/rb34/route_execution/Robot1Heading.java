package com.rb34.route_execution;

// Maintains the Heading of the First Robot

public class Robot1Heading {

	private static Headings heading = Headings.PLUS_X;
	
	public static Headings getHeading() {
		return heading;
	}

	public static void setHeading(Headings val) {
		heading = val;
	}
	
}
