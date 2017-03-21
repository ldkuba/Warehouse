package com.rb34.dummy;

import java.io.PrintStream;
import com.rb34.main.JunctionFollower;
import com.rb34.message.TestMessage;
import com.rb34.network.Client;
import com.rb34.robot_interface.RobotScreen;
import lejos.nxt.comm.RConsole;

public class TrialMainNxt {
	private JunctionFollower robotMovement;
	private RobotScreen screen;
	public static Client client;

	TrialMainNxt() {
		client = new Client();
		client.start();

		while (!client.isConnected()) {
		}

		screen = new RobotScreen();
		robotMovement = new JunctionFollower(screen);

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
