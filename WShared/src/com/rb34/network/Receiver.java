package com.rb34.network;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.rb34.message.MessageListener;
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

				if(type != -1)
					System.out.println("RECEIVED MESSAGE OF TYPE: " + type);
				
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
						listeners.get(i).receivedTestMessage(msg);
					}
				} else
				{

				}

			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
