package main;
//

import javax.swing.*;

import static java.awt.BorderLayout.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.text.StyleConstants.ColorConstants;

import player.Player;
import player.board;

public class qBoard extends JFrame implements ActionListener{

	public final static String BOARD_WINDOW_TITLE = "Quoridor Board";
	public final static Color BUTTON_DEFAULT_COLOR = new Color(220,220,220);
	public final static int boardLength = 9;
	
	private board playingBoard;
	
	public JButton[][] board = new JButton[9][9];
	public JButton[][] wallVert = new JButton[8][9];
	public JButton[][] wallHor = new JButton[9][8];

	private JPanel buttonPanel; 
	private JPanel statusPanel;
	private JLabel statusLabel;
	
	//this is the constructor that should probably always be used, probably
	public qBoard(board b) {
		super();
		playingBoard = b;
		initialize();
	}
	
	// I just took everything out of the old default constructor and pasted it in this method
	private void initialize() {
		setName(BOARD_WINDOW_TITLE);
		setTitle(BOARD_WINDOW_TITLE);
		setSize(512, 356);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());

		buttonPanel = new JPanel();
		statusPanel = new JPanel();
		statusLabel = new JLabel();
		buttonPanel.setLayout(null);
		statusPanel.setLayout(new BorderLayout());
		
		initializeButtons();
		buttonPanel.setSize(356, 356);
		statusPanel.setPreferredSize(new Dimension(100, 356));
		setStatus();
		statusPanel.add(statusLabel);

		add (buttonPanel);
		add (statusPanel, BorderLayout.EAST);
		//setSize(512,512);

