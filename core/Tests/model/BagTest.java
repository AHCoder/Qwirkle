package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.ArrayList;

public class BagTest {
	Bag bag;
	Bag bag1;
	Stone stone1;
	Stone stone2;	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		bag = new Bag(30, true);
		stone1 = new Cube("St","gr");
		stone2 = new Cube("Ra","bl");
	}

	@Test
	public void testBagInt() {
		
		assertNotNull(bag.getBagStones());
		assertFalse(bag.getBagStones().isEmpty());
		
		//assertEquals(bag.getAmount(), bag.getBagStones().size());
		//System.out.println("bag:" + bag.getBagStones().get(0).toString());
		for (Stone stone : bag.getBagStones()) {
			System.out.println(stone.getSymbol() + " " + stone.getColor());
		}
		
	}

	@Test
	public void testBagArrayListOfStone() {
		String bag = "* St ro|Kr or|So ge|St bl|Bl li|Qu ro|Ra gr";
		bag1 = new Bag(bag);
		System.out.println(bag1.getAmount());
		assertEquals(7, bag1.getAmount());
	}

	@Test
	public void testSwapStones() {
		int before = bag.getAmount();
		int length = bag.getBagStones().size();
		ArrayList<Stone> swap = new ArrayList<Stone>();
		swap.add(stone1);
		swap.add(stone2);
		bag.swapStones(swap);
		
		assertEquals(before, bag.getAmount());
		assertEquals(length, bag.getBagStones().size());
	}

	@Test
	public void testAddStones() {
		int before = bag.getAmount();
		int length = bag.getBagStones().size();
		ArrayList<Stone> addedStones = new ArrayList<Stone>();
		addedStones.add(stone1);
		addedStones.add(stone2);
		bag.addStones(addedStones);
		
		assertNotEquals(before,bag.getAmount());
		assertNotEquals(length,bag.getBagStones().size());
	}

	@Test
	public void testRemoveStones() {
		
		int before = bag.getAmount();
		int length = bag.getBagStones().size();
		bag.removeFirstStones(1);
		
		assertNotEquals(before,bag.getAmount());
		assertNotEquals(length,bag.getBagStones().size());
		}

	@Test
	public void testDrawStone() {
		int before = bag.getAmount();
		int length = bag.getBagStones().size();
		Cube cube = bag.drawStone();
		assertNotEquals(null, cube);
		System.out.println(before);
		System.out.println(length);
		
		assertNotEquals(before, bag.getAmount());
		assertNotEquals(length, bag.getBagStones().size());
		
	}

}
