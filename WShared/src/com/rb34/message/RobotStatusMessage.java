package com.rb34.message;

import com.rb34.util.ArrayUtils;

public class RobotStatusMessage implements Message
{
	private final byte type = 2;
	private int robotId;

	private int x, y;
	private String heading = "";
	private boolean isOnRoute, isOnJob, isWaitingForNewPath;

	public RobotStatusMessage()
	{

	}

	public boolean isWaitingForNewPath()
	{
		return isWaitingForNewPath;
	}

	public void setWaitingForNewPath(boolean isWaitingForNewPath)
	{
		this.isWaitingForNewPath = isWaitingForNewPath;
	}

	public String getHeading()
	{
		return heading;
	}

	public void setHeading(String heading)
	{
		this.heading = heading;
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

	public boolean isOnRoute()
	{
		return isOnRoute;
	}

	public void setOnRoute(boolean isOnRoute)
	{
		this.isOnRoute = isOnRoute;
	}

	public boolean isOnJob()
	{
		return isOnJob;
	}

	public void setOnJob(boolean isOnJob)
	{
		this.isOnJob = isOnJob;
	}

	@Override
	public byte[] toByteArray()
	{
		int lengthInBytes = 4 + 4 + 4 + 1 + 4 + 2 * heading.length();

		byte[] output = { type };
		output = ArrayUtils.concat(output, ArrayUtils.intToBytes(lengthInBytes));

		// ROBOT ID
		output = ArrayUtils.concat(output, ArrayUtils.intToBytes(robotId));

		// POSITION
		output = ArrayUtils.concat(output, ArrayUtils.intToBytes(x));
		output = ArrayUtils.concat(output, ArrayUtils.intToBytes(y));

		// TEXT
		output = ArrayUtils.concat(output, ArrayUtils.intToBytes(2 * heading.length()));
		output = ArrayUtils.concat(output, ArrayUtils.stringToBytes(heading));

		// FLAGS
		byte flags = (byte) (((isWaitingForNewPath ? 1 : 0) << 2) + (((isOnRoute ? 1 : 0) << 1) + (isOnJob ? 1 : 0)));
		output = ArrayUtils.concat(output, new byte[] { flags });

		return output;
	}

	public static RobotStatusMessage fromByteArray(byte[] bytes)
	{
		RobotStatusMessage msg = new RobotStatusMessage();
		int index = 0;

		// ROBOT ID
		int robotId = ArrayUtils.bytesToInt(bytes, index);
		index += 4;
		msg.setRobotId(robotId);

		// X
		int xPos = ArrayUtils.bytesToInt(bytes, index);
		msg.setX(xPos);
		index += 4;

		// Y
		int yPos = ArrayUtils.bytesToInt(bytes, index);
		msg.setY(yPos);
		index += 4;

		// HEADING
		int headingLength = ArrayUtils.bytesToInt(bytes, index);
		index += 4;
		byte[] headingBytes = ArrayUtils.subArray(bytes, index, index + headingLength);
		index += headingLength;
		String head = ArrayUtils.bytesToString(headingBytes);
		msg.setHeading(head);

		// FLAGS
		byte flags = bytes[index];
		index++;

		if ((flags & 1) == 1)
		{
			msg.setOnJob(true);
		} else
		{
			msg.setOnJob(false);
		}

		if (((flags >> 1) & 1) == 1)
		{
			msg.setOnRoute(true);
		} else
		{
			msg.setOnRoute(false);
		}

		if (((flags >> 2) & 1) == 1)
		{
			msg.setWaitingForNewPath(true);
		} else
		{
			msg.setWaitingForNewPath(false);
		}

		return msg;
	}

	@Override
	public String toString()
	{
		return "RobotStatusMessage [type=" + type + ", robotId=" + robotId + ", x=" + x + ", y=" + y + ", heading="
				+ heading + ", isOnRoute=" + isOnRoute + ", isOnJob=" + isOnJob + ", isWaitingForNewPath="
				+ isWaitingForNewPath + "]";
	}
}
