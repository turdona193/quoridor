package main;

import javax.swing.*;

import static java.awt.BorderLayout.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Quoridor extends JFrame implements ActionListener {

	public final static String MAIN_WINDOW_TITLE = "Qouridor";


	public final static String LABEL_NAME = "TheLabel";

	// messages displayed by the label
	public static final String INITIAL_MESSAGE = "GAME ON?";
	public static final String[] MESSAGES = {"Play Game Clicked", "Options Clicked", "Exit Clicked"};

	public final static String BUTTON_NAME_PREFIX = "Button";
	public final static String[] BUTTON_TEXTS = {"Play Game", "Options", "Exit"};
	
	//public final static int[] BUTTON_LOCATONS = {FlowLayout.LEFT, FlowLayout.CENTER, FlowLayout.RIGHT};
	
	private JLabel label;
	private JPanel panel;
	private List<JButton> buttons;
	
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem playMenuItem, optionsMenuItem, exitMenuItem;
	
	private void initializeButtons() {
		for (int i = 0; i < BUTTON_TEXTS.length; ++i) {
			JButton button = new JButton(BUTTON_TEXTS[i]); // sets the text
			button.setName(BUTTON_NAME_PREFIX+i);
			button.addActionListener(this);

			buttons.add(button);
			panel.add(button);
		}
	}

	public Quoridor() {
		super(MAIN_WINDOW_TITLE);
		setName(MAIN_WINDOW_TITLE);

		setSize(640, 480);
		setDefaultCloseOperation(EXIT_ON_CLOSE);


		//http://docs.oracle.com/javase/tutorial/uiswing/layout/using.html
		
		setLayout(new BorderLayout());
		
		panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setVisible(true);
		
		JLabel top = new JLabel("Quoridor");
		top.setHorizontalAlignment(SwingConstants.CENTER);
		top.setFont(new Font("Arial", Font.BOLD, 48));		// We need to either find a cooler font, replace this with an image later.
		add(top, BorderLayout.PAGE_START);

		label = new JLabel();
		label.setName(LABEL_NAME);
		label.setText(INITIAL_MESSAGE);

		panel.add(label);


		buttons = new ArrayList<JButton>();
		initializeButtons();
		
		// initializes the menu bar, adds the menu bar to the JFrame, and then add the file menu to the menu bar
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		setFileMenu();
		
		
		setVisible(true);

	}
	
	
	// method to create a file menu on the menu bar, along with all the menu items
	private void setFileMenu() {
		fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		playMenuItem = new JMenuItem("Play");
		fileMenu.add(playMenuItem);
		playMenuItem.addActionListener(this);
		
		optionsMenuItem = new JMenuItem("Options");
		fileMenu.add(optionsMenuItem);
		optionsMenuItem.addActionListener(this);
		
		exitMenuItem = new JMenuItem("Exit");
		fileMenu.add(exitMenuItem);
		exitMenuItem.addActionListener(this);

	}
	
	
	public void run(){


	}
	public static void main(String[] args) {
		Quoridor Window = new Quoridor();
		Window.run();
	}

	// if the object that was clicked is found, the method will end since we won't need to check
	// the others
	@Override
	public void actionPerformed(ActionEvent action) {
		if (checkButtons(action)) 
			return;
		if (checkMenuItems(action))
			return;
	}
	
	// checks to see if the action event was caused by any of the buttons being clicked
	// if it turns out that it was caused by one of the buttons being clicked, true will 
	// be returned, otherwise the method will return false
	private boolean checkButtons(ActionEvent action) {
		if (buttons.get(0) == action.getSource()) {
			play();
			return true;
		}
		
		if (buttons.get(1) == action.getSource()) {
			options();
			return true;
		}
		
		if (buttons.get(2) == action.getSource()) {
			exit();
			return true;
		}
		return false;
	}
	
	// checks to see if the action event was caused by any of the menu items being clicked
	// if it turns out that it was caused by one of the menu items being clicked, true will 
	// be returned, otherwise the method will return false
	private boolean checkMenuItems(ActionEvent action) {
		if (playMenuItem == action.getSource()) {
			play();
			return true;
		}
		
		if (optionsMenuItem == action.getSource()) {
			options();
			return true;
		}
		
		if (exitMenuItem == action.getSource()) {
			exit();
			return true;
		}
		return false;
		// JMenuItem playMenuItem, optionsMenuItem, exitMenuItem;
	}

	//  The three following methods don't do much now, but someday they'll have real functions.
	private void play() {
		label.setText(MESSAGES[0]);
		qBoard board = new qBoard();
	}

	private void options() {
		label.setText(MESSAGES[1]);
		
	}

	private void exit() {
		label.setText(MESSAGES[2]);
		System.exit(0);
	}
	
	
	

}
