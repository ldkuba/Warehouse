package com.rb34.jobInput.implementation;

import com.rb34.jobInput.interfaces.IItem;
import com.rb34.jobInput.interfaces.IOrder;

public class Order implements IOrder {

	private Item itemName;
	private int count;
	
	public Order(Item itemName, int count){
		this.itemName = itemName;
		this.count = count;
		
	}
	@Override
	public IItem getItem() {
		return itemName;
	}

	@Override
	public int getCount() {
		return count;
	}

}
