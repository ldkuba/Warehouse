package com.rb34.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.rb34.message.Message;
import com.rb34.message.MessageListener;
import com.rb34.network.Receiver;
import com.rb34.network.Sender;

import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;

public class Client extends Thread
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

	}
	
	public void run()
	{
		System.out.println("waiting");
		
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
			inputStream.close();
			outputStream.close();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		} catch (IOException e2)
		{
			e2.printStackTrace();
		}

	}

	public boolean isConnected()
	{
		return sender != null;
	}
	
	public void send(Message msg)
	{
		while(sender == null)
		{
			
		}
		
		sender.send(msg);
	}

	public void addListener(MessageListener listener)
	{
		while(receiver == null)
		{
			
		}
		
		receiver.addListener(listener);
	}

}
