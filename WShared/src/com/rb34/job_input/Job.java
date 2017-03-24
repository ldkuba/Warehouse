package com.rb34.job_input;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import com.rb34.job_input.interfaces.*;


public class Job implements IJob,  Comparable<Job> {

	private String jobId;
	private HashMap<String, IOrder> orderList;
	private boolean wasCancelled;
	
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
	@Override
	public boolean getCancelled() {
		return wasCancelled;
	}
	@Override
	public void setCancelled(boolean wasCancelled) {
		this.wasCancelled  = wasCancelled;
	}

	public float getTotalReward(){
		float netReward = 0;
		
		Iterator<IOrder> itr = getOrderList().values().iterator();
		while (itr.hasNext()) {
			IOrder element = itr.next();
			netReward += element.getItem().getReward() * element.getCount();
		}
		return netReward;
	}
	
	// Compares based on net reward of job!
	@Override
	public int compareTo(Job o) {
		if(getTotalReward() > o.getTotalReward()){
			return 1;
		}
		else if(getTotalReward() < o.getTotalReward()){
			return -1;
		}
		return 0;
	}
}
