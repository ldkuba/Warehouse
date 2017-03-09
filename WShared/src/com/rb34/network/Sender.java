package com.rb34.network;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.rb34.message.Message;
import com.rb34.message.TestMessage;

public class Sender extends Thread
{
	private ArrayList<Message> messageQueue;
	
	private DataOutputStream outputStream;
	
	public Sender(DataOutputStream outputStream)
	{
		this.outputStream = outputStream;
		messageQueue = new ArrayList<Message>();
	}
	
	public void send(Message message)
	{
		this.messageQueue.add(message);
	}
	
	public void run()
	{
		while(true)
		{						
			if(!messageQueue.isEmpty())
			{
				System.out.println("SENDING MESSAGE");
				
				byte[] message = messageQueue.get(0).toByteArray();
				
				System.out.println(message.length);
				
				try
				{
					outputStream.write(message, 0, message.length);
					System.out.println(outputStream.size());
					outputStream.flush();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
				
				messageQueue.remove(0);
			}
		}
	}
}
