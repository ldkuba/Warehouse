package com.rb34.connection;

import com.rb34.message.Message;
import com.rb34.message.MessageListener;

import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;

public interface Connection extends Runnable
{
	boolean connect(NXTComm _comm) throws NXTCommException;
	
	boolean isConnected();
	
	void addListener(MessageListener listener);
	
	void send(Message msg);
}
