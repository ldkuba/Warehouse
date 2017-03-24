package com.rb34.message;

import com.rb34.util.ArrayUtils;

public class LocalisationResultMessage implements Message
{
	private final byte type = 6;

	private int x;
	private int y;
	private int robotId;

	public LocalisationResultMessage()
	{

	}

	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
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
		int lengthInBytes = 4 + 4 + 4;

		// GENERAL MESSAGE PARAMS
		byte[] output = { type };
		output = ArrayUtils.concat(output, ArrayUtils.intToBytes(lengthInBytes));

		// ROBOT ID
		output = ArrayUtils.concat(output, ArrayUtils.intToBytes(robotId));

		// POSITION
		output = ArrayUtils.concat(output, ArrayUtils.intToBytes(x));
		output = ArrayUtils.concat(output, ArrayUtils.intToBytes(y));

		return output;
	}

	public LocalisationResultMessage toByteArray(byte[] bytes)
	{
		LocalisationResultMessage msg = new LocalisationResultMessage();
		int index = 0;

		// ROBOT ID
		int robotId = ArrayUtils.bytesToInt(bytes, index);
		index += 4;
		msg.setRobotId(robotId);

		// X POS
		int x = ArrayUtils.bytesToInt(bytes, index);
		index += 4;
		msg.setX(x);

		// Y POS
		int y = ArrayUtils.bytesToInt(bytes, index);
		index += 4;
		msg.setY(y);

		return msg;
	}

}
