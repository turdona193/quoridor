package player;

import java.awt.Color;

public class Player {

	private int x, y; 		// x and y are the coordinates of the player on the board.
	private int playerID;	// playerID identifies the player as being player 1, player 2, etc.
	private int walls;		// walls is the number of walls the player has left to place.
	private Color col;		// stores the color of a player
	
	// default constructor, should probably never be used.
	public Player() {
		playerID = 0;
		setStartingLocation();
		setStartingWalls(0);
		setDefaultColor(0);
	}

	// ID is 1 for player 1, 2 for player 2,...  NumOfPlayers is the number of players in this game.
	public Player(int ID, int startingWalls) {
		playerID = ID;
		setStartingLocation();
		setStartingWalls(startingWalls);
		setDefaultColor(ID);
		
	}
	
	//This constructor is used when a player has picked a color other than the default
	public Player(int ID, int NumOfPlayers, Color c) {
		playerID = ID;
		setStartingLocation();
		setStartingWalls(NumOfPlayers);
		setColor(c);
		
	} 
	
	// sets the color
	private void setColor(Color c) {
		col = c;
	}
	
	// returns the color
	public Color getColor() {
		return col;
	}

	//sets the default color of a player
	private void setDefaultColor(int ID) {
		if (playerID == 1) {
			col = Color.blue;
		}else if (playerID == 2) {
			col = Color.red;
		}else if (playerID == 3) {
			col = Color.green;
		}else if (playerID == 4) {
			col = Color.yellow;
		}else {
			col = Color.white;
		}
		
	}

	//Sets the number of walls
	private void setStartingWalls(int startingWalls) {
		walls = startingWalls;
	}
	
	//returns number of walls remaining.
	public int getWalls() {
		return walls;
	}
	
	//decrements number of walls by one.
	public void placeWall() {
		walls--;
	}

	//sets the location of the piece on the board.
	public void setLocation(int i, int j) {
		x = i;
		y = j;
		
	}

	//returns the x coordinate of the player.
	public int getX() {
		return x;
	}
	
	// returns the y coordinate of the player.
	public int getY() {
		return y;
	}
	
	//sets a starting location for the player.
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
