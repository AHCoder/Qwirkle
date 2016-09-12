package model;

import java.util.ArrayList;
import java.io.Serializable; 



/**
 * @author Toenges
 *
 */
public class Map implements Serializable{ 
	
	private static final long serialVersionUID = 2679322372272942315L; 

	private ArrayList<Position> newAddedStones;

	private ArrayList<Position> playable;

	private ArrayList<Position> placedStones;

	private ArrayList<Position> playableTurn;
	
	private ArrayList<Position> allPositions;
	
	

	public Map() {
		this.newAddedStones = new ArrayList<Position>();
		this.playable = new ArrayList<Position>();
		this.placedStones = new ArrayList<Position>();
		this.playableTurn = new ArrayList<Position>();
		this.allPositions = new ArrayList<Position>();
		
		Position firstPosition = new Position(0,0);
		
		
		ArrayList<String> symbols = new ArrayList<String>();;
		ArrayList<String> colors = new ArrayList<String>();
		ArrayList<Stone> stones = new ArrayList<Stone>();
			
		symbols.add("St");
		symbols.add("Ra");
		symbols.add("Kr");
		symbols.add("Qu");
		symbols.add("Bl");
		symbols.add("So");

		colors.add("gr");
		colors.add("ge");
		colors.add("ro");
		colors.add("bl");
		colors.add("li");
		colors.add("or");
		
		//Berechne alle moeglichen Steinvarianten
		
		for(int i = 0;i<symbols.size();i++){
			for (int j =0;j<colors.size();j++) {
				stones.add(new Stone(symbols.get(i),colors.get(j)));
			}
		}
		
		//Setze alle Steinvarianten als moegliche Steine fuer die erste Position
		firstPosition.setPossibleStones(stones);
		
		//Setze die erste Position zu den bespielbaren Positionen und zu allen Positionen
		playable.add(firstPosition);
		allPositions.add(firstPosition);
		playableTurn.add(firstPosition);
		
	}
	
	/***
	 * Diese Methode versucht einen Stein auf eine Position zu legen.
	 * 
	 * @param stone
	 * 			das zu plazierende Stein-Objekt.
	 * @param position
	 * 			die Position, auf die der Stein plaziert werden soll.
	 * @postconditions
	 * 			Bei erfolgreicher Plazierung des Steines wird die Position zu newAddedStones hinzugefügt.
	 * 			Die Positionen im Attribut playableTurn werden gemaess Qwirkle-Regelwerk angepasst.
	 * @return 
	 * 			@true: der Stein wurde erfolgreich auf der Position plaziert.
	 * 			@false: der Stein konnte nicht auf der Position plaziert werden.
	 */

	public boolean placeStone(Stone stone, Position position) {
		
		
		Boolean playable = false;
		Boolean possible = false;
		
		Position realPosition = getPosition(position.getxPos(), position.getyPos());
		
		if (realPosition != null) {
		
		playable = isPlayableThisTurn(realPosition);
		
		if(playable) {
			possible = position.isPossibleStone(stone);
			
			if(possible) {	
				realPosition.setStone(stone);
				insertToNewAddedStones(realPosition);
				insertToPlacedStones(realPosition);
				calculatePlayable();
				calculatePlayableForTurn();
			}	
		}
		
		return (possible && playable);
		} else {
			return false;
		}
	}
	
	/***
	 * Diese Methode prueft, ob eine Position in der aktuellen Runde besetzt werden kann.
	 * 
	 * @param position
	 * 			die zu besetzende Position.
	 * @return 
	 * 			@true: die Position ist in der aktuellen Runde besetzbar.
	 * 			@false: die Position ist in der aktuellen Runde nicht besetzbar.
	 */
	
	public boolean isPlayableThisTurn(Position position) {
		return playableTurn.contains(position);
	}

	public void insertToNewAddedStones(Position position) {
			this.newAddedStones.add(position);
	}
	
