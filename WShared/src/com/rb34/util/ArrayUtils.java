package com.rb34.util;

import java.util.ArrayList;

public class ArrayUtils
{
	public static byte[] concat(byte[] a1, byte[] a2)
	{
		byte[] output = new byte[a1.length + a2.length];
		
		for(int i = 0; i < a1.length; i++)
		{
			output[i] = a1[i];
		}
		
		for(int i = 0; i < a2.length; i++)
		{
			output[i+a1.length] = a2[i];
		}
		
		return output;
	}
	
	public static byte[] intToBytes(int a)
	{
		byte[] bytes = new byte[4];
		for (int i = 0; i < 4; i++) {
		    bytes[i] = (byte)(a >>> (i * 8));
		}
		
		return bytes;
	}
	
	public static int bytesToInt(byte[] bytes, int begin)
	{
		byte[] intBytes = ArrayUtils.subArray(bytes, begin, begin + 4);
		
		return intBytes[0] << 24 | (intBytes[1] & 0xFF) << 16 | (intBytes[2] & 0xFF) << 8 | (intBytes[3] & 0xFF);
	}
	
	public static byte[] subArray(byte[] array, int begin, int end)
	{
		byte[] output = new byte[end-begin];
		
		for(int i = begin; i < end; i++)
		{
			output[i-begin] = array[i];
		}
		
		return output;
	}
}
