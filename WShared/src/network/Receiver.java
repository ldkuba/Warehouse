package network;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.rb34.message.MessageListener;
import com.rb34.message.TestMessage;

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
				while(inputStream.available() == 0)
				{
					
				}
				
				byte type = inputStream.readByte();

				System.out.println("RECEIVED MESSAGE OF TYPE: " + type);
				
				if (type == 0)
				{
					int length = inputStream.readInt();
					
					System.out.println("Size: " + length);
					
					boolean ff = true;
					
					while(ff)
					{
						
					}
					
					byte[] bytes = new byte[length];
					
					inputStream.read(bytes, 0, length);
				
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
