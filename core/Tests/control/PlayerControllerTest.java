package control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import model.Bag;
import model.Cube;
import model.Map;
import model.Player;
import model.Position;
import model.Qwirkle;
import model.Stone;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PlayerControllerTest {
	private PlayerController playerCtrl;
	private GameController gameCtrl = new GameController();
	private Player player;
	private Qwirkle qwirkle;
	private ArrayList<Player> list = new ArrayList<Player>();
	private Map map = new Map();
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
//		map = new Map();
//		player = new Player("Sopra");
//		list.add(player);
//		list.add(new Player("Bla"));
//		gameCtrl.newGame(36, true, list);
//		qwirkle = new Qwirkle(true, new Bag(36, true), list);
//		qwirkle.setCurrentPlayer(player);
//		playerCtrl = new PlayerController(gameCtrl);
	}

	/**
	 * Anlegen der benötigten Klassen um PlayerController zu testen
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		map = new Map();
		player = new Player("Sopra");
		list.add(player);
		//list.add(new Player("Bla"));
		list.add(new Player("Robert"));
		list.add(new Player("Jenny"));
		list.add(new Player("Phil"));
		gameCtrl.newGame(30, true, list, "");
		qwirkle = new Qwirkle(true, new Bag(30, true), list);
		qwirkle.setCurrentPlayer(player);
		playerCtrl = new PlayerController(gameCtrl);
		//System.out.println(qwirkle.getCurrentPlayer().getName()); // kann nicht ausgegeben werden. Irgendweo werden die Player nicht richtig gesetzt
	}

	/**
	 * Testet den Kontruktor: Stein-Liste des Spielers soll nicht null und nicht leer sein
	 * Spieler bekommt zu Anfang direkt 6 Steine
	 */
	@Test
	public void testPlayerController() {
		assertNotNull(player.getStones());
		assertTrue(!player.getStones().isEmpty());
		
		for (Stone stone : player.getStones()) {
			System.out.println(stone.getSymbol() + " " + stone.getColor());
		}
	}

	/**
	 * Testet würfeln eines Würfels: Farbe des Würfels darf sich nicht verändern, Symbol kann verschieden sein 
	 * (zum Test ist Ausgabe implementiert). 
	 * 
	 * @preconditions Ausgewählter Würfel darf noch nicht gewürfelt worden sein
	 * @postconditions Attribut rolled von Würfel muss auf true gesetzt worden sein
	 * 				Symbol kann sich verämdert haben, Farbe muss gleich geblieben sein
	 * 				Anzahl der Würfel des Spielers muss gleich bleiben 
	 */
	@Test
	public void testRollCube() {
		ArrayList<Stone> stones = player.getStones();
		Cube cube = (Cube) stones.get(0);
		int amount = stones.size();
		System.out.println(amount);
		String color = stones.get(0).getColor();
		String symbol = stones.get(0).getSymbol();
		System.out.println(symbol);
		
		//Vorbedingung
		assertFalse(cube.isRolled());
		
		playerCtrl.rollCube(0);
		
		//Nachbedingung
		stones = player.getStones();
		cube = (Cube) stones.get(0);
		System.out.println(stones.get(0).getSymbol());
		
		assertEquals(color, stones.get(0).getColor());
		assertEquals(amount, stones.size());
		assertTrue(cube.isRolled());
	}

	/**
	 * Testet das Platzieren eines Steines: Ein Stein wird auf das Spielfeld gelegt, Spielzug ist aber noch nicht beendet
	 * 
	 * @postconditions die Anzahl der Steine des Spielers ist um ein geringer als vorher
	 * 					wenn Spielstein legen geklappt hat, wird der Stein dem Inventar des Spielers abgezogen
	 * 					wenn nicht, hat Spieler gleiche Anzahl an Steinen
	 */
	@Test
	public void testPlaceCube() {
		Player currentPlayer = qwirkle.getCurrentPlayer();
		int amountBefore = currentPlayer.getStones().size();
		System.out.println(qwirkle.getMap().getPlacedStones().size());
		System.out.println(qwirkle.getMap().getNewAddedStones().size());
		System.out.println(amountBefore);
		
		boolean condition = playerCtrl.placeCube(1, new Position(0, 0));
		
		System.out.println(qwirkle.getMap().getPlacedStones().size());
		System.out.println(qwirkle.getMap().getNewAddedStones().size());
		System.out.println(currentPlayer.getStones().size());
		assertTrue(condition);	
		if( condition ) {
			assertNotEquals(amountBefore, currentPlayer.getStones().size());
			System.out.println("Spielzug ist spielbar");
		} else {
			System.out.println("Spielzug hat nicht geklappt!");
		}
	}

	/**
	 * Testet einenn Zug zurücknehmen: Noch nciht implementiert
	 */
	@Test
	public void testUndoTurn() {
		
		Player player = qwirkle.getCurrentPlayer();
		System.out.println(player.getName());
		int map = qwirkle.getMap().getPlacedStones().size();
		System.out.println(map);
		System.out.println(qwirkle.getMap().getNewAddedStones().size());
		ArrayList<Stone> currentStones = player.getStones();
		int size = currentStones.size();
		for (Stone stone : currentStones) {
			System.out.println(stone.getSymbol() + " " + stone.getColor());
		}
		System.out.println(size);
		playerCtrl.placeCube(0, new Position(0, 0));
		System.out.println(qwirkle.getMap().getNewAddedStones().size());
		
		assertNotEquals(size, player.getStones().size());
		playerCtrl.endTurn();
		System.out.println(gameCtrl.getQwirkle().getCurrentPlayer().getName());
		System.out.println(qwirkle.getMap().getPlacedStones().size());
		
		playerCtrl.undoTurn(player);
		ArrayList<Stone> actualStones = gameCtrl.getQwirkle().getCurrentPlayer().getStones();
		
		for (Stone stone : actualStones) {
			System.out.println(stone.getSymbol() + " " + stone.getColor());
		}
		assertEquals(size, actualStones.size());
		System.out.println(qwirkle.getCurrentPlayer().getName());
		
		
	}

	/**
	 * Testet Zug beenden: Beendet den Zug eines Spielers und der nächste darf Steine legen
	 * 
	 * @postconditions currentPlayer muss weiter gesetzt sein -> darf nicht gleich dem vorherigen Spieler sein
	 * 					Die Steinanzahl des vorherigen Spielers muss wieder 6 sein
	 */
	@Test
	public void testEndTurn() {
		Player currentPlayer = qwirkle.getCurrentPlayer();
		int currentScore = currentPlayer.getScore();
		System.out.println(currentPlayer.getName());
		System.out.println(currentPlayer.getStones().size());
		playerCtrl.placeCube(0, new Position(0,0));
		playerCtrl.placeCube(2, new Position(0,1));
		
		System.out.println(currentPlayer.getStones().size());
		playerCtrl.endTurn();
	
		System.out.println(currentPlayer.getStones().size());
		
		Player actualPlayer = gameCtrl.getQwirkle().getCurrentPlayer(); 
		
		assertNotEquals(currentPlayer.getName(), actualPlayer.getName());
		assertEquals(6, currentPlayer.getStones().size());	
		assertNotEquals(currentScore, currentPlayer.getScore());
	}

	/**
	 * Testet tauschen von Steinen: wenn Steine getauscht werden, ist der Zug beendet
	 * 
	 * @postconditions Farbe und Symbol der Steine können sich verändert haben (zum Test ist Ausgabe implementiert).
	 * 					Anzahl der Steine des Spielers muss gleich bleiben
	 */
	@Test
	public void testSwapStones() {
		ArrayList<Stone> stones = player.getStones();
		int[] index = {0,2};
		int amount = stones.size();
		System.out.println(amount);
		System.out.println(stones.get(0).getSymbol()+ " " +stones.get(0).getColor());
		System.out.println(stones.get(2).getSymbol()+ " " +stones.get(2).getColor()+ "\n");
		
		playerCtrl.swapStones(index);
		
		System.out.println(stones.get(0).getSymbol()+ " " +stones.get(0).getColor());
		System.out.println(stones.get(2).getSymbol()+ " " +stones.get(2).getColor());
		
		//Nachbedingung
		assertEquals(amount, stones.size());
	}

	/**
	 * Testet Tipp geben: gibt bisher eine zufällige Position wieder
	 * 
	 * @preconditions Spieler ist kein Cheater
	 * @postconditions Spieler wird als Cheater gesetzt und Tipp darf nicht leer sein
	 * 					angegebene Position muss spielbar sein
	 */
	@Test
	public void testShowHint() {
		try {
			for (Stone stone : player.getStones()) {
				System.out.println(stone.getSymbol() + " " + stone.getColor());
			}
			assertFalse(player.isCheater());
			
			String hint = playerCtrl.showHint();
			String[] actualHint = hint.split(" ");
			System.out.println("\n" + hint);
			
			assertNotNull(hint);
			assertTrue(player.isCheater());
		
			int x = Integer.parseInt(actualHint[2]);
			int y = Integer.parseInt(actualHint[3]);
					
			
			Position realPosition = map.getPosition(x, y);
			Position realPosition2 = map.getPosition(1, 1);
			assertFalse(map.isPlayableThisTurn(realPosition2));
			assertTrue(map.isPlayableThisTurn(realPosition));
			
		}catch (NumberFormatException e) {
			System.out.println("Parse-Error!");
		}
	}

	/**
	 * Testet das Nachziehen von Steinen aus dem Bag: 
	 * 
	 * @preconditions Spieler hat Steine abgelegt und besitzt weniger als 6 Steine,
	 * 					wenn nicht, werden keine Steine nachgezogen
	 * @postconditions Spieler hat wieder 6 Steine
	 */
	@Test
	public void testDrawStones() {
		ArrayList<Stone> stones = player.getStones();
		int amount = stones.size();
		System.out.println("Anzahl vor legen: " + amount);
		//qwirkle.getBag().setBagStones(new ArrayList<Stone>());
		playerCtrl.placeCube(0, new Position(0, 0));
		System.out.println("Anzahl nach legen: " + qwirkle.getCurrentPlayer().getStones().size());
		System.out.println(qwirkle.getBag().getAmount());
		
		playerCtrl.drawStones(player);
		
		if (qwirkle.getCurrentPlayer().getStones().size() < 6) {
			System.out.println("false " + qwirkle.getCurrentPlayer().getStones().size());
			assertNotEquals(amount, player.getStones().size());
		} else {
			System.out.println("true " + qwirkle.getCurrentPlayer().getStones().size());
			assertEquals(amount, player.getStones().size());
		}	
	}
	
	@Test
	public void testDoTurn() {
		Cube cube1 = (Cube) player.getStones().get(0);
		//Cube cube2 = (Cube) player.getStones().get(2);
		String turn = "# 0 0 " + cube1.getSymbol() + " " + cube1.getColor();/* + "|1 0 " +cube2.getSymbol() + " " + cube2.getColor();*/
		playerCtrl.doTurn(turn);
		map = gameCtrl.getQwirkle().getMap();
		System.out.println(map.getPlacedStones().size());
		assertTrue(!map.getPlacedStones().isEmpty());
	}
}
