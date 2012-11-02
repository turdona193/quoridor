package main;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
/**
 * This class makes an options menu pop up for Quoridor.  OptionsMenu takes a Quoridor Object as a parameter, and 
 * changes variables in Quoridor whenever an appropriate button is clicked or an option is selected.
 */
public class OptionsMenu extends JFrame implements ActionListener, ItemListener {

	/**
	 * This holds the name of the options menu.
	 */
	public final static String OPTIONS_WINDOW_TITLE = "Options";
	/**
	 * Holds the names of the Radio Buttons.
	 */
	public final static String[] RADIO_BUTTON_TITLES = {"Radio2", "Radio4"};
	/**
	 * Holds the names of the text fields showing the Player number and color.
	 */
	public final static String[] COLOR_TEXT_FIELD_TITLES = {"col0", "col1", "col2", "col3"};
	/**
	 * Holds the names of the red text boxes containing the red value of the color.
	 */
	public final static String[] RED_TEXT_FIELD_TITLES = {"red0", "red1", "red2", "red3"};
	/**
	 * Holds the names of the red text boxes containing the green value of the color.
	 */
	public final static String[] GREEN_TEXT_FIELD_TITLES = {"green0", "green1", "green2", "green3"};
	/**
	 * Holds the names of the red text boxes containing the blue value of the color.
	 */
	public final static String[] BLUE_TEXT_FIELD_TITLES = {"blue0", "blue1", "blue2", "blue3"};
	/**
	 * Holds the names of the combo boxes.
	 */
	public final static String[] COMBO_BOX_TITLES = {"combo0", "combo1", "combo2", "combo3"};
	/**
	 * This points to the Quoridor object that called the options menu.  Pretty much everything in 
	 * the options menu class makes changes to variables in this quoridor object.
	 */
	Quoridor Q;
	
	/**
	 * This label just says Players on it and sits above the Radio buttons controlling the number of players.
	 */
	private JLabel PlayersLabel;
	/**
	 * These Radiobuttons are used to choose the number of Players.
	 */
	private JRadioButton twoPlayers, fourPlayers;
	/**
	 * This makes it so that when one button is clicked, the other is unclicked.
	 */
	private ButtonGroup numberOfPlayers;
	/**
	 * these panels hold TextFields and a jcombobox for each Player.
	 */
	private JPanel upperColorPanel, lowerColorPanel;
	/**
	 * Holds the Textfields which hold the red value of each Player's color.
	 */
	private List<JTextField> reds;
	/**
	 * Holds the Textfields which hold the blue value of each Player's color.
	 */
	private List<JTextField> blues;
	/**
	 * Holds the Textfields which hold the green value of each Player's color.
	 */
	private List<JTextField> greens;
	/**
	 * The textFields in this list will show the current Color of the Player.
	 */
	private List<JTextField> showColor;
	/**
	 * These comboBoxes will be used to select the Player's type.
	 */
	private List<JComboBox> boxes;
	/**
	 * This holds all of the options in the comboBoxes.
	 */
	private String[] playerTypeNames = {"Local", "AI", "Net"};
	
	/**
	 * Used to make a box pop up when an error occurs.
	 */
	private JFrame errorFrame;
	
	/**
	 * Might be useful for testing, maybe.  Initial tests show that it's probably not.
	 */
	public OptionsMenu() {
		super(OPTIONS_WINDOW_TITLE);
		Q = new Quoridor();
		initialize();
		setVisible(true);
	}
	
