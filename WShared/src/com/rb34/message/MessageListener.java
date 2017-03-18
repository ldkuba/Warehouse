package com.rb34.message;

public interface MessageListener
{
	void receivedTestMessage(TestMessage msg);
	
	void recievedNewPathMessage(NewPathMessage msg);
	
	void recievedRobotStatusMessage(RobotStatusMessage msg);
}
