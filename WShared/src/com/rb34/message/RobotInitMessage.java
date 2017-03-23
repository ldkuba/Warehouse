package com.rb34.message;

import com.rb34.util.ArrayUtils;

public class RobotInitMessage implements Message
{
	@Override
	public String toString()
	{
		return "RobotInitMessage [type=" + type + ", robotId=" + robotId + ", x=" + x + ", y=" + y + ", heading="
				+ heading + "]";
	}

	private final byte type = 3;
	private int robotId;

	private int x;
	private int y;
	private String heading;
	
	public RobotInitMessage()
	{
	}

	public String getHeading()
	{
		return heading;
	}

	public void setHeading(String string)
	{
		this.heading = string;
	}

	public int getRobotId()
	{
		return robotId;
	}

	public void setRobotId(int robotId)
	{
		this.robotId = robotId;
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

	@Override
	public byte[] toByteArray()
	{
		int lengthInBytes = 4 + 4 + 4 + 4 + 2*heading.length();

		byte[] output = { type };
		output = ArrayUtils.concat(output, ArrayUtils.intToBytes(lengthInBytes));

		// ROBOT ID
		output = ArrayUtils.concat(output, ArrayUtils.intToBytes(robotId));

		// X POS
		output = ArrayUtils.concat(output, ArrayUtils.intToBytes(x));

		// Y POS
		output = ArrayUtils.concat(output, ArrayUtils.intToBytes(y));
		
		// HEADING
		output = ArrayUtils.concat(output,  ArrayUtils.intToBytes(heading.length()));
		output = ArrayUtils.concat(output, ArrayUtils.stringToBytes(heading));

		return output;
	}

	public static RobotInitMessage fromByteArray(byte[] bytes)
	{
		RobotInitMessage msg = new RobotInitMessage();
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
		
		// HEADING
		int headingLength = ArrayUtils.bytesToInt(bytes, index);
		index += 4;
		byte[] headingBytes = ArrayUtils.subArray(bytes, index, index + headingLength);
		index += headingLength;
		String head = ArrayUtils.bytesToString(headingBytes);	
		msg.setHeading(head);

		return msg;
	}

}
