package com.rb34.general;

import java.util.Collection;

import com.rb34.general.interfaces.ICurrentJob;
import com.rb34.jobInput.interfaces.IJob;

public class currentJob implements ICurrentJob {

	private Collection<IJob> jobList;
	
	@Override
	public Collection<IJob> getJobList() {
		return jobList;
	}

	@Override
	public void setjobList(Collection<IJob> jobList) {
		this.jobList = jobList;	
	}

}
