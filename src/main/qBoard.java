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

public class qBoard extends JFrame implements ActionListener{

	public final static String BOARD_WINDOW_TITLE = "Qouridor Board";
	public final static Color BUTTON_DEFAULT_COLOR = new Color(220,220,220);

	protected JButton[][] board = new JButton[9][9];
	private JPanel buttonPanel; 

	public qBoard() {
		// TODO Auto-generated constructor stub	
		super();
		setName(BOARD_WINDOW_TITLE);
		setSize(400,400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

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
		for(int i = 0;i < 17;i++){
			int fromLeft = 0;

			for (int j = 0; j < 17; j++) {
				if(!border){
					if(j%2 == 0){
						JButton button = new JButton(""); // sets the text
						button.setName("sNumber"+i+" "+j);
						button.addActionListener(this);
						button.setRolloverEnabled(true);
						button.setBackground(BUTTON_DEFAULT_COLOR);
						
						buttonPanel.add(button);

						Insets insets = buttonPanel.getInsets();
						button.setBounds(fromLeft + insets.left, fromTop + insets.top, 25, 25);
						fromLeft += 26;
						
						board[j/2][i/2]=button;

					}
					else{
						JButton button = new JButton(""); // sets the text
						button.setName("wNumber"+i+" "+j);
						//button.addActionListener(this);
						button.setRolloverEnabled(true);
						button.setBackground(BUTTON_DEFAULT_COLOR);


						buttonPanel.add(button);

						Insets insets = buttonPanel.getInsets();
						button.setBounds(fromLeft + insets.left, fromTop + insets.top, 10, 25);
						fromLeft += 11;
					}

				}
				if (border){
					JButton button = new JButton(""); // sets the text
					button.setName("wNumber"+i+" "+j);
					//button.addActionListener(this);
					button.setRolloverEnabled(true);
					button.setBackground(BUTTON_DEFAULT_COLOR);


					buttonPanel.add(button);

					Insets insets = buttonPanel.getInsets();
					Dimension size = button.getPreferredSize();
					button.setBounds(fromLeft + insets.left, fromTop + insets.top, 25, 10);
					fromLeft += 37;
					j++;
				}

			}
			if(!border){
				fromTop += 26;
			}else{
				fromTop += 11;
			}
			border = !border;
		}
	}
	
	public void actionPerformed(ActionEvent action) {
		Color col = new Color(12,34,54);
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j< board[i].length ; j++){
				if (action.getSource() == board[j][i]) {
					System.out.println(board[j][i].getBackground());
					if(isDefaultColor(board[j][i].getBackground())){
						col = new Color(i*20, i*j, j*20);
						board[j][i].setBackground(col);
					}else{
						board[j][i].setBackground(BUTTON_DEFAULT_COLOR);
					}
					break;
				}
			}
		}
	}
	
	private boolean isDefaultColor(Color bColor){
		return (bColor.equals(BUTTON_DEFAULT_COLOR));
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
		qBoard board = new qBoard();
		//createAndShowGUI();
	}

}
