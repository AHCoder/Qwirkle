package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Robert
 *
 */

public class Player implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 534149386275311742L;
	
	private boolean cheater;
	private String name;
	private int score;
	private ArrayList<Stone> playerStones;
	private boolean isBot;
	
	
	/**
	 * Fügt die übergebene Punktzahl dem Score hinzu
	 * 
	 * @param currentScore Punktzahl die hinzugefügt werden soll
	 */
	public void addPoints(int currentScore) {
		setScore( getScore() + currentScore );
	}

	/**
	 * Konstruktor
	 * @param name Name des neuen Benutzers
	 */
	public Player(String name) {
		this.name = name;
		this.cheater = false;
		this.score = 0;
		isBot = false;
		playerStones = new ArrayList<Stone>();
	}

	/**
	 * @return gibt zurück, ob der Player geschummelt hat
	 */
	public boolean isCheater() {
		return cheater;
	}

	public void setCheater(boolean cheater) {
		this.cheater = cheater;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public ArrayList<Stone> getStones() {
		return playerStones;
	}

	public void setStones(ArrayList<Stone> playerStones) {
		this.playerStones = playerStones;
	}
	
	public boolean isBot(){
		return isBot;
	}

}
