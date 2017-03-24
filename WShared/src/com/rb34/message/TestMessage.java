package com.rb34.message;

import com.rb34.util.ArrayUtils;

public class TestMessage implements Message {
	private final byte type = 0;

	private String text;
	private int robotId;

	public TestMessage() {
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return this.text;
	}

	public int getRobotId() {
		return robotId;
	}

	@Override
	public String toString() {
		return "TestMessage [type=" + type + ", text=" + text + ", robotId=" + robotId + "]";
	}

	public void setRobotId(int robotId) {
		this.robotId = robotId;
	}

	public byte[] toByteArray() {
		int lengthInBytes = 4 + 4 + 2 * text.length();

		// GENERAL MESSAGE PARAMS
		byte[] output = { type };
		output = ArrayUtils.concat(output, ArrayUtils.intToBytes(lengthInBytes));

		// ROBOT ID
		output = ArrayUtils.concat(output, ArrayUtils.intToBytes(robotId));

		// TEXT
		output = ArrayUtils.concat(output, ArrayUtils.intToBytes(2 * text.length()));
		output = ArrayUtils.concat(output, ArrayUtils.stringToBytes(text));

		return output;
	}

	/**
	 * 
	 * @param bytes
	 *            WITHOUT the initial type byte and the size
	 * @return TestMessage object based on the bytes supplied
	 */
	public static TestMessage fromByteArray(byte[] bytes) {
		TestMessage msg = new TestMessage();
		int index = 0;

		// ROBOT ID
		int robotId = ArrayUtils.bytesToInt(bytes, index);
		index += 4;
		msg.setRobotId(robotId);

		// TEXT
		int textLength = ArrayUtils.bytesToInt(bytes, index);
		index += 4;
		byte[] textBytes = ArrayUtils.subArray(bytes, index, index + textLength);
		index += textLength;
		String text = ArrayUtils.bytesToString(textBytes);
		msg.setText(text);

		return msg;
	}

}