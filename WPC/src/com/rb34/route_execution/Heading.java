package com.rb34.route_execution;

import java.util.HashMap;

// Maintains the Heading of the robot
//Replace with pose class in lejos?

public class Heading
{
	private static HashMap<Integer, String> headings = new HashMap<>();

	public static String getHeading(int robotId)
	{
		return headings.get(robotId);
	}

	public static void setHeading(String val, int robotId)
	{
		headings.put(robotId, val);
	}

}
