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
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class OptionsMenu extends JFrame implements ActionListener, ItemListener {

	public final static String OPTIONS_WINDOW_TITLE = "Options";
	List<Color> colors;
	Quoridor Q;
	
	private JLabel PlayersLabel;
	private JRadioButton twoPlayers, fourPlayers;
	private ButtonGroup numberOfPlayers;
	private JPanel upperColorPanel, lowerColorPanel;
	private List<JTextField> reds;
	private List<JTextField> blues;
	private List<JTextField> greens;
	private List<JTextField> showColor;
	private List<JComboBox> boxes;
	private String[] playerTypeNames = {"Local", "AI", "Net"};
	
	
	public OptionsMenu() {
		super(OPTIONS_WINDOW_TITLE);
		Q = new Quoridor();
		colors = new ArrayList<Color>();
		initialize();
		setVisible(true);
	}
	
	public OptionsMenu(Quoridor Qu) {
		super(OPTIONS_WINDOW_TITLE);
		Q = Qu;
		initialize();
		setVisible(true);	
	}
	
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
			color.setName("Color" + i);
			color.setText("P" + i);
			color.setBackground(Q.colors[i]);
			color.setEnabled(false);
			
			JTextField red = new JTextField("" + Q.colors[i].getRed());
			red.setName("Red" + i);
			JTextField green = new JTextField("" + Q.colors[i].getGreen());
			green.setName("Green" + i);
			JTextField blue = new JTextField("" + Q.colors[i].getBlue());
			blue.setName("Blue" + i);
			
			
			red.addActionListener(this);
			green.addActionListener(this);
			blue.addActionListener(this);
			
			JComboBox box = new JComboBox(playerTypeNames);
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
		for (int i = 0; i < Q.players; i++) {
			r = Integer.parseInt(reds.get(i).getText());
			g = Integer.parseInt(greens.get(i).getText());
			b = Integer.parseInt(blues.get(i).getText());
			c = new Color(r, g, b);
			showColor.get(i).setBackground(c);
			Q.colors[i] = c;
		}
		
	}

	public void itemStateChanged(ItemEvent item) {
		if (item.getSource() == twoPlayers) {
			Q.players = 2;
			remove(lowerColorPanel);
			repaint();
			return;
		}
		if (item.getSource() == fourPlayers) {
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