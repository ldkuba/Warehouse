package com.rb34.route_planning.graph_entities;

public class ReservationInfo {
	private int robotId;
	private int time;

	
	public ReservationInfo(int robotId, int time ) {
		this.time = time;
		this.robotId = robotId;
	}
	
	public int getReservationTime() {
		return time;
	}
	
	public int getRobotId() {
		return robotId;
	}
}
