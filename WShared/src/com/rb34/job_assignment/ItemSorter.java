package com.rb34.job_assignment;

import java.util.ArrayList;
import java.util.List;

import com.rb34.jobInput.Item;
import com.rb34.jobInput.Job;
import com.rb34.jobInput.Order;
import com.rb34.jobInput.interfaces.IOrder;

public class ItemSorter {
	ArrayList<Item> items;
	private int robotX;
	private int robotY;
	private int dropX;
	private int dropY;

	public ItemSorter(Job job, int rX, int rY, int dX, int dY) {
		items = new ArrayList<>();
		for (IOrder order : job.getOrderList().values()) {
			items.add((Item) order.getItem());
		}
		robotX = rX;
		robotY = rY;
		dropX = dX;
		dropY = dY;
	}

	public ArrayList<Item> getSortedItems() {
		ArrayList<Item> sortedItems = new ArrayList<>();
		ArrayList<Integer> indexes = new ArrayList<>();

		int numberOfItems = items.size();
		int[][] distances = new int[numberOfItems + 2][numberOfItems];

		for (int i = 0; i < numberOfItems; i++) {
			indexes.add(i);
			distances[numberOfItems][i] = Math.abs(robotX - items.get(i).getX())
					+ Math.abs(robotY - items.get(i).getY());
			distances[numberOfItems + 1][i] = Math.abs(dropX - items.get(i).getX())
					+ Math.abs(dropY - items.get(i).getY());
			distances[i][i] = 0;
			for (int j = i + 1; j < numberOfItems; j++) {
				distances[i][j] = Math.abs(items.get(i).getX() - items.get(j).getX())
						+ Math.abs(items.get(i).getY() - items.get(j).getY());
				distances[j][i] = distances[i][j];
			}
		}

		ArrayList<ArrayList<Integer>> permutations = new ArrayList<>();
		permute(indexes, 0, permutations);

		ArrayList<Integer> bestPermutation = null;
		int shortestDistance = Integer.MAX_VALUE;

		for (ArrayList<Integer> permutation : permutations) {
			int distance = 0;
			for (int i = 0; i < numberOfItems; i++) {
				if (i == 0)
					distance += distances[numberOfItems][permutation.get(i)];
				else
					distance += distances[permutation.get(i - 1)][permutation.get(i)];
			}
			distance += distances[numberOfItems + 1][permutation.get(numberOfItems - 1)];
			if (distance < shortestDistance) {
				shortestDistance = distance;
				bestPermutation = new ArrayList<>(permutation);
			}
		}

		for (int index : bestPermutation) {
			sortedItems.add(items.get(index));
		}

		return sortedItems;
	}

	private static void permute(List<Integer> array, int k, ArrayList<ArrayList<Integer>> permutations) {
		for (int i = k; i < array.size(); i++) {
			java.util.Collections.swap(array, i, k);
			permute(array, k + 1, permutations);
			java.util.Collections.swap(array, k, i);
		}
		if (k == array.size() - 1) {
			ArrayList<Integer> list = new ArrayList<>();
			for (int index : array) {
				list.add(index);
			}
			permutations.add(list);
		}
	}

}
