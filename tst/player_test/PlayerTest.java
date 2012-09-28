package player_test;

import java.awt.Color;
import java.awt.Dimension;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import player.Player;

public class PlayerTest {
	private Player pl;
		
	private void testXAndYValues(int expectX, int expectY) {
		assertThat(expectX, is(equalTo(pl.getX())));
		assertThat(expectY, is(equalTo(pl.getY())));	
	}
	
	@Test
	public void testGetAndSetLocation() {
		pl = new Player();
		pl.setLocation(5, 5);
		int expectedX = 5;
		int expectedY = 5;
		testXAndYValues(expectedX, expectedY);
		
	}
	
	@Test
	public void testStartingLocationsOfPlayers() {
		int expectedX[] = {5, 5, 0, 9};
		int expectedY[] = {0, 9, 5, 5};
		for (int i = 0; i < expectedX.length; i++) {
			pl = new Player(i+1, 4);
			testXAndYValues(expectedX[i], expectedY[i]);
		}
	}
	
	@Test
	public void testGetColor() {
		pl = new Player(1, 4);
		Color expectedColor = Color.blue;
		assertThat(expectedColor, is(equalTo(pl.getColor())));
		
		pl = new Player(2, 4);
		expectedColor = Color.red;
		assertThat(expectedColor, is(equalTo(pl.getColor())));
		
		pl = new Player(3, 4);
		expectedColor = Color.green;
		assertThat(expectedColor, is(equalTo(pl.getColor())));
		
		pl = new Player(4, 4);
		expectedColor = Color.yellow;
		assertThat(expectedColor, is(equalTo(pl.getColor())));
		
	}
		
}
