package com.rb34.util;

import java.util.ArrayList;

public class ArrayUtils {
	public static byte[] concat(byte[] a1, byte[] a2) {
		byte[] output = new byte[a1.length + a2.length];

		for (int i = 0; i < a1.length; i++) {
			output[i] = a1[i];
		}

		for (int i = 0; i < a2.length; i++) {
			output[i + a1.length] = a2[i];
		}

		return output;
	}

	public static byte[] intToBytes(int a) {
		byte[] bytes = new byte[4];
		for (int i = 0; i < 4; i++) {
			bytes[i] = (byte) (a >>> (i * 8));
		}

		return reverse(bytes);
	}

	public static byte[] intArrayToBytes(int[] array) {
		byte[] bytes = new byte[4];
		bytes = concat(bytes, intToBytes(array.length));

		for (int i = 0; i < array.length; i++) {
			bytes = concat(bytes, intToBytes(array[i]));
		}

		return bytes;
	}

	public static byte[] shortToBytes(short a) {
		byte[] bytes = new byte[2];
		for (int i = 0; i < 2; i++) {
			bytes[i] = (byte) (a >>> (i * 8));
		}

		return reverse(bytes);
	}

	public static byte[] shortArrayToBytes(short[] array) {
		byte[] bytes = new byte[4];
		bytes = concat(bytes, intToBytes(array.length));

		for (int i = 0; i < array.length; i++) {
			bytes = concat(bytes, shortToBytes(array[i]));
		}

		return bytes;
	}

	public static byte[] charToBytes(char a) {
		byte[] bytes = new byte[2];
		for (int i = 0; i < 2; i++) {
			bytes[i] = (byte) (a >>> (i * 8));
		}

		return reverse(bytes);
	}

	public static byte[] charArrayToBytes(char[] array) {
		byte[] bytes = new byte[4];
		bytes = concat(bytes, intToBytes(array.length));

		for (int i = 0; i < array.length; i++) {
			bytes = concat(bytes, charToBytes(array[i]));
		}

		return bytes;
	}

	public static byte[] booleanArrayToBytes(boolean[] array) {
		byte[] bytes = new byte[4];
		bytes = concat(bytes, intToBytes(array.length));

		for (int i = 0; i < array.length; i++) {
			bytes = concat(bytes, new byte[] { (byte) (array[i] ? 1 : 0) });
		}

		return bytes;
	}

	public static byte[] stringToBytes(String s) {
		byte[] bytes = new byte[s.length() * 2];
		for (int i = 0; i < s.length(); i++) {
			bytes[2 * i] = (byte) ((byte) (s.charAt(i) & 0xFF00) >> 8);
			bytes[2 * i + 1] = (byte) (s.charAt(i) & 0x00FF);
		}

		return bytes;

	}

	public static String bytesToString(byte[] bytes) {
		String output = "";

		for (int j = 0; j < bytes.length / 2; j++) {
			char value = 0;
			for (int i = 0; i < 2; i++) {
				int shift = (2 - 1 - i) * 8;
				value += (bytes[(j * 2) + i] & 0x00FF) << shift;
			}
			output += value;
		}

		return output;
	}

	public static byte[] reverse(byte[] bytes) {
		byte[] newBytes = new byte[bytes.length];

		for (int i = 0; i < bytes.length; i++) {
			newBytes[i] = bytes[bytes.length - i - 1];
		}

		return newBytes;
	}

	public static int bytesToInt(byte[] bytes, int begin) {
		byte[] intBytes = ArrayUtils.subArray(bytes, begin, begin + 4);

		int value = 0;
		for (int i = 0; i < 4; i++) {
			int shift = (4 - 1 - i) * 8;
			value += (intBytes[i] & 0x000000FF) << shift;
		}
		return value;
	}

	public static byte[] subArray(byte[] array, int begin, int end) {
		byte[] output = new byte[end - begin];

		for (int i = begin; i < end; i++) {
			output[i - begin] = array[i];
		}

		return output;
	}

	public static ArrayList<Byte> arrayToArrayList(byte[] bs) {
		ArrayList<Byte> arrayList = new ArrayList<>();

		for (int i = 0; i < bs.length; i++) {
			arrayList.add(bs[i]);
		}

		return arrayList;
	}
}
