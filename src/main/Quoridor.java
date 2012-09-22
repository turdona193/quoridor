package main;

import javax.swing.*;

import static java.awt.BorderLayout.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
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
	public static final String[] MESSAGES = {"Play Game", "Options", "Exit"};

	public final static String BUTTON_NAME_PREFIX = "Button";
	public final static String[] BUTTON_TEXTS = {"Play Game", "Options", "Exit"};
	
	//public final static int[] BUTTON_LOCATONS = {FlowLayout.LEFT, FlowLayout.CENTER, FlowLayout.RIGHT};
	
	private JLabel label;
	private JPanel panel;
	private List<JButton> buttons;

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
		add(top, BorderLayout.PAGE_START);

		label = new JLabel();
		label.setName(LABEL_NAME);
		label.setText(INITIAL_MESSAGE);

		panel.add(label);


		buttons = new ArrayList<JButton>();
		initializeButtons();
		
		setVisible(true);

	}
	
	public void run(){


	}
	public static void main(String[] args) {
		Quoridor Window = new Quoridor();
		Window.run();
	}

	@Override
	public void actionPerformed(ActionEvent action) {
		for (int i = 0; i < buttons.size(); ++i) {
			if (action.getSource() == buttons.get(i)) {
				label.setText(MESSAGES[i]);
				break;
			}
		}

	}
	
	
	

}
