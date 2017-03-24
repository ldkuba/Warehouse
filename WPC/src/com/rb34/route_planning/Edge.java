package com.rb34.route_planning;

import com.rb34.route_planning.graph_entities.interfaces.IEdge;
import com.rb34.route_planning.graph_entities.interfaces.IVertex;

public class Edge implements IEdge {
	private IVertex target;
	private Integer cost;

	public Edge(IVertex target, Integer cost) {
		this.target = target;
		this.cost = cost;
	}

	@Override
	public IVertex getTgt() {
		return target;
	}

	@Override
	public Integer getCost() {
		return cost;
	}
};