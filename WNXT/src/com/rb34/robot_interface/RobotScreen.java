package localisation;

import lejos.nxt.LCD;

public class RobotScreen {
	
	private String xyAxis;
	private String anyMessage;
	private String moving = "I am moving";
	private String waiting = "I am waiting";
	private LCD lcd = new LCD ();
	
	// 99, 63
	
	public RobotScreen (int x, int y) {
		xyAxis = "I am at " + x + ":" + y;
	}
	
	private void drawBordersTop () {	
		for (int x = 0; x <= 99; x++) {
			for (int y = 0; y <= 5; y++) {
				LCD.setPixel(x, y, 1);
			}
			
			for (int y = 58; y <= 63; y++) {
				LCD.setPixel(x, y, 1);
			}
		}
		
		for (int y = 0; y <= 63; y++) {
			for (int x = 0; x <= 5; x++) {
				LCD.setPixel(x, y, 1);
			}
			
			for (int x = 94; x <= 99; x++) {
				LCD.setPixel(x, y, 1);
			}
		}
	}
	
	public void printMessage (String string) {
		LCD.clear();
		drawBordersTop();
		LCD.drawString(string, LCD.CELL_WIDTH / 2,LCD.CELL_HEIGHT / 2);
	}
	
	public void myLocation () {
		LCD.clear();
		drawBordersTop();
		LCD.drawString(xyAxis,LCD.CELL_WIDTH / 2,LCD.CELL_HEIGHT / 2);;
	}
	
	public void myLocation (int x, int y) {
		LCD.clear();
		drawBordersTop();
		xyAxis = "I am at " + x + ":" + y;
		LCD.drawString(xyAxis,LCD.CELL_WIDTH / 2,LCD.CELL_HEIGHT / 2);
	}
	
	public void printState (String state) {
		LCD.clear();
		drawBordersTop();
		
		switch (state) {
		case "moving": LCD.drawString("Moving",LCD.CELL_WIDTH / 2,LCD.CELL_HEIGHT / 2);
		break;
		case "turning": LCD.drawString("Turning",LCD.CELL_WIDTH / 2,LCD.CELL_HEIGHT / 2);
		break;
		default: LCD.drawString("Waiting",LCD.CELL_WIDTH / 2,LCD.CELL_HEIGHT / 2);;
		break;
		}
	}
}
