package com.rb34.route_execution;

import java.util.HashMap;

public class Heading {
	private static HashMap<Integer, String> headings = new HashMap<>();

	public static String getHeading(int robotId) {
		return headings.get(robotId);
	}

	public static void setHeading(String val, int robotId) {
		headings.put(robotId, val);
	}

}