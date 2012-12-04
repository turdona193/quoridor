package ai;

import java.awt.Point;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.Semaphore;

import main.Quoridor;
import main.QBoard;
import player.Board;
import player.GameState;
import player.Player;
import util.Graph;

/**
 * AI implements an artificial intelligence for the game.
 *
 * @author  <a href="mailto:barkle36@potsdam.edu">Andrew Allen Barkley</a>
 * @author  <a href="mailto:kosowsjm195@potsdam.edu">John M. Kosowsky</a>
 * @version 2012-11-30
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
     *     the semaphore used by this AI
     */
    public AI(Board board, Semaphore sem) {
        this.board = board;
        this.sem = sem;
    }

    /**
     * Makes a move along the shortest path to the goal for the current player.
     */
    @SuppressWarnings("null")
	public void makeMove() {
        List<Point>   path, opponentPath, targetPath;
        Set<Point>    opponentGoal, targetGoal;
        Graph<Point>  graph;
        Point         nextLocation;
        String        move;
        int 		  shortestOpponentPathSize;
        Player 		  target;

        state = board.getCurrentState();
        
        Set<Player> opponents = new HashSet<Player>(Arrays.asList(state.getPlayerArray()));
        opponents.remove(state.getCurrentPlayer());//you are not your own opponent
        
        graph = state.getGraphWithJumpEdges((state.getNextPlayer()).getLocation());
    	opponentGoal = new HashSet<Point>(Arrays.asList(state.getNextPlayer().getGoalLine()));
    	opponentPath = graph.findPath(DEFAULT_SEARCH,
    						   state.getNextPlayer().getLocation(), 
    					       opponentGoal);
    	
    	shortestOpponentPathSize = opponentPath.size();
    	target 				     = state.getNextPlayer();
        
    	if(state.getNumberOfPLayers() == 4){//don't need to find the lowest if only 2 players
    		for(Player opponent: opponents){
        	graph = state.getGraphWithJumpEdges(opponent.getLocation());
        	opponentGoal = new HashSet<Point>(Arrays.asList(opponent.getGoalLine()));
        	opponentPath = graph.findPath(DEFAULT_SEARCH,
        					   opponent.getLocation(), 
        					   opponentGoal);
        		if(opponentPath.size() < shortestOpponentPathSize){
        			shortestOpponentPathSize = opponentPath.size();
        			target                   = opponent;
        		}
    		}
    	}
        
        graph = state.getGraphWithJumpEdges(state.getCurrentPlayerLocation());
        path  = graph.findPath(DEFAULT_SEARCH,
                               state.getCurrentPlayerLocation(),
                               state.getCurrentPlayerGoalSet());
        
        graph = state.getGraphWithJumpEdges(target.getLocation());
    	targetGoal = new HashSet<Point>(Arrays.asList(target.getGoalLine()));
    	targetPath = graph.findPath(DEFAULT_SEARCH,
    					            target.getLocation(), 
    					            targetGoal);
        

        
        
        if(targetPath.size() < path.size()){
        	/////////place a wall because the opponent is closer
        	System.out.println("You're Losing");
        }
        
        
        nextLocation = path.get(1);
        move = "M " + nextLocation.x + " " +
                      nextLocation.y;
        if (board.isStringLegal(move)) {
            board.readStringFromGUI(move);
        }
    }
    
    /**
     * Acquires a semaphore and waits for a release to make a move.
     */
    public void run() {
    	while (true) {
    		sem.acquireUninterruptibly();
    		makeMove();
    	}
    }
}
