package com.rb34.serialization;

import java.io.NotSerializableException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;

import com.rb34.util.ArrayUtils;

public class Serializer
{
	public static ArrayList<Byte> serializeObject(Object object) throws IllegalArgumentException, IllegalAccessException, NotSerializableException
	{
		ArrayList<Byte> output = new ArrayList<>();

		Field[] fields = object.getClass().getDeclaredFields();

		for (Field field : fields) {
			Class<?> clazz = field.getType();

			if (clazz.isArray()) {
				Class<?> arrayClazz = clazz.getComponentType();

				if (arrayClazz.isPrimitive()) {
					if (arrayClazz.equals(Byte.TYPE)) {
						byte[] bytes = (byte[]) field.get(object);
						output.addAll(ArrayUtils.arrayToArrayList(bytes));
					} else if (arrayClazz.equals(Integer.TYPE)) {
						int[] ints = (int[]) field.get(object);
						output.addAll(ArrayUtils.arrayToArrayList(ArrayUtils.intArrayToBytes(ints)));
					} else if (arrayClazz.equals(Short.TYPE)) {
						short[] shorts = (short[]) field.get(object);
						output.addAll(ArrayUtils.arrayToArrayList(ArrayUtils.shortArrayToBytes(shorts)));
					} else if (arrayClazz.equals(Character.TYPE)) {
						char[] chars = (char[]) field.get(object);
						output.addAll(ArrayUtils.arrayToArrayList(ArrayUtils.charArrayToBytes(chars)));
					} else if (arrayClazz.equals(Boolean.TYPE)) {
						boolean[] booleans = (boolean[]) field.get(object);
						output.addAll(ArrayUtils.arrayToArrayList(ArrayUtils.booleanArrayToBytes(booleans)));
					}else if(arrayClazz.equals(Long.TYPE))
					{
						long[] longs = (long[]) field.get(object);
						output.addAll(ArrayUtils.arrayToArrayList(ArrayUtils.longArrayToBytes(longs)));
					}
				}else
				{
					if(isSerializable(arrayClazz))
					{
						
					}else
					{
						
					}
				}
			} else {
				if (field.getType().isPrimitive()) {
					if (clazz.equals(Byte.TYPE)) {
						output.add((Byte) field.get(object));
					} else if (clazz.equals(Integer.TYPE)) {
						int value = field.getInt(object);
						ArrayList<Byte> bytes = ArrayUtils.arrayToArrayList(ArrayUtils.intToBytes(value));
						output.addAll(bytes);
					} else if (clazz.equals(Short.TYPE)) {
						short value = field.getShort(object);
						ArrayList<Byte> bytes = ArrayUtils.arrayToArrayList(ArrayUtils.shortToBytes(value));
						output.addAll(bytes);
					} else if (clazz.equals(Character.TYPE)) {
						char value = field.getChar(object);
						ArrayList<Byte> bytes = ArrayUtils.arrayToArrayList(ArrayUtils.charToBytes(value));
						output.addAll(bytes);
					} else if (clazz.equals(Boolean.TYPE)) {
						boolean value = field.getBoolean(object);
						output.add(new Byte((byte) (value?1:0)));
					}else if(clazz.equals(Long.TYPE))
					{
						long value = field.getLong(object);
						ArrayList<Byte> bytes = ArrayUtils.arrayToArrayList(ArrayUtils.longToBytes(value));
						output.addAll(bytes);
					}
				}else
				{
					if(isSerializable(clazz))
					{
						output.addAll(serializeObject(field));
					}else
					{
						throw new NotSerializableException();
					}
				}
			}

		}

		return output;
	}
	
	private static boolean isSerializable(Class<?> clazz)
	{
		for(Class<?> i : clazz.getInterfaces())
		{
			if(clazz.equals(Serializable.class))
			{
				return true;
			}
		}
		
		return false;
	}
}