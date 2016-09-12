package control;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import model.Bag;
import model.Bot;
import model.Map;
import model.Player;
import model.Qwirkle;
import model.QwirkleStack;

public class GameController {
	
	private Qwirkle qwirkle;
	private PlayerController playerCtrl;
	private IOController ioCtrl;
	private Player winner = null;
	private Player currentPlayer;
	private BotController botCtrl;
	private int size;
	private QwirkleStack qStack;
	private HighscoreList highscore;
	
	public GameController() {
		
		qwirkle = new Qwirkle(false, new Bag(0, false), new ArrayList<Player>());
		playerCtrl = new PlayerController(this);
		ioCtrl = new IOController(this);
		highscore = new HighscoreList();
		qStack = new QwirkleStack(qwirkle,this);
		botCtrl = new BotController(this);
	}

	/**
	 * @author Alexander
	 * @param bagSize
	 *            Anzahl der Stones/Cubes, groesse des Bag
	 * @param cubesVariant
	 *            falls true wird die Cubesvariante gespielt, sonst das
	 *            originale Qwirkle
	 * @param players
	 *            Eine Liste der am Spiel teilnehmeneden Player
	 * @postconditions ein neues Spiel mit den gewaehlten Optionen wird
	 *                 gestartet
	 */
	public void newGame(int bagSize, boolean cubesVariant,ArrayList<Player> players, String bagText) {
			size = bagSize;
			if(bagText.equals("")) {
				qwirkle = new Qwirkle(cubesVariant, new Bag(bagSize, cubesVariant), players);
				System.out.println("normal newgame");
			}else{
				qwirkle = new Qwirkle(cubesVariant, new Bag(bagText), players);
				System.out.println("bag eingelesen");
			}
			qwirkle.setMap(new Map());
			qwirkle.setCurrentPlayer(players.get(0));		//currentPlayer von qwirkle erstmal gesetzt, bevor er abgefragt werden kann
			currentPlayer = qwirkle.getCurrentPlayer();
			for (int i = 0; i < players.size(); i++) {
				playerCtrl.drawStones(players.get(i));
			}
			if (currentPlayer.isBot()) {
				botCtrl.playTurn((Bot)currentPlayer);
			}
			ioCtrl.save();
			qStack.push();
	}

	public void nextPlayer() {
		currentPlayer = qwirkle.nextPlayer();
		if (currentPlayer.isBot() == true) {
			System.out.println("KI ist an der Reihe!");
			botCtrl.playTurn((Bot)currentPlayer);
		}
	}

	/**
	 * @return die aktuelle HighscoreList als String, "||" trennt einzelne
	 *         Attribute, "//" trennt Spieler
	 */
	public String showHighscore() {
		ArrayList<Player> needed = highscore.getHighscore(size);
		String highScore = "";
		int k = 0;
		for (int i = 0; i < needed.size() - 1; i++) {
			highScore = highScore + "Spieler " + i + "|| Name: "
					+ needed.get(i).getName() + "|| Score: "
					+ needed.get(i).getScore() + " //";
			k = i;
		}
		if (k < 10) {
			k = 10 - k;
			highScore = highScore + "Noch Platz fuer " + k + " Spieler.";
		}
		return highScore;
	}

	/**
	 * der Gewinner der letzten Party wird der entsprechenden HighscoreListe
	 * hinzugefuegt
	 * 
	 * @param winner
	 *            der Gewinner der letzten Partie
	 */
	public void addToHighscore(Player winner) {
		highscore.addPlayer(winner, size);
		saveHighscore();
	}

	public Qwirkle getQwirkle() {
		return qwirkle;
	}

	public void setQwirkle(Qwirkle qwirkle) {
		this.qwirkle = qwirkle;
		size=this.qwirkle.getBag().getSize();
	}

	public QwirkleStack getQwirkleStack() {
		return qStack;
	}

	public void setQwirkleStack(QwirkleStack qStack) {
		this.qStack = qStack;
	}

	public PlayerController getPlayerController() {
		return playerCtrl;
	}

	public BotController getBotController() {
		return botCtrl;
	}
	public void setPlayerController(PlayerController playerController) {
		playerCtrl = playerController;
	}

	public IOController getIoController() {
		return ioCtrl;
	}

	public void setIoController(IOController ioController) {
		ioCtrl = ioController;
	}

	/**
	 * @param players
	 *            the new HighscoreList
	 */
	public void setHighscore(ArrayList<Player> players) {
		highscore.setHighscore(players, size);
	}

	/**
	 * @param player
	 *            winner of the game
	 */
	public void setWinner(Player player) {
		winner = player;
	}

	/**
	 * @return winner of the game
	 */
	public Player getWinner() {
		return winner;
	}

	public ArrayList<Player> getHighscore(int bagSize) {
		return highscore.getHighscore(bagSize);
	}
	
	public HighscoreList getHighscoreList(){
		return highscore;
	}

	public void setHighscoreList(HighscoreList highscore) {
		this.highscore = highscore;
	}

	/**
	 * Lädt die aktuelle HighscoreList aus der Datei "highscor"
	 * 
	 * @precondition HighscoreList muss zuvor mindestens einmal gespeichert werden
	 * @postcondition das zuletzt abgespeicherte HighscoreListobjekt wurde geladen
	 * @return Exception, falls das Laden nicht möglich war
	 * 
	 */
	public void loadHighscore() throws IOException {
		try {
			ObjectInputStream stream = new ObjectInputStream(new FileInputStream("highscore"));
			this.setHighscoreList((HighscoreList) stream.readObject());
			stream.close();
		} catch (ClassNotFoundException cnfex) {
			System.err
					.println("Die Klasse des geladenen Objekts konnte nicht gefunden werden.");
		} /*catch (IOException ioex) {
			System.err.println("Das Objekt konnte nicht geladen werden.");
			ioex.printStackTrace();
		}*/
	}

	/**
	 * Speichert die aktuelle HighscoreList
	 * 
	 * @postcondition HighscoreList wurde mit allen Objekten in eine Datei "highscore"
	 *                abgespeichert
	 * @return Exception, falls das Speichern nicht möglich war
	 * 
	 */
	public void saveHighscore() {
		try {
			ObjectOutputStream stream = new ObjectOutputStream(
					new FileOutputStream("highscore"));
			stream.writeObject(this.getHighscoreList());
			stream.close();

		} catch (IOException ioex) {
			System.err
					.println("Fehler beim Schreiben des Objekts aufgetreten.");
			ioex.printStackTrace();
		}
	}
	
	public void setBagSize(int bagSize){
		size = bagSize;
	}
	
	public int getBagSize(){
		return size;
	}

}
