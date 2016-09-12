package control;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import model.Player;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class IOControllerTest {

	private GameController gCtrl = new GameController();
	private IOController ioCtrl = new IOController(gCtrl);;
	
	private int size = 30;
	
	private Player player1 = new Player("Jan");
	private Player player2 = new Player("Patrick");
	private Player player3 = new Player("Lisa");
	private Player player4 = new Player("Jenny");
	private Player player5 = new Player("Doris");
	
	private ArrayList<Player> playerList = new ArrayList<Player>();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		playerList.add(player1);
		playerList.add(player2);
		playerList.add(player3);
		playerList.add(player4);
		
		gCtrl.newGame(size, true, playerList, "");
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Zunächst wird Speichern beim IOController aufgerufen und geprüft ob im aktuellen Qwirkle der 
	 * player1 vorhanden ist. Anschließend wird das Qwirkleobjekt auf null gesetzt und geprüft ob 
	 * das erfolgreich war. Dann laden wir das zuvorgespeicherte Objekt mit load und prüfen ob 
	 * player1 wieder wie zu Beginn vorhanden war. Zuletzt wird noch geprüft ob es auch funktioniert 
	 * wenn man wieder ein neues Spiel speichert und lädt.
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * 
	 * @precondition Ein Qwirkleobjekt wurde erstellt
	 * @postcondition Alles ist wir erwartet Ausgeführt worden
	 */
	@Test
	public void testSaveAndLoad() throws ClassNotFoundException, IOException {
		ioCtrl.save();
		assertTrue(gCtrl.getQwirkle().getPlayerList().get(1).getName().equals("Patrick"));
		gCtrl.setQwirkle(null);
		assertTrue(gCtrl.getQwirkle() == null);
		ioCtrl.load();
		assertTrue(gCtrl.getQwirkle().getPlayerList().get(1).getName().equals("Patrick"));
		playerList.add(player5);
		gCtrl.newGame(size, true, playerList, "");
		assertTrue(gCtrl.getQwirkle().getPlayerList().get(4).getName().equals("Doris"));
		ioCtrl.load();
		assertTrue(gCtrl.getQwirkle().getPlayerList().size() == 4);
		playerList.add(player5);
		gCtrl.newGame(size, true, playerList, "");
		ioCtrl.save();
		assertTrue(gCtrl.getQwirkle().getPlayerList().get(4).getName().equals("Doris"));
	}

}
