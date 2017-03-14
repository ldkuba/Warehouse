package com.rb34.job_assignment;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import com.rb34.jobInput.Item;
import com.rb34.jobInput.Job;
import com.rb34.jobInput.Order;

public class ItemSorterTest {

	@Test
	public void testCorrectOrder() {
		Job job = new Job("1000");

		Item p1 = new Item("p1", 1f, 1f);
		p1.setX(3);
		p1.setY(4);

		Item p2 = new Item("p2", 1f, 1f);
		p2.setX(5);
		p2.setY(6);

		Item p3 = new Item("p3", 1f, 1f);
		p3.setX(0);
		p3.setY(2);

		job.addItem("p1", new Order(p1, 1));
		job.addItem("p2", new Order(p2, 1));
		job.addItem("p3", new Order(p3, 1));

		ItemSorter itemSorter = new ItemSorter(job, 2, 2, 0, 0);
		ArrayList<Item> sortedItems = itemSorter.getSortedItems();

		assert (sortedItems.get(0).getItemID().equals("p1") && sortedItems.get(1).getItemID().equals("p2")
				&& sortedItems.get(2).getItemID().equals("p3"));
	}

}
