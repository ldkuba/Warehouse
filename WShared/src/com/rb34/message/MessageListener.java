package com.rb34.message;

public interface MessageListener {
	void recievedTestMessage(TestMessage msg);

	void recievedNewPathMessage(NewPathMessage msg);

	void recievedRobotStatusMessage(RobotStatusMessage msg);

	void recievedRobotInitMessage(RobotInitMessage msg);

	void recievedLocationTypeMessage(LocationTypeMessage msg);
	
	void recieveedStartLocalisationMessage(StartLocalisationMessage msg);
	
	void recieveedLocalisationResultMessage(LocalisationResultMessage msg);
}
