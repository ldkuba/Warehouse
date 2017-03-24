package com.rb34.message;

import java.util.ArrayList;
import java.util.Arrays;

import com.rb34.general.PathChoices;
import com.rb34.util.ArrayUtils;

public class NewPathMessage implements Message {
	@Override
	public String toString() {
		return "NewPathMessage [type=" + type + ", robotId=" + robotId + ", commands=" + Arrays.toString(commands)
				+ "]";
	}

	private final byte type = 1;
	private int robotId;

	PathChoices[] commands;

	public NewPathMessage() {
	}

	public int getRobotId() {
		return robotId;
	}

	public void setRobotId(int robotId) {
		this.robotId = robotId;
	}

	public void setCommands(ArrayList<PathChoices> commandsList) {
		commands = new PathChoices[commandsList.size()];
		for (int i = 0; i < commandsList.size(); i++) {
			if (commandsList.get(i).equals(PathChoices.FORWARD)) {
				commands[i] = PathChoices.FORWARD;
			} else if (commandsList.get(i).equals(PathChoices.LEFT)) {
				commands[i] = PathChoices.LEFT;
			} else if (commandsList.get(i).equals(PathChoices.RIGHT)) {
				commands[i] = PathChoices.RIGHT;
			} else if (commandsList.get(i).equals(PathChoices.ROTATE)) {
				commands[i] = PathChoices.ROTATE;
			}
		}

	}

	public ArrayList<PathChoices> getCommands() {
		ArrayList<PathChoices> commandsList = new ArrayList<PathChoices>();

		for (int i = 0; i < commands.length; i++) {
			commandsList.add(commands[i]);
		}

		return commandsList;
	}

	public byte[] toByteArray() {
		int lengthInBytes = 4 + 4 + 4 * commands.length;

		// GENERAL MESSAGE PARAMS
		byte[] output = { type };
		output = ArrayUtils.concat(output, ArrayUtils.intToBytes(lengthInBytes));

		// ROBOT ID
		output = ArrayUtils.concat(output, ArrayUtils.intToBytes(robotId));

		// COMMANDS
		output = ArrayUtils.concat(output, ArrayUtils.intToBytes(commands.length));
		for (int i = 0; i < commands.length; i++) {
			output = ArrayUtils.concat(output, ArrayUtils.intToBytes(commands[i].ordinal()));
		}

		return output;
	}

	public static NewPathMessage fromByteArray(byte[] bytes) {
		NewPathMessage msg = new NewPathMessage();
		int index = 0;

		// ROBOT ID
		int robotId = ArrayUtils.bytesToInt(bytes, index);
		index += 4;
		msg.setRobotId(robotId);

		// COMMANDS
		int commandsLength = ArrayUtils.bytesToInt(bytes, index);
		index += 4;
		ArrayList<PathChoices> commands = new ArrayList<PathChoices>();

		for (int i = 0; i < commandsLength; i++) {
			commands.add(PathChoices.values()[ArrayUtils.bytesToInt(bytes, index)]);
			index += 4;
		}

		msg.setCommands(commands);

		return msg;
	}
}
