package com.rb34.job_input;

import com.rb34.job_input.interfaces.*;

public class Order implements IOrder {

	private IItem item;
	private int count;
	
	public Order(IItem item, int count){
		this.item = item;
		this.count = count;
	}
	
	@Override
	public IItem getItem() {
		return item;
	}

	@Override
	public int getCount() {
		return count;
	}

}