	public void insertToPlacedStones(Position position) {
		   placedStones.add(position);
		   //allPositions.add(position);
		   playable.remove(position);
	}
	

	/***
	 * Diese Methode berechnet die Positionen, die in Abhängigkeit von den bereits gelegten Steinen
	 * noch in der aktuellen Runde belegt werden können.
	 * 
     *
	 */
	

	public void calculatePlayableForTurn() {
		int xAxis;
		int yAxis;
		
		playableTurn = new ArrayList<Position>();
		
		
			if (newAddedStones.size() > 1) { //Es wurde schon mehr als ein Stein in dieser Runde gelegt
				
				
				
				if(newAddedStones.get(1).getxPos() != newAddedStones.get(0).getxPos()) { //Die Reihe liegt auf der x-Achse
					xAxis = newAddedStones.get(1).getyPos();
					for(int i = 0;i<playable.size();i++) {
						if(playable.get(i).getyPos() == xAxis) {
							playableTurn.add(playable.get(i));
						}
					}
					
				}
				if(newAddedStones.get(1).getyPos() != newAddedStones.get(0).getyPos()) { //Die Reihe liegt auf der y-Achse
					yAxis = newAddedStones.get(1).getxPos();
					for(int i = 0;i<playable.size();i++) {
						if(playable.get(i).getxPos() == yAxis) {
							playableTurn.add(playable.get(i));
						}
					}
				}
				
			} else { // Der erste Stein wurde in dieser Runde gelegt.
				 xAxis = newAddedStones.get(0).getxPos();
				 yAxis = newAddedStones.get(0).getyPos();
				 
				 for(int i = 0;i<playable.size();i++) {
						if(playable.get(i).getyPos() == yAxis || playable.get(i).getxPos() == xAxis) {
							playableTurn.add(playable.get(i));
						}
					}
				 
			}
	}		
	
	
	/***
	 * Diese Methode berechnet die 10 möglichen Nachbarsteine zu einem Stein.
	 * 
	 * @param stone
	 * 			der Stein, für den die Nachbarn berechnet werden sollen.
	 * @return 
	 *			Eine ArrayList mit möglichen Nachbarsteinen.
	 */
	
	public ArrayList<Stone> calculatePossibleNeigbours(Stone stone) {
		ArrayList<String> symbols = new ArrayList<String>();;
		ArrayList<String> colors = new ArrayList<String>();
		
		symbols.add("St");
		symbols.add("Ra");
		symbols.add("Kr");
		symbols.add("Qu");
		symbols.add("Bl");
		symbols.add("So");

		colors.add("gr");
		colors.add("ge");
		colors.add("ro");
		colors.add("bl");
		colors.add("li");
		colors.add("or");
		
		ArrayList<Stone> newPossible = new ArrayList<Stone>();
		String stoneColor = stone.getColor();
		String stoneSymbol = stone.getSymbol();
		
		symbols.remove(stoneSymbol);
		colors.remove(stoneColor);
		
		for(int i = 0;i<colors.size();i++){
			newPossible.add(new Stone(stoneSymbol,colors.get(i)));
		}
		for(int j = 0;j<symbols.size();j++) {
			newPossible.add(new Stone(symbols.get(j),stoneColor));
		}
		
		return newPossible;
	}
	
	/***
	 * Diese Methode bestimmt die Schnittmenge von Steinen (A geschnitten B), die auf eine Position kommen sollen.
	 * 
	 * @param listA
	 * 			Eine Liste von Steinen, die die Menge A darstellen.
	 * @param position
	 * 			Die Position, für die die Schnittmenge bestimmt werden soll. Die möglichen Steine auf position stellen die Menge B dar.
	 * @return 
	 *			Die Schnittmenge von A und B.
	 */
	
	public ArrayList<Stone> stoneIntersection (ArrayList<Stone> listA, Position position) {
		
		ArrayList<Stone> returnList = new ArrayList<Stone>();
		
		for(int i = 0;i<listA.size();i++){
			if(position.isPossibleStone(listA.get(i)) == true) {
				returnList.add(listA.get(i));
			}
		}
			
		return returnList;
	}
	
