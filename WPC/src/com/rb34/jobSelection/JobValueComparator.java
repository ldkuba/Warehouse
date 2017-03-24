package com.rb34.jobSelection;

import java.util.Comparator;

import com.rb34.jobInput.Job;

public class JobValueComparator implements Comparator<Job> {

	@Override
	public int compare(Job jobToAdd, Job existingJob) {
		if (jobToAdd.getJobValue() > existingJob.getJobValue()) {
			return -1;
		}
		if (jobToAdd.getJobValue() < existingJob.getJobValue()) {
			return 1;
		}
		return 0;
	}

}
