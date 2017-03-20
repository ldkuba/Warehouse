package com.rb34.network;

import java.util.ArrayList;

import com.rb34.connection.BluetoothConnection;
import com.rb34.connection.Connection;
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

	}

	public void run()
	{
		connections = new ArrayList<Connection>();

		try
		{
			BluetoothConnection connection1 = new BluetoothConnection(new NXTInfo(NXTCommFactory.BLUETOOTH, "WALL-E", "001653115A7E"));
			connections.add(connection1);
			connection1.connect(NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH));

			BluetoothConnection connection2 = new BluetoothConnection(new NXTInfo(NXTCommFactory.BLUETOOTH, "NXT", "0016531817C1"));
			connections.add(connection2);
			connection2.connect(NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH));
			
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
			e.printStackTrace();
		}
	}

	public boolean areAllConnected()
	{
		if(connections == null)
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
		while(!areAllConnected())
		{
			System.out.println("Waiting for conneection");
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
		if(connections.size() > robotId)
		{
			if(connections.get(robotId) != null)
				connections.get(robotId).send(msg);
		}
	}

}