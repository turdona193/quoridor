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
		int expectedY[] = {9, 0, 5, 5};
		for (int i = 0; i < expectedX.length; i++) {
			pl = new Player(i+1, 4);
			testXAndYValues(expectedX[i], expectedY[i]);
		}
	}
	
	@Test
	public void testDefaultColors() {
		Color expectedColor[] = {Color.blue, Color.red, Color.green, Color.yellow};
		
		for (int i = 0; i < expectedColor.length; i++) {
			pl = new Player(i+1, 4);
			assertThat(expectedColor[i], is(equalTo(pl.getColor())));
		}
		
	}
		
}
