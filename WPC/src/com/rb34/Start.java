package com.rb34;

import com.rb34.general.RobotManager;

import com.rb34.network.Master;
import com.rb34.warehouse_interface.StartScreen;

public class Start {
	
	public static Master master;

	public static void main(String[] args) {
		master = new Master();
		
		new StartScreen();
	
		try {
			master.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}