	/***
	 * Diese Methode Prüft eine Reihe auf die QwirkleBedingungen
	 * 
	 * @param listA
	 * 			Eine Liste von Steinen, die eine Reihe darstellen
	 * @return 
	 *			Die Möglichen Steine, die an diese Reihe noch hinzugefügt werden können.
	 */
	
	public ArrayList<Stone> stoneRowIntersection(ArrayList<Stone> listA) {
		
		ArrayList<Stone> returnList = new ArrayList<Stone>();
		
		ArrayList<String> symbols = new ArrayList<String>();
		ArrayList<String> colors = new ArrayList<String>();
		Boolean sameColor = true;
		Boolean sameSymbol = true;
		Boolean doubleStone = false;
		
		symbols.add("St");
		symbols.add("Ra");
		symbols.add("Kr");
		symbols.add("Qu");
		symbols.add("Bl");
		symbols.add("So");

		colors.add("gr");
		colors.add("ge");
		colors.add("ro");
		colors.add("bl");
		colors.add("li");
		colors.add("or");
		
		//Prüfe ob ein Stein doppelt in der Liste vorkommt
		for(int i = 0;i<listA.size();i++) {
			for (int j = i+1;j<listA.size();j++) {
				if(listA.get(i).getColor().equals(listA.get(j).getColor()) && listA.get(i).getSymbol().equals(listA.get(j).getSymbol())) {
					doubleStone = true;
				}
			}
		}
		
		
		for(int i=0;i<listA.size()-1;i++) {
			if(listA.get(i).getColor().equals(listA.get(i+1).getColor()) == false) {				
				sameColor = false;
			}
			if(listA.get(i).getSymbol().equals(listA.get(i+1).getSymbol()) == false) {
				sameSymbol = false;
			}
		}	
			
		
		if(listA.size() >= 2) {
			
			//Die Farbe der Reihe ist gleich
			if(sameColor) {
				
				String rowColor = listA.get(0).getColor();
				for(int i = 0;i<listA.size();i++) {
					symbols.remove(listA.get(i).getSymbol());
				}
				
				for(int i = 0;i<symbols.size();i++) {
					Stone newStone = new Stone(symbols.get(i),rowColor);
					returnList.add(newStone);
				}
				
			}
			
			
			//Die Symbole der Reihe sind gleich
			if(sameSymbol) {
				
				String rowSymbol = listA.get(0).getSymbol();
				
				for(int i = 0;i<listA.size();i++) {
					colors.remove(listA.get(i).getColor());
				}
				
				for(int i = 0;i<colors.size();i++) {
					Stone newStone = new Stone(rowSymbol,colors.get(i));
					returnList.add(newStone);
				}
				
			}
			
			if(doubleStone) {
				returnList.clear();
			}
			
			
		}
		
		
		
		return returnList;
	}
	
	
	/**
	 *  Diese Methode berechnet die bespielbaren Positionen
	 */
	
