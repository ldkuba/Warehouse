package com.rb34.route_planning.graph_entities;

import java.util.Optional;

// A Label labels a vertex with a number of pieces of information: 

public class Label {
	// Information that may be used to manage and track the graph
	// search. Must be present if the vertex with this label has a
	// parent, and absent otherwise. It must never be null.
	private Optional<IVertex> parentVertex;

	// Current cost to reach the vertex that has this label. May be
	// used in search algorithms.
	private Integer cost;

	// The name of the vertex with this label, in particular used for
	// the dot file.
	private String name;
	
	// x and y coordinates
	private int x;
	private int y;

	public Label() {
		this.cost = 0;
		this.parentVertex = Optional.empty();
		this.name = "UNAMED";
	}

	public Integer getCost() {
		return this.cost;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
	}

	// Visitation parent
	public void setParentVertex(IVertex parentVertex) {
		if (parentVertex == null)
			this.parentVertex = Optional.empty();
		else
			this.parentVertex = Optional.of(parentVertex);
	}

	public Optional<IVertex> getParentVertex() {
		return this.parentVertex;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
}
