package com.rb34.connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.rb34.message.TestMessage;

import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTInfo;

public class BluetoothConnection implements Connection
{
	private NXTInfo nxtInfo;

	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;

	private boolean running = true;

	public BluetoothConnection(NXTInfo nxtInfo)
	{
		this.nxtInfo = nxtInfo;
	}

	@Override
	public void run()
	{
		while (running)
		{
			TestMessage msg = new TestMessage("Hellooooooooooo");
			try
			{
				outputStream.writeObject(msg);
				outputStream.flush();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			
			try
			{
				Thread.sleep(300);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			
		}
	}

	@Override
	public boolean connect(NXTComm comm) throws NXTCommException
	{
		if (comm.open(nxtInfo))
		{
			try
			{
				inputStream = new ObjectInputStream(comm.getInputStream());
				outputStream = new ObjectOutputStream(comm.getOutputStream());
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		return isConnected();
	}

	@Override
	public boolean isConnected()
	{
		return outputStream != null;
	}

}
