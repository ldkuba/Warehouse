package com.rb34.route_planning.graph_entities.interfaces;

// An edge has a target vertex (Tgt) and a cost. 

public interface IEdge {
	IVertex getTgt();

	Integer getCost();
}
