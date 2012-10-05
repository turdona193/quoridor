package player;

import java.awt.Color;
import java.awt.Point;
import java.util.Scanner;

import main.qBoard;

public class board {
	public final static Color BUTTON_DEFAULT_COLOR = new Color(220,220,220);
	
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
			gui = new qBoard(this);
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
	}
	
	// I'm assuming the String for moving a piece will look like "M X Y"
	// where X and Y are variables representing the coordinates of the move
	public void readString(String input) {
		Point xy = new Point();
		Scanner sc = new Scanner(input);
		if (sc.next().equals("M")) {
			xy.x = sc.nextInt();
			xy.y = sc.nextInt();
			move(xy);
		}
	}
	
	// called by readString when a piece needs to be moved
	// p is the location where the player wants to move their piece
	private void move(Point p) {
		gui.setColorOfSpace(players[turn].getLocation(), BUTTON_DEFAULT_COLOR);
		players[turn].setLocation(p);
		gui.setColorOfSpace(players[turn].getLocation(), players[turn].getColor());
		nextTurn();

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
		turn = (turn + pl + 1) % pl;
	}
	
	public Point getPlayerLocation(int player) {
		return players[player].getLocation();
	}

	public static void main(String[] args) {
		board b = new board(true);
	}
}
