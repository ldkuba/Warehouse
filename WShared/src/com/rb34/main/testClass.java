package com.rb34.main;

public class testClass {

	public static void main(String[] args) {
		
		loader load  = new loader ();
		
		System.out.print(load.createDropList().get(0).getX());
		System.out.print(",");
		System.out.println(load.createDropList().get(0).getY());
		System.out.print(load.createDropList().get(1).getX());
		System.out.print(",");
		System.out.println(load.createDropList().get(0).getY());

	}

}
