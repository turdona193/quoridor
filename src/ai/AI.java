package ai;

import java.awt.Point;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

//import util.Graph;
import player.board;

public class AI extends Thread {

    //private Graph<Point> graph;
    private board board;
    private Semaphore sem;

    //public AI(Graph<Point> graph, Board board) {
    public AI(board board, Semaphore sem) {
        //this.graph = graph;
        this.board = board;
        this.sem = sem;
    }

    public void makeMove() {
        print("Enter an AI move: ");
        Scanner in = new Scanner(System.in);
        board.readStringFromGUI(in.nextLine());
    }
    
    //Method to make a random move, doesn't look as cool as I thought it would be.
    private void randomAIMove() throws InterruptedException {
    	sleep(100);
    	if (board.hasWalls(board.getTurn())) {
        	int MoveOrWall = (int)(Math.random()*2);
        	if (MoveOrWall == 0)
        		makeRandomMove();
        	if (MoveOrWall == 1)
        		placeRandomWall();
    	}
    	else
    		makeRandomMove();

    }

    //Calls either placeHoriWall
    private void placeRandomWall() {
    	while(true) {
    		int VertOrHori = (int)(Math.random()*2);
    		int x = (int)(Math.random()*8);
			int y = (int)(Math.random()*8);
        	if (VertOrHori == 0) {
        		String str = ("H" + " " + x + " " + y);
        		if(board.isStringLegal(str)) {
					board.readStringFromGUI(str);
					return;
				}	
        	}
        	if (VertOrHori == 1) {
        		String str = ("V" + " " + x + " " + y);
        		if(board.isStringLegal(str)) {
					board.readStringFromGUI(str);
					return;
				}	
        	}
    	}
    	
   
	}
    
    //Picks a random location and places a wall there if it's legal.
    private void placeHoriWall() {
     	while(true) {
			int x = (int)(Math.random()*8);
			int y = (int)(Math.random()*8);
			String str = ("H" + " " + x + " " + y);
			if(board.isStringLegal(str)) {
				board.readStringFromGUI(str);
				return;
			}				
		}
	}

	private void placeVertWall() {
	 	while(true) {
			int x = (int)(Math.random()*8);
			int y = (int)(Math.random()*8);
			String str = ("V" + " " + x + " " + y);
			if(board.isStringLegal(str)) {
				board.readStringFromGUI(str);
				return;
			}
		}
    }

	private void makeRandomMove() {
		while(true) {
			int x = (int)(Math.random()*9);
			int y = (int)(Math.random()*9);
			String str = ("M" + " " + x + " " + y);
			if(board.isStringLegal(str)) {
				board.readStringFromGUI(str);
				return;
			}
		}
		
	}

	private void print(String s) {
        System.out.print(s);
    }
    
    public void run() {
    	while (true) {
    		sem.acquireUninterruptibly();
    		//makeMove();
    		try {
				randomAIMove();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
}
