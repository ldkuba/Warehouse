package com.rb34.job_selection.naive_bayes;

import java.util.ArrayList;

import com.rb34.job_input.*;
import com.rb34.job_input.Reader;

public class BayesRunner {
	
    private static String CANCELATION = "not set";
    private static Classifier<String, String> bayes;

    public static void cleanData(ArrayList<Job> jobList) {
        bayes = new BayesClassifier<String, String>();
        
        ArrayList <Item> itemList = Reader.readItemList();
        
        //trains data
        for(Job job : jobList){
        	ArrayList <String> info = new ArrayList<String>();
        	
        	//Checks category
        	if(job.getCancelled()){
        		CANCELATION = "positive";
        	}
        	else{
        		CANCELATION = "negative";
        	}
  
        	//sets features for that category
        	for(Item item : itemList){
        		for(String itemIds : job.getItemsID()){
        			if(itemIds.equals(item.getItemID())){
        				info.add(itemIds);
        			}
        		}
        	}
        	//trains 
        	bayes.learn(CANCELATION, info);
        }
        
        for(String feature : bayes.getFeatures()){
        	
        	//Prints probabilities of each feature
        	System.out.print("feature: " + feature + ": ");
        	System.out.println("Probability: " + bayes.featureProbability(feature, "positive") + " ");
        	
        	//If probability of being cancelled is too high, remove from jobList
        	if(bayes.featureProbability(feature, "positive") >= 0.85f){
        		for(Job job : jobList){
        			for(String id : job.getItemsID()){
        				if(id.equals(feature)){
        					jobList.remove(job);
        					break;
        				}
        			}
        			
        		}
        	}
        }
    }
    /*
    public static void main(String[] args) {
    	Reader.setFilePath("myDocs/");
    	ArrayList <Job> jobList = Reader.createJobList();
    	cleanData(jobList);
    }
    */
}