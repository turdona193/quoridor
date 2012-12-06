package net;

import java.io.*;
import java.net.*;
import java.util.Scanner;

import player.Board;

import net.NetworkPlayer;

/**
 * MoveServer objects connects to board object designed for our implementation of Quoridor. Creates a TCP Server Socket
 * and uses a NetworkPlayer object for communication with game client. 
 * Can me Initialized with desired port or use predefined port '6969'.
 * 
 * @author 
 */
public class MoveServer{

	private ServerSocket serverSocket;
	private NetworkPlayer networkPlayer;
	private int numberOfPlayers;
	private boolean isPlayer;
	private boolean isObserver;
	private Board board;

	/**
	 * Class constructor. 
	 * Creates MoveSocket object, initializes serverSocket at predetermined value, '6969'.
	 * Initializes networkPlayer with an ServerSocket accept from GameClient.
	 * Upon completing a game, The move server will go back to waiting for an invite to a game.
	 * 
	 * @throws Exception Throws I/O exception if error when initializing NetworkPlayer 
	 */
	public MoveServer() throws Exception{
		serverSocket = new ServerSocket(6969);
		while(true){
			try{
				networkPlayer = new NetworkPlayer(serverSocket.accept());
				run();
			}catch(Exception e){System.err.println("Failed to connect to Server: " + e);}
		}
	}

	/**
	 * Class constructor. 
	 * Creates MoveSocket object, initializes serverSocket at parameter 'port'.
	 * Initializes networkPlayer with an ServerSocket accept from GameClient.
	 * Upon completing a game, The move server will go back to waiting for an invite to a game.
	 * 
	 * @param port int representation of desired port value of serverSocket.
	 * @throws Exception Throws I/O exception if error when initializing NetworkPlayer 
	 */
	public MoveServer(int port) throws Exception{
		serverSocket = new ServerSocket(port);
		while(true){
			try{
				networkPlayer = new NetworkPlayer(serverSocket.accept());
				run();
			}catch(Exception e){System.err.println("Failed to connect to Server: " + e);}
		}
	}

	public void run() throws Exception{
		waitForInvite();
		gameOn();
	}

	/**
	 * 
	 * @throws Exception
	 */
	private void waitForInvite() throws Exception{
		//initializing variables needed
		String move = ""; // holds next move
		String next = ""; // used to take apart move 
		Scanner sc; // opened on move to parse through it
		boolean notInvited = true;

		isPlayer = false;
		isObserver = false;

		// Waits to be invited to game
		while(notInvited){
			if((move = networkPlayer.read()) != null){
				System.out.println(move);
				sc = new Scanner(move);
				next = sc.next();
				Thread.sleep(100);
				if(next.contains("QUORIDOR")){ // Signifies if it is a player
					next = sc.next();
					numberOfPlayers = Integer.parseInt(next);
					next = sc.next();
					networkPlayer.setPlayerNumber(Integer.parseInt(next));

					board = new Board(numberOfPlayers , networkPlayer.getPlayerNumber());

					networkPlayer.write("READY " + networkPlayer.getPlayerNumber());
					isPlayer = true;
				}
				if(next.contains("WATCH")){	 // Signifies it is a observer-server
					next = sc.next();
					numberOfPlayers = Integer.parseInt(next);
					isObserver = true;
				}
				notInvited = false;
			}
		}
	}
	private void gameOn() throws Exception{
		//Initializes variables needed
		String move = ""; // holds next move
		String next = ""; // used to take apart move 
		String toBoard;
		Scanner sc; // opened on move to parse through it
		String nextMove;

		while(isPlayer){
			toBoard = "";
			if((move = networkPlayer.read()) != null){
				System.out.println(move); // For testing
				sc = new Scanner(move);
				next = sc.next();
				if(next.contains("MOVE?")){
					nextMove = board.readStringFromNet(next);
					System.out.println("got back " +nextMove);
					networkPlayer.write(nextMove);

				}
				if(next.contains("MOVED")){
					sc.next();					
					toBoard = sc.nextLine();
					board.readStringFromNet(toBoard);
				}
				if(next.contains("REMOVED")){
					System.out.println("I am getting kicked in the move server");

					networkPlayer.close();
					isPlayer = false;
					board.removePlayer(sc.nextInt());
					//break;
				}
				if(next.contains("WINNER")){
					board.winWindow(sc.nextInt());
					networkPlayer.close();
					isPlayer = false;
					//break;
				}
			}
		}

		while(isObserver){
			if((move = networkPlayer.read()) != null){
				System.out.println(move);
				sc = new Scanner(move);
				next = sc.next();
				if(next.contains("DRAW")){
				}
				if(next.contains("MOVED")){
					//nothing for now	
				}
				if(next.contains("REMOVED")){
					//nothing for now	
				}
				if(next.contains("WINNER")){
					//nothing for now	
				}
			}
		}
	}



	/**
	 * 
	 * @param args Can enter Port to open socket on.
	 * @throws Exception
	 */
	public static void main(String [] args) throws Exception{
		MoveServer moveServer;

		try{
			if(args.length != 0){
				moveServer = new MoveServer(Integer.parseInt(args[0]));
			}else{ moveServer = new MoveServer();}
		}catch(Exception e){
			System.err.println("Failled to start the server: " + e);
			e.printStackTrace();
		}

	}	
}

