package com.rb34.jobInput;

import java.util.Collection;
import java.util.HashMap;

import com.rb34.jobInput.interfaces.*;


public class Job implements IJob {

	private String jobId;
	private HashMap<String, IOrder> orderList;
	
	
	public Job(String jobId){
		this.jobId = jobId;
		this.orderList = new HashMap<String, IOrder>();
	}
	@Override
	public String getJobId() {
		return jobId;
	}

	@Override
	public int getCount(String itemID) {
		return orderList.get(itemID).getCount();
	}
	@Override
	public HashMap<String, IOrder> getOrderList(){
		return orderList;
	}
	@Override
	public Collection<String> getItemsID() {
		return orderList.keySet();
	}
	@Override
	public void addItem(String itemID, IOrder order) {
		orderList.put(itemID, order);
	}
}
