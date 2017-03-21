package com.rb34.serialization;

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.rb34.util.ArrayUtils;

public class Serializer
{
	public static ArrayList<Byte> serializeObject(Object object) throws IllegalArgumentException, IllegalAccessException
	{
		ArrayList<Byte> output = new ArrayList<>();
		
		Field[] fields = object.getClass().getDeclaredFields();
		
		for(Field field : fields)
		{
			Class<?> clazz = field.getType();
			
			if(clazz.isArray())
			{
				Class<?> arrayClazz = clazz.getComponentType();
				
				if(arrayClazz.isPrimitive())
				{
					if(arrayClazz.equals(Byte.TYPE))
					{
						Byte[] bytes = (Byte[]) field.get(object);
					}else if(arrayClazz.equals(Integer.TYPE))
					{
						
					}else if(arrayClazz.equals(Short.TYPE))
					{
						
					}else if(arrayClazz.equals(Character.TYPE))
					{
						
					}else if(arrayClazz.equals(Boolean.TYPE))
					{
						
					}
				}else
				{
					
				}
			}else
			{	
				if(field.getType().isPrimitive())
				{
					if(clazz.equals(Byte.TYPE))
					{
						output.add((Byte) field.get(object));
					}else if(clazz.equals(Integer.TYPE))
					{
						int value = field.getInt(object);
						ArrayList<Byte> bytes = ArrayUtils.arrayToArrayList(ArrayUtils.intToBytes(value));
						output.addAll(bytes);
					}else if(clazz.equals(Short.TYPE))
					{
						short value = field.getShort(object);
						ArrayList<Byte> bytes = ArrayUtils.arrayToArrayList(ArrayUtils.shortToBytes(value));
						output.addAll(bytes);
					}else if(clazz.equals(Character.TYPE))
					{
						char value = field.getChar(object);
						ArrayList<Byte> bytes = ArrayUtils.arrayToArrayList(ArrayUtils.charToBytes(value));
						output.addAll(bytes);
					}else if(clazz.equals(Boolean.TYPE))
					{
						boolean value = field.getBoolean(object);
						output.add(new Byte((byte) (value?1:0)));
					}
				}else
				{
					if(clazz.equals(String.class))
					{
						String text = (String) field.get(object);
						ArrayList<Byte> lengthBytes = ArrayUtils.arrayToArrayList(ArrayUtils.intToBytes(text.length()));
						output.addAll(lengthBytes);
						ArrayList<Byte> textBytes = ArrayUtils.arrayToArrayList(ArrayUtils.stringToBytes(text));
						output.addAll(textBytes);
					}else
					{
						output.addAll(serializeObject(field));
					}
				}
			}
		
		}
		
		return output;
	}
	
	
}

