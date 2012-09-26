package player;

import java.awt.Dimension;

public class Player {

	private Dimension xy;
	
	public Player() {
		xy = new Dimension(0,0);
	}
	
	public void setLocation(int i, int j) {
		xy.setSize(i, j);
		
	}
	
	public void setLocation(Dimension d) {
		xy.setSize(d);
	}

	public Dimension getLocation() {
		return xy;
	}

	
}
