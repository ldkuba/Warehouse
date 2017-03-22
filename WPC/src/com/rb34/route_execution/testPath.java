package com.rb34.route_execution;

import com.rb34.general.Robot;
import com.rb34.route_planning.Graph;

public class testPath {

	public static void main(String[] args) {
		Graph graph = new Graph();
		Robot robot = new Robot();
		graph.executeRoute("0|0", "0|0", robot);

	}

}
