package player_test;

import java.awt.Dimension;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import player.Player;

public class PlayerTest {
	private Player pl;
		
	/*@Before
	  public void initialize() {
	    pl = new Player();
	  }*/  
	
	@Test
	public void testGetAndSetLocation() {
		pl = new Player();
		pl.setLocation(5, 5);
		Dimension d = new Dimension(5, 5);
		assertThat(d, is(equalTo(pl.getLocation())));
		
	}
	
	@Test
	public void testStartingLocationOfP1() {
		pl = new Player(1);
		Dimension d = new Dimension(5, 0);
		assertEquals(d, pl.getLocation());
	}
	
	@Test
	public void testStartingLocationOfP2() {
		pl = new Player(2);
		Dimension d = new Dimension(5, 9);
		assertEquals(d, pl.getLocation());	
	}
	
}
