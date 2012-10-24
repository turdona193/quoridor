package player;

import java.awt.Color;
import java.awt.Point;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

import javax.swing.JButton;

import util.Graph;
import util.GraphEdgeIsDuplicateException;
import util.GraphEdgeNotFoundException;
import util.GraphNodeIsDuplicateException;
import util.GraphNodeNotFoundException;

import main.qBoard;
import ai.AI;

public class board {
	public final static Color BUTTON_DEFAULT_COLOR = new Color(220,220,220);
	public static final Color WALL_COLOR = Color.black;  //This will eventually be switched to brown
	
	private int[][] walls;		// holds the locations of all the walls; 0 = no wall, 1 = vertical wall, 2 = horizontal wall
	private Player players[];	// holds information about each player
	private int turn, pl;		// turn tells us which player's turn it is;  pl is the number of players
	private qBoard gui;			// allows board to communicate with the gui
	private AI ai;				// allows board to communicate with the ai
	public Graph<Point> graph;	// needed to find a path from a point to the goal
	public Semaphore sem;		// used to tell the ai when it's turn is, needs a better name
	
	// default constructor, assumes 2 players all using their default colors
	// will probably only be used for testing
	public board() {
		walls = new int[8][8];
		initializeToZero(walls);
		pl = 2;
		players = new Player[pl];
		for (int i = 0; i < pl; i++) {
			players[i] = new Player(i, 10);
		}
		turn = 0;
		newGUI();
	}
	
	// constructor that will make a 2 player game where player 0 is using the gui, and player 1 is an ai opponent
	public board(boolean usingAI) {
		walls = new int[8][8];
		initializeToZero(walls);
		pl = 2;
		players = new Player[pl];
		players[0] = new Player(0, 10, Color.blue, Player.GUI_PLAYER);
		if (usingAI) {
			players[1] = new Player(1, 10, Color.red, Player.AI_PLAYER);	
		}
		else {
			players[1] = new Player(1, 10, Color.red, Player.GUI_PLAYER);
		}
		turn = 0;
		initializeGraph();
		initializeAIIfNeeded();
		newGUI();
	}
	
	// this is the constructor that will be used most often
	public board(int numOfPlayers, Color[] colArray, int[] playerTypes) {
		walls = new int[8][8];
		initializeToZero(walls);
		pl = numOfPlayers;
		players = new Player[pl];
		for (int i = 0; i < pl; i++) {
			players[i] = new Player(i, 20/pl, colArray[i], playerTypes[i]);
		}
		turn = 0;
		initializeGraph();
		initializeAIIfNeeded();
		newGUI();
	}
	
	private void initializeToZero(int[][] w) {
		for (int i = 0; i < w.length; i++) 
			for (int j = 0; j < w.length; j++) {
				w[i][j] = 0;
			}
		
	}
	
	// checks to see if any of the players are AI players, and initializes an AI and Semaphore if there are
	private void initializeAIIfNeeded() {
		boolean isNeeded = false;
		for (int i = 0; i < pl; i++) {
			if (players[i].getPlayerType() == Player.AI_PLAYER) {
				isNeeded = true;
			}
		}
		if (isNeeded) {
			sem = new Semaphore(0);
			ai = new AI(this, sem);
			ai.start();
		}

	}
	
