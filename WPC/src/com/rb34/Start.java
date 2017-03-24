package com.rb34;

import com.rb34.general.RobotManager;
import com.rb34.network.Master;
import com.rb34.warehouse_interface.StartScreen;

public class Start
{
	public static Master master;
	
	public static void main(String[] args)
	{
		master = new Master();
		Start.master.start();
		while(Start.master.areAllConnected()){}
		
		master.addListener(new RobotManager());	// Initialize
		System.out.println("A");
		new StartScreen();
		System.out.println("B");
		
		
		
		try {
			master.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}