package com.rb34.jobInput;

import java.util.Collection;
import java.util.HashMap;

import com.rb34.jobInput.interfaces.*;


public class Job implements IJob {

	private String jobId;
	private HashMap<IItem, Integer> orderList;
	private Collection <IItem> itemCollection;
	
	
	public Job(String jobId){
		this.jobId = jobId;
		this.orderList = new HashMap<IItem, Integer>();
	}
	@Override
	public String getJobId() {
		return jobId;
	}

	@Override
	public int getCount(IItem item) {
		// TODO Auto-generated method stub
		return orderList.get(item);
	}
	@Override
	public HashMap<IItem, Integer> getOrderList(){
		return orderList;
	}
	@Override
	public Collection<IItem> getItems() {
		return orderList.keySet();
	}
	@Override
	public void addItem(IItem item, int count) {
		orderList.put(item, count);
	}
}
