package com.rb34.message;

public interface MessageListener
{
	default void receiverTestMessage(TestMessage msg)
	{
	}
	
}
