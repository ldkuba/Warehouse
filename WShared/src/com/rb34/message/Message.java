package com.rb34.message;

import java.io.Serializable;

public interface Message extends Serializable
{
	byte[] toByteArray();
}
