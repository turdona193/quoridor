package player_test;

import java.awt.Color;
import java.awt.Point;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import player.Player;

public class PlayerTest {
	private Player pl;
	private Player[] players;
		
	@Before
	public void initialize(){
		players = new Player[4];
		for (int i = 0; i < 4; i++) {
			players[i] = new Player(i, 5, Player.color[i], 0);
		}
	}
	
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
		for (int i = 0; i < Player.X.length; i++) {
			pl = players[i];
			testXAndYValues(Player.X[i], Player.Y[i]);
		}
	}
	
	@Test
	public void testDefaultColors() {
		for (int i = 0; i < Player.color.length; i++) {
			pl = players[i];
			assertThat(Player.color[i], is(equalTo(pl.getColor())));
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
		pl = new Player();
		pl.setLocation(0,0);
		assertNull(pl.up());
		assertNull(pl.left());
		pl.setLocation(8,8);
		assertNull(pl.down());
		assertNull(pl.right());
	}
	
	@Test
	public void testGoalLine() {
		pl = new Player(0, 10, Color.green, 0);
		boolean expectedResult = false;
		assertThat(expectedResult, is(equalTo(pl.hasWon())));
		for (int i = 0; i < 9; i++) {
			pl.setLocation(i, 0);
			expectedResult = true;
			assertThat(expectedResult, is(equalTo(pl.hasWon())));
		}

	}
	
	@Test
	public void testWalls() {
		pl = new Player(0, 2, Color.blue, 0);
		assertTrue(pl.decrementWall());
		assertTrue(pl.decrementWall());
		assertFalse(pl.decrementWall());
		assertThat(0, is(equalTo(pl.getWalls())));
	}
	
		
}
