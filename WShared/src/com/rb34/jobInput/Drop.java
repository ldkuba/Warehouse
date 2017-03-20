package com.rb34.jobInput;

import com.rb34.jobInput.interfaces.IDrop;

public class Drop implements IDrop {

	private int xLoc;
	private int yLoc;
	
	public Drop(int xLoc, int yLoc){
		this.xLoc = xLoc;
		this.yLoc = yLoc;
	}
	
	@Override
	public int getX() {
		return xLoc;
	}

	@Override
	public int getY() {
		return yLoc;
	}

}
