package com.rb34.route_planning;

import java.util.ArrayList;
import java.util.Collection;

import com.rb34.route_planning.graph_entities.Label;
import com.rb34.route_planning.graph_entities.interfaces.IEdge;
import com.rb34.route_planning.graph_entities.interfaces.IVertex;

public class Vertex implements IVertex {
	ArrayList<IEdge> edges;
	Label label;

	public Vertex() {
		edges = new ArrayList<>();
		label = new Label();
	}

	public Vertex(int x, int y) {
		edges = new ArrayList<>();
		label = new Label();
		label.setX(x);
		label.setY(y);
	}

	@Override
	public void addEdge(IEdge edge) {
		edges.add(edge);
	}

	@Override
	public Collection<IEdge> getSuccessors() {
		return edges;
	}

	@Override
	public Label getLabel() {
		return label;
	}

	@Override
	public void setLabel(Label label) {
		this.label = label;
	}

	// No purpose for the compareTo method yet
	@Override
	public int compareTo(IVertex o) {
		return 0;
	}

}