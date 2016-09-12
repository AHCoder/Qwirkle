package control;

import java.io.Serializable;
import java.util.*;

import model.Player;


public class HighscoreList implements Serializable{
	
	private static final long serialVersionUID = 4878381157640245442L;
	
	private ArrayList<Player> highScoreCubesQuick;		//Highscore Liste f�r ein kurzes QwirkleCubes Spiel 
	private ArrayList<Player> highScoreCubesMedium;		//Highscore Liste f�r ein mittleres QwirkleCubes Spiel
	private ArrayList<Player> highScoreCubesLong;		//Highscore Liste f�r ein langes QwirkleCubes Spiel
	private ArrayList<Player> highScoreQwirkleQuick;	//Highscore Liste f�r ein kurzes original Qwirkle Spiel
	private ArrayList<Player> highScoreQwirkleMedium;	//Highscore Liste f�r ein mittleres original Qwirkle Spiel
	private ArrayList<Player> highScoreQwirkleLong;		//Highscore Liste f�r ein langes original Qwirkle Spiel

	public HighscoreList(){
		
		highScoreCubesQuick = new ArrayList<Player>();
		highScoreCubesMedium = new ArrayList<Player>();
		highScoreCubesLong = new ArrayList<Player>();
		highScoreQwirkleQuick = new ArrayList<Player>();
		highScoreQwirkleMedium = new ArrayList<Player>();
		highScoreQwirkleLong = new ArrayList<Player>();
	}
	
	/**
	 * adds a new Player to the highscoreList. If the list contains less than 10 Player, the Player is added. 
	 * Else the Player with the smallest Score is searched. Is the smallest score in the highscoreList smaller than the player score, the Player are exchanged 
	 * @author Alexander
	 * @param player the player that has to be added to the highscoreList
	 * @preconditions highscoreList contains the Player with the highest score so far, sorted from low to high
	 * @postconditions highscoreList contains the Player with the highest score so far and it does not contain more than 10 objects
	 */
	public void addPlayer(Player player, int bagSize) {
		ArrayList<Player> selected = this.select(bagSize);
		if(selected.size()>=10){										//does the highscoreList contain 10 Player?
			Player exchange = selected.get(0);
			if(player.getScore()>exchange.getScore()){					//player score greater than the smallest score?
				selected.remove(0);
				selected.add(player);
			}
		}
		else selected.add(player);
		this.sort(selected);
	}
	
	/**
	 * @return the highscoreList
	 */
	public ArrayList<Player> getHighscore(int bagSize){
		ArrayList<Player> highscoreList = this.select(bagSize);
		return highscoreList;
	}
	
	/**
	 * @param players a list of Player
	 */
	public void setHighscore(ArrayList<Player> players, int bagSize){
		// GameController braucht man hier nicht weil die Zahlen unterschiedlich sind
		//if(gameCtrl.getQwirkle().isCubesVariant() == true){							//Auswahl der Listen als original Qwirkle oder QwirkleCubes
			switch(bagSize){
			case(30): highScoreCubesQuick = players; break;		//Kurzes Spiel, 30 Wuerfel
			case(60): highScoreCubesMedium = players; break;	//Mittleres Spiel, 60 Wuerfel
			case(90): highScoreCubesLong = players; break;		//Normales Spiel, 90 Wuerfel
			//}
		//}
		//else{
			//switch(bagSize){
			case(36): highScoreQwirkleQuick = players; break;	//Kurzes Spiel, 36 Steine
			case(72): highScoreQwirkleMedium = players; break;	//Mittleres Spiel, 72 Steine
			case(108): highScoreQwirkleLong = players; break;	//Normales Spiel, 108 Steine
			}
	}

	/**
	 * @return the size of the highscoreList
	 */
	public int size(int bagSize) {
		return this.select(bagSize).size();
	}

	/**
	 * @param i the index of the searched Player
	 * @return the Player at position i
	 */
	public Player get(int index, int bagSize) {
		return this.select(bagSize).get(index);
	}
	
	/**
	 * sorts a list from lowest score to highest score
	 * @param toSort the list, that has to be sorted
	 * @preconditions the list is unsorted
	 * @postconditions the list is sorted, lowest score in the first, highest score in the last element
	 */
	private void sort(ArrayList<Player> toSort){
		int index = toSort.size()-1;
		Player added = toSort.get(index);
		int place=0;
		while(added.getScore()>toSort.get(place).getScore() && place<=index){
			place++;
		}
		if(place!=index){
			toSort.add(place, added);
			while(toSort.size()>10)
				toSort.remove(toSort.size()-1);
		}
	}
	
	private ArrayList<Player> select(int bagSize){
	ArrayList<Player> selected = null;

		switch(bagSize){
		case(30): selected = highScoreCubesQuick; break;	//quick game, 30 cubes
		case(60): selected = highScoreCubesMedium; break;	//medium distance, 60 cubes
		case(90): selected = highScoreCubesLong; break;		//normal distance, 90 cubes
		case(36): selected = highScoreQwirkleQuick; break;	//quick game, 36 stones
		case(72): selected = highScoreQwirkleMedium; break;	//medium distance, 72 stones
		case(108): selected = highScoreQwirkleLong; break;	//normal distance, 108 stones
		}
	return selected;
	}
	
	public HighscoreList getHighscoreList(){
		return this;
	}
}
