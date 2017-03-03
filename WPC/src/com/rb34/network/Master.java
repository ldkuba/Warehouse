package com.rb34.network;

import java.util.ArrayList;

import com.rb34.connection.BluetoothConnection;
import com.rb34.connection.Connection;

import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;

public class Master
{
	private ArrayList<Connection> connections;

	private boolean running;

	public Master()
	{
		connections = new ArrayList<Connection>();
		
		try
		{
			BluetoothConnection connection1 = new BluetoothConnection(new NXTInfo(NXTCommFactory.BLUETOOTH, "Aaron", "0016531B59FF"));
			connections.add(connection1);
			connection1.connect(NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH));

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

}