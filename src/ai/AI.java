package ai;

import java.awt.Point;
import java.util.ArrayList;
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

	public void makeMove() {
        List<Point>   path, opponentPath, targetPath, longPath;
        //List<String>  bestMoves = new ArrayList<String>();
        Set<Point>    opponentGoal, targetGoal;
        Graph<Point>  graph;
        Point         nextLocation;
        String        move = null;
        int 		  shortestOpponentPathSize;
        //int 		  wallRow = 0;
        //int			 wallCol  = 0;
        Player 		  target;//the player that is closest to their goal
        
        GameState[][] verticalStates = new GameState[8][8];
        GameState[][] horizontalStates = new GameState[8][8];
        int[][]       verticalStateLengths = new int[8][8];
        int[][]		  horizontalStateLengths = new int[8][8];
        Graph<Point>  vertGraph, horiGraph;
        
        state = board.getCurrentState();
		int[][] walls = state.getWalls();
        
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

        	/*if((targetPath.size() < path.size())&&(state.getCurrentPlayer().hasWalls())){  /////START OF WALL PLACEMENT
        		
        		longPath = targetPath;
        		walls = state.getWalls();
        		
        		for(int row = 0; row < 8; row++){
        			for(int col = 0; col < 8; col++){
        				if(walls[row][col] == 0){
        					System.out.println("Cloning State " + row + " " + col);
        					verticalStates[row][col] = state.clone();
        					verticalStates[row][col].walls[row][col] = 1; 
        					
        					horizontalStates[row][col] = state.clone();
        					horizontalStates[row][col].walls[row][col] = 2; 
        				}
        			}
        		}
        		
        		for(int row = 0; row < 8; row++){
        			for(int col = 0; col < 8; col++){
        				if(walls[row][col] == 0){
        					System.out.println("Finding Path Length for state " + row + " " + col);
        					vertGraph = verticalStates[row][col].getGraphWithJumpEdges(target.getLocation());
        					horiGraph = horizontalStates[row][col].getGraphWithJumpEdges(target.getLocation());
        					
        					verticalStateLengths  [row][col] = vertGraph.findPath(DEFAULT_SEARCH, 
        													 target.getLocation(), targetGoal).size();
        					horizontalStateLengths[row][col] = horiGraph.findPath(DEFAULT_SEARCH, 
									 					     target.getLocation(), targetGoal).size();
        				}
        			}
        		}
        		
        		for(int row = 0; row < 8; row++){
        			for(int col = 0; col < 8; col++){
        				if(walls[row][col] == 0){
        					System.out.println("LongPath size: " + longPath.size());
        					System.out.println("VerticalStateSize at " + row + " " + col + ": " + verticalStateLengths [row][col]);
        					System.out.println("HorizontalStateSize at " + row + " " + col + ": " + horizontalStateLengths [row][col]);
        					if(verticalStateLengths [row][col] >= longPath.size()){
        						move = "V " + row + " " + col;
        						longPath = verticalStates[row][col].getGraphWithJumpEdges(target.getLocation())
        														   .findPath(DEFAULT_SEARCH, target.getLocation()
        														   , targetGoal);
        					}
        					if(horizontalStateLengths [row][col] >= longPath.size()){
        						move = "H " + row + " " + col;
        						longPath = horizontalStates[row][col].getGraphWithJumpEdges(target.getLocation())
								   									 .findPath(DEFAULT_SEARCH, target.getLocation()
								   									 , targetGoal);
        					}
        				}
        			}
        		}
        		
        		vertGraph = null;
        		horiGraph = null;
        		
        		for(int row = 0; row < 8; row++){
        			for(int col = 0; col < 8; col++){
    					verticalStates  [row][col] = null;
    					horizontalStates[row][col] = null;
        				verticalStateLengths  [row][col] = 0;
        				horizontalStateLengths[row][col] = 0;
        			}
        		}

        	}else{
              //we would put the commands to move forward here.  
        	}
        	*/
        	nextLocation = path.get(1);
            move = "M " + nextLocation.x + " " + nextLocation.y;
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
