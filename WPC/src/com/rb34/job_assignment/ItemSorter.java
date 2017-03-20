package com.rb34.job_assignment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.*;
import org.jfree.util.Log;

import com.rb34.jobInput.Drop;
import com.rb34.jobInput.Item;
import com.rb34.jobInput.Job;
import com.rb34.jobInput.interfaces.IOrder;
import com.rb34.route_planning.Graph;

public class ItemSorter {
	final static Logger logger = Logger.getLogger(ItemSorter.class);
	private ArrayList<Item> items;
	private ArrayList<Integer> orderCount;
	private ArrayList<Drop> drops;
	private ArrayList<Item> sortedItems;
	private ArrayList<String> bestPathCoordinates;
	private int robotX;
	private int robotY;
	

	public ItemSorter(Job job, int rX, int rY, ArrayList<Drop> drops) {
		logger.debug("Started ItemSorter");
		items = new ArrayList<>();
		orderCount = new ArrayList<>();
		for (IOrder order : job.getOrderList().values()) {
			items.add((Item) order.getItem());
			orderCount.add(order.getCount());
		}
		this.drops = new ArrayList<>(drops);
		robotX = rX;
		robotY = rY;
		logger.debug("Job id: " + job.getJobId());
	}
	
	public ArrayList<Item> getSortedItems() {
		return sortedItems;
	}
	
	public ArrayList<String> getDestinations() {
		return bestPathCoordinates;
	}

	public void sortItems() {
		sortedItems = new ArrayList<>();
		ArrayList<Integer> indexes = new ArrayList<>();
		Graph graph = new Graph();
		int numberOfDropLocations = drops.size();
		int numberOfItems = items.size();
		logger.debug("Received a job that has " + numberOfItems + " items");
		int[][] distances = new int[numberOfItems + 1 + numberOfDropLocations][numberOfItems];

		for (int i = 0; i < numberOfItems; i++) {
			distances[numberOfItems][i] = graph
					.aStar(robotX + "|" + robotY, items.get(i).getX() + "|" + items.get(i).getY()).getPathCost().get();
			for (int j = 0; j < numberOfDropLocations; j++) {
				distances[numberOfItems + 1 + j][i] = graph.aStar(drops.get(j).getX() + "|" + drops.get(j).getY(),
						items.get(i).getX() + "|" + items.get(i).getY()).getPathCost().get();
			}
			indexes.add(i);
			distances[i][i] = 0;
			for (int j = i + 1; j < numberOfItems; j++) {
				distances[i][j] = graph.aStar(items.get(j).getX() + "|" + items.get(j).getY(),
						items.get(i).getX() + "|" + items.get(i).getY()).getPathCost().get();
				distances[j][i] = distances[i][j];
			}
		}

		ArrayList<ArrayList<Integer>> permutations = new ArrayList<>();
		permute(indexes, 0, permutations);
		logger.debug("Generated all possible permutations of items");

		ArrayList<Integer> bestPermutation = null;
		bestPathCoordinates = null;
	

		ArrayList<String> pathCoordinates = new ArrayList<>();
		int shortestDistance = Integer.MAX_VALUE;

		for (ArrayList<Integer> permutation : permutations) {
			int distance = 0;
			float weight = 0f;

			pathCoordinates.clear();
			pathCoordinates.add(robotX + "|" + robotY);
			for (int i = 0; i < numberOfItems; i++) {

				if (i == 0) {
					distance += distances[numberOfItems][permutation.get(i)];
					weight += orderCount.get(permutation.get(i)) * items.get(permutation.get(i)).getWeight();
					pathCoordinates
							.add(items.get(permutation.get(i)).getX() + "|" + items.get(permutation.get(i)).getY());

				} else if (weight + items.get(permutation.get(i)).getWeight() < 50f) {
					distance += distances[permutation.get(i - 1)][permutation.get(i)];
					weight += orderCount.get(permutation.get(i)) * items.get(permutation.get(i)).getWeight();
					
					pathCoordinates
							.add(items.get(permutation.get(i)).getX() + "|" + items.get(permutation.get(i)).getY());

				} else {
					int shortestPitStopDistance = Integer.MAX_VALUE;
					int bestDrop = -1;
					for (int j = 0; j < numberOfDropLocations; j++) {
						int pitStopDistance = distances[numberOfItems + 1 + j][permutation.get(i - 1)]
								+ distances[numberOfItems + 1 + j][permutation.get(i)];
						if (pitStopDistance < shortestPitStopDistance) {
							shortestPitStopDistance = pitStopDistance;
							bestDrop = j;
						}
					}
					distance += shortestDistance;
					weight = 0f;
					pathCoordinates.add(drops.get(bestDrop).getX() + "|" + drops.get(bestDrop).getY());
					pathCoordinates.add(items.get(permutation.get(i)).getX() + "|" + items.get(permutation.get(i)).getY());

				}
				
			}

			int shortestPitStopDistance = Integer.MAX_VALUE;
			int bestDrop = -1;
			for (int j = 0; j < numberOfDropLocations; j++) {
				int pitStopDistance = distances[numberOfItems + 1 + j][permutation.get(numberOfItems - 1)];
				if (pitStopDistance < shortestPitStopDistance) {
					shortestPitStopDistance = pitStopDistance;
					bestDrop = j;
				}
			}
			distance += shortestDistance;
			weight = 0f;
			pathCoordinates.add(drops.get(bestDrop).getX() + "|" + drops.get(bestDrop).getY());

			if (distance < shortestDistance) {
				shortestDistance = distance;
				bestPermutation = new ArrayList<>(permutation);
				bestPathCoordinates = new ArrayList<>(pathCoordinates);
			}
		}

		logger.debug("Selected best permutation");

		String logMessage = "The item order is: ";
		for (int index : bestPermutation) {
			sortedItems.add(items.get(index));
			//logger.debug("index: " + index);
			logMessage += items.get(index).getItemID() + " ";
		}

		bestPathCoordinates.remove(0);
		logMessage = "The coordinates are: ";
		for (String coordinates : bestPathCoordinates) {
			logMessage += coordinates + " ";
		}
		logger.debug(logMessage);

	}

	// create every permutation for the order of items (Yay brute force)
	private static void permute(List<Integer> array, int k, ArrayList<ArrayList<Integer>> permutations) {
		for (int i = k; i < array.size(); i++) {
			Collections.swap(array, i, k);
			permute(array, k + 1, permutations);
			Collections.swap(array, k, i);
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
