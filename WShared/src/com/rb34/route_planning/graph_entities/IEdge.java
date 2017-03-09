package com.rb34.route_planning.graph_entities;

// An edge has a target vertex (Tgt) and a cost. 

public interface IEdge {
	IVertex getTgt();

	Float getCost();
}
