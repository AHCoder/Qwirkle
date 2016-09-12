package control;

import java.util.ArrayList;
import java.util.HashSet;

import model.Bot;
import model.Map;
import model.Player;
import model.Position;
import model.Qwirkle;
import model.Stone;

public class BotController {
	
	private Bot currentPlayer;
	private GameController gameCtrl;
	private String turn;
	
	public String getTurn() {
		return turn;
	}

	public void setTurn(String turn) {
		this.turn = turn;
	}

	public BotController(GameController gameCtrl) throws NullPointerException {
		if (gameCtrl == null) throw new NullPointerException("Referenz ist null");
		else this.gameCtrl = gameCtrl;
	}
		
	public String playTurn(Bot player) {
		turn = "";
		currentPlayer = player;
		if(currentPlayer.getDifficulty().equals("Low")) {
			turn = calculateLow();
		}
		if(currentPlayer.getDifficulty().equals("Medium")) {
			for (int i = 0; i < 2; i++) {
			turn += calculateMedium();
			}
		}
		if(currentPlayer.getDifficulty().equals("High")) {
			turn = calculateHigh();
		}
		return turn;
	}

	/**
	 * Die Methode geht durch die Liste der Steine mit einer Gemeinsamkeit durch, berechnet den höchst-
	 * möglichen Score und führt diesen aus
	 */
	private String calculateHigh() {
		Qwirkle qwirkle = gameCtrl.getQwirkle();
		Map map = qwirkle.getMap();
		// Variable um den besten Zug zu speichern und später doTurn damit aufzurufen
		String bestTurn = "";
		ArrayList<ArrayList<Stone>> all = findMatchingStones();
		for (ArrayList<Stone> best : all) { 
			// for Schleife durch "best"
			bestTurn = calculateMVP(map, best, bestTurn);
		}
		if (bestTurn.equals("")) {
			if ( qwirkle.isCubesVariant()) {
				ArrayList<Stone> stoneList = currentPlayer.getStones();
				for ( Stone cube : findDoubles()) {
					for ( int cubeIndex = 0; cubeIndex < stoneList.size(); cubeIndex++) {
						if ( stoneList.get(cubeIndex).getColor().equals(cube.getColor()) && 
								stoneList.get(cubeIndex).getSymbol().equals(cube.getSymbol()) ) {
							gameCtrl.getPlayerController().rollCube(cubeIndex);
						}
					}
				}
				return calculateHigh();
			} else { 
				ArrayList<Stone> swapStones = qwirkle.getCurrentPlayer().getStones();
				for ( Stone swap : swapStones ) {
					bestTurn += swap.getSymbol() + " " + swap.getColor() + "|";
				}
				bestTurn = "+ " + bestTurn.substring(0, bestTurn.length()-1);
			}
		} else {
			bestTurn = "# " + bestTurn.substring(0, bestTurn.length()-1);
		}
		// Zug ausführen mit doTurn
		gameCtrl.getPlayerController().doTurn(bestTurn);
		return bestTurn;
	}

	/**
	 * Die Methode sucht sich die längste Liste aus den Steinen aus der Hand des aktuellen Spielers
	 * mit einer Gemeinsamkeit.
	 * Die Steine aus der Liste werden platziert und die höchstmögliche Punktzahl wird ermittelt.
	 * Der Zug wird gespeichert und dann mit doTurn(String) am Ende ausgeführt
	 * 
	 */
	private String caculateMedium() {
		//Robert und Jenny
		Qwirkle qwirkle = gameCtrl.getQwirkle();
		Map map = qwirkle.getMap();
		ArrayList<ArrayList<Stone>> all = findMatchingStones();
		ArrayList<Stone> best = new ArrayList<Stone>();
		// Berechnung "best" mit der längsten Folge von Steinen aus der Hand
		for (ArrayList<Stone> al : all) {
			if (al.size() > best.size()) { 
				best = al;
			}
		}
		// Variable um den besten Zug zu speichern und später doTurn damit aufzurufen
		String bestTurn = ""; 
		// for Schleife durch "best"
		bestTurn = calculateMVP(map, best, bestTurn);
		if (bestTurn.equals("")) {
			if ( qwirkle.isCubesVariant()) {
				ArrayList<Stone> stoneList = currentPlayer.getStones();
				for ( Stone cube : findDoubles()) {
					for ( int cubeIndex = 0; cubeIndex < stoneList.size(); cubeIndex++) {
						if ( stoneList.get(cubeIndex).getColor().equals(cube.getColor()) && 
								stoneList.get(cubeIndex).getSymbol().equals(cube.getSymbol()) ) {
							gameCtrl.getPlayerController().rollCube(cubeIndex);
						}
					}
				}
				return calculateMedium();
			} else {
				ArrayList<Stone> swapStones = qwirkle.getCurrentPlayer().getStones();
				for ( Stone swap : swapStones ) {
					bestTurn += swap.getSymbol() + " " + swap.getColor() + "|";
				}
				bestTurn = "+ " + bestTurn.substring(0, bestTurn.length()-1);
			}
		} else {
			bestTurn = "# " + bestTurn.substring(0, bestTurn.length()-1);
		}
		// Zug ausführen mit doTurn
		gameCtrl.getPlayerController().doTurn(bestTurn);
		return bestTurn;		
	}

