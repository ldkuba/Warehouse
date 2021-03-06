package com.rb34.network;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.rb34.message.Message;

public class Sender extends Thread {
	private ArrayList<Message> messageQueue;

	private DataOutputStream outputStream;

	public Sender(DataOutputStream outputStream) {
		this.outputStream = outputStream;
		messageQueue = new ArrayList<Message>();
	}

	public void send(Message message) {
		this.messageQueue.add(message);
	}

	public void run() {
		while (true) {
			if (!messageQueue.isEmpty()) {

				// System.out.println("messageQueue size: " +
				// messageQueue.size());

				// System.out.println("IS MQ null?: " + (messageQueue == null));

				if (messageQueue.get(0) == null) {
					System.out.println("NULLLLLLLLLLLLLLLLLLLLLLLERINO");
				}

				byte[] message = messageQueue.get(0).toByteArray();

				// System.out.println(messageQueue.get(0).toString());

				try {
					outputStream.write(message, 0, message.length);
					outputStream.flush();

				} catch (IOException e) {
					e.printStackTrace();
				}

				messageQueue.remove(0);

				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}
	}
}
