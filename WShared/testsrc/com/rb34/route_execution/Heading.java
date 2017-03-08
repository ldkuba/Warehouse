package com.rb34.route_execution;

// Maintains the Heading of the robot
//Replace with pose class in lejos?

public class Heading {
	
	private static String heading = "E";
	public static String getHeading() {
		return heading;
	}

	public static void setHeading(String val) {
		heading = val;
	}
	
}
