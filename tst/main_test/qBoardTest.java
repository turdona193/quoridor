package main_test;

import java.awt.Color;

import main.*;

import static org.junit.Assert.*;
import static org.hamcrest.text.IsEqualIgnoringWhiteSpace.equalToIgnoringWhiteSpace;
import static org.hamcrest.Matchers.*;
import static main.Quoridor.*;
import static main.qBoard.*;

import javax.swing.JButton;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.Quoridor;

import com.objogate.wl.swing.AWTEventQueueProber;
import com.objogate.wl.swing.driver.ComponentDriver;
import com.objogate.wl.swing.driver.JButtonDriver;
import com.objogate.wl.swing.driver.JFrameDriver;
import com.objogate.wl.swing.driver.JLabelDriver;
import com.objogate.wl.swing.gesture.GesturePerformer;

import org.junit.Test;

import com.objogate.wl.swing.*;

import main_test.QuoridorTest;

public class qBoardTest {

	JFrameDriver driver;
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		qBoard board = new qBoard(); // I can call main if I want
		driver = new JFrameDriver(new GesturePerformer(), new AWTEventQueueProber(), JFrameDriver.named(BOARD_WINDOW_TITLE), JFrameDriver.showingOnScreen());
	}
	
	//private JButton
	
    @Test
    public void stubTest() {
        assertEquals(1, 1);
    }
    
    public void WindowUpWithTitle() {
        assertEquals(1, 1);
    }
    
    public void lableColorsWithClick(){
    	JButtonDriver bDriver = JButton;
    	
    	for (int i = 0; i < board.length; i++) {
			for (int j = 0; j< board[i].length ; j++) // fixed, Nick forgot to increment j initially
			if (action.getSource() == board[j][i]) {
				col = new Color(i*20, i*j, j*20);
				board[j][i].setBackground(col);
				break;
			}
    	
    }
}
