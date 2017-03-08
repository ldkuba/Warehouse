package localisation;

import java.util.ArrayList;
import lejos.geom.Line;
import lejos.geom.Rectangle;
import rp.robotics.mapping.*;

public interface Map {
	
	public static GridMap createGridMap () {
		
		// Height, width of a map
		float xMap = 3.70f, yMap = 2.45f;
				 
		// Starting position, height, width, cell size of a grid
		int gridWidth  = 12, gridHeight = 8;
		float xInset   = (1f / ((float) gridWidth * 2f))  * xMap;
		float yInset   = (1f / ((float) gridHeight * 2f)) * yMap;
		// 				 0.31f;
		float cellSize = 0.31f;
		
		ArrayList<Line> lines = new ArrayList<Line>();
		
		// Borders of a Map
		lines.add (new Line (0f  , 0f  , xMap, 0f));
		lines.add (new Line (xMap, 0f  , xMap, yMap));
		lines.add (new Line (xMap, yMap, 0f  , yMap));
		lines.add (new Line (0f  , yMap, 0f  , 0f));
		
		float boxWidth      = xMap / 12f, boxHeight     = yMap * 5f / 8f;
		float distanceFromX = 1f / gridWidth  , distanceFromY = 1f / gridHeight;		
		
		// 1st box			 0.63f, 0.57f, 0.63f, 0.57f + 1.255f
		lines.add (new Line (xMap * distanceFromX           , distanceFromY * yMap            , xMap * distanceFromX           , distanceFromY * yMap + boxHeight));
		lines.add (new Line (xMap * distanceFromX           , distanceFromY * yMap + boxHeight, xMap * distanceFromX + boxWidth, distanceFromY * yMap + boxHeight));
		lines.add (new Line (xMap * distanceFromX + boxWidth, distanceFromY * yMap            , xMap * distanceFromX + boxWidth, distanceFromY * yMap + boxHeight));
		lines.add (new Line (xMap * distanceFromX           , distanceFromY * yMap            , xMap * distanceFromX + boxWidth, distanceFromY * yMap));
		
		// 2nd box
		float xChangeFor2ndBox = (3f / 12f) * xMap;
																							
		lines.add (new Line (xMap * distanceFromX + xChangeFor2ndBox           , distanceFromY * yMap            , xMap * distanceFromX + xChangeFor2ndBox, distanceFromY * yMap + boxHeight));
		lines.add (new Line (xMap * distanceFromX + xChangeFor2ndBox           , distanceFromY * yMap + boxHeight, xMap * distanceFromX + boxWidth + xChangeFor2ndBox, distanceFromY * yMap + boxHeight));
		lines.add (new Line (xMap * distanceFromX + boxWidth + xChangeFor2ndBox, distanceFromY * yMap            , xMap * distanceFromX + boxWidth + xChangeFor2ndBox, distanceFromY * yMap + boxHeight));
		lines.add (new Line (xMap * distanceFromX + xChangeFor2ndBox           , distanceFromY * yMap            , xMap * distanceFromX + boxWidth + xChangeFor2ndBox, distanceFromY * yMap));
	
		// 3rd box
		float xChangeFor3rdBox = xChangeFor2ndBox * 2; 	
	
		lines.add (new Line (xMap * distanceFromX + xChangeFor3rdBox           , distanceFromY * yMap            , xMap * distanceFromX + xChangeFor3rdBox, distanceFromY * yMap + boxHeight));
		lines.add (new Line (xMap * distanceFromX + xChangeFor3rdBox           , distanceFromY * yMap + boxHeight, xMap * distanceFromX + boxWidth + xChangeFor3rdBox, distanceFromY * yMap + boxHeight));
		lines.add (new Line (xMap * distanceFromX + boxWidth + xChangeFor3rdBox, distanceFromY * yMap            , xMap * distanceFromX + boxWidth + xChangeFor3rdBox, distanceFromY * yMap + boxHeight));
		lines.add (new Line (xMap * distanceFromX + xChangeFor3rdBox           , distanceFromY * yMap            , xMap * distanceFromX + boxWidth + xChangeFor3rdBox, distanceFromY * yMap));
		
		// 4th box
		float xChangeFor4thBox = xChangeFor2ndBox * 3;
		
		lines.add (new Line (xMap * distanceFromX + xChangeFor4thBox           , distanceFromY * yMap            , xMap * distanceFromX + xChangeFor4thBox, distanceFromY * yMap + boxHeight));
		lines.add (new Line (xMap * distanceFromX + xChangeFor4thBox           , distanceFromY * yMap + boxHeight, xMap * distanceFromX + boxWidth + xChangeFor4thBox, distanceFromY * yMap + boxHeight));
		lines.add (new Line (xMap * distanceFromX + boxWidth + xChangeFor4thBox, distanceFromY * yMap            , xMap * distanceFromX + boxWidth + xChangeFor4thBox, distanceFromY * yMap + boxHeight));
		lines.add (new Line (xMap * distanceFromX + xChangeFor4thBox           , distanceFromY * yMap            , xMap * distanceFromX + boxWidth + xChangeFor4thBox, distanceFromY * yMap));
		
		Line[] lineArray = new Line[lines.size()];
		
		lines.toArray(lineArray);
		
		return new GridMap (gridWidth, gridHeight, xInset, yInset, cellSize, new LineMap (lineArray, new Rectangle (0, 0, xMap, yMap)));
	}
}