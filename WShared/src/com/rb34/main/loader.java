package com.rb34.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import com.rb34.interfaces.*;

public class loader implements Iloader {

	@Override
	public ArrayList<Ijob> createJobList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Iitem> createItemList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Idrop> createDropList() {
		try {
			int Id = 0;
	        File file = new File("myDocs/drops.csv");
	        Scanner scanner = new Scanner(file);
	        
	        ArrayList<Idrop> dropList = new ArrayList <Idrop>();

	        while (scanner.hasNextLine()) {                
	            String line = scanner.nextLine();
	            String array[] = line.split(",");
	            int xLoc = Integer.parseInt(array[0]);
	            int yLoc = Integer.parseInt(array[1]);
	            Drop dropLoc  = new Drop (Id, xLoc ,yLoc);
	            dropList.add(dropLoc);
	        }
	        scanner.close();
	        return dropList;
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
		return null;
	}

	@Override
	public ArrayList<Iorder> createOrderList() {
		// TODO Auto-generated method stub
		return null;
	}

}
