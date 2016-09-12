package model;

import java.io.Serializable;
import java.util.*;
import exception.IsEmptyException;

public class Qwirkle implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2064770214675943935L;
	private boolean cubesVariant;
	private ArrayList<Player> playerList = new ArrayList<Player>();
	private Player currentPlayer;
	private Bag currentBag;
	private int index;
	private Map currentMap;

	public Qwirkle(boolean variant, Bag newBag, ArrayList<Player> list){
		currentMap = new Map();
		playerList = list;
		cubesVariant = variant;
		currentBag = newBag;
		index = 0;
	}
	
	/**
	 * @author Alexander
	 * @description Der naechste Player aus der playerList wird zum currentPlayer
	 * @preconditions Der currentPlayer hat seinen Turn beendet
	 * @postconditions Der naechste Player ist an der Reihe und kann seinen Turn machen
	 */
	public Player nextPlayer() {
		if(index < playerList.size()-1) index++;	// ueberprueft, ob die playerList durchlaufen wurde
		else index = 0;								// nach dem Durchlauf ist der erste Player wieder an der Reihe
		setCurrentPlayer(playerList.get(index));
		return currentPlayer;
	}
	
	public Bag getBag() {
		return currentBag;
	}

	public void setBag(Bag newBag) {
		currentBag = newBag;
	}

	public boolean isCubesVariant() {
		return cubesVariant;
	}

	public void setCubesVariant(boolean variant) {
		cubesVariant = variant;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player player) {
		currentPlayer = player;
	}
	
	public void setFirstPlayer(){
		currentPlayer = playerList.get(index);
	}
	
	public Map getMap(){
		return currentMap;
	}
	
	public void setMap(Map map){
		currentMap = map;
	}
	
	public ArrayList<Player> getPlayerList(){
		return playerList;
	}
	
	public void setPlayerList(ArrayList<Player> newPlayerList)throws IsEmptyException{
		if(newPlayerList.size()<2) throw new IsEmptyException();
		else playerList = newPlayerList;
	}
}