	// creates a graph containing 81 nodes, each representing a space on the board, and add edges between
	// nodes representing spaces directly adjacent to each other
	private void initializeGraph() {
		graph = new Graph<Point>();
		try {
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					graph.addNode(new Point(i,j));
				}
			}
			
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 9; j++) {
					graph.addEdge(new Point(i, j), new Point(i + 1, j));
				}
			}
			
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 9; j++) {
					graph.addEdge(new Point(j, i), new Point(j, i + 1));
				}
			}
			
		} catch (GraphNodeIsDuplicateException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (GraphNodeNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (GraphEdgeIsDuplicateException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
		
	// method to look at a String representing a move and return whether or not it represents a valid move
	// works for players trying to move their piece; returns true whenever a player tries to place a wall
	public boolean isStringLegal(String input) {
		Point xy = new Point();
		Scanner sc = new Scanner(input);
		String firstCh = sc.next();
		if (firstCh.equals("M")) {
			xy.x = sc.nextInt();
			xy.y = sc.nextInt();
			return isMoveLegal(turn, xy);
		} else if (firstCh.equals("H")) {
			xy.x = sc.nextInt();
			xy.y = sc.nextInt();
			return isHoriWallLegal(turn, xy);
		} else if (firstCh.equals("V")) {
			xy.x = sc.nextInt();
			xy.y = sc.nextInt();
			return isVertWallLegal(turn, xy);
		}
		return false;
	}
	
	// player is the ID of the player trying to place a wall, loc represents the location of the wall
	public boolean isHoriWallLegal(int player, Point loc) {
		if (players[player].getWalls() > 0 && loc.x < 8) {
			if (walls[loc.x][loc.y] > 0)
				return false;
			if (loc.x < 7)
				if (walls[loc.x+1][loc.y] == 2)
					return false;
			if (loc.x > 0)
				if (walls[loc.x-1][loc.y] == 2)
					return false;
			return true;
		}
		return false;
	}
	
	// player is the ID of the player trying to place a wall, loc represents the location of the wall
	public boolean isVertWallLegal(int player, Point loc) {
		if (players[player].getWalls() > 0 && loc.y < 8) {
			if (walls[loc.x][loc.y] > 0)
				return false;
			if (loc.y < 7)
				if (walls[loc.x][loc.y+1] == 1)
					return false;
			if (loc.y > 0)
				if (walls[loc.x][loc.y-1] == 1)
					return false;
			return true;
		}
		return false;
	}
	
	public boolean isMoveLegal(int player, Point move) {
		return isMoveLegal(player, move, 0);
	}
	
	// will eventually return true if a move is legal and false if it is not legal
	// player is the player's number, and move is where the player is attempting to move
	private boolean isMoveLegal(int player, Point move, int rec) {
		if (rec >= players.length)
			return false;
		
		Player currentPlayer = players[player];
		Point[] adjacentSpaces = new Point[4];
		adjacentSpaces[0] = currentPlayer.up();
		adjacentSpaces[1] = currentPlayer.down();
		adjacentSpaces[2] = currentPlayer.left();
		adjacentSpaces[3] = currentPlayer.right();
		
		for (int i = 0; i < adjacentSpaces.length; i++) {
			if (adjacentSpaces[i] != null) {
				if (!isBlocked(currentPlayer.getLocation(), adjacentSpaces[i])) {
					int PID = PlayerOnSpace(adjacentSpaces[i]);
					if (PID >= 0) {
						if (isMoveLegal(PID, move, rec+1)) {
							return true;
						}
					}
					else if (adjacentSpaces[i].equals(move)) {
						return true;	
					}
				}
			}
		}
		
		return false;
	}
	
	public int[][] getWallArray() {
		return walls;
	}
	
	public int getTurn() {
		return turn;
	}
	
	public Player[] getPlayersArray() {
		return players;
	}
	
	public int getCurrentPlayerType() {
		return players[turn].getPlayerType();
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
			try {
				graph.removeEdge(new Point(xy.x,xy.y), new Point(xy.x,xy.y+1));
				graph.removeEdge(new Point(xy.x+1,xy.y), new Point(xy.x+1,xy.y+1));
			}
			catch (GraphNodeNotFoundException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			catch (GraphEdgeNotFoundException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			
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
			try {
				graph.removeEdge(new Point(xy.x,xy.y), new Point(xy.x+1,xy.y));
				graph.removeEdge(new Point(xy.x,xy.y+1), new Point(xy.x+1,xy.y+1));
			}
			catch (GraphNodeNotFoundException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			catch (GraphEdgeNotFoundException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}

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
		
		Point[] adjacentSpaces = new Point[4];
		adjacentSpaces[0] = pl.up();
		adjacentSpaces[1] = pl.down();
		adjacentSpaces[2] = pl.left();
		adjacentSpaces[3] = pl.right();
		
		for (int i = 0; i < adjacentSpaces.length; i++) {
			if (adjacentSpaces[i] != null) {
				if (!isBlocked(pl.getLocation(), adjacentSpaces[i])) {
					int PID = PlayerOnSpace(adjacentSpaces[i]);
					if (PID >= 0)
						showMoves(players[PID], b, rec+1);
					else
						enableAndChangeColor(adjacentSpaces[i], b, c);			
				}
			}
		}
		
		enableAndChangeColor(pl.getLocation(), false, pl.getColor());
	}
	
	// method which will return true if there is a wall between the two points of false if there isn't.
	// assumes that the two spaces are directly next to each other
	public boolean isBlocked(Point p1, Point p2) {
		int smaller = -1; // 
		// if the two spaces are in the same column
		if (p1.x == p2.x) {
			smaller = Math.min(p1.y,p2.y); //finds the point that's higher up
			if (p1.x < 8)
				if (walls[p1.x][smaller] == 2)
					return true;
			if (p1.x > 0)
				if (walls[p1.x-1][smaller] == 2)
					return true;
		}
		// if the two spaces are in the same row
		else if (p1.y == p2.y) {
			smaller = Math.min(p1.x,p2.x); //finds the space to the left
			if (p1.y < 8)
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
		//gui.setSpaceClickable(p, b);
	}
	
	//will eventually be able to send a string containing a move to an AI or over a network after we have more information
	private void Send() {
		
	}
	
	private void nextTurn() {
		showMoves(players[turn], false);
		turn = (turn + pl + 1) % pl;
		showMoves(players[turn], true);
		if (getCurrentPlayerType() == Player.AI_PLAYER) {
			sem.release();
		}

	}
	
	public Point getPlayerLocation(int player) {
		return players[player].getLocation();
	}

	public static void main(String[] args) {
		board b = new board(true);
	}
}
