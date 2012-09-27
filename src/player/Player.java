package player;

import java.awt.Dimension;

public class Player {

	private int x, y;
	private int playerID;
	private int walls;
	
	public Player() {
		playerID = 0;
		setStartingLocation();
	}
	
	public Player(int ID) {
		playerID = ID;
		setStartingLocation();
	}
	
	public Player(int ID, int NumOfPlayers) {
		playerID = ID;
		setStartingLocation();
		setStartingWalls(NumOfPlayers);
		
	}
	
	private void setStartingWalls(int numOfPlayers) {
		if (numOfPlayers == 2) {
			walls = 10;
		}else if (numOfPlayers == 4) {
			walls = 5;
		}else {
			walls = 0;
		}
		
	}
	
	public int getWalls() {
		return walls;
	}
	
	public void placeWall() {
		walls--;
	}

	public void setLocation(int i, int j) {
		x = i;
		y = j;
		
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	private void setStartingLocation() {
		if (playerID == 1) {
			x = 5;
			y = 0;
		}else if (playerID == 2) {
			x = 5;
			y = 9;
		}else if (playerID == 3) {
			x = 0;
			y = 5;
		}else if (playerID == 4) {
			x = 9;
			y = 5;
		}else {
				x = 0;
				y = 0;
		}
	}
	
}
