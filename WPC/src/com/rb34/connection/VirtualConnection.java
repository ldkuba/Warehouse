package com.rb34.connection;

import com.rb34.message.Message;
import com.rb34.message.MessageListener;

import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;

public class VirtualConnection implements Connection
{
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void send(Message msg)
	{
		// TODO Auto-generated method stub
		
	}
	
}