	public void calculatePlayable() {
		
		Position north;
		Position east;
		Position south;
		Position west;
		Position opposite = null;
		Position mainsite = null;

				
		if(placedStones.size()==1) { //Sonderfall für den ersten Stein ueberhaupt.
			
			playableForOneStone();
			
		} else {
			
		Position lastAddedPosition = placedStones.get(placedStones.size()-1);
		Stone lastAddedStone = lastAddedPosition.getStone();
	
		int xLast = lastAddedPosition.getxPos();
		int yLast = lastAddedPosition.getyPos();
		int newX = lastAddedPosition.getxPos();
		int newY = lastAddedPosition.getyPos();
		int oppX = 0;
		int oppY = 0;
		
		//Hole Positionen Rund um den gesetzten Stein (Nebenpositionen) 
		north = getPosition(xLast,yLast+1);
		east = getPosition(xLast+1,yLast);
		south = getPosition(xLast,yLast-1);
		west = getPosition(xLast-1,yLast);
		
		ArrayList<Position> aroundPositions = new ArrayList<Position>();
		aroundPositions.add(north);
		aroundPositions.add(east);
		aroundPositions.add(south);
		aroundPositions.add(west);
		
		Boolean[] handeled = new Boolean[4];	
		for(int i = 0;i<handeled.length;i++) {
			handeled[i] = false;
		}
		
		/*Fuer alle 4 Seiten um den zuletzt Hinzugefuegten Stein gibt es folgende Moeglichkeiten:
		  1. Die Position ist null. Es muss eine neue Position angelegt werden und playableStones fuer diese ermittelt werden
		  2. Auf der Position liegt bereits ein Stein. Dann muss diese Richtung bis zum Ende abgelaufen werden. Alle Steine dieser Reihe muessen gemerkt werden.
		  	 Am Ende muss fuer die freie Position possibleStones ermittelt werden.
		  3. Auf der Position liegt kein Stein. playableStones muss fuer diese ermittelt werden. 	 
		*/
		
		for(int i =0;i<aroundPositions.size();i++) {
			
			if(handeled[i] == false) {
				switch(i) {
				case 0:
					mainsite = north;
					opposite = south;
					newX = 0;
					newY = 1;
					oppY = -1;
					oppX = 0;
					break;
				case 1:
					mainsite = east;
					opposite = west;
					newX = 1;
					newY = 0;
					oppX = -1;
					oppY = 0;
					break;
				case 2:
					mainsite = south;
					opposite = north;
					newX = 0;
					newY = -1;
					oppY = 1;
					oppX = 0;
					break;
				case 3:
					mainsite = west;
					opposite = east;
					newX = -1;
					newY = 0;
					oppX = 1;
					oppY = 0;
					break;
				}
				
				//Position existiert nicht
				if(aroundPositions.get(i) == null) {
					
					opposite = handleNonExistingPosition(opposite, lastAddedStone, xLast, yLast, newX, newY, oppX, oppY, handeled, i);
				
				} else { //Position existiert

					handleExistingPosition(mainsite, lastAddedStone, newX, newY, handeled, i); 	
				}				
			}
			
		}	
		}
		
	}

	/** Refactor Method
	 * @param mainsite
	 * @param lastAddedStone
	 * @param newX
	 * @param newY
	 * @param handeled
	 * @param i
	 */
	private void handleExistingPosition(Position mainsite,
			Stone lastAddedStone, int newX, int newY, Boolean[] handeled, int i) {
		// Die Position existiert 
		if(mainsite != null && mainsite.isFree() == true) { // Die Position ist frei
			   
			   Position nextMainsite = getPosition(mainsite.getxPos()+newX, mainsite.getyPos()+newY);
			   ArrayList<Stone> stonesInRow = new ArrayList<Stone>();
			   if(nextMainsite != null && nextMainsite.isFree() == false) {

				   stonesInRow.add(lastAddedStone);
				   while(nextMainsite != null && nextMainsite.isFree() == false) {
					 
					   
					   stonesInRow.add(nextMainsite.getStone());
					   nextMainsite = getPosition(nextMainsite.getxPos()+newX, nextMainsite.getyPos()+newY);
					  
				   }
				   mainsite.setPossibleStones(stoneRowIntersection(stonesInRow));
				   handeled[i] = true;
			   } else {
			
			   mainsite.setPossibleStones(stoneIntersection(calculatePossibleNeigbours(lastAddedStone), mainsite));
			   
			   handeled[i] = true;
			   }
		}
	}