	/**
	 * The constructor that will be called by Quoridor.
	 * 
	 * @param Qu
	 * 		The Quoridor Object that's opening the options menu.
	 */
	public OptionsMenu(Quoridor Qu) {
		super(OPTIONS_WINDOW_TITLE);
		Q = Qu;
		initialize();
		setVisible(true);	
	}
	/**
	 * Initializes everything.
	 */
	private void initialize() {

		setName(OPTIONS_WINDOW_TITLE);
		setSize(500,400);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(null);
		
		PlayersLabel = new JLabel("Number of Players");
		PlayersLabel.setSize(140, 30);
		PlayersLabel.setLocation(30, 70);
		add(PlayersLabel);
		
		twoPlayers = new JRadioButton("2 Players");
		fourPlayers = new JRadioButton("4 Players");
		
		twoPlayers.setName(RADIO_BUTTON_TITLES[0]);
		fourPlayers.setName(RADIO_BUTTON_TITLES[1]);
		
		twoPlayers.setSize(100, 30);
		fourPlayers.setSize(100, 30);
		twoPlayers.setLocation(30, 100);
		fourPlayers.setLocation(30, 130);
		
		twoPlayers.addItemListener(this);
		fourPlayers.addItemListener(this);
		
		add(twoPlayers);
		add(fourPlayers);
		
		numberOfPlayers = new ButtonGroup();
		numberOfPlayers.add(twoPlayers);
		numberOfPlayers.add(fourPlayers);
		
		reds = new ArrayList<JTextField>();
		blues = new ArrayList<JTextField>();
		greens = new ArrayList<JTextField>();
		showColor = new ArrayList<JTextField>();
		boxes = new ArrayList<JComboBox>();
		initializeColorPanels();
		
	}
	/**
	 * Initializes the two panels that contain information about individual Players.
	 */
	private void initializeColorPanels() {
		upperColorPanel = new JPanel();
		lowerColorPanel = new JPanel();
		
		upperColorPanel.setSize(300, 60);
		lowerColorPanel.setSize(300, 60);
		
		upperColorPanel.setLocation(200, 100);
		lowerColorPanel.setLocation(200, 160);
		
		upperColorPanel.setLayout(new GridLayout(2,5));
		lowerColorPanel.setLayout(new GridLayout(2,5));
		
		for (int i = 0; i < Q.colors.length; i++) {
			JTextField color = new JTextField();
			color.setName(COLOR_TEXT_FIELD_TITLES[i]);
			color.setText("P" + (i + 1));
			color.setBackground(Q.colors[i]);
			color.setEnabled(false);
			
			JTextField red = new JTextField("" + Q.colors[i].getRed());
			red.setName(RED_TEXT_FIELD_TITLES[i]);
			JTextField green = new JTextField("" + Q.colors[i].getGreen());
			green.setName(GREEN_TEXT_FIELD_TITLES[i]);
			JTextField blue = new JTextField("" + Q.colors[i].getBlue());
			blue.setName(BLUE_TEXT_FIELD_TITLES[i]);
			
			
			red.addActionListener(this);
			green.addActionListener(this);
			blue.addActionListener(this);
			
			JComboBox box = new JComboBox(playerTypeNames);
			box.setName(COMBO_BOX_TITLES[i]);
			box.setSelectedIndex(Q.playerTypes[i]);
			box.addItemListener(this);
			
			reds.add(red);
			greens.add(green);
			blues.add(blue);
			showColor.add(color);
			boxes.add(box);
			
			JPanel playerPanel = new JPanel();
			playerPanel.setLayout(new GridLayout(1,2));
			JPanel rgbPanel = new JPanel();
			rgbPanel.setLayout(new GridLayout(1,3));
			
			if (i < 2) {
				playerPanel.add(color);
				playerPanel.add(box);
				rgbPanel.add(red);
				rgbPanel.add(green);
				rgbPanel.add(blue);
				upperColorPanel.add(playerPanel);
				upperColorPanel.add(rgbPanel);
			}
			else {
				playerPanel.add(color);
				playerPanel.add(box);
				rgbPanel.add(red);
				rgbPanel.add(green);
				rgbPanel.add(blue);
				lowerColorPanel.add(playerPanel);
				lowerColorPanel.add(rgbPanel);
			
			}
			
		}
		
		add(upperColorPanel);
		if (Q.players == 4) {
			add(lowerColorPanel);
		}
	
	}

	public void actionPerformed(ActionEvent action) {
		Color c;
		int r, b, g;
		try {
			for (int i = 0; i < Q.players; i++) {
				if (Integer.parseInt(reds.get(i).getText()) > 255 || Integer.parseInt(reds.get(i).getText()) < 0)
					reds.get(i).setText("0");
				if (Integer.parseInt(greens.get(i).getText()) > 255 || Integer.parseInt(greens.get(i).getText()) < 0)
					greens.get(i).setText("0");
				if (Integer.parseInt(blues.get(i).getText()) > 255 || Integer.parseInt(blues.get(i).getText()) < 0)
					blues.get(i).setText("0");
				r = Integer.parseInt(reds.get(i).getText());
				g = Integer.parseInt(greens.get(i).getText());
				b = Integer.parseInt(blues.get(i).getText());
				c = new Color(r, g, b);
				showColor.get(i).setBackground(c);
				Q.colors[i] = c;
			}
		}
		catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(errorFrame,
				    "Please only enter Integers in the boxes.");

		}
		
	}

	public void itemStateChanged(ItemEvent item) {
		if (twoPlayers.isSelected()) {
			Q.players = 2;
			remove(lowerColorPanel);
			repaint();
			return;
		}
		if (fourPlayers.isSelected()) {
			Q.players = 4;
			add(lowerColorPanel);
			setVisible(true);
			return;
		}
		for (int i = 0; i < Q.players; i++) {
			if (item.getSource() == boxes.get(i)) {
				Q.playerTypes[i] = boxes.get(i).getSelectedIndex();
			}
		}
		
	}
	
	public static void main(String[] args) {
		OptionsMenu menu = new OptionsMenu();
		//Window.run();
	}

}
