package localisation;

public class DistanceFromJunction {
	
	private final float PLUS_X, MINUS_X, PLUS_Y, MINUS_Y;
	private final int x, y;
	
	public DistanceFromJunction (int x, int y, float PLUS_X, float MINUS_X, float PLUS_Y, float MINUS_Y) {
		
		this.x = x;
		this.y = y;
		this.PLUS_X = PLUS_X;
		this.MINUS_X = MINUS_X;
		this.PLUS_Y  = PLUS_Y;
		this.MINUS_Y  = MINUS_Y;
	}
	
	public float getPlusX () {
		return PLUS_X;
	}
	
	public float getMinusX () {
		return MINUS_X;
	}
	
	public float getPlusY () {
		return PLUS_Y;
	}
	
	public float getMinusY () {
		return MINUS_Y;
	}	
	
	public int getX () {
		return x;
	}
	
	public int getY () {
		return y;
	}
	
	public String putToString () {
		return x + " " + y + " " + PLUS_X + " " + MINUS_X + " " + PLUS_Y + " " + MINUS_Y;
	}
}
