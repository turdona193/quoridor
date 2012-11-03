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
	public final static int X[] = {4, 4, 0, 8};
	/**
	 * Y holds all of the starting Y values of the Players.
	 */
	public final static int Y[] = {8, 0, 4, 4};
	/**
	 * color holds the default Colors of the Players.
	 */
	public final static Color[] color = {Color.blue, Color.red, Color.green, Color.yellow};
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
	
	/**
	 * Holds the goalLine as a set.
	 */
	public Set<Point> goalSet;
	
	/**
	 * Only exists for testing purposes.
	 */
	public Player() {
		playerID = -1;
		setStartingLocation();
		setGoalLine();
		setStartingWalls(0);
		setDefaultColor();
		playerType = GUI_PLAYER;
	}

	/**
	 * This is the constructor that is called in the constructor for the board class.
	 * 
	 * @param ID
	 * 		This is the Player's ID number.  It should be a number between 0 and 3.
	 * 
	 * @param startingWalls
	 * 		This is the number of walls the player starts with.
	 * 
	 * @param c
	 * 		This is the Color of the Player.
	 * 
	 * @param pType
	 * 		This is the Player's Type.
	 */
	public Player(int ID, int startingWalls, Color c, int pType) {
		playerID = ID;
		setStartingLocation();
		setGoalLine();
		setStartingWalls(startingWalls);
		setColor(c);
		playerType = pType;
	}
	
	/**
	 * Method to return the Player's type.
	 * 
	 * @return
	 * 		Returns an int representing a Player's type.
	 */
	public int getPlayerType() {
		return playerType;
	}
	
	/**
	 * Method called in the constructor to set the goalLine and goalSet.
	 */
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
	
	/**
	 * Prints the goal as a String.
	 */
	public void printGoal() {
		for (int i = 0; i < goalLine.length; i++) {
			System.out.println(goalLine[i].toString());
		}
	}
	
	/**
	 * Returns the goalLine array.
	 * 
	 * @return
	 * 		Returns an array containing the locations the Player can move to to win the game.
	 */
	public Point[] getGoalLine() {
		return goalLine;
	}

	/**
	 * Checks to see if a Player is has reached their goal.
	 * 
	 * @return
	 * 		Returns true if the Player is at their goal.
	 * 		Returns false otherwise.
	 */
	public boolean hasWon() {
		for (int i = 0; i < goalLine.length; i++) {
			if (goalSet.contains(xy)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Called in the constructor to set the Player's Color.
	 * 
	 * @param c
	 * 		The player's Color.
	 */
	private void setColor(Color c) {
		col = c;
	}
	
	/**
	 * Returns the Player's Color.
	 * 
	 * @return
	 * 		Returns the Player's Color.
	 */
	public Color getColor() {
		return col;
	}

	/**
	 * Sets the Player's Color to the default Color based on their PlayerID.
	 */
	private void setDefaultColor() {
		if (playerID > -1 && playerID < 4) {
			col = color[playerID];
			col = color[playerID];
		} else {
			col = Color.white;
		}

	}

	/**
	 * Called in the constructor to set the starting walls for the Player.
	 * 
	 * @param startingWalls
	 * 		The number of walls the Player starts with.
	 */
	private void setStartingWalls(int startingWalls) {
		walls = startingWalls;
	}
	
	/**
	 * Returns the number of walls the Player has left.
	 * 
	 * @return
	 * 		Returns the number of walls the Player has left.
	 */
	public int getWalls() {
		return walls;
	}
	
	/**
	 * Decrements the number of walls the Player has left by one.  If the Player has walls remaining, 
	 * walls is decremented and true is returned.  Otherwise, walls stays the same and false is returned.
	 * 
	 * @return
	 * 		Returns true if there were any walls left.
	 * 		Returns false if the Player has no walls.
	 */
	public boolean decrementWall() {
		if (walls > 0){
			walls--;
			return true;
		}
		return false;
	}

	/**
	 * Sets the location of the Player.
	 * 
	 * @param i
	 * 		i is the Player's x-coordinate.
	 * @param j
	 * 		j is the Player's y-coordinate.
	 */
	public void setLocation(int i, int j) {
		xy.setLocation(i, j);
	}
	
	/**
	 * Sets the location of the Player.
	 * 
	 * @param loc
	 * 		loc is the location where the Player wants to move.
	 */
	public void setLocation(Point loc) {
		xy.setLocation(loc);
	}

	/**
	 * Returns the x-coordinate of the Player.
	 * 
	 * @return
	 * 		Returns the x-coordinate of the Player.
	 */
	public int getX() {
		return xy.x;
	}
	
	/**
	 * Returns the y-coordinate of the Player.
	 * 
	 * @return
	 * 		Returns the y-coordinate of the Player.
	 */
	public int getY() {
		return xy.y;
	}
	
	/**
	 * Returns the location of the Player as a Point Object.
	 * 
	 * @return
	 * 		Returns the location of the Player as a Point.
	 */
	public Point getLocation() {
		return xy;
	}
	
	/**
	 * Returns the location directly above the Player as a Point.  If that location is off the board, null is returned.
	 * 
	 * @return
	 * 		Returns the location directly above the Player, unless it is off the board.  If it's off the board, null is 
	 * 		returned.
	 */
	public Point up() {
		Point p;
		if (xy.y > 0) {
			p = new Point(xy.x, xy.y-1);
		} else
			p = null;
		return p;
	}
	
	/**
	 * Returns the location directly below the Player as a Point.  If that location is off the board, null is returned.
	 * 
	 * @return
	 * 		Returns the location directly below the Player, unless it is off the board.  If it's off the board, null is 
	 * 		returned.
	 */
	public Point down() {
		Point p;
		if (xy.y < 8) {
			p = new Point(xy.x, xy.y+1);
		} else
			p = null;
		return p;
	}
	
	/**
	 * Returns the location directly to the left of the Player as a Point.  If that location is off the board, null is returned.
	 * 
	 * @return
	 * 		Returns the location directly to the left of the Player, unless it is off the board.  If it's off the board, null is 
	 * 		returned.
	 */
	public Point left() {
		Point p;
		if (xy.x > 0) {
			p = new Point(xy.x-1, xy.y);
		} else
			p = null;
		return p;
	}
	
	/**
	 * Returns the location directly to the right of the Player as a Point.  If that location is off the board, null is returned.
	 * 
	 * @return
	 * 		Returns the location directly to the right of the Player, unless it is off the board.  If it's off the board, null is 
	 * 		returned.
	 */
	public Point right() {
		Point p;
		if (xy.x < 8) {
			p = new Point(xy.x+1, xy.y);
		} else
			p = null;
		return p;
	}
	
	/**
	 * Called in the constructor to set the starting location of the Player based on the Player's ID.
	 */
	private void setStartingLocation() {
		if (playerID > -1 && playerID < 4) 
			xy = new Point(X[playerID], Y[playerID]);
		else 
			xy = new Point(0, 0);
	}

	/**
	 * Determines whether or not the Player has any walls left.
	 * 
	 * @return
	 * 		Returns true if the Player has any walls remaining.
	 * 		Returns false otherwise.
	 */
	public boolean hasWalls() {
		if (walls > 0)
			return true;
		return false;
	}
	
}
