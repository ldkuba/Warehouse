package com.rb34.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import com.rb34.message.Message;
import com.rb34.message.MessageListener;

import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;
import network.Receiver;
import network.Sender;

public class Client
{
	/**
	 * Experimental - Object Serializer private MyObjectInputStream inputStream;
	 * private MyObjectOutputStream outputStream;
	 */
	private DataInputStream inputStream;
	private DataOutputStream outputStream;
	
	private Receiver receiver;
	private Sender sender;

	private boolean running = true;

	public Client()
	{
		Thread clientThread = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				BTConnection connection = Bluetooth.waitForConnection();
				
				System.out.println("Connected!");

				inputStream = connection.openDataInputStream();
				outputStream = connection.openDataOutputStream();
				
				receiver = new Receiver(inputStream);
				sender = new Sender(outputStream);
				
				receiver.start();
				sender.start();
				
				try
				{
					receiver.join();
					sender.join();
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		});

		clientThread.start();

		try
		{
			clientThread.join();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	public void send(Message msg)
	{
		sender.send(msg);
	}
	
	public void addListener(MessageListener listener)
	{
		receiver.addListener(listener);
	}

}
