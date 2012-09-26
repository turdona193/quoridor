package player;

import java.awt.Dimension;

public class Player {

	private Dimension xy;
	private int playerNumber;
	
	public Player() {
		playerNumber = 0;
		setStartingLocation();
	}
	
	public Player(int n) {
		playerNumber = n;
		setStartingLocation();
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
	
	private void setStartingLocation() {
		if (playerNumber == 1) {
			xy = new Dimension(5,0);
		}else if (playerNumber == 2) {
			xy = new Dimension(5,9);
		}else {
				xy = new Dimension(0,0);
		}
	}
	
	private Dimension getStartingLocation() {
		Dimension d = new Dimension(5, 0);
		return d;
	}

	
}
