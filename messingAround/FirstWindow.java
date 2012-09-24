/**
 * 
 */
package ui;

import static java.awt.BorderLayout.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * A JFrame is a Java Frame...a rectangular region mapped onto the screen in
 * some way. It could be considered a "window" except that in terms of screen
 * widgets (widget is the generic term for things that display on the screen:
 * buttons, menus, checkboxes, and, yes, frames) a window is only the largest,
 * top-level widget and there can be multiple frames within any single
 * top-level.
 * 
 * This class demonstrates how to create a window, add a label, and test that
 * the window and the label appear using the WindowLicker toolkit.
 * 
 * @author blad
 * 
 */
public class FirstWindow extends JFrame implements ActionListener{
	// Title AND name of the main window displayed (shared with WindowLicker tests)
	public final static String MAIN_WINDOW_TITLE = "FirstWindow";
	
	public final static String LABEL_NAME = "TheLabel";

	// messages displayed by the label
	public static final String INITIAL_MESSAGE = "One fish,";
	public static final String[] MESSAGES = {"Two fish,", "Red fish,", "Blue fish."};
	
	public final static String BUTTON_NAME_PREFIX = "Button";
	public final static String[] BUTTON_TEXTS = {"One", "Two", "Three"};
	
	private JLabel label;
	private List<JButton> buttons;
	private JPanel panel;
	
	//At the moment, this class creates a bunch of buttons and adds them to the JPanel
	private void initializeButtons() {
		for (int i = 0; i < 81; ++i) {
			JButton button = new JButton(""); // sets the text
			button.setName(BUTTON_NAME_PREFIX+i);
			button.addActionListener(this);
			
			buttons.add(button);
			panel.add(button);
		}
	}
	/**
	 * Create a new top-level window out of this type. The constructor calls the
	 * super constructor, sets the name, title, size, and what happens when the
	 * window closes, and then makes the window visible on the screen.
	 * 
	 * In order for WindowLicker to find the window, the window must be visible
	 * and have a name that WL knows (assuming you use JFrameDriver.named to
	 * find the window).
	 */
	public FirstWindow() {
		super();
		setName(MAIN_WINDOW_TITLE);
		setTitle(MAIN_WINDOW_TITLE);
		setSize(640, 480);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		/* A layout manager is part of any widget that can act as a container 
		 * of other widgets. The layout manager determines how the contained
		 * widgets will be arranged. Look at the Java tutorial at
		 * http://docs.oracle.com/javase/tutorial/uiswing/layout/using.html
		 * for more information (and a rundown of available layout managers).
		 */
		setLayout(new GridLayout(2,1));
		
		label = new JLabel(INITIAL_MESSAGE, SwingConstants.CENTER);
		label.setName(LABEL_NAME);
		label.setText(INITIAL_MESSAGE);	
		
		//The only way I can find to change the font size is with the method setFont.
		//I'm not sure why there doesn't seem to be a method that just changes the font size and nothing else.
		label.setFont(new Font("Arial", Font.PLAIN, 36));

		
		add(label);
		
		panel = new JPanel();
		
		//Creates 9x9 grid layout with 10 pixels between each object both vertically and horizontally
		panel.setLayout(new GridLayout(9,9,10,10));
		add(panel);
		buttons = new ArrayList<JButton>();
		initializeButtons();
		
		panel.setVisible(true);
		setVisible(true);
	}
	
	//This is my first attempt at drawing a grid, it's not pretty yet.
	//In fact, it's still pretty ugly.
	//Every time repaint() is called, it will draw whatever is in here on the screen.
	public void paint(Graphics g) {
		
		//These two lines basically just cover up everything on the bottom half of the window.
		//Ideally, instead of having the values hardcoded, the values should work based off of 
		//two variables representing the size of the window.
		//These is a getSize() method which may be worth looking at.
		
		g.setColor(Color.WHITE);
		//The first two parameters represent the coordinates of the top left corner of the rectangle
		//And the second two parameters represent the width and height of the rectangle.
		g.fillRect(0,240, 640, 240);
		
		//Draws some black lines in the form of a grid, not a pretty grid, but a grid nonetheless
		g.setColor(Color.black);
		for (int i = 0; i < 10; i++) {
			g.fillRect(i*(640/9),240,1,240);
			g.fillRect(0, i*(240/9)+245, 640, 1);
		}

		
	}

	@Override
	//Every time a button is clicked, repaint() is called which redraws the grid.
	public void actionPerformed(ActionEvent action) {
		for (int i = 0; i < buttons.size(); ++i) {
			if (action.getSource() == buttons.get(i)) {
				//label.setText(MESSAGES[i]);
				repaint();
				break;
			}
		}
	}
	

	/**
	 * The main function. Remember: main is just a name for a function. It is
	 * also the name of the function searched for by Java when running a class;
	 * that does not preclude calling the function directly. This is an example
	 * of dependency injection.
	 * 
	 * @param args
	 */
	public static void main(String... args) {
		FirstWindow window = new FirstWindow();
	}

}
