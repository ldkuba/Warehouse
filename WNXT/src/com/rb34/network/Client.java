package com.rb34.network;

import java.io.IOException;

import com.rb34.io.MyObjectInputStream;
import com.rb34.io.MyObjectOutputStream;
import com.rb34.message.AbstractMessage;
import com.rb34.message.TestMessage;

import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;

public class Client
{
	private MyObjectInputStream inputStream;
	private MyObjectOutputStream outputStream;

	private boolean running = true;

	public Client()
	{
		Thread clientThread = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				BTConnection connection = Bluetooth.waitForConnection();

				try
				{
					inputStream = new MyObjectInputStream(connection.openInputStream());
					outputStream = new MyObjectOutputStream(connection.openOutputStream());
				} catch (IOException e)
				{
					e.printStackTrace();
				}
				
				while (running)
				{
					try
					{
						AbstractMessage m = (AbstractMessage) inputStream.readObject();
						
						if(m instanceof TestMessage)
						{
							TestMessage msg = (TestMessage) m;
							
							System.out.println(msg.getText());
						}
						
					} catch (ClassNotFoundException e)
					{
						e.printStackTrace();
					} catch (IOException e)
					{
						e.printStackTrace();
					}
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

}
