package com.rb34.message;

import com.rb34.util.ArrayUtils;

public class RobotStatusMessage implements Message
{
	private final byte type = 2;
	
	private int x, y;
	private boolean isOnRoute, isOnJob;

	public RobotStatusMessage()
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
		int lengthInBytes = 4 + 4 + 1;
		
		byte[] output = { type };
		output = ArrayUtils.concat(output, ArrayUtils.intToBytes(lengthInBytes));
		
		output = ArrayUtils.concat(output, ArrayUtils.intToBytes(x));
		output = ArrayUtils.concat(output, ArrayUtils.intToBytes(y));
		
		byte flags = (byte) (((isOnRoute?1:0) << 1) + (isOnJob?1:0));
		
		output = ArrayUtils.concat(output, new byte[]{ flags });
	
		return output;
	}

	public static RobotStatusMessage fromByteArray(byte[] bytes)
	{
		RobotStatusMessage msg = new RobotStatusMessage();
		int index = 0;
		
		// X
		int xPos = ArrayUtils.bytesToInt(bytes, index);
		msg.setX(xPos);
		index += 4;
		
		// Y
		int yPos = ArrayUtils.bytesToInt(bytes, index);
		msg.setY(yPos);
		index += 4;
		
		// FLAGS
		byte flags = bytes[index];
		
		if((flags & 1) == 1)
		{
			msg.setOnJob(true);
		}else
		{
			msg.setOnJob(false);
		}
		
		if(((flags >> 1) & 1) == 1)
		{
			msg.setOnRoute(true);
		}else
		{
			msg.setOnRoute(false);
		}
		
		return msg;
	}
	
}
