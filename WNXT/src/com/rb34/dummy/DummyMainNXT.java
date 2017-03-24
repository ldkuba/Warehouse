package com.rb34.dummy;

import com.rb34.message.LocalisationResultMessage;
import com.rb34.message.LocationTypeMessage;
import com.rb34.message.MessageListener;
import com.rb34.message.NewPathMessage;
import com.rb34.message.RobotInitMessage;
import com.rb34.message.RobotStatusMessage;
import com.rb34.message.StartLocalisationMessage;
import com.rb34.message.TestMessage;
import com.rb34.network.Client;

//Inital test class when trying to integrate robot motion and networking
public class DummyMainNXT {
	
	public DummyMainNXT() {
		Client client = new Client();
		client.start();

		client.addListener(new MessageListener() {
			
			public void receivedTestMessage(TestMessage msg) {
				System.out.println(msg.getText());
			}

			@Override
			public void recievedNewPathMessage(NewPathMessage msg) {
				System.out.println(msg.getCommands().size());
			}

			@Override
			public void recievedRobotStatusMessage(RobotStatusMessage msg) {
				
			}

			@Override
			public void recievedTestMessage(TestMessage msg) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void recievedRobotInitMessage(RobotInitMessage msg) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void recievedLocationTypeMessage(LocationTypeMessage msg) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void recieveedStartLocalisationMessage(StartLocalisationMessage msg)
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void recieveedLocalisationResultMessage(LocalisationResultMessage msg)
			{
				// TODO Auto-generated method stub
				
			}
		});
		
		TestMessage msg1 = new TestMessage();
		msg1.setText("HELLO MASTER");
		client.send(msg1);
		
		try {
			client.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		DummyMainNXT dummy = new DummyMainNXT();
	}
}
