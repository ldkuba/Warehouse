package com.rb34.message;

import java.util.ArrayList;

import com.rb34.util.ArrayUtils;

public class NewPathMessage
{
	private final byte type = 1;
	
	String[] commands;
	
	public NewPathMessage()
	{	
	}
	
	public void setCommands(ArrayList<String> commandsList)
	{
		commands = (String[]) commandsList.toArray();		
	}
	
	public ArrayList<String> getCommands()
	{
		ArrayList<String> commandsList = new ArrayList<String>();
		
		for(int i = 0; i < commands.length; i++)
		{
			commandsList.add(commands[i]);
		}
		
		return commandsList;
	}
	
	public byte[] toByteArray()
	{
		int lengthInBytes = 4;
		for(int i = 0; i < commands.length; i++)
		{
			lengthInBytes += (4 + 2*commands[i].length());
		}

		// GENERAL MESSAGE PARAMS
		byte[] output = { type };
		output = ArrayUtils.concat(output, ArrayUtils.intToBytes(lengthInBytes));
		
		// COMMANDS
		output = ArrayUtils.concat(output, ArrayUtils.intToBytes(commands.length));
		for(int i = 0; i < commands.length; i++)
		{
			output = ArrayUtils.concat(output, ArrayUtils.intToBytes(commands[i].length()*2));
			output = ArrayUtils.concat(output, ArrayUtils.stringToBytes(commands[i]));
		}
		
		return output;
	}
	
	public static NewPathMessage fromByteArray(byte[] bytes)
	{
		NewPathMessage msg = new NewPathMessage();
		int index = 0;
		
		//COMMANDS
		int commandsLength = ArrayUtils.bytesToInt(bytes, index);
		index += 4;
		ArrayList<String> commands = new ArrayList<String>();
		
		for(int i = 0; i < commandsLength; i++)
		{
			int commandLength = ArrayUtils.bytesToInt(bytes, index);
			index += 4;
			byte[] commandBytes = ArrayUtils.subArray(bytes, index, index + commandLength);
			commands.add(ArrayUtils.bytesToString(commandBytes));
		}
		
		msg.setCommands(commands);
		
		return msg;
	}
}
