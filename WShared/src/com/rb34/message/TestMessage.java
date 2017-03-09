package com.rb34.message;

import com.rb34.util.ArrayUtils;

public class TestMessage implements Message
{
	private final byte type = 0;
	
	private String text;
	
	public TestMessage()
	{
	}
	
	public void setText(String text)
	{
		this.text = text;
	}
	
	public String getText() 
	{	
		return this.text;
	}
	
	public byte[] toByteArray()
	{
		int lengthInBytes = 4 + 2*text.length();
		
		//GENERAL MESSAGE PARAMS
		byte[] output = {type};
		output = ArrayUtils.concat(output, ArrayUtils.intToBytes(lengthInBytes));
		
		//TEXT
		output = ArrayUtils.concat(output, ArrayUtils.intToBytes(2*text.length()));
		output = ArrayUtils.concat(output, text.getBytes());		
		
		return output;
	}
	
	/**
	 * 
	 * @param bytes WITHOUT the initial type byte and the size
	 * @return TestMessage object based on the bytes supplied
	 */
	public static TestMessage fromByteArray(byte[] bytes)
	{
		TestMessage msg = new TestMessage();
		int index = 0;
		
		//TEXT
		int textLength = ArrayUtils.bytesToInt(bytes, index);
		index += 4;
		byte[] textBytes = ArrayUtils.subArray(bytes, index, index + textLength);
		index += textLength;
		msg.setText(new String(textBytes));		
				
		return msg;
	}
	
}