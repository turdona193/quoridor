package player;

import java.awt.Color;
import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

/**
 * This class stores all the required information about a Player.
 */

public class Player {
	/**
	 * X holds all of the starting X values of the Players.
	 */
	private final int X[] = {4, 4, 0, 8};
	/**
	 * Y holds all of the starting Y values of the Players.
	 */
	private final int Y[] = {8, 0, 4, 4};
	/**
	 * color holds the default Colors of the Players.
	 */
	private final Color[] color = {Color.blue, Color.red, Color.green, Color.yellow};
	/**
	 * Constant representing a local player
	 */
	public final static int GUI_PLAYER = 0;
	/**
	 * Constant representing an ai player.
	 */
	public final static int AI_PLAYER = 1;
	/**
	 * Constant representing a player playing over a network
	 */
	public final static int NET_PLAYER = 2;
	
	/**
	 * holds whether a player is using the gui, is an ai, or is playing over a network.
	 */
	private int playerType;
	/**
	 * holds the location of the player on the board.
	 */
	private Point xy;
	/**
	 * Holds a Player's ID number.
	 */
	private int playerID;
	/**
	 * Holds the number of walls a Player has left.
	 */
	private int walls;
	/**
	 * Holds the Player's Color.
	 */
	private Color col;
	/**
	 * Holds all of the spaces a Player can move to to win the game.
	 */
	private Point[] goalLine;
	
	public Set<Point> goalSet;
	
	/**
	 * Only still exists because I'll have to redo the Player tests if this is removed.
	 */
	public Player() {
		playerID = -1;
		setStartingLocation();
		setGoalLine();
		setStartingWalls(0);
		setDefaultColor();
		playerType = GUI_PLAYER;
	}

	// ID is 1 for player 1, 2 for player 2,...  NumOfPlayers is the number of players in this game.
	public Player(int ID, int startingWalls) {
		playerID = ID;
		setStartingLocation();
		setGoalLine();
		setStartingWalls(startingWalls);
		setDefaultColor();
		playerType = GUI_PLAYER;
	}
	
	// new constructor that lets you set a playerType
	public Player(int ID, int startingWalls, Color c, int pType) {
		playerID = ID;
		setStartingLocation();
		setGoalLine();
		setStartingWalls(startingWalls);
		setColor(c);
		playerType = pType;
	}
	
	// returns a value 
	public int getPlayerType() {
		return playerType;
	}
	
	// fills the goalLine array with all the spaces a player could move to to win the game
	private void setGoalLine() {
		goalLine = new Point[9];
		goalSet = new HashSet<Point>();
		int temp = 0;
		if (xy.x == 8) {
			temp = 0;
			for (int i = 0; i < goalLine.length; i++) {
				goalLine[i] = new Point(temp, i);
				goalSet.add(new Point(temp, i));
			}
		} else if (xy.x == 0) {
			temp = 8;
			for (int i = 0; i < goalLine.length; i++) {
				goalLine[i] = new Point(temp, i);
				goalSet.add(new Point(temp, i));
			}
		} else if (xy.y == 8) {
			temp = 0;
			for (int i = 0; i < goalLine.length; i++) {
				goalLine[i] = new Point(i, temp);
				goalSet.add(new Point(i, temp));
			}
		} else if (xy.y == 0) {
			temp = 8;
			for (int i = 0; i < goalLine.length; i++) {
				goalLine[i] = new Point(i, temp);
				goalSet.add(new Point(i, temp));
			}
		}	
	}
	
	public void printGoal() {
		for (int i = 0; i < goalLine.length; i++) {
			System.out.println(goalLine[i].toString());
		}
	}
	
	// returns the array containing the location of the goalLine
	public Point[] getGoalLine() {
		return goalLine;
	}

	// method which checks to see whether a player is on the goalLine
	public boolean hasWon() {
		for (int i = 0; i < goalLine.length; i++) {
			if (goalSet.contains(xy)) {
				return true;
			}
		}
		return false;
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
		if (playerID > -1 && playerID < 4) {
			col = color[playerID];
			col = color[playerID];
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
	public void decrementWall() {
		walls--;
	}

	//sets the location of the piece on the board.
	public void setLocation(int i, int j) {
		xy.setLocation(i, j);
	}
	
	// sets location of piece on the board
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
		if (playerID > -1 && playerID < 4) 
			xy = new Point(X[playerID], Y[playerID]);
		else 
			xy = new Point(0, 0);
	}
	
}
