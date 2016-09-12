package model;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Test;

import exception.IsEmptyException;

public class QwirkleTest {
	private Player player1 = new Player("Player1");
	private Player player2 = new Player("Player2");
	private Player player3 = new Player("Player3");
	private Player player4 = new Player("Player4");
	private Player player5 = new Player("Player5");
	private Bot bot = new Bot("Bot", "Low");
	private ArrayList<Player> playerList = new ArrayList<Player>();
	private Bag bag = new Bag(30, true);
	
	
	@Test
	public void testNextPlayer() {
		playerList.add(player1);
		playerList.add(player2);
		playerList.add(player3);
		playerList.add(player4);
		playerList.add(player5);
		Qwirkle qwirkle = new Qwirkle(true, bag, playerList);
		qwirkle.setCurrentPlayer(playerList.get(0));
		assertEquals(player1.getName(), qwirkle.getCurrentPlayer().getName());
		qwirkle.nextPlayer();
		assertEquals(player2.getName(), qwirkle.getCurrentPlayer().getName());
		qwirkle.nextPlayer();
		assertEquals(player3.getName(), qwirkle.getCurrentPlayer().getName());
		qwirkle.nextPlayer();
		assertEquals(player4.getName(), qwirkle.getCurrentPlayer().getName());
		qwirkle.nextPlayer();
		assertEquals(player5.getName(), qwirkle.getCurrentPlayer().getName());
		qwirkle.nextPlayer();
		assertEquals(player1.getName(), qwirkle.getCurrentPlayer().getName());
		
		playerList.clear();
		playerList.add(player1);
		playerList.add(player2);
		playerList.add(bot);
		try{
			qwirkle.setPlayerList(playerList);
		}
		catch(IsEmptyException ise){
			System.out.println("Zu wenig Spieler");
		}
		qwirkle.setCurrentPlayer(playerList.get(0));
		assertEquals(player1.getName(), qwirkle.getCurrentPlayer().getName());
		qwirkle.nextPlayer();
		assertEquals(player2.getName(), qwirkle.getCurrentPlayer().getName());
		qwirkle.nextPlayer();
		assertEquals(bot.getName(), qwirkle.getCurrentPlayer().getName());
	}

}