	/** Refactor Method
	 * @param opposite
	 * @param lastAddedStone
	 * @param xLast
	 * @param yLast
	 * @param newX
	 * @param newY
	 * @param oppX
	 * @param oppY
	 * @param handeled
	 * @param i
	 * @return
	 */
	private Position handleNonExistingPosition(Position opposite,
			Stone lastAddedStone, int xLast, int yLast, int newX, int newY,
			int oppX, int oppY, Boolean[] handeled, int i) {
		Position mainsite;
		mainsite = new Position(xLast+newX,yLast+newY);
		allPositions.add(mainsite);
		playable.add(mainsite);
		
		//Ist auf der gegenüberliegenden Position ein Stein? D.h. der Stein wurde an eine Reihe gelegt
		if(opposite != null && opposite.isFree() == false) {
			   ArrayList<Stone> stonesInRow = new ArrayList<Stone>();
			   stonesInRow.add(lastAddedStone);
			   
			   while(opposite != null && opposite.isFree() == false) {
				   stonesInRow.add(opposite.getStone());
				   opposite = getPosition(opposite.getxPos()+oppX, opposite.getyPos()+oppY);
			   }
			   
			   if(opposite!= null && opposite.isFree() == true) {
				   //Bestimme hier die möglichen Steine, die an eine Reihe angelegt werden können für south und north
				   //ArrayList<Stone> test = stoneRowIntersection(stonesInRow);
				   opposite.setPossibleStones(stoneIntersection(stoneRowIntersection(stonesInRow), opposite));
				   mainsite.setPossibleStones(stoneRowIntersection(stonesInRow));
				   handeled[i] = true;
				   handeled[(i+2)%4] = true;
			   }
		} else { //Auf der gegenüberliegenden Seite liegt kein Stein und die neue Position existiert nicht
			   
			mainsite.setPossibleStones(calculatePossibleNeigbours(lastAddedStone));
			handeled[i] = true;
		}
		return opposite;
	}

	/**Refactor Method
	 * Berechnet bespielbaren Positionen für den Sonderfall, dass der erste Stein im Spiel gelegt wurde.
	 */
	private void playableForOneStone() {
		Position north;
		Position east;
		Position south;
		Position west;
		Stone firstStone = placedStones.get(0).getStone();
		ArrayList<Stone> newPossible = calculatePossibleNeigbours(firstStone);
				
		north = new Position(0,1);
		north.setPossibleStones(newPossible);
		allPositions.add(north);
		playable.add(north);
		
		east = new Position(1,0);
		east.setPossibleStones(newPossible);
		allPositions.add(east);
		playable.add(east);
		
		south = new Position(0,-1);
		south.setPossibleStones(newPossible);
		allPositions.add(south);
		playable.add(south);
		
		west = new Position(-1,0);
		west.setPossibleStones(newPossible);
		allPositions.add(west);
		playable.add(west);
	}
	
	/**
	 * Diese Methode gibt eine Position auf der Map zurück
	 * @param xPos
	 * @param yPos
	 * @return Die reale Position des Modells
	 */

	
	public Position getPosition(int xPos, int yPos) {
		
		Position searchedPosition = null;
		for(int i = 0;i<allPositions.size();i++) {
			if(allPositions.get(i).getxPos() == xPos && allPositions.get(i).getyPos() == yPos) {
				searchedPosition = allPositions.get(i);
			}
		}
		
		return searchedPosition;
	}


	public ArrayList<Position> playablePositions(Stone stone) {
		
		ArrayList<Position> posList = new ArrayList<Position>();
		
		for (int i = 0;i<playableTurn.size();i++) {
			if (playableTurn.get(i).isPossibleStone(stone)) {
				posList.add(playableTurn.get(i));
			}
			
		
		}
		
		return posList;	
		
	}

	public int getCurrentScore() {
		return 0;
	}

	public ArrayList<Position> getNewAddedStones() {
		return newAddedStones;
	}

	public void setNewAddedStones(ArrayList<Position> newAddedStones) {
		this.newAddedStones = newAddedStones;
	}

	public ArrayList<Position> getPlayable() {
		return playable;
	}

	public void setPlayable(ArrayList<Position> playable) {
		this.playable = playable;
	}

