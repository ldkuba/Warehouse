package com.rb34.dummy;
import com.rb34.behaviours.*;
import com.rb34.robot_interface.*;
import com.rb34.main.JunctionFollower;
import com.rb34.network.Client;
public class TrialMainNxt {
	private JunctionFollower robotMovement;
	private RobotScreen screen;
	
	TrialMainNxt() {
		Client client = new Client();
		client.start();
		screen = new RobotScreen(0, 0, "Starting");
		
		//System.out.println("HELOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
		
		robotMovement = new JunctionFollower(screen, client);
		
		System.out.println("");
	
		try {
			client.join();
		} catch (InterruptedException e) {
			System.out.println("Something went wrong.");
		}
		
	}
	
	public static void main(String[] args) {
		TrialMainNxt robot = new TrialMainNxt();
	}
}

