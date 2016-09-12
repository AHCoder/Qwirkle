package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class CubeTest {

	private Cube cube1;

	@Before
	public void setUp() throws Exception {
		cube1 = new Cube("St", "gr");
		
	}

	@Test
	public void testCube() {
		assertEquals("St", cube1.getSymbol());
		assertEquals("gr", cube1.getColor());
	}

	@Test
	public void testRoll() {
		/*System.out.println(cube1.getSymbol() + " " + cube1.getColor() + " " + cube1.isRolled());
		cube1.roll();
		System.out.println(cube1.getSymbol() + " " + cube1.getColor() + " " + cube1.isRolled());
		cube1.roll();
		System.out.println(cube1.getSymbol() + " " + cube1.getColor() + " " + cube1.isRolled());*/
		String colorBefore = cube1.getColor();
		cube1.roll();
		assertTrue(cube1.isRolled());
		assertEquals(colorBefore, cube1.getColor());
	}

	@Test
	public void testIsRolled() {
		cube1.setRolled(true);
		assertTrue(cube1.isRolled());
		cube1.setRolled(false);
		assertFalse(cube1.isRolled());
	}

	@Test
	public void testSetRolled() {
		cube1.setRolled(true);
		assertTrue(cube1.isRolled());
		cube1.setRolled(false);
		assertFalse(cube1.isRolled());
	}

}
