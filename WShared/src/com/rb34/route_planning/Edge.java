package com.rb34.route_planning;

import com.rb34.route_planning.graph_entities.IEdge;
import com.rb34.route_planning.graph_entities.IVertex;

public class Edge implements IEdge {
	private IVertex target;
	private Float cost;

	public Edge(IVertex target, Float cost) {
		this.target = target;
		this.cost = cost;
	}

	@Override
	public IVertex getTgt() {
		return target;
	}

	@Override
	public Float getCost() {
		return cost;
	}
};