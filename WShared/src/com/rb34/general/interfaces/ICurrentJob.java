package com.rb34.general.interfaces;

import java.util.Collection;

import com.rb34.jobInput.interfaces.IJob;

public interface ICurrentJob {

	Collection <IJob> getJobList();
	void setjobList(Collection <IJob> jobList);
	
}
