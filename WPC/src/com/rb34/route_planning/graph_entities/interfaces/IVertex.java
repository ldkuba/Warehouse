package com.rb34.route_planning.graph_entities.interfaces;

import java.util.Collection;

import com.rb34.route_planning.graph_entities.Label;

// Vertices are comparable according to their corresponding *current*
// cost stored in their labels

// Different vertex exploration policies based on this cost, or not
// define different graph search algorithms

public interface IVertex extends Comparable<IVertex> {
	// Add an edge to this vertex.
	void addEdge(IEdge edge);

	// We get all the edges emanating from this vertex:
	Collection<IEdge> getSuccessors();

	// See class Label for an an explanation:
	Label getLabel();

	void setLabel(Label label);
}
