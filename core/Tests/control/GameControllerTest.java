package control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import model.Bot;
import model.Player;

import org.junit.Test;

public class GameControllerTest {
	
	private GameController gameCtrl = new GameController();
	
	private int size = 30;
	
	private Player player1 = new Player("Player1");
	private Player player2 = new Player("Player2");
	private Player player3 = new Player("Player3");
	private Player player4 = new Player("Player4");
	private Player player5 = new Player("Player5");
	private Player player6 = new Player("Player6");
	private Player player7 = new Player("Player7");
	private Player player8 = new Player("Player8");
	private Player player9 = new Player("Player9");
	private Player player10 = new Player("Player10");
	
	private Bot bot1 = new Bot("Bot1", "hard");
	private Bot bot2 = new Bot("Bot2", "medium");
	private Bot bot3 = new Bot("Bot3", "low");
	
	@Test
	public void testNewGame(){
		ArrayList<Player> list = new ArrayList<Player>();
		
		list.add(player1);
		list.add(player2);
		list.add(player3);
		list.add(player4);
		list.add(player5);
		
		gameCtrl.newGame(size, true, list, "");
		assertNotNull(gameCtrl.getQwirkle());
		assertNotNull(gameCtrl.getQwirkle().getBag());
		assertNotNull(gameCtrl.getQwirkle().getMap());
		assertTrue(gameCtrl.getQwirkle().getBag().getSize() == size);
		assertEquals(list.get(0).getName(),gameCtrl.getQwirkle().getPlayerList().get(0).getName());
		assertEquals(list.get(1).getName(),gameCtrl.getQwirkle().getPlayerList().get(1).getName());
		assertEquals(list.get(2).getName(),gameCtrl.getQwirkle().getPlayerList().get(2).getName());
		assertEquals(list.get(3).getName(),gameCtrl.getQwirkle().getPlayerList().get(3).getName());
		assertEquals(list.get(4).getName(),gameCtrl.getQwirkle().getPlayerList().get(4).getName());
		assertEquals(list.get(0),gameCtrl.getQwirkle().getCurrentPlayer());
		assertTrue(gameCtrl.getQwirkle().isCubesVariant());
		
		list.remove(4);
		list.remove(3);
		list.remove(2);
		
		gameCtrl.newGame(size, true, list, "");
		assertNotNull(gameCtrl.getQwirkle());
		assertNotNull(gameCtrl.getQwirkle().getBag());
		assertNotNull(gameCtrl.getQwirkle().getMap());
		assertTrue(gameCtrl.getQwirkle().getBag().getSize() == size);
		assertEquals(list.get(0).getName(),gameCtrl.getQwirkle().getPlayerList().get(0).getName());
		assertEquals(list.get(1).getName(),gameCtrl.getQwirkle().getPlayerList().get(1).getName());
		assertEquals(list.get(0),gameCtrl.getQwirkle().getCurrentPlayer());
		assertTrue(gameCtrl.getQwirkle().isCubesVariant());

		
		list.add(bot1);
		list.add(bot2);
		list.add(bot3);
		
		gameCtrl.newGame(size, true, list, "");
		assertNotNull(gameCtrl.getQwirkle());
		assertNotNull(gameCtrl.getQwirkle().getBag());
		assertNotNull(gameCtrl.getQwirkle().getMap());
		assertTrue(gameCtrl.getQwirkle().getBag().getSize() == size);
		assertEquals(list.get(0).getName(),gameCtrl.getQwirkle().getPlayerList().get(0).getName());
		assertEquals(list.get(1).getName(),gameCtrl.getQwirkle().getPlayerList().get(1).getName());
		assertEquals(list.get(2).getName(),gameCtrl.getQwirkle().getPlayerList().get(2).getName());
		assertEquals(list.get(3).getName(),gameCtrl.getQwirkle().getPlayerList().get(3).getName());
		assertEquals(list.get(4).getName(),gameCtrl.getQwirkle().getPlayerList().get(4).getName());
		assertEquals(list.get(0),gameCtrl.getQwirkle().getCurrentPlayer());
		assertTrue(gameCtrl.getQwirkle().isCubesVariant());
	}
	
	@Test
	public void testAddToHighscore() {
		gameCtrl.setBagSize(size);
		
		player1.setScore(10);
		player2.setScore(20);
		player3.setScore(30);
		player4.setScore(40);
		player5.setScore(50);
		player6.setScore(60);
		player7.setScore(70);
		player8.setScore(80);
		player9.setScore(90);
		player10.setScore(100);
		
		gameCtrl.addToHighscore(player1);
		assertEquals(player1.getName(),gameCtrl.getHighscore(size).get(gameCtrl.getHighscore(size).size()-1).getName());
		gameCtrl.addToHighscore(player2);
		assertEquals(player2.getName(),gameCtrl.getHighscore(size).get(gameCtrl.getHighscore(size).size()-1).getName());
		gameCtrl.addToHighscore(player3);
		assertEquals(player3.getName(),gameCtrl.getHighscore(size).get(gameCtrl.getHighscore(size).size()-1).getName());
		gameCtrl.addToHighscore(player4);
		assertEquals(player4.getName(),gameCtrl.getHighscore(size).get(gameCtrl.getHighscore(size).size()-1).getName());
		gameCtrl.addToHighscore(player5);
		assertEquals(player5.getName(),gameCtrl.getHighscore(size).get(gameCtrl.getHighscore(size).size()-1).getName());
		gameCtrl.addToHighscore(player6);
		assertEquals(player6.getName(),gameCtrl.getHighscore(size).get(gameCtrl.getHighscore(size).size()-1).getName());
		gameCtrl.addToHighscore(player7);
		assertEquals(player7.getName(),gameCtrl.getHighscore(size).get(gameCtrl.getHighscore(size).size()-1).getName());
		gameCtrl.addToHighscore(player8);
		assertEquals(player8.getName(),gameCtrl.getHighscore(size).get(gameCtrl.getHighscore(size).size()-1).getName());
		gameCtrl.addToHighscore(player9);
		assertEquals(player9.getName(),gameCtrl.getHighscore(size).get(gameCtrl.getHighscore(size).size()-1).getName());
		gameCtrl.addToHighscore(player10);
		assertEquals(player10.getName(),gameCtrl.getHighscore(size).get(gameCtrl.getHighscore(size).size()-1).getName());
		
		Player winner1 = new Player("Winner1");
		winner1.setScore(55);
		gameCtrl.setWinner(winner1);
		gameCtrl.addToHighscore(winner1);
		assertEquals(winner1.getName(),gameCtrl.getHighscore(size).get(4).getName());
		
		Player winner2 = new Player("Winner2");
		winner2.setScore(100);
		gameCtrl.setWinner(winner2);
		gameCtrl.addToHighscore(winner2);
		assertEquals(winner2.getName(),gameCtrl.getHighscore(size).get(8).getName());
		
		Player winner3 = new Player("Winner3");
		winner3.setScore(50);
		gameCtrl.setWinner(winner3);
		gameCtrl.addToHighscore(winner3);
		assertEquals(winner3.getName(),gameCtrl.getHighscore(size).get(1).getName());
		
		Player winner4 = new Player("Winner3");
		winner3.setScore(1);
		gameCtrl.setWinner(winner3);
		gameCtrl.addToHighscore(winner3);
		assertTrue(winner4.getScore() != gameCtrl.getHighscore(size).get(0).getScore());
	}
}