	public ArrayList<Position> getPlacedStones() {
		return placedStones;
	}

	public void setPlacedStones(ArrayList<Position> placedStones) {
		this.placedStones = placedStones;
	}

	public ArrayList<Position> getPlayableTurn() {
		return playableTurn;
	}

	public void setPlayableTurn(ArrayList<Position> playableTurn) {
		this.playableTurn = playableTurn;
	}
	
	public ArrayList<Position> getAllPositions() {
		return allPositions;
	}

	
	/**
	 * Leert das Array NewAddedStones und passt das Array playable an
	 */
	public void clearTurn(){
		playableTurn = playable;
		newAddedStones.clear();
	}
	
	/**
	 * Die Methode berechnet die erreichten Punkte in einem Zug
	 * @return die erlangten Punkte des Zuges als int
	 */
	
	public int calculateCurrentScore(){
		
		int columnScore = 0;
		int score = 0; 
		int rowScore = 0;
		ArrayList<Position> temp = (ArrayList<Position>) newAddedStones.clone();
		//temp.remove(0);
		Boolean[] handeled = new Boolean[4];
			
		if (temp.size() > 1) { //Es wurde schon mehr als ein Stein in dieser Runde gelegt
				
			if(temp.get(1).getxPos() != temp.get(0).getxPos()) { //Die Reihe liegt auf der x-Achse

				score = calculateXRow(columnScore, score);
					
			} else { // Die Reihe liegt auf der y-Achse
					
				score = calculateYColumn(score, rowScore);		
			}
		
		}  else { // Es wurde nur ein Stein oder kein in der Runde gelgt				
			
			if(temp.size() == 1) {
			score = scoreForOneStone(score, handeled)+1;
			} else {
				score = 0;
			}
		}
		
		return score;
}

	/**Refactor Method
	 * @param score
	 * @param rowScore
	 * @return
	 */
	private int calculateYColumn(int score, int rowScore) {
		int columnScore;
		int yPos;
		int xPos;
		ArrayList<Position> temp = (ArrayList<Position>) newAddedStones.clone();
		columnScore = 1;
		//Berechne zunächst die Punkte der Reihe
		Position addedPosition = temp.get(temp.size()-1);
		yPos =  addedPosition.getyPos();
		xPos = addedPosition.getxPos();
		Position north = getPosition(xPos, yPos+1);
		Position south = getPosition(xPos, yPos-1);
		
		while(north != null && north.isFree() == false) {
			   columnScore++;
			   north = getPosition(north.getxPos(), north.getyPos()+1);
		   }
		while(south != null && south.isFree() == false) {
			   columnScore++;
			   south = getPosition(south.getxPos(), south.getyPos()-1);
		   }

		if(columnScore == 6) {
			columnScore = columnScore+6;
		}
		
		score = score + columnScore;
		//Berechne jetzt die Punkte der ergänzten Reihen nach oben&unten
		
		while(temp.size() != 0) {
			
			addedPosition = temp.get(temp.size()-1);
			yPos =  addedPosition.getyPos();
			xPos = addedPosition.getxPos();
			Position east = getPosition(xPos+1, yPos);
			Position west = getPosition(xPos-1, yPos);
			rowScore = 0;
			
			while(east != null && east.isFree() == false) {
				   rowScore++;
				   east = getPosition(east.getxPos()+1, east.getyPos());
			   }
			while(west != null && west.isFree() == false) {
				   rowScore++;
				   west = getPosition(west.getxPos()-1, west.getyPos());
			   }
			if(rowScore > 0) {
				rowScore++;
			}
			
			if(rowScore == 6) {
				rowScore = rowScore+6;
			}
			
			score = score + rowScore;
			temp.remove(temp.size()-1);
		}
		return score;
	}

