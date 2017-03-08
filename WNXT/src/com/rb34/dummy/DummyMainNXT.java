package com.rb34.dummy;

import com.rb34.message.MessageListener;
import com.rb34.message.TestMessage;
import com.rb34.network.Client;

public class DummyMainNXT
{
	public DummyMainNXT()
	{
		Client client = new Client();
		client.start();

		client.addListener(new MessageListener()
		{
			public void receivedTestMessage(TestMessage msg)
			{
				System.out.println(msg.getText());
			}
		});

		TestMessage msg = new TestMessage();
		msg.setText("HELLO MASTER");

		for (int i = 0; i < 2; i++)
		{
			client.send(msg);
			// System.out.print("HALLALAALALALLLOOOOOOOO");
		}

		try
		{
			client.join();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		DummyMainNXT dummy = new DummyMainNXT();
	}
}
