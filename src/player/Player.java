package player;

public class Player {

	private int x, y; 		// x and y are the coordinates of the player on the board.
	private int playerID;	// playerID identifies the player as being player 1, player 2, etc.
	private int walls;		// walls is the number of walls the player has left to place.
	
	// default constructor, should probably never be used.
	public Player() {
		playerID = 0;
		setStartingLocation();
		setStartingWalls(0);
	}

	// ID is 1 for player 1, 2 for player 2,...  NumOfPlayers is the number of players in this game.
	public Player(int ID, int NumOfPlayers) {
		playerID = ID;
		setStartingLocation();
		setStartingWalls(NumOfPlayers);
		
	}
	
	//Sets the number of walls based on the number of players, 10 if there are 2 players, 5 if there are 4 players.
	private void setStartingWalls(int numOfPlayers) {
		if (numOfPlayers == 2) {
			walls = 10;
		}else if (numOfPlayers == 4) {
			walls = 5;
		}else {
			walls = 0;
		}
		
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
