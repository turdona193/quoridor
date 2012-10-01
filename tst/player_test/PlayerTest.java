package player_test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

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
		int expectedX[] = {4, 4, 0, 8};
		int expectedY[] = {8, 0, 4, 4};
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
	
	@Test
	public void testUpDownLeftRightWhenNotNextToEdgeOfBoard() {
		pl = new Player();
		pl.setLocation(4,4);
		Point[] expectedLocs = new Point[4];
		initializePointArray(expectedLocs);
		assertThat(expectedLocs[0], is(equalTo(pl.up())));
		assertThat(expectedLocs[1], is(equalTo(pl.down())));
		assertThat(expectedLocs[2], is(equalTo(pl.left())));
		assertThat(expectedLocs[3], is(equalTo(pl.right())));
	}
	
	private void initializePointArray(Point[] p) {
		int expectedX[] = {4, 4, 3, 5};
		int expectedY[] = {3, 5, 4, 4};
		for (int i = 0; i < p.length; i++) 
			p[i] = new Point(expectedX[i], expectedY[i]);
	}
	
	@Test
	public void testDirectionMethodsWhenNextToEdges() {
		Point expectedLoc = new Point(-1,-1);
		pl = new Player();
		pl.setLocation(0,0);
		assertNull(pl.up());
		assertNull(pl.left());
		pl.setLocation(8,8);
		assertNull(pl.down());
		assertNull(pl.right());
	}
	
		
}
