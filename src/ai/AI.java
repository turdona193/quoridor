package ai;

import java.util.Scanner;

//import util.Graph;
import player.board;

public class AI {

    //private Graph<Point> graph;
    private board board;

    //public AI(Graph<Point> graph, Board board) {
    public AI(board board) {
        //this.graph = graph;
        this.board = board;
    }

    public void makeMove() {
        print("Enter an AI move: ");
        Scanner in = new Scanner(System.in);
        board.readStringFromGUI(in.nextLine());
    }

    private void print(String s) {
        System.out.print(s);
    }
}
