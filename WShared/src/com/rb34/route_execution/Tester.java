package com.rb34.route_execution;


public class Tester {

	public static void main(String[] args) {
		
		Execute route = new Execute();
		// Test Class to ensure functionality so far works and that Heading is maintained through multiple runs
		// Executing a route for two given points
		// In final implementation this will have to be obtained from the job selection class and converted to 
		// appropriate format
		route.runRoute("0|0", "2|3");
		
		route.runRoute("2|3", "5|4");
		
		route.runRoute("5|4", "0|7");
		
	}
	
}
