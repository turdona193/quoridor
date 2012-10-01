package player;

import java.awt.Color;
import java.awt.Point;

public class Player {

	private final int X[] = {4, 4, 0, 8};  //X[] and Y[] hold the starting coordinates of each of the 4 players
	private final int Y[] = {8, 0, 4, 4};
	private final Color[] color = {Color.blue, Color.red, Color.green, Color.yellow};  //default colors 
	
	private Point xy;		// holds x and y as an ordered pair
	private int playerID;	// playerID identifies the player as being player 1, player 2, etc.
	private int walls;		// walls is the number of walls the player has left to place.
	private Color col;		// stores the color of a player
	
	// default constructor, exists only for testing
	public Player() {
		playerID = 0;
		setStartingLocation();
		setStartingWalls(0);
		setDefaultColor();
	}

	// ID is 1 for player 1, 2 for player 2,...  NumOfPlayers is the number of players in this game.
	public Player(int ID, int startingWalls) {
		playerID = ID;
		setStartingLocation();
		setStartingWalls(startingWalls);
		setDefaultColor();
		
	}
	
	//This constructor is used when a player has picked a color other than the default
	public Player(int ID, int startingWalls, Color c) {
		playerID = ID;
		setStartingLocation();
		setStartingWalls(startingWalls);
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
	private void setDefaultColor() {
		if (playerID > 0 && playerID < 5) {
			col = color[playerID-1];
			col = color[playerID-1];
		} else {
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
		xy.setLocation(i, j);
	}
	
	// sets location of peice on the board
	public void setLocation(Point loc) {
		xy.setLocation(loc);
	}

	//returns the x coordinate of the player.
	public int getX() {
		return xy.x;
	}
	
	// returns the y coordinate of the player.
	public int getY() {
		return xy.y;
	}
	
	// returns current location of player
	public Point getLocation() {
		return xy;
	}
	
	// returns location directly above the player
	public Point up() {
		Point p;
		if (xy.y > 0) {
			p = new Point(xy.x, xy.y-1);
		} else
			p = null;
		return p;
	}
	
	// returns location directly below player
	public Point down() {
		Point p;
		if (xy.y < 8) {
			p = new Point(xy.x, xy.y+1);
		} else
			p = null;
		return p;
	}
	
	// returns space directly to the left of player
	public Point left() {
		Point p;
		if (xy.x > 0) {
			p = new Point(xy.x-1, xy.y);
		} else
			p = null;
		return p;
	}
	
	// returns space directly to the right of the player
	public Point right() {
		Point p;
		if (xy.x < 8) {
			p = new Point(xy.x+1, xy.y);
		} else
			p = null;
		return p;
	}
	
	//sets a starting location for the player.
	private void setStartingLocation() {
		if (playerID > 0 && playerID < 5) 
			xy = new Point(X[playerID-1], Y[playerID-1]);
		else 
			xy = new Point(0, 0);
	}
	
}
