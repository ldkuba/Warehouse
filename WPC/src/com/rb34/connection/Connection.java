package com.rb34.connection;

import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;

public interface Connection extends Runnable
{
	public boolean connect(NXTComm _comm) throws NXTCommException;
	
	public boolean isConnected();
}
