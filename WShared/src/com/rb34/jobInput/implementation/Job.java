package com.rb34.jobInput.implementation;

import java.util.ArrayList;

import com.rb34.jobInput.interfaces.IJob;
import com.rb34.jobInput.interfaces.IOrder;

public class Job implements IJob {

	private String jobId;
	private ArrayList <IOrder> orderList;
	
	
	public Job(String jobId, ArrayList <IOrder> orderList){
		this.jobId = jobId;
		this.orderList = orderList;
	}
	@Override
	public String getJobId() {
		return jobId;
	}

	@Override
	public ArrayList<IOrder> getOrders() {
		return orderList;
	}
}
