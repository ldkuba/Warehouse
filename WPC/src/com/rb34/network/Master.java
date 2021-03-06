package com.rb34.network;

import java.util.ArrayList;
import java.util.HashMap;

import com.rb34.connection.BluetoothConnection;
import com.rb34.connection.Connection;
import com.rb34.general.RobotManager;
import com.rb34.message.Message;
import com.rb34.message.MessageListener;

import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;

public class Master extends Thread
{
	private ArrayList<Connection> connections;

	private boolean running = true;

	public Master()
	{
		connections = new ArrayList<Connection>();
	}

	@Override
	public void run()
	{
		try
		{	
			BluetoothConnection connection1, connection2, connection3;
			switch(RobotManager.getRobots().size()){
				case 3:
					connection3 = new BluetoothConnection(new NXTInfo(NXTCommFactory.BLUETOOTH, "manfred", "00165308E53D"));
					connections.add(connection3);
					connection3.connect(NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH));
					
					connection2 = new BluetoothConnection(new NXTInfo(NXTCommFactory.BLUETOOTH, "Red Riding Hood", "0016531AFBBB"));
					connections.add(connection2);
					connection2.connect(NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH));
					
					connection1 = new BluetoothConnection(new NXTInfo(NXTCommFactory.BLUETOOTH, "NiXTy", "001653157A48"));
					connections.add(connection1);
					connection1.connect(NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH));
					break;
				case 2:
					connection3 = new BluetoothConnection(new NXTInfo(NXTCommFactory.BLUETOOTH, "NiXTy", "001653157A48"));
					connections.add(connection3);
					connection3.connect(NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH));
					
					connection2 = new BluetoothConnection(new NXTInfo(NXTCommFactory.BLUETOOTH, "Red Riding Hood", "0016531AFBBB"));
					connections.add(connection2);
					connection2.connect(NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH));
				
					break;
				case 1:
					/*connection3 = new BluetoothConnection(new NXTInfo(NXTCommFactory.BLUETOOTH, "WALL-E", "001653115A7E"));
					connections.add(connection3);
					connection3.connect(NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH));*/
					
					connection2 = new BluetoothConnection(new NXTInfo(NXTCommFactory.BLUETOOTH, "Red Riding Hood", "0016531AFBBB"));
					connections.add(connection2);
					connection2.connect(NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH));
					break;
			}
			
			System.out.println("ALL CONNECTED !!!!!!!");
			
			ArrayList<Thread> threads = new ArrayList<>(connections.size());

			for (Connection connection : connections)
			{
				threads.add(new Thread(connection));
			}

			for (Thread thread : threads)
			{
				thread.start();
			}

			for (Thread thread : threads)
			{
				try
				{
					thread.join();
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}																																															
			}

		} catch (NXTCommException e)
		{
			System.out.println("FAILED TO CONNECT TO ROBOT");
		}
	}

	public boolean areAllConnected(int val)
	{		
		if(connections.size() < val)
		{
			return false;
		}
		
		for(int i = 0; i < connections.size(); i++)
		{
			if(!connections.get(i).isConnected())
			{
				return false;
			}
		}
		
		return true;
	}
	
	public void addListener(MessageListener listener)
	{
		while(!areAllConnected(connections.size()))
		{
		}
		
		while(connections == null)
		{
			
		}
		
		for(Connection connection : this.connections)
		{
			connection.addListener(listener);
		}
	}

	public void send(Message msg, int robotId)
	{
		System.out.println("Sending message to robot: " + robotId + "\nMessage:\n" + msg.toString());
		
		if(connections.size() > robotId)
		{
			if(connections.get(robotId) != null)
				connections.get(robotId).send(msg);
		}
	}

}