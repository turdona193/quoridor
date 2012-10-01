package player_test;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

import player.Wall;

public class WallTest {
	private Wall wall;
	
	@Before
	public void setUp() {
		wall = new Wall();
	}
	
	@Test
	public void testPlacingAWall() {
		wall.place(1,1, true);
		Point expectedLoc = new Point(1,1);
		assertThat(expectedLoc, is(equalTo(wall.getLocation())));
	}
	
}
