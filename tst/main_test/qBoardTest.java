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
import main.qBoard;

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
	
	@SuppressWarnings("unchecked")
	@After
	public void shutDown(){
		driver.dispose();
	}
	
    @SuppressWarnings("unchecked")
	private JButtonDriver button(String name){
        return new JButtonDriver(driver, JButton.class, ComponentDriver.named(name));
	}
    @SuppressWarnings("unchecked")
    private JLabelDriver label(String name) {
        return new JLabelDriver(driver, ComponentDriver.named(name));
    }
	
    @Test
    public void stubTest() {
        assertEquals(1, 1);
    }
    
    @Test
    public void WindowUpWithTitle() {
		driver.hasTitle(BOARD_WINDOW_TITLE);
    }
    
    @Test
    public void lableColorsWithClick(){
    	Color col;
    	
    	for (int i = 0; i < boardLength; i++) {
			for (int j = 0; j< boardLength ; j++){
				String buttonName = ("M "+i+" "+j);
		    	JButtonDriver bDriver = button(buttonName);
				bDriver.click();
			}
		}
    }
   }
