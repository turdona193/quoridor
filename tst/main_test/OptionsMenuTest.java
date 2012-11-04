package main_test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import main.OptionsMenu;
import main.Quoridor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.objogate.wl.swing.AWTEventQueueProber;
import com.objogate.wl.swing.driver.ComponentDriver;
import com.objogate.wl.swing.driver.JButtonDriver;
import com.objogate.wl.swing.driver.JComboBoxDriver;
import com.objogate.wl.swing.driver.JFrameDriver;
import com.objogate.wl.swing.driver.JRadioButtonDriver;
import com.objogate.wl.swing.driver.JTextFieldDriver;
import com.objogate.wl.swing.gesture.GesturePerformer;

public class OptionsMenuTest {
	private Quoridor Q;
	private final int WAIT_TIME = 20;
	JFrameDriver menuDriver, quorDriver;
	
	@Before
	public void setUp() throws Exception {
		Q = new Quoridor();
		quorDriver = new JFrameDriver(new GesturePerformer(), new AWTEventQueueProber(), JFrameDriver.named(Quoridor.MAIN_WINDOW_TITLE), JFrameDriver.showingOnScreen());
		menuDriver = new JFrameDriver(new GesturePerformer(), new AWTEventQueueProber(), JFrameDriver.named(OptionsMenu.OPTIONS_WINDOW_TITLE), JFrameDriver.showingOnScreen());
		String buttonName = ("Button1");
		JButtonDriver bDriver = qButton(buttonName);
		bDriver.click();
	}
	
	@After
	public void shutDown() {
		menuDriver.dispose();
		quorDriver.dispose();
	}
	
	private JRadioButtonDriver radioButton(String name) {
        return new JRadioButtonDriver(menuDriver, JRadioButton.class, ComponentDriver.named(name));
	}
	
	private JButtonDriver qButton(String name) {
        return new JButtonDriver(quorDriver, JButton.class, ComponentDriver.named(name));
	}
	
	private JButtonDriver mButton(String name) {
        return new JButtonDriver(menuDriver, JButton.class, ComponentDriver.named(name));
	}
	
	private JTextFieldDriver text(String name) {
		return new JTextFieldDriver(menuDriver, JTextField.class, ComponentDriver.named(name));
	}
	
	private JComboBoxDriver combo(String name) {
		return new JComboBoxDriver(menuDriver, JComboBox.class, ComponentDriver.named(name));
	}
	
	@Test
    public void ClickRadioButtons() throws InterruptedException {
		
    	for (int i = 0; i < 2; i++) {
			String buttonName = (OptionsMenu.RADIO_BUTTON_TITLES[i]);
	    	JRadioButtonDriver rbDriver = radioButton(buttonName);
			rbDriver.click();
			Thread.sleep(WAIT_TIME);
			//Tests to see if it equals 2 the first time, and 4 the second time.
			assertThat((i*2+2), is(equalTo(Q.players)));
		}
    }
	
	@Test
	public void TestTextFields() throws InterruptedException {
		click4PButton();
		String textName = (OptionsMenu.RED_TEXT_FIELD_TITLES[0]);
		JTextFieldDriver tfDriver = text(textName);
		tfDriver.focusWithMouse();
		tfDriver.replaceAllText("255");
		tfDriver.pressReturn();
		Thread.sleep(500);
	}
	
	@Test
	public void randomlyChangeTextFields() {
		click4PButton();
		JTextFieldDriver tfDrivers[] = new JTextFieldDriver[12];
		for (int i = 0; i < 4; i++) {
			tfDrivers[i*3] = text(OptionsMenu.RED_TEXT_FIELD_TITLES[i]);
			tfDrivers[i*3+1] = text(OptionsMenu.GREEN_TEXT_FIELD_TITLES[i]);
			tfDrivers[i*3+2] = text(OptionsMenu.BLUE_TEXT_FIELD_TITLES[i]);
		}
		
		for (int i = 0; i < 15; i++) {
			int j = (int)(Math.random()*256);
			int k = (int)(Math.random()*12);
			tfDrivers[k].focusWithMouse();
			tfDrivers[k].replaceAllText("" + j);
			tfDrivers[k].pressReturn();
		}
		
	}
	
	@Test
	public void testComboBox() throws InterruptedException {
		click4PButton();
		String comboName = OptionsMenu.COMBO_BOX_TITLES[3];
		JComboBoxDriver comboDriver = combo(comboName);
		comboDriver.selectItem(1);
		Thread.sleep(100);
	}
	
	private void click4PButton() {
		String buttonName = (OptionsMenu.RADIO_BUTTON_TITLES[1]);
    	JRadioButtonDriver rbDriver = radioButton(buttonName);
		rbDriver.click();
	}
	
}
