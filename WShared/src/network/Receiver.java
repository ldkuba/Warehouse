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
	}
		
	public void addListener(MessageListener listener)
	{
		listeners.add(listener);
	}
	
	public void run()
	{
		while(true)
		{
			try
			{
				byte type = inputStream.readByte();
				
				if(type == 0)
				{
					int length = inputStream.readInt();
					byte[] bytes = new byte[length];
					inputStream.read(bytes);
					TestMessage msg = TestMessage.fromByteArray(bytes);
					
					for(int i = 0; i < listeners.size(); i++)
					{
						listeners.get(i).receiverTestMessage(msg);
					}
				}else
				{
					
				}
				
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
