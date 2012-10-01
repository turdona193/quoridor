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

public class qBoard extends JFrame implements ActionListener {

	public final static String BOARD_WINDOW_TITLE = "Qouridor Board";
	public final static Color BUTTON_DEFAULT_COLOR = new Color(220, 220, 220);
	public final static int boardLength = 9;

	public JButton[][] board = new JButton[9][9];
	private JPanel buttonPanel;
	private Player[] players;
	private int cp;
	private int np;

	public qBoard() {
		// TODO Auto-generated constructor stub
		super();
		setName(BOARD_WINDOW_TITLE);
		setTitle(BOARD_WINDOW_TITLE);
		setSize(400, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		buttonPanel = new JPanel();
		buttonPanel.setLayout(null);

		initializeButtons();
		buttonPanel.setSize(512, 512);

		// add(buttonPanel, BorderLayout.PAGE_START);
		add(buttonPanel);
		// setSize(512,512);
		players = new Player[4];
		players[0] = new Player(1, 5);
		players[1] = new Player(2, 5);
		players[2] = new Player(3, 5);
		players[3] = new Player(4, 5);
		cp = 0;
		np = 4;
		board[players[0].getX()][players[0].getY()].setBackground(players[0]
				.getColor());
		board[players[1].getX()][players[1].getY()].setBackground(players[1]
				.getColor());
		board[players[2].getX()][players[2].getY()].setBackground(players[2]
				.getColor());
		board[players[3].getX()][players[3].getY()].setBackground(players[3]
				.getColor());
		highlightMoves(true);

		setVisible(true);

	}

	private void highlightMoves(boolean bool) {
		Color c;
		if (bool == true) {
			c = Color.pink;
		} else {
			c = BUTTON_DEFAULT_COLOR;
		}
		
		if (players[cp].up() != null) {
			board[players[cp].up().x][players[cp].up().y]
					.setBackground(c);
		}
		
		if (players[cp].down() != null) {
			System.out.println(players[cp].getY());
			board[players[cp].down().x][players[cp].down().y]
					.setBackground(c);
		}
		
		if (players[cp].left() != null) {
			board[players[cp].left().x][players[cp].left().y]
					.setBackground(c);
		}
		
		if (players[cp].right() != null) {
			System.out.println("testing");
			board[players[cp].right().x][players[cp].right().y]
					.setBackground(c);
		}

	}

	private void initializeButtons() {

		int fromTop = 0;
		boolean border = false;
		for (int i = 0; i < 17; i++) {
			int fromLeft = 0;

			for (int j = 0; j < 17; j++) {

				if (!border) {
					if (j % 2 == 0) {
						JButton button = new JButton(""); // sets the text
						button.setName("bNumber" + i + " " + j);
						button.addActionListener(this);
						button.setRolloverEnabled(true);
						button.setBackground(BUTTON_DEFAULT_COLOR);

						buttonPanel.add(button);

						Insets insets = buttonPanel.getInsets();
						button.setBounds(fromLeft + insets.left, fromTop
								+ insets.top, 25, 25);
						fromLeft += 26;

						board[j / 2][i / 2] = button;

					} else {
						JButton button = new JButton(""); // sets the text
						button.setName("wNumber" + i + " " + j);
						// button.addActionListener(this);
						button.setRolloverEnabled(true);
						button.setBackground(BUTTON_DEFAULT_COLOR);

						buttonPanel.add(button);

						Insets insets = buttonPanel.getInsets();
						button.setBounds(fromLeft + insets.left, fromTop
								+ insets.top, 10, 25);
						fromLeft += 11;
					}

				}
				if (border) {
					JButton button = new JButton(""); // sets the text
					button.setName("wNumber" + i + " " + j);
					// button.addActionListener(this);
					button.setRolloverEnabled(true);
					button.setBackground(BUTTON_DEFAULT_COLOR);

					buttonPanel.add(button);

					Insets insets = buttonPanel.getInsets();
					Dimension size = button.getPreferredSize();
					button.setBounds(fromLeft + insets.left, fromTop
							+ insets.top, 25, 10);
					fromLeft += 37;
					j++;
				}

			}
			if (!border) {
				fromTop += 26;
			} else {
				fromTop += 11;
			}
			border = !border;
		}
	}

	public void actionPerformed(ActionEvent action) {
		if (players[cp].up() != null)
			if (board[players[cp].up().x][players[cp].up().y] == action.getSource()) {
				highlightMoves(false);
				board[players[cp].getX()][players[cp].getY()].setBackground(BUTTON_DEFAULT_COLOR);
				players[cp].setLocation(players[cp].up());
				board[players[cp].getX()][players[cp].getY()].setBackground(players[cp].getColor());
				cp = (cp+np+1) % np;
				highlightMoves(true);
				//(p+NP+1) % NP
			}
		
		if (players[cp].down() != null)
			if (board[players[cp].down().x][players[cp].down().y] == action.getSource()) {
				highlightMoves(false);
				board[players[cp].getX()][players[cp].getY()].setBackground(BUTTON_DEFAULT_COLOR);
				players[cp].setLocation(players[cp].down());
				board[players[cp].getX()][players[cp].getY()].setBackground(players[cp].getColor());
				cp = (cp+np+1) % np;
				highlightMoves(true);
				//(p+NP+1) % NP
			}
		
		if (players[cp].left() != null)
			if (board[players[cp].left().x][players[cp].left().y] == action.getSource()) {
				highlightMoves(false);
				board[players[cp].getX()][players[cp].getY()].setBackground(BUTTON_DEFAULT_COLOR);
				players[cp].setLocation(players[cp].left());
				board[players[cp].getX()][players[cp].getY()].setBackground(players[cp].getColor());
				cp = (cp+np+1) % np;
				highlightMoves(true);
				//(p+NP+1) % NP
			}
		
		if (players[cp].right() != null)
			if (board[players[cp].right().x][players[cp].right().y] == action.getSource()) {
				highlightMoves(false);
				board[players[cp].getX()][players[cp].getY()].setBackground(BUTTON_DEFAULT_COLOR);
				players[cp].setLocation(players[cp].right());
				board[players[cp].getX()][players[cp].getY()].setBackground(players[cp].getColor());
				cp = (cp+np+1) % np;
				highlightMoves(true);
				//(p+NP+1) % NP
			}
		
		// Color col = new Color(12,34,54);
		/*
		 * for (int i = 0; i < boardLength; i++) { for (int j = 0; j<
		 * boardLength ; j++){ if (action.getSource() == board[j][i]) {
		 * //System.out.println(board[j][i].getBackground());
		 * if(isDefaultColor(board[j][i].getBackground())){ col = new
		 * Color(i*20, i*j, j*20); board[j][i].setBackground(col); }else{
		 * board[j][i].setBackground(BUTTON_DEFAULT_COLOR); } break; } } }
		 */
	}

	private boolean isDefaultColor(Color bColor) {
		return (bColor.equals(BUTTON_DEFAULT_COLOR));
	}

	/*
	 * private static void createAndShowGUI() { JFrame frame = new
	 * JFrame("Quoridor Board");
	 * frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 * frame.getContentPane().add( new qBoard()); frame.pack();
	 * frame.setVisible(true); frame.setSize(500,200); }
	 */

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		qBoard board = new qBoard();
		// createAndShowGUI();
	}

}
