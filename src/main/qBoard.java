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
		setSize(400,400);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		buttonPanel = new JPanel();
		buttonPanel.setLayout(null);

		initializeButtons();
		buttonPanel.setSize(512, 512);

		//add(buttonPanel, BorderLayout.PAGE_START);
		add (buttonPanel);
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
		if (playingBoard.getCurrentPlayerType() == Player.GUI_PLAYER) {
			String move = ((JButton) action.getSource()).getName();
			if (playingBoard.isStringLegal(move)) {
				playingBoard.readStringFromGUI(move);
				System.out.println(((JButton) action.getSource()).getName());
			}
		}

	}
	
	private boolean isDefaultColor(Color bColor){
		return (bColor.equals(BUTTON_DEFAULT_COLOR));
	}
	
	public void setColorOfSpace(Point p, Color c) {
		board[p.x][p.y].setBackground(c);
	}
	
	public void setSpaceClickable(Point p, boolean b) {
		board[p.x][p.y].setEnabled(b);
	}
	
	public boolean isSpaceClickable(Point p) {
		return board[p.x][p.y].isEnabled();
	}
	
	public void setHoriWallColor(Point p, Color c) {
		wallHor[p.x][p.y].setBackground(c);
	}
	
	public void setHoriWallClickable(Point p, boolean b) {
		wallHor[p.x][p.y].setEnabled(b);
	}
	
	public boolean isHoriWallClickable(Point p) {
		return wallHor[p.x][p.y].isEnabled();
	}
	
	public void setVertWallColor(Point p, Color c) {
		wallVert[p.x][p.y].setBackground(c);
	}
	
	public void setVertWallClickable(Point p, boolean b) {
		wallVert[p.x][p.y].setEnabled(b);
	}
	
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
