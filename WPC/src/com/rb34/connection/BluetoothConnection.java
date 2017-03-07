package com.rb34.connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import com.rb34.message.Message;
import com.rb34.message.MessageListener;

import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTInfo;
import network.Receiver;
import network.Sender;

public class BluetoothConnection implements Connection
{
	private NXTInfo nxtInfo;

	/* EXPERIMANTAL
	private MyObjectInputStream inputStream;
	private MyObjectOutputStream outputStream;
	*/
	
	private DataInputStream inputStream;
	private DataOutputStream outputStream;
	
	private Sender sender;
	private Receiver receiver;
	
	private boolean running = true;

	public BluetoothConnection(NXTInfo nxtInfo)
	{
		this.nxtInfo = nxtInfo;
	}

	@Override
	public void run()
	{
		receiver = new Receiver(inputStream);
		sender = new Sender(outputStream);
		
		receiver.start();
		sender.start();
		
		try
		{
			receiver.join();
			sender.join();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
	}

	@Override
	public boolean connect(NXTComm comm) throws NXTCommException
	{
		if (comm.open(nxtInfo))
		{
			inputStream = new DataInputStream(comm.getInputStream());
			outputStream = new DataOutputStream(comm.getOutputStream());
		}

		return isConnected();
	}

	@Override
	public boolean isConnected()
	{
		return outputStream != null;
	}
	
	public void addListener(MessageListener listener)
	{
		receiver.addListener(listener);
	}

	public void send(Message msg)
	{
		sender.send(msg);
	}
	
}
