package com.rb34.jobSelection;
import java.util.ArrayList;
import java.util.Collections;

import com.rb34.jobInput.*;

public class Selection {
	
	public static ArrayList <Job> rewardSelection(ArrayList <Job> jobList){
		Collections.sort(jobList,Collections.reverseOrder());
		return jobList;
	}
}