	/**Refactor Method
	 * @param columnScore
	 * @param score
	 * @return
	 */
	private int calculateXRow(int columnScore, int score) {
		
		int yPos;
		int xPos;
		int rowScore;
		ArrayList<Position> temp = (ArrayList<Position>) newAddedStones.clone();
		rowScore = 1;
		//Berechne zunächst die Punkte der Reihe
		Position firstPos = temp.get(0);
		yPos =  firstPos.getyPos();
		xPos = firstPos.getxPos();
		Position east = getPosition(xPos+1, yPos);
		Position west = getPosition(xPos-1, yPos);
		
		while(east != null && east.isFree() == false) {
			   rowScore++;
			   east = getPosition(east.getxPos()+1, east.getyPos());
		   }
		while(west != null && west.isFree() == false) {
			   rowScore++;
			   west = getPosition(west.getxPos()-1, west.getyPos());
		   }
		
		if(rowScore == 6) {
			rowScore = rowScore+6;
		}
		
		score = score + rowScore;
		//Berechne jetzt die Punkte der ergänzten Reihen nach oben&unten
		
		while(temp.size() != 0) {
			Position addedPosition = temp.get(temp.size()-1);
			yPos =  addedPosition.getyPos();
			xPos = addedPosition.getxPos();
			Position north = getPosition(xPos, yPos+1);
			Position south = getPosition(xPos, yPos-1);
			columnScore = 0;
			
			while(north != null && north.isFree() == false) {
				   columnScore++;
				   north = getPosition(north.getxPos(), north.getyPos()+1);
			   }
			while(south != null && south.isFree() == false) {
				   columnScore++;
				   south = getPosition(south.getxPos(), south.getyPos()-1);
			   }
			if(columnScore > 0) {
				columnScore++;
			}
			if(columnScore == 6) {
				columnScore = columnScore+6;
			}
			
			score = score + columnScore;
			temp.remove(temp.size()-1);
		}
		return score;
	}

	/**Refactor Method
	 * @param score
	 * @param handeled
	 * @return
	 */
	private int scoreForOneStone(int score, Boolean[] handeled) {
		
		ArrayList<Position> temp = (ArrayList<Position>) newAddedStones.clone();
		Position addedPosition = temp.get(0);
		

		int xLast = addedPosition.getxPos();
		int yLast = addedPosition.getyPos();
		
		Position north = getPosition(xLast,yLast+1);
		Position east = getPosition(xLast+1,yLast);
		Position south = getPosition(xLast,yLast-1);
		Position west = getPosition(xLast-1,yLast);
		
		ArrayList<Position> aroundPositions = new ArrayList<Position>();
		aroundPositions.add(north);
		aroundPositions.add(east);
		aroundPositions.add(south);
		aroundPositions.add(west);
		
		Position mainsite = null;
		int newX = 0, newY = 0;
			
		for(int i = 0;i<4;i++) {
			handeled[i] = false;
		}
		
		for(int i = 0;i < 4;i++) {
			if(handeled[i] == false) {
				switch(i) {
				case 0:
					mainsite = north;
					newX = 0;
					newY = 1;
					break;
				case 1:
					mainsite = east;
					newX = 1;
					newY = 0;
					break;
				case 2:
					mainsite = south;
					newX = 0;
					newY = -1;
					break;
				case 3:
					mainsite = west;
					newX = -1;
					newY = 0;
					break;
				}
				
				if(mainsite.isFree() == false) {
					   ArrayList<Stone> stonesInRow = new ArrayList<Stone>();
					   stonesInRow.add(addedPosition.getStone());
					   
					   while(mainsite != null && mainsite.isFree() == false) {
						   stonesInRow.add(mainsite.getStone());
						   mainsite = getPosition(mainsite.getxPos()+newX, mainsite.getyPos()+newY);
					   }
					   
					   if(mainsite!= null && mainsite.isFree() == true) {
						   
						   if(stonesInRow.size() == 6) {
							  score = score +11;
						   } else {
							   score = score + stonesInRow.size()-1;
						   }
							   handeled[i] = true;

					   }
				
		}
}	
}
		return score;
	}
}	
