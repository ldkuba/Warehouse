package com.rb34.message;

public class TestMessage extends AbstractMessage
{
	private String text;
	
	public TestMessage(String text)
	{
		this.text = text;
	}
	
	public String getText() 
	{	
		return this.text;
	}
}