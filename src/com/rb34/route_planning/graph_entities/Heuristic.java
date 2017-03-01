package com.rb34.route_planning.graph_entities;

import java.util.function.BiFunction;

public class Heuristic<T> implements BiFunction<IVertex, IVertex, Float> {

	@Override
	public Float apply(IVertex t, IVertex u) {
		return (float) Math.abs(t.getLabel().getX() - u.getLabel().getX()) 
				+ Math.abs(t.getLabel().getY() - u.getLabel().getY());
	}

}
