package com.rb34.message;

import com.rb34.util.ArrayUtils;

public class LocationTypeMessage implements Message
{
	private final byte type = 4;
	
	private int robotId;
	
	private int locationType; // DROPOFF = 1,   ITEM = 0
	
	private String itemId = "";
	private int itemCount;
	
	public LocationTypeMessage()
	{
	}
	
	public int getLocationType()
	{
		return locationType;
	}

	public void setLocationType(int locationType)
	{
		this.locationType = locationType;
	}

	public String getItemId()
	{
		return itemId;
	}

	public void setItemId(String itemId)
	{
		this.itemId = itemId;
	}

	public int getItemCount()
	{
		return itemCount;
	}

	public void setItemCount(int itemCount)
	{
		this.itemCount = itemCount;
	}

	public int getRobotId()
	{
		return this.robotId;
	}
	
	public void setRobotId(int robotId)
	{
		this.robotId = robotId;
	}
	
	@Override
	public byte[] toByteArray()
	{
		int lengthInBytes = 4 + 4 + 4 + itemId.length()*2 + 4;

		// GENERAL MESSAGE PARAMS
		byte[] output = { type };
		output = ArrayUtils.concat(output, ArrayUtils.intToBytes(lengthInBytes));
		
		// ROBOT ID
		output = ArrayUtils.concat(output, ArrayUtils.intToBytes(robotId));
		
		// LOCATION TYPE
		output = ArrayUtils.concat(output, ArrayUtils.intToBytes(locationType));
		
		// ITEM ID
		output = ArrayUtils.concat(output, ArrayUtils.intToBytes(2 * itemId.length()));
		output = ArrayUtils.concat(output, ArrayUtils.stringToBytes(itemId));
		
		// ITEM COUNT
		output = ArrayUtils.concat(output, ArrayUtils.intToBytes(itemCount));

		return output;
	}
	
	public static LocationTypeMessage fromByteArray(byte[] bytes)
	{
		LocationTypeMessage msg = new LocationTypeMessage();
		int index = 0;
		
		//ROBOT ID
		int robotId = ArrayUtils.bytesToInt(bytes, index);
		index += 4;
		msg.setRobotId(robotId);
		
		//LOCATION TYPE
		int locationType = ArrayUtils.bytesToInt(bytes, index);
		index += 4;
		msg.setLocationType(locationType);
		
		// ITEM ID
		int idLength = ArrayUtils.bytesToInt(bytes, index);
		index += 4;
		byte[] idBytes = ArrayUtils.subArray(bytes, index, index + idLength);
		index += idLength;
		String itemId = ArrayUtils.bytesToString(idBytes);	
		msg.setItemId(itemId);
		
		
		
		//ITEM COUNT
		int itemCount = ArrayUtils.bytesToInt(bytes, index);
		index += 4;
		msg.setItemCount(itemCount);
		
		return msg;
	}
	
	@Override
	public String toString()
	{
		return "LocationTypeMessage [type=" + type + ", robotId=" + robotId + ", locationType=" + locationType
				+ ", itemId=" + itemId + ", itemCount=" + itemCount + "]";
	}

}
