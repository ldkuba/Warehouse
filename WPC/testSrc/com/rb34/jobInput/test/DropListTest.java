package com.rb34.jobInput.test;

import com.rb34.jobInput.*;
import java.util.ArrayList;
import org.junit.Test;

public class DropListTest {
	
	@Test
	public void DropTest(){
		System.out.println("----------Drop Location Test ----------");
		ArrayList<Drop> dropList = Reader.createDropList();
		for(int i = 0; i <= dropList.size() - 1; i++){
			System.out.print("X:" + dropList.get(i).getX());
			System.out.println(" Y:" + dropList.get(i).getY());
			System.out.println("-----------------");
		}
	}
}
