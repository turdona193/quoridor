package player;

import java.awt.Color;
import java.awt.Point;
import java.util.Scanner;

import main.qBoard;

public class board {
	public final static Color BUTTON_DEFAULT_COLOR = new Color(220,220,220);
	public static final Color WALL_COLOR = Color.black;  //This will eventually be switched to brown
	
	private int[][] walls;		// holds the locations of all the walls; 0 = no wall, 1 = vertical wall, 2 = horizontal wall
	private Player players[];	// holds information about each player
	private int turn, pl;		// turn tells us which player's turn it is;  pl is the number of players
	private qBoard gui;			// allows board to communicate with the gui
	
	// default constructor, assumes 2 players all using their default colors, and will not call the gui.
	// will probably only be used for early testing
	public board() {
		walls = new int[8][8];
		initializeToZero(walls);
		pl = 2;
		players = new Player[pl];
		for (int i = 0; i < pl; i++) {
			players[i] = new Player(i, 10);
		}
		turn = 0;
	}
	
	//if needBoard is true, a new gui will be displayed, otherwise, it won't
	public board(boolean needBoard) {
		walls = new int[8][8];
		initializeToZero(walls);
		pl = 2;
		players = new Player[pl];
		for (int i = 0; i < pl; i++) {
			players[i] = new Player(i, 10);
		}
		turn = 0;
		if (needBoard)
			newGUI();
	}
	
	// this is the constructor that will be used most often
	public board(int numOfPlayers, Color[] colArray) {
		walls = new int[8][8];
		initializeToZero(walls);
		pl = numOfPlayers;
		players = new Player[pl];
		for (int i = 0; i < pl; i++) {
			players[i] = new Player(i, 20/pl, colArray[i]);
		}
		turn = 0;
		newGUI();
	}
	
	private void initializeToZero(int[][] w) {
		for (int i = 0; i < w.length; i++) 
			for (int j = 0; j < w.length; j++) {
				w[i][j] = 0;
			}
		
	}
	
	// makes a new board appear on screen and sets default locations of the players 
	public void newGUI() {
		gui = new qBoard(this);
		for (int i = 0; i < players.length; i++) {
			gui.setColorOfSpace(players[i].getLocation(), players[i].getColor());
		}
		showMoves(players[turn], true);
	}
	
	// I'm assuming the String for moving a piece will look like "M X Y"
	// where X and Y are variables representing the coordinates of the move
	public void readStringFromGUI(String input) {
		Point xy = new Point();
		Scanner sc = new Scanner(input);
		String firstCh = sc.next();
		if (firstCh.equals("M")) {
			xy.x = sc.nextInt();
			xy.y = sc.nextInt();
			move(xy);
		} else if (firstCh.equals("H")) {
			xy.x = sc.nextInt();
			xy.y = sc.nextInt();
			placeHoriWall(xy);
		} else if (firstCh.equals("V")) {
			xy.x = sc.nextInt();
			xy.y = sc.nextInt();
			placeVertWall(xy);
		}
	}
	
	// called by readString when a horizontal wall needs to be placed
	// p is the location where the wall is begin place
	private void placeHoriWall(Point xy) {
		if (players[turn].getWalls() > 0) {
			showMoves(players[turn], false);
			walls[xy.x][xy.y] = 2;
			gui.setHoriWallColor(xy, WALL_COLOR);
			gui.setHoriWallColor(new Point(xy.x+1,xy.y), WALL_COLOR);
		
			gui.setHoriWallClickable(xy, false);
			gui.setHoriWallClickable(new Point(xy.x+1,xy.y), false);
			if (xy.x > 0) 
				gui.setHoriWallClickable(new Point(xy.x-1,xy.y), false);
			gui.setVertWallClickable(xy, false);
			players[turn].decrementWall();
			nextTurn();
		}
	}
	
	private void placeVertWall(Point xy) {
		if (players[turn].getWalls() > 0) {
			showMoves(players[turn], false);
			walls[xy.x][xy.y] = 1;
			gui.setVertWallColor(xy, WALL_COLOR);
			gui.setVertWallColor(new Point(xy.x,xy.y+1), WALL_COLOR);
		
			gui.setVertWallClickable(xy, false);
			gui.setVertWallClickable(new Point(xy.x,xy.y+1), false);
			if (xy.y > 0) 
				gui.setVertWallClickable(new Point(xy.x,xy.y-1), false);
			gui.setHoriWallClickable(xy, false);
			players[turn].decrementWall();
			nextTurn();
		}
	}

