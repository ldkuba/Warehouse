package com.rb34.message;

import com.rb34.util.ArrayUtils;

public class StartLocalisationMessage implements Message
{
	private final byte type = 5;

	private int robotId;

	public StartLocalisationMessage()
	{
	}

	public int getRobotId()
	{
		return robotId;
	}

	public void setRobotId(int robotId)
	{
		this.robotId = robotId;
	}

	@Override
	public byte[] toByteArray()
	{
		int lengthInBytes = 4;

		// GENERAL MESSAGE PARAMS
		byte[] output = { type };
		output = ArrayUtils.concat(output, ArrayUtils.intToBytes(lengthInBytes));

		// ROBOT ID
		output = ArrayUtils.concat(output, ArrayUtils.intToBytes(robotId));

		return output;
	}

	public StartLocalisationMessage fromByteArray(byte[] bytes)
	{
		StartLocalisationMessage msg = new StartLocalisationMessage();
		int index = 0;

		// ROBOT ID
		int robotId = ArrayUtils.bytesToInt(bytes, index);
		index += 4;
		msg.setRobotId(robotId);
		
		return msg;

	}
}
