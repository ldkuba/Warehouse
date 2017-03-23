package com.rb34.job_input;

import com.rb34.jobInput.Drop;
import com.rb34.job_input.*;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class DropListTest{

	public void DropTest(){
		System.out.println("----------Drop Location Test ----------");
		Reader.setFilePath("myDocs/");
		ArrayList<Drop> dropList = Reader.createDropList();
		for(int i = 0; i <= dropList.size() - 1; i++){
			System.out.print("X:" + dropList.get(i).getX());
			System.out.println(" Y:" + dropList.get(i).getY());
			System.out.println("-----------------");
		}
	}

	@Test
	public void sizeOfDropList(){
		Reader.setFilePath("myDocs/");
		ArrayList<Drop> dropList = Reader.createDropList();
		assertEquals("Number of jobs in list should be 2", 2, dropList.size());

	}
}
