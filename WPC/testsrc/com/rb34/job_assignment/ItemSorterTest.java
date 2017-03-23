package com.rb34.job_assignment;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import com.rb34.jobInput.Drop;
import com.rb34.jobInput.Item;
import com.rb34.jobInput.Job;
import com.rb34.jobInput.Order;

public class ItemSorterTest {

	@Test
	public void testCorrectOrder() {
		Job job = new Job("1000");

		Item p1 = new Item("p1", 1f, 10f);
		p1.setX(3);
		p1.setY(4);

		Item p2 = new Item("p2", 1f, 20f);
		p2.setX(5);
		p2.setY(6);

		Item p3 = new Item("p3", 1f, 19f);
		p3.setX(0);
		p3.setY(2);
		
		Item p4 = new Item("p4", 1f, 5f);
		p4.setX(6);
		p4.setY(0);
		
		Item p5 = new Item("p5", 1f, 5f);
		p5.setX(6);
		p5.setY(7);
		
		Item p6 = new Item("p6", 1f, 5f);
		p6.setX(0);
		p6.setY(7);
		
		Item p7 = new Item("p7", 1f, 30f);
		p7.setX(8);
		p7.setY(3);
		

		Item p8 = new Item("p8", 1f, 3f);
		p8.setX(9);
		p8.setY(0);
		
		job.addItem("p5", new Order(p5, 6));
		job.addItem("p6", new Order(p6, 3));

		job.addItem("p2", new Order(p2, 1));
		job.addItem("p3", new Order(p3, 1));
		job.addItem("p4", new Order(p4, 1));
		job.addItem("p1", new Order(p1, 7));
		job.addItem("p7", new Order(p7, 1));
		job.addItem("p8", new Order(p8, 8));
		
		ArrayList<Drop> drops = new ArrayList<>();
		Drop drop = new Drop(8, 2);
		drops.add(drop);
		drops.add(new Drop(0, 0));

		ItemSorter itemSorter = new ItemSorter(job, 3, 3, drops);
		itemSorter.sortItems();

	}

}
