package com.rb34.main;

import com.rb34.interfaces.Idrop;

public class Drop implements Idrop {
	
	private int xLoc;
	private int yLoc;
	private int dropID;

	public Drop(int dropID , int xLoc, int yLoc){
		this.dropID = dropID;
		this.xLoc = xLoc;
		this.yLoc = yLoc;
	}
	@Override
	public int getX() {
		return xLoc;
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return yLoc;
	}

}
