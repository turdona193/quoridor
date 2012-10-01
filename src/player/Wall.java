package player;

import java.awt.Point;

public class Wall {
	private Point xy;
	private boolean orientation;//true = horizontal, false = vertical

	public void place(int i, int j, boolean vh) {
		xy = new Point(i, j);
		orientation = vh;
		
	}

	public Point getLocation() {
		return xy;
	}
	
	public boolean getOrientation() {
		return orientation;
	}
	
	public int getX() {
		return xy.x;
	}
	
	public int getY() {
		return xy.y;
	}

}