		setVisible(true);
	}
	
	private void initializeButtons(){

		int fromTop = 0;
		boolean border = false;
		for(int i = 0;i < 17;i++){ //Row
			int fromLeft = 0;

			for (int j = 0; j < 17; j++) { // Column

				if(!border){
					if(j%2 == 0){
						JButton button = new JButton(""); // sets the text
						button.setName("M " + j/2 + " " + i/2);
						button.addActionListener(this);
						button.setRolloverEnabled(true);
						button.setBackground(BUTTON_DEFAULT_COLOR);
						//button.setEnabled(false);
						
						buttonPanel.add(button);

						Insets insets = buttonPanel.getInsets();
						button.setBounds(fromLeft + insets.left, fromTop + insets.top, 25, 25);
						fromLeft += 26;
						
						board[j/2][i/2]=button;

					}
					else{
						JButton button = new JButton(""); // sets the text
						button.setName("V " + j/2 + " " + i/2);
						button.addActionListener(this);
						button.setRolloverEnabled(true);
						button.setBackground(BUTTON_DEFAULT_COLOR);


						buttonPanel.add(button);

						Insets insets = buttonPanel.getInsets();
						button.setBounds(fromLeft + insets.left, fromTop + insets.top, 10, 25);
						fromLeft += 11;
						wallVert[j/2][i/2]=button;
					}

				}
				if (border){
					JButton button = new JButton(""); // sets the text
					button.setName("H " + j/2 + " " + i/2);
					button.addActionListener(this);
					button.setRolloverEnabled(true);
					button.setBackground(BUTTON_DEFAULT_COLOR);


					buttonPanel.add(button);

					Insets insets = buttonPanel.getInsets();
					Dimension size = button.getPreferredSize();
					button.setBounds(fromLeft + insets.left, fromTop + insets.top, 25, 10);
					fromLeft += 37;
					j++;
					wallHor[j/2][i/2]=button;
				}

			}
			if(!border){ // Fix Spacing
				fromTop += 26;
			}else{
				fromTop += 11;
			}
			border = !border;
		}
		//disableEdgeWalls();
	}
	
	public void setStatus(){
		StringBuffer sb = new StringBuffer();
        sb.append("<html><p align=center>");
        sb.append("Player 1: " + playingBoard.numberOfWalls(0));
        sb.append("<br>");
        sb.append("Player 2: " + playingBoard.numberOfWalls(1));
        sb.append("<br>");
        if(playingBoard.getNumOfPlayers()>2){
        sb.append("Player 3: " + playingBoard.numberOfWalls(2)); //should format
        sb.append("<br>");
        sb.append("Player 4: " + playingBoard.numberOfWalls(3));
        sb.append("<br>");

        }
        sb.append("<br>");
        sb.append("It is currently <br> Player " + (playingBoard.getTurn()+1) + " turn");

        sb.append("</p></html>");
        
		statusLabel.setText(sb.toString());
	}
	// method which stops the user from clicking walls around the right and bottom edges
	private void disableEdgeWalls() {
		for (int i=0; i < wallVert.length; i++) {
			wallVert[i][wallVert[0].length-1].setEnabled(false);
		}
		for (int j = 0; j < wallHor[0].length; j++) {
			wallHor[wallHor.length-1][j].setEnabled(false);
		}
	}
	
	public void actionPerformed(ActionEvent action) {
		//if (playingBoard.getCurrentPlayerType() == Player.GUI_PLAYER) {
			String move = ((JButton) action.getSource()).getName();
			if (playingBoard.isStringLegal(move)) {
				playingBoard.readStringFromGUI(move);
				System.out.println(((JButton) action.getSource()).getName());
			}
			
		//}

	}
	
	/**
	 * Checks to see if the supplied color is equivalent to the
	 * button default color.
	 * 
	 * @param bColor
	 * 		the color that will be compared to the default color
	 * 		by inspecting its RGB values
	 * 
	 * @return
	 * 		returns true if the argument color is the same as
	 * 		the button default color. Otherwise, returns false
	 */
	private boolean isDefaultColor(Color bColor){
		return (bColor.equals(BUTTON_DEFAULT_COLOR));
	}
	
	/**
	 * Changes the color of a chosen space to a new color.
	 * 
	 * @param p
	 * 		the point of the chosen space. This contains x and
	 * 		y coordinates
	 * 
	 * @param c
	 * 		the new color of the space, held in a color object
	 * 		and indicated by new RGB values
	 */
	public void setColorOfSpace(Point p, Color c) {
		board[p.x][p.y].setBackground(c);
	}
	
	/**
	 * Enables or disables the ability for the user to click a certain space.
	 * 
	 * @param p
	 * 		the point of the chosen space. This contains x and
	 * 		y coordinates
	 * 
	 * @param b
	 * 		Enable (true) or disable (false) the ability to click
	 * 		on the chosen space
	 */
	public void setSpaceClickable(Point p, boolean b) {
		board[p.x][p.y].setEnabled(b);
	}
	
	/**
	 * Checks if the space is enabled and returns true if it is. Otherwise, the
	 * method returns false.
	 * 
	 * @param p
	 * 		the point of the chosen space
	 * 
	 * @return
	 * 		returns true if the space is enabled, otherwise the method will return false
	 */
	public boolean isSpaceClickable(Point p) {
		return board[p.x][p.y].isEnabled();
	}
	
	/**
	 * Takes a "point" from the wallHor 2D array and changes the color
	 * value of that wall.
	 * 
	 * @param p
	 * 		the point of the chosen wall
	 * 
	 * @param c
	 * 		the new color of the wall, held in a color object
	 * 		and indicated by new RGB values
	 */
	public void setHoriWallColor(Point p, Color c) {
		wallHor[p.x][p.y].setBackground(c);
	}
	
	/**
	 * Enables or disables the ability for the user to click a certain horizontal wall.
	 * 
	 * @param p
	 * 		the point of the chosen wall. This contains x and
	 * 		y coordinates
	 * 
	 * @param b
	 * 		Enable (true) or disable (false) the ability to click
	 * 		on the chosen space
	 */
	public void setHoriWallClickable(Point p, boolean b) {
		wallHor[p.x][p.y].setEnabled(b);
	}
	
	/**
	 * Checks if the space is enabled and returns true if it is. Otherwise, the
	 * method returns false.
	 * 
	 * @param p
	 * 		the point of the chosen wall
	 * 
	 * @return
	 * 		returns true if the horizontal wall is enabled, otherwise the method will return false
	 */
	public boolean isHoriWallClickable(Point p) {
		return wallHor[p.x][p.y].isEnabled();
	}
	
	/**
	 * Takes a "point" from the wallVert 2D array and changes the color
	 * value of that wall.
	 * 
	 * @param p
	 * 		the point of the chosen wall
	 * 
	 * @param c
	 * 		the new color of the wall, held in a color object
	 * 		and indicated by new RGB values
	 */
	public void setVertWallColor(Point p, Color c) {
		wallVert[p.x][p.y].setBackground(c);
	}
	
	/**
	 * Enables or disables the ability for the user to click a certain vertical wall.
	 * 
	 * @param p
	 * 		the point of the chosen wall. This contains x and
	 * 		y coordinates
	 * 
	 * @param b
	 * 		Enable (true) or disable (false) the ability to click
	 * 		on the chosen space
	 */
	public void setVertWallClickable(Point p, boolean b) {
		wallVert[p.x][p.y].setEnabled(b);
	}
	
	/**
	 * Checks if the space is enabled and returns true if it is. Otherwise, the
	 * method returns false.
	 * 
	 * @param p
	 * 		the point of the chosen wall
	 * 
	 * @return
	 * 		returns true if the vertical wall is enabled, otherwise the method will return false
	 */
	public boolean isVertWallClickable(Point p) {
		return wallVert[p.x][p.y].isEnabled();
	}
	
	

	/*private static void createAndShowGUI() {
	        JFrame frame = new JFrame("Quoridor Board");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.getContentPane().add( new qBoard());
	        frame.pack();
	        frame.setVisible(true);
	        frame.setSize(500,200);
	    }
	 */



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		board b = new board(true);
		//qBoard board = new qBoard(); broken at the moment
		//createAndShowGUI();
	}

}
