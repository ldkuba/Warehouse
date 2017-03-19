package com.rb34.network;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.rb34.message.MessageListener;
import com.rb34.message.NewPathMessage;
import com.rb34.message.RobotInitMessage;
import com.rb34.message.RobotStatusMessage;
import com.rb34.message.TestMessage;
import com.rb34.util.ArrayUtils;

public class Receiver extends Thread
{
	private DataInputStream inputStream;

	private ArrayList<MessageListener> listeners;

	public Receiver(DataInputStream inputStream)
	{
		this.inputStream = inputStream;
		listeners = new ArrayList<MessageListener>();
	}

	public void addListener(MessageListener listener)
	{
		listeners.add(listener);
	}

	public void run()
	{

		while (true)
		{
			try
			{
				int type = inputStream.read();	
				
				System.out.println("Received message type: " + type);
				
				if (type == 0)
				{
					//System.out.println("RECEIVED MESSAGE OF TYPE: " + type);
					
					byte[] lengthBytes = new byte[4];
					inputStream.read(lengthBytes, 0, 4);

					int length = ArrayUtils.bytesToInt(lengthBytes, 0);

					byte[] bytes = new byte[length];

					inputStream.read(bytes);
					
					TestMessage msg = TestMessage.fromByteArray(bytes);

					for (int i = 0; i < listeners.size(); i++)
					{
						listeners.get(i).recievedTestMessage(msg);
					}
				} else if(type == 1)
				{
					byte[] lengthBytes = new byte[4];
					inputStream.read(lengthBytes, 0, 4);

					int length = ArrayUtils.bytesToInt(lengthBytes, 0);

					byte[] bytes = new byte[length];

					inputStream.read(bytes);
					
					NewPathMessage msg = NewPathMessage.fromByteArray(bytes);

					for (int i = 0; i < listeners.size(); i++)
					{
						listeners.get(i).recievedNewPathMessage(msg);
					}
					
					
				} else if(type == 2)
				{
					byte[] lengthBytes = new byte[4];
					inputStream.read(lengthBytes, 0, 4);

					int length = ArrayUtils.bytesToInt(lengthBytes, 0);

					byte[] bytes = new byte[length];

					inputStream.read(bytes);
					
					RobotStatusMessage msg = RobotStatusMessage.fromByteArray(bytes);

					for (int i = 0; i < listeners.size(); i++)
					{
						listeners.get(i).recievedRobotStatusMessage(msg);
					}
				} else if(type == 3)
				{
					byte[] lengthBytes = new byte[4];
					inputStream.read(lengthBytes, 0, 4);

					int length = ArrayUtils.bytesToInt(lengthBytes, 0);

					byte[] bytes = new byte[length];

					inputStream.read(bytes);
					
					RobotInitMessage msg = RobotInitMessage.fromByteArray(bytes);
					
					for (int i = 0; i < listeners.size(); i++)
					{
						listeners.get(i).recievedRobotInitMessage(msg);
					}
				}

			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