	/**
	 * @param map aktuelle Map
	 * @param best aktuelle Liste der Steine mit einer Gemeinsamkeit
	 * @param bestTurn String mit aktuell bestmöglichen Zug
	 * @return String mit bestem Zug mit den Steinen
	 */
	private String calculateMVP(Map map, ArrayList<Stone> best, String bestTurn) {
		gameCtrl.getQwirkleStack().push();
		int t = 0;
		int currScore = 0;
		for (int y = 0; y < best.size(); y++) {
			int i = 0;
			
			Position position;
			ArrayList<Position> playable = new ArrayList<Position>();
			for(int a = 0; a < map.getPlayable().size(); a++) {playable.add(map.getPlayable().get(a));
				//playable.add(new Position (map.getPlayable().get(a).getxPos(),map.getPlayable().get(a).getyPos());
			}
			// Filtern der Positionen für den Stein y aus "best"
			while (i < playable.size()) {
				position = playable.get(i);
				boolean possible = position.isPossibleStone(best.get(y));
				if ( possible ){
					i++;
				}else {
					playable.remove(i);
				}
			}
			
			// Stein y wird als erstes auf eine der Positionen von playable gelegt
			//if ( map.getNewAddedStones().isEmpty()) {
				if ( !playable.isEmpty() && t < playable.size()) {
					map.placeStone(best.get(y), playable.get(t));
					bestTurn = playable.get(t).getxPos() + " " +  playable.get(t).getyPos() + " " +
							best.get(y).getSymbol() + " " + best.get(y).getColor() + "|";
					bestTurn += calculateBestScore(map, best, bestTurn, currScore);
				}
			//}
//			} else {
//				ArrayList<Position> playableForTurn = new ArrayList<Position>();
//				for(int a = 0; a < map.getPlayableTurn().size(); a++) {
//					playableForTurn.add(map.getPlayable().get(a));
//				}
//				bestTurn += playableForTurn.get(t).getxPos() + " " +  playableForTurn.get(t).getyPos() + " " +
//						best.get(y).getSymbol() + " " + best.get(y).getColor() + "|";
//			}
			t++;
			/* 
			 * for Schleife geht durch die Liste der oben berechneten Positionen für den ersten Stein
			 * durch
			 * 
			 */
			//int currScore = 0;
			//if (best.size() > 1) {
//			for (int l = y+1; l < playable.size() && l<best.size(); l++) {
//				map.placeStone(best.get(l), playable.get(l));
//				bestTurn += calculateBestScore(map, best, bestTurn, currScore);
//			}
			//}
		}
		//gameCtrl.getPlayerController().undoTurn(currentPlayer);
		return bestTurn;
	}

	/**
	 * Berechnet mit Steinen die Möglichkeit mit den meisten Punkten
	 * 
	 * @param map die aktuelle Map
	 * @param best ArrayList mit Steinen für die die größt Mögliche Punktzahl berechnet werden soll
	 * @param bestTurn aktuell bester Zug als String
	 * @param currScore aktuell bester Score im Zug
	 * @return String mit dem besten Zug nach Vorgaben von doTurn
	 */
	private String calculateBestScore(Map map, ArrayList<Stone> best, String bestTurn, int currScore) {
		ArrayList<Position> pTurn = map.getPlayableTurn();
		String currTurn = "";
		/*
		 * 1. for Schleife geht durch die größte Liste der Steine mit einer Gemeinsamkeit durch
		 * 2. for Schleife läuft durch die Liste mit den neu berechneten Positionen innerhalb des Zuges 
		 * if Anweisung prüft, ob Stein aus "best" plaziert werden kann und legt dann diesen und springt
		 * zum nächsten Stein in "best"
		 * Am Ende soll der currentScore berechnet werden und zwishengespeichert werden
		 * 
		 */
		
		for ( int k = 1; k < best.size(); k++ ) {
			for ( int m = 0; m < pTurn.size(); m++ ) {
				gameCtrl.getQwirkleStack().push();
				if (pTurn.get(m).isPossibleStone(best.get(k)) ) {
					map.placeStone(best.get(k), pTurn.get(m));
					
					// Zug als String speichern um ihn später noch zu wissen
					currTurn +=  pTurn.get(m).getxPos() + " " +  pTurn.get(m).getyPos() + " " +
							best.get(k).getSymbol() + " " + best.get(k).getColor() + "|";
					m = pTurn.size();
				}
				// Besten Zug merken
				if ( currScore < map.calculateCurrentScore() ) {
					currScore = map.calculateCurrentScore();
					bestTurn = currTurn;
				}
				// Zug zurücknehmen
				gameCtrl.getPlayerController().undoTurn(currentPlayer);
			}
		}
		if (best.size() <= 1) {
			gameCtrl.getPlayerController().undoTurn(currentPlayer);
		}
		return bestTurn;
	}
	
