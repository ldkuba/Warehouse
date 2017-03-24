package com.rb34.jobSelection;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.util.Comparator;

public class CoordinatesComparator implements Comparator<Point2D.Float> {

	@Override
	public int compare(Point2D.Float coordinateToAdd, Point2D.Float existingCoordinate) {
		// Compare the X coordinates first.
		if (coordinateToAdd.getX() < existingCoordinate.getX()) {
			return -1;
		}
		if (coordinateToAdd.getX() > existingCoordinate.getX()) {
			return 1;
		}
		// If the X coordinates are the same, compare the Y coordinates.
		if (coordinateToAdd.getX() == existingCoordinate.getX()) {
			if (coordinateToAdd.getY() < existingCoordinate.getY()) {
				return -1;
			}
			if (coordinateToAdd.getY() > existingCoordinate.getY()) {
				return 1;
			}
			return 0;
		}
		return 0;
	}

}
