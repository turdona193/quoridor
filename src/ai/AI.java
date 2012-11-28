package ai;

import java.awt.Point;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

import main.Quoridor;
import main.QBoard;
import player.Board;
import player.GameState;
import util.Graph;

/**
 * AI implements an artificial intelligence for the game.
 */
public class AI extends Thread {

    /**
     * Default search algorithm to use when searching for paths.
     */
    private static final String DEFAULT_SEARCH = "breadth-first";

    private Board board;
    private Semaphore sem;
    private GameState state;

    /**
     * Creates a new AI.
     *
     * @param board
     *     the board for this game
     *
     * @param sem
     *     the semaphor used by this AI
     */
    public AI(Board board, Semaphore sem) {
        this.board = board;
        this.sem = sem;
    }

    /**
     * Makes a move along the shortest path to the goal for the current player.
     */
    public void makeMove() {
        List<Point>  path;
        Graph<Point> graph;
        Point        nextLocation;
        String       move;

        state = board.getCurrentState();
        graph = state.getGraph();
        path  = graph.findPath(DEFAULT_SEARCH,
                               state.getCurrentPlayerLocation(),
                               state.getCurrentPlayerGoalSet());
        nextLocation = path.get(1);
        move = "M " + nextLocation.x + " " +
                      nextLocation.y;
        if (board.isStringLegal(move)) {
            board.readStringFromGUI(move);
        }
    }
    
    /**
     * Acquires a semaphor and waits for a release to make a move.
     */
    public void run() {
    	while (true) {
    		sem.acquireUninterruptibly();
    		makeMove();
    	}
    }
}