	private String calculateMedium() {
		Player currentPlayer = gameCtrl.getQwirkle().getCurrentPlayer();
		ArrayList<Stone> currentCubes = currentPlayer.getStones();
		String bestTurn = "";
		ArrayList<Position> currentPositions = gameCtrl.getQwirkle().getMap()
				.getPlayableTurn();
		for (int i = 0; i < currentCubes.size(); i++) {
			for (int j = 0; j < currentPositions.size(); j++) {
				if (currentPositions.get(j).isPossibleStone(currentCubes.get(i))) {
					bestTurn = "# " + currentPositions.get(j).getxPos() + " " + currentPositions.get(j).getyPos() + " " + currentCubes.get(i).getSymbol() + " " + currentCubes.get(i).getColor();
					gameCtrl.getPlayerController().doTurn(bestTurn);
					return bestTurn;
				}
			}
		}
		bestTurn = "+ " + currentCubes.get(0).getSymbol() + " " + currentCubes.get(0).getColor();
		gameCtrl.getPlayerController().doTurn(bestTurn);
		return bestTurn;
	}

	/**
	 * Die Methode nimmt die erste Liste aus den Steinen aus der Hand des aktuellen Spielers mit 
	 * einer Gemeinsamkeit.
	 * Die Steine aus der Liste werden platziert und die höchstmögliche Punktzahl wird ermittelt.
	 * Der Zug wird gespeichert und dann mit doTurn(String) am Ende ausgeführt
	 * 
	 */

	private String calculateLow() {
		Player currentPlayer = gameCtrl.getQwirkle().getCurrentPlayer();
		ArrayList<Stone> currentCubes = currentPlayer.getStones();
		String bestTurn = "";
		ArrayList<Position> currentPositions = gameCtrl.getQwirkle().getMap()
				.getPlayable();
		for (int i = 0; i < currentCubes.size(); i++) {
			for (int j = 0; j < currentPositions.size(); j++) {
				if (currentPositions.get(j).isPossibleStone(currentCubes.get(i))) {
					bestTurn = "# " + currentPositions.get(j).getxPos() + " " + currentPositions.get(j).getyPos() + " " + currentCubes.get(i).getSymbol() + " " + currentCubes.get(i).getColor();
					gameCtrl.getPlayerController().doTurn(bestTurn);
					return bestTurn;
				}
			}
		}
		if ( !gameCtrl.getQwirkle().isCubesVariant()) {
			bestTurn = "+ " + currentCubes.get(0).getSymbol() + " " + currentCubes.get(0).getColor();
		} else {
			bestTurn = "* " + currentCubes.get(0).getSymbol()+ " " + currentCubes.get(0).getColor();
		}
//		for ( Stone swap : currentCubes ) {
//			bestTurn += swap.getSymbol() + " " + swap.getColor() + "|";
//		}
//		bestTurn = "+ " + bestTurn.substring(0, bestTurn.length()-1);
		gameCtrl.getPlayerController().doTurn(bestTurn);
		return bestTurn;		
	}

	
	/**
	 * Methode erstellt Liste mit Listen von Steinen, die eine Gemeinsamkeit haben, Farbe oder Symbol
	 * 
	 * @return Liste von Listen mit Steinen die eine Gemeinsamkeit haben, entweder Farbe oder Symbol
	 */
	private ArrayList<ArrayList<Stone>> findMatchingStones() {
		ArrayList<ArrayList<Stone>> var = new ArrayList<ArrayList<Stone>>();
		ArrayList<Stone> curr = currentPlayer.getStones();
		for (int i = 0; i < curr.size()-1; i++) {
			ArrayList<Stone> temp = new ArrayList<Stone>();
			ArrayList<Stone> tempS = new ArrayList<Stone>();
			Stone currI = curr.get(i);
			temp.add(currI);
			tempS.add(currI);
			for ( int k = i+1; k < curr.size(); k++) {
				Stone currK = curr.get(k);
				if ( currI.getSymbol().equals(currK.getSymbol())) {
					temp.add(currK);
				} else {
					if ( currI.getColor().equals(currK.getColor())) {
						tempS.add(currK);
					}
				}
			}
			var.add(temp);
			var.add(tempS);
		}
		HashSet<ArrayList<Stone>> hashSet = new HashSet<ArrayList<Stone>>(var);
		var.clear();
		var.addAll(hashSet);		
		return var;
	}
	
	/**
	 * Sucht doppelte Steine/Würfel in der Hand des aktuellen Players
	 * @return ArrayList mit Steinen die in der Hand des aktuellen Spielers doppelt vorkommen
	 */
	private ArrayList<Stone> findDoubles() {
		ArrayList<Stone> temp = new ArrayList<Stone>();
		ArrayList<Stone> curr = currentPlayer.getStones();
		for (int i = 0; i < curr.size()-1; i++ ) {
			if ( curr.get(i).getColor().equals(curr.get(i+1).getColor()) && 
					curr.get(i).getSymbol().equals(curr.get(i+1).getSymbol())) {
				temp.add(curr.get(i+1));
				i++;
			}
		}
		return temp;
	}

}
