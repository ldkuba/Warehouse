package com.rb34.dummy;

import com.rb34.message.MessageListener;
import com.rb34.message.NewPathMessage;
import com.rb34.message.TestMessage;
import com.rb34.network.Master;

public class DummyMainPC
{
	private Master master;

	public DummyMainPC()
	{
		master = new Master();
		master.start();

		master.addListener(new MessageListener()
		{
			public void receivedTestMessage(TestMessage msg)
			{
				System.out.println(msg.getText());				
			}

			@Override
			public void recievedNewPathMessage(NewPathMessage msg)
			{
				System.out.println(msg.getCommands().size());
			}
		});
		
		
		TestMessage msg = new TestMessage();
		msg.setText("HELLO ROBOT");
		master.send(msg, 0);
		
			
		System.out.println("MASTER IS ACTUALLY WORKING");

		try
		{
			master.join();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		DummyMainPC dummy = new DummyMainPC();
	}
}
