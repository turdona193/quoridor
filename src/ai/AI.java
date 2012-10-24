package ai;

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

    private void print(String s) {
        System.out.print(s);
    }
    
    public void run() {
    	while (true) {
    		sem.acquireUninterruptibly();
    		makeMove();
    	}
    }
}
