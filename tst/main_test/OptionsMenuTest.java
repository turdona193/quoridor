package main_test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import javax.swing.JButton;
import javax.swing.JRadioButton;

import main.OptionsMenu;
import main.Quoridor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.objogate.wl.swing.AWTEventQueueProber;
import com.objogate.wl.swing.driver.ComponentDriver;
import com.objogate.wl.swing.driver.JButtonDriver;
import com.objogate.wl.swing.driver.JFrameDriver;
import com.objogate.wl.swing.driver.JRadioButtonDriver;
import com.objogate.wl.swing.gesture.GesturePerformer;

public class OptionsMenuTest {
	private Quoridor Q;
	JFrameDriver menuDriver, quorDriver;
	
	@Before
	public void setUp() throws Exception {
		Q = new Quoridor();
		quorDriver = new JFrameDriver(new GesturePerformer(), new AWTEventQueueProber(), JFrameDriver.named(Quoridor.MAIN_WINDOW_TITLE), JFrameDriver.showingOnScreen());
		menuDriver = new JFrameDriver(new GesturePerformer(), new AWTEventQueueProber(), JFrameDriver.named(OptionsMenu.OPTIONS_WINDOW_TITLE), JFrameDriver.showingOnScreen());
	}
	
	@After
	public void shutDown(){
		menuDriver.dispose();
	}
	
	private JRadioButtonDriver radioButton(String name){
        return new JRadioButtonDriver(menuDriver, JRadioButton.class, ComponentDriver.named(name));
	}
	
	private JButtonDriver button(String name){
        return new JButtonDriver(quorDriver, JButton.class, ComponentDriver.named(name));
	}
	
	@Test
    public void ClickRadioButtons() throws InterruptedException{
		String buttonName = ("Button1");
		JButtonDriver bDriver = button(buttonName);
		bDriver.click();
		
    	for (int i = 0; i < 2; i++) {
			buttonName = (OptionsMenu.RADIO_BUTTON_TITLES[i]);
	    	JRadioButtonDriver rbDriver = radioButton(buttonName);
			rbDriver.click();
			//Tests to see if it equals 2 the first time, and 4 the second time.
			Thread.sleep(20);
			assertThat((i*2+2), is(equalTo(Q.players)));
		}
    }
}
