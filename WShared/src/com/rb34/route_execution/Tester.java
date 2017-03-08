package com.rb34.route_execution;


public class Tester {

	public static void main(String[] args) {
		
		Execute route = new Execute();
		// Test Class to ensure functionality so far works and that Heading is maintained through multiple runs
		// Executing a route for two given points
		// In final implementation this will have to be obtained from the job selection class and converted to 
		// appropriate format
		
		//Simulating an order where item locations are (2,3),(5,4),(8,2) and drop off point is (10,7)
		
		System.out.println("Picking up First Item: " + "\n" + route.runRoute("0|3", "3|4"));
		
		System.out.println("");
		
		System.out.println("Picking up Second Item: " + "\n" + route.runRoute("3|4", "6|2"));
		
		System.out.println("");
		
		System.out.println("Picking up Third Item: " + "\n" + route.runRoute("6|2", "9|5"));
		
		System.out.println("");
		
		System.out.println("Dropping off Order: " + "\n" + route.runRoute("9|5", "11|7"));
		
		System.out.println("Return Home: " + "\n" + route.runRoute("11|7", "0|0"));
		
		
		
		
		
		
	}
	
}
