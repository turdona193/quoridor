package ai;

import java.awt.Point;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

import javax.swing.JButton;

import main.Quoridor;
import main.QBoard;

/*import com.objogate.wl.swing.AWTEventQueueProber;
import com.objogate.wl.swing.driver.ComponentDriver;
import com.objogate.wl.swing.driver.JButtonDriver;
import com.objogate.wl.swing.driver.JFrameDriver;
import com.objogate.wl.swing.driver.JRadioButtonDriver;
import com.objogate.wl.swing.gesture.GesturePerformer;*/

//import util.Graph;
import player.Board;

public class AI extends Thread {

    //private Graph<Point> graph;
    private Board board;
    private Semaphore sem;
    //JFrameDriver qBoardDriver;

    //public AI(Graph<Point> graph, Board board) {
    public AI(Board board, Semaphore sem) {
        //this.graph = graph;
        this.board = board;
        this.sem = sem;
        //qBoardDriver = new JFrameDriver(new GesturePerformer(), new AWTEventQueueProber(), JFrameDriver.named(qBoard.BOARD_WINDOW_TITLE), JFrameDriver.showingOnScreen());
    }

    public void makeMove() {
        print("Enter an AI move: ");
        Scanner in = new Scanner(System.in);
        board.readStringFromGUI(in.nextLine());
    }
    
    /*private JButtonDriver button(String name) {
        return new JButtonDriver(qBoardDriver, JButton.class, ComponentDriver.named(name));
	}*/
    
    //Method to make a random move, doesn't look as cool as I thought it would be.
    private void randomAIMove() throws InterruptedException {
    	//sleep(100);
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

    //Keeps picking a random location and orientation until it finds a legal move, then makes it.
    private void placeRandomWall() {
    	while(true) {
    		int VertOrHori = (int)(Math.random()*2);
    		int x = (int)(Math.random()*8);
			int y = (int)(Math.random()*8);
			String str = "";
        	if (VertOrHori == 0)
        		str = ("H" + " " + x + " " + y);
        	if (VertOrHori == 1)
        		str = ("V" + " " + x + " " + y);
        	
        	if(board.isStringLegal(str)) {
        		board.readStringFromGUI(str);
        		//JButtonDriver bDriver = button(str);
       			//bDriver.click();
				return;
			}       	
    	}
	}

    //Keeps picking a random location until a legal move has been found, then makes it.
	private void makeRandomMove() {
		while(true) {
			int x = (int)(Math.random()*9);
			int y = (int)(Math.random()*9);
			String str = ("M" + " " + x + " " + y);
			if(board.isStringLegal(str)) {
				board.readStringFromGUI(str);
				//JButtonDriver bDriver = button(str);
       			//bDriver.click();
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