	// called by readString when a piece needs to be moved
	// p is the location where the player wants to move their piece
	private void move(Point p) {
		showMoves(players[turn], false);
		gui.setColorOfSpace(players[turn].getLocation(), BUTTON_DEFAULT_COLOR);
		players[turn].setLocation(p);
		gui.setColorOfSpace(players[turn].getLocation(), players[turn].getColor());
		nextTurn();


	}
	
	// this method can show the available moves a player can make if b is true, this needs to be called again with
	// b being false to stop showing the moves a player could make.
	private void showMoves(Player pl, boolean b) {
		showMoves(pl, b, 0);
	}
	
	// rec is the number of times a recursive call was made it should probably get a better name
	private void showMoves(Player pl, boolean b, int rec) {
		if (rec >= players.length)
			return;
		Color c;
		if (b == true) {
			c = Color.pink;
		} else {
			c = BUTTON_DEFAULT_COLOR;
		}

		// These if statements really need to be made into a loop one of these days
		if (pl.up() != null) {
			if (!isBlocked(pl.getLocation(), pl.up())) {
				int PID = PlayerOnSpace(pl.up());
				if (PID >= 0)
					showMoves(players[PID], b, rec+1);
				else
					enableAndChangeColor(pl.up(), b, c);			
			}

		}

		if (pl.down() != null) {
			if (!isBlocked(pl.getLocation(), pl.down())) {
			int PID = PlayerOnSpace(pl.down());
			if (PID >= 0)
				showMoves(players[PID], b, rec+1);
			else
				enableAndChangeColor(pl.down(), b, c);
			}
		}

		if (pl.left() != null) {
			if (!isBlocked(pl.getLocation(), pl.left())) {
			int PID = PlayerOnSpace(pl.left());
			if (PID >= 0)
				showMoves(players[PID], b, rec+1);
			else
				enableAndChangeColor(pl.left(), b, c);
			}
		}

		if (pl.right() != null) {
			if (!isBlocked(pl.getLocation(), pl.right())) {
			int PID = PlayerOnSpace(pl.right());
			if (PID >= 0)
				showMoves(players[PID], b, rec+1);
			else
				enableAndChangeColor(pl.right(), b, c);
			}
		}
		
		enableAndChangeColor(pl.getLocation(), false, pl.getColor());
	}
	
	// method which will return true if there is a wall between the two points of false if there isn't.
	// assumes that the two spaces are directly next to each other
	private boolean isBlocked(Point p1, Point p2) {
		int smaller = -1; // 
		// if the two spaces are in the same column
		if (p1.x == p2.x && p1.x < 8) {
			smaller = Math.min(p1.y,p2.y); //finds the point that's higher up
			if (walls[p1.x][smaller] == 2)
				return true;
			if (p1.x > 0)
				if (walls[p1.x-1][smaller] == 2)
					return true;
		}
		// if the two spaces are in the same row
		else if (p1.y == p2.y && p1.y < 8) {
			smaller = Math.min(p1.x,p2.x); //finds the space to the left
			if (walls[smaller][p1.y] == 1)
				return true;
			if (p1.y > 0)
				if (walls[smaller][p1.y-1] == 1)
					return true;
		}
		
		return false;
	}
	
	// returns the ID of the player at location p, if no player is found, -1 is returned
	private int PlayerOnSpace(Point p) {
		for (int i = 0; i < players.length; i++) {
			if (p.getLocation().equals(players[i].getLocation())) {
				return i;
			}
		}
		return -1;
	}

	// just a small helper method
	private void enableAndChangeColor(Point p, boolean b, Color c) {
		gui.setColorOfSpace(p, c);
		gui.setSpaceClickable(p, b);
	}
	
	//will be called by readString to place a wall
	// p is the location where the wall will be placed
	// vh is either 1 or 2 depending on whether the wall is vertical or horizontal
	private void placeWall(Point p, int vh) {
		//if it is legal to place the wall, do it
		//and then decrement the number of walls the player has
		//else give illegal wall error
		
	}
	
	//will eventually be able to send a string containing a move to an AI or over a network after we have more information
	private void Send() {
		
	}
	
	private void nextTurn() {
		showMoves(players[turn], false);
		turn = (turn + pl + 1) % pl;
		showMoves(players[turn], true);
	}
	
	public Point getPlayerLocation(int player) {
		return players[player].getLocation();
	}

	public static void main(String[] args) {
		board b = new board(true);
	}
}
