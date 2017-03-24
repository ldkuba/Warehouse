package com.rb34.behaviours;

//This class is used to make sure that the robot only moves when it should

public class ShouldMove {
	private boolean shouldMove;

	public ShouldMove() {
		shouldMove = false;
	}

	public boolean isShouldMove() {
		return shouldMove;
	}

	public void setShouldMove(boolean shouldMove) {
		this.shouldMove = shouldMove;
	}

}
