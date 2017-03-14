package com.rb34.message;

import java.util.ArrayList;

import com.rb34.general.PathChoices;
import com.rb34.util.ArrayUtils;

public class NewPathMessage implements Message
{
	private final byte type = 1;
	
	PathChoices[] commands;
	
	public NewPathMessage()
	{	
	}
	
	public void setCommands(ArrayList<PathChoices> commandsList)
	{
		commands = (PathChoices[]) commandsList.toArray();		
	}
	
	public ArrayList<PathChoices> getCommands()
	{
		ArrayList<PathChoices> commandsList = new ArrayList<PathChoices>();
		
		for(int i = 0; i < commands.length; i++)
		{
			commandsList.add(commands[i]);
		}
		
		return commandsList;
	}
	
	public byte[] toByteArray()
	{
		int lengthInBytes = 4 + 4*commands.length;

		// GENERAL MESSAGE PARAMS
		byte[] output = { type };
		output = ArrayUtils.concat(output, ArrayUtils.intToBytes(lengthInBytes));
		
		// COMMANDS
		output = ArrayUtils.concat(output, ArrayUtils.intToBytes(commands.length));
		for(int i = 0; i < commands.length; i++)
		{
			output = ArrayUtils.concat(output, ArrayUtils.intToBytes(commands[i].ordinal()));
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
		ArrayList<PathChoices> commands = new ArrayList<PathChoices>();
		
		for(int i = 0; i < commandsLength; i++)
		{
			commands.add(PathChoices.values()[ArrayUtils.bytesToInt(bytes, index)]);
			index += 4;
		}
		
		msg.setCommands(commands);
		
		return msg;
	}
}
