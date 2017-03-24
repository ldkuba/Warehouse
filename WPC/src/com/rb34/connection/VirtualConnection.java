package com.rb34.connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import com.rb34.message.Message;
import com.rb34.message.MessageListener;
import com.rb34.network.Receiver;
import com.rb34.network.Sender;

import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;

public class VirtualConnection implements Connection
{
	private DataInputStream inputStream;
	private DataOutputStream outputStream;
	
	private Sender sender;
	private Receiver receiver;
	
	private boolean running = true;
	
	public VirtualConnection()
	{
		
	}

	@Override
	public void run()
	{
		
	}

	@Override
	public boolean connect(NXTComm _comm) throws NXTCommException
	{
		return false;
	}

	@Override
	public boolean isConnected()
	{
		return false;
	}

	@Override
	public void addListener(MessageListener listener)
	{
		
	}

	@Override
	public void send(Message msg)
	{
			
	}
	
}
