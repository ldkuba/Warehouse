package com.rb34.connection;

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
	
}
