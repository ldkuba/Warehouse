package com.rb34.jobInput.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import com.rb34.job_input.Drop;

public class DropListTest {
	
	public Reader reader = new Reader();
	@Test
	public void DropTest(){
		System.out.println("----------Drop Location Test ----------");
		ArrayList<Drop> dropList = reader.createDropList();
		for(int i = 0; i <= dropList.size() - 1; i++){
			System.out.print("X:" + dropList.get(i).getX());
			System.out.println(" Y:" + dropList.get(i).getY());
			System.out.println("-----------------");
		}
	}
	@Test
	public void sizeOfDropList() {
		ArrayList<Drop> dropList = reader.createDropList();
		assertEquals("Number of jobs in list should be 2", 2, dropList.size());

	}
}
