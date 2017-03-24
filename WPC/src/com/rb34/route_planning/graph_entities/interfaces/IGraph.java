package com.rb34.route_planning.graph_entities.interfaces;

import java.util.Collection;

import com.rb34.route_planning.graph_entities.Result;

public interface IGraph {
	void addVertex(String vertexId, IVertex vertex);

	void addEdge(String vertexSrcId, String vertexTgtId, Integer cost);

	Collection<IVertex> getVertices();

	Collection<String> getVertexIds();

	IVertex getVertex(String vertexId);

	default Result aStar(String startVertexId, String endVertexId) {
		return new Result();
	}
}
