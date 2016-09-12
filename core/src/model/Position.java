package model;
import java.io.Serializable;
import java.util.ArrayList;

public class Position implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2710006289208852151L;

	private int xPos;

	private int yPos;

	private Stone stone;
	
	private ArrayList<Stone> possibleStones;
	
	
	public Position (int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.possibleStones = new ArrayList<Stone>();
	}
	
	/***
	 * Diese Methode prueft, ob eine Stein auf diese Position gesetzt werden kann.
	 * 
	 * @param stone
	 * 			die zu setzende Stein.
	 * @return 
	 * 			@true: die Stein kann auf die Position gesetzt werden.
	 * 			@false: die Stein kann nicht auf die Position gesetzt werden.
	 */
	
	public Boolean isPossibleStone(Stone stone) {
		
		String color = stone.getColor();
		String symbol = stone.getSymbol();
		
		Boolean found = false;
		int i = 0;
		int size = possibleStones.size();
		
		// Durchsuche alle Steine in possibleStones auf gleichheit mit dem Symbol/Farbe des gesuchten Steins
		while(i<size && !found) {
			if(possibleStones.get(i).getColor().equals(color) && possibleStones.get(i).getSymbol().equals(symbol)) {
				found = true;
			}
			i++;
		}
		return found;
	}
	
	/***
	 * Diese Methode setzt einen Stein auf die Position und  
	 * entfernt alle Elemente aus possibleStones.
	 * 
	 * @param stone
	 * 			die zu setzende Stein.
	 * 			
	 */
	
	public void setStone(Stone stone) {
		this.stone = stone;
		this.possibleStones = new ArrayList<Stone>();	
	}
	
	public Boolean isFree() {
		if (this.stone == null) {
			return true;
		} else {
			return false;
		}	
	}

	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

	public ArrayList<Stone> getPossibleStones() {
		return possibleStones;
	}

	public void setPossibleStones(ArrayList<Stone> possibleStones) {
		this.possibleStones = possibleStones;
	}

	public Stone getStone() {
		return stone;
	}

}
