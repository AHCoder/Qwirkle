package control;

import java.util.ArrayList;

import model.Bag;
import model.Cube;
import model.Map;
import model.Player;
import model.Position;
import model.Qwirkle;
import model.Stone;

public class PlayerController {

	private GameController gameCtrl;
	private String[] symbols = {"st", "ra", "kr", "qu", "bl", "so"};
	private String[] colors = {"gr", "ge", "ro", "bl", "li", "or"};

	// private Qwirkle qwirkle = gameCtrl.getQwirkle();

	/**
	 * @param gameCtrl
	 *            Referenz auf zentralen Controller um Auschtausch zwischen den
	 *            Contorllern möglich zu machen
	 * @throws NullPointerException
	 *             NullPointerException wird geworfen wenn der Parameter null
	 *             ist
	 */
	public PlayerController(GameController gameCtrl)
			throws NullPointerException {
		if (gameCtrl == null)
			throw new NullPointerException("Referenz ist null");
		else
			this.gameCtrl = gameCtrl;
	}

	/**
	 * Methode zum Würfel würfeln
	 * 
	 * @param cubeIndex
	 *            gibt die Stelle des zu würfelnden Würfels vom Spieler an
	 * @return gibt bei Erfolg einen String mit dem Symbol und der Farbe des
	 *         Würfels getrennt durch ein Leerzeichen aus. Bei Misserfolg wird
	 *         folgender String zurückgegeben: "ERROR: Cube already rolled!"
	 */
	public String rollCube(int cubeIndex) {
		Player currentPlayer = gameCtrl.getQwirkle().getCurrentPlayer(); // qwirkle.getCurrentPlayer();
		ArrayList<Stone> currentCubes = currentPlayer.getStones();
		Cube cube = (Cube) currentCubes.get(cubeIndex);
		if (!cube.isRolled()) {
			cube.roll();
			return cube.getSymbol() + " " + cube.getColor();
		} else {
			return "ERROR: Cube already rolled!";
		}

	}

	/**
	 * Methode zum Versuchen einen Stein auf das Spielfeld zu legen
	 * 
	 * @param stoneIndex
	 *            der Index des Steins vom aktuellen Spieler
	 * @param position
	 *            die Position auf der Map, auf die der Stein gelegt werden soll
	 * @return
	 * @true wenn das Legen an die Position geklappt hat. Der Stein wird dem
	 *       Spieler weggenommen
	 * @false wenn das Legen an dieser Position nicht möglich ist
	 */

	public boolean placeCube(int stoneIndex, Position position) {
		Qwirkle qwirkle = gameCtrl.getQwirkle();
		Map map = qwirkle.getMap();
		Player currentplayer = qwirkle.getCurrentPlayer();
		Stone placeStone = currentplayer.getStones().get(stoneIndex);
		
        Position realPosition = map.getPosition(position.getxPos(), position.getyPos()); 	                 
	     
		
		if (realPosition != null && map.placeStone(placeStone, realPosition)) {
			currentplayer.getStones().remove(stoneIndex);
			return true;
		} else {
			return false;
		}
	}

	public void undo() {
		Qwirkle lastQwirkle = gameCtrl.getQwirkleStack().pop();
		gameCtrl.setQwirkle(lastQwirkle);
	}

	public void undoTurn(Player player) {
		undo();
		while (!player.getName().equals(
				gameCtrl.getQwirkle().getCurrentPlayer().getName())) {
			undo();
		}
		gameCtrl.getQwirkle().getCurrentPlayer().setCheater(true);
	}

	/**
	 * Wird aufgerufen wenn der Spieler auf Zug beenden in der Gui klickt
	 * @author Patrick
	 * @return true, falls das beenden des Zugs möglich war und das Spiel nicht
	 *         beendet ist und false, falls das Spiel beendet ist
	 */
	public boolean endTurn() {
		Map map = gameCtrl.getQwirkle().getMap();
		Player currentPlayer = gameCtrl.getQwirkle().getCurrentPlayer();
		Bag bag = gameCtrl.getQwirkle().getBag();

		int currentScore = map.calculateCurrentScore();
		map.clearTurn();
		Boolean sum = true;
		if (currentPlayer.getStones().isEmpty() && bag.getBagStones().isEmpty()) {
			currentPlayer.addPoints(currentScore+6);
			ArrayList<Player> playerList = gameCtrl.getQwirkle().getPlayerList();
			Player winner = gameCtrl.getQwirkle().getCurrentPlayer();
			Player score;
			for(int i = 0; i < playerList.size(); i++){
				score = playerList.get(i);
				if(score.getScore() > winner.getScore()){
					winner = score;
				}
			}
			if(!winner.isCheater() || !winner.isBot()){
				gameCtrl.addToHighscore(winner);
			}
			
			sum = false;
		} else {
			currentPlayer.addPoints(currentScore);
			drawStones(currentPlayer);
			gameCtrl.nextPlayer();
			gameCtrl.getQwirkleStack().push();
			sum = true;
		}
		if ( gameCtrl.getQwirkle().isCubesVariant()) {
			ArrayList<Stone> stoneList = currentPlayer.getStones();
			for ( Stone stone : stoneList) {
				Cube cube = (Cube) stone;
				cube.setRolled(false);
			}
		}
		return sum;
	}

	/**
	 * Methode um Steine zu tauschen
	 * 
	 * @param stonesIndex
	 *            Liste in der die Indizes der zu tauschenden Würfel des
	 *            Spielers sind
	 * @return null bei leerem Parameter oder bei falschen Werten in der
	 *         übergebenen Liste Bei Erfolg wird eine ArrayList<String>
	 *         zurückgegeben in der die Steine des Spielers als String
	 *         "[SYMBOL] [COLOR]" gespeichert sind
	 */
	public ArrayList<String> swapStones(int[] stonesIndex) {
		if (!stonesIndex.equals(null)) {
			Player currentPlayer = gameCtrl.getQwirkle().getCurrentPlayer(); // qwirkle.getCurrentPlayer();
			ArrayList<Stone> currentStones = currentPlayer.getStones();
			Bag bag = gameCtrl.getQwirkle().getBag(); // qwirkle.getBag();
			ArrayList<Stone> swapStoneList = new ArrayList<Stone>();
			for (int index : stonesIndex) {
				if (index >= 0 && index <= 5) {
					swapStoneList.add(currentStones.get(index));
					currentStones.remove(index);
				} else {
					return null;
				}
			}
			ArrayList<Stone> newStones = bag.swapStones(swapStoneList);
			for (Stone stoneIndex : newStones) {
				currentPlayer.getStones().add(stoneIndex);
			}
			currentStones = currentPlayer.getStones();
			ArrayList<String> stringList = new ArrayList<String>();
			for (Stone stoneIndex : currentStones) {
				stringList.add(stoneIndex.getSymbol() + " "
						+ stoneIndex.getColor());
			}
			//endTurn();
			return stringList;
		} else {
			return null;
		}
	}

	/**
	 * @return Wenn eine Position gefunden wurde an die ein Stein eines Spielers
	 *         gelegt werden kann, wird ein String mit dem Symbol und der Farbe
	 *         des Steins und die x- und y-Position zurückgegeben Format des
	 *         Rückgabestrings: "SS CC X Y" -- SS für Symbol -- CC für die Farbe
	 *         -- X / Y für die Koordinate Wenn keine passende Position gefunden
	 *         wurde, wird "Würfeln Sie einen Würfel" zurückgegeben
	 * 
	 */
	public String showHint() {
		Player currentPlayer = gameCtrl.getQwirkle().getCurrentPlayer();
		ArrayList<Stone> currentCubes = currentPlayer.getStones();
		ArrayList<Position> currentPositions = gameCtrl.getQwirkle().getMap()
				.getPlayable();
		for (Stone stone : currentCubes) {
			for (Position position : currentPositions) {
				if (position.isPossibleStone(stone)) {
					currentPlayer.setCheater(true);
					return stone.getSymbol() + " " + stone.getColor() + " "
							+ position.getxPos() + " " + position.getyPos();
				}
			}
		}

		return "Wuerfeln Sie einen Wuerfel";
	}

	// Spieler zieht Steine nach
	public void drawStones(Player player) {
		ArrayList<Stone> playerStones = player.getStones();
		while (playerStones.size() < 6) {
			Stone newStone = gameCtrl.getQwirkle().getBag().drawStone();
			if (newStone != null) {
				playerStones.add(newStone);
			} else {
				break;
			}
		}
		player.setStones(playerStones);
	}

	/**
	 * Methode die einen String in einen Zug umwandelt
	 * 
	 * @param turn: String nach Vorgabe
	 * @return 
	 * 			true: wenn erfogreich
	 * 			false: wenn ein Parse-Error kommt
	 */
	public boolean doTurn(String turn){
		Qwirkle qwirkle = gameCtrl.getQwirkle();
		boolean executed = false;
		
		int index = 0;
		
		Player player = qwirkle.getCurrentPlayer();
		
	
		if(turn.startsWith("#")){			//SPIELZUG
			String cmd[] = turn.substring(2).split("\\|");
			String cmds[][] = new String[cmd.length][4];
			for(int i = 0; i < cmd.length; i++){
				if(cmd[i].split(" ").length != 4){
					for(int j = 0; j < cmd[i].split(" ").length; j++){
						System.out.println(cmd[i].split(" ")[j] + j + ":"+ i);
					}
					return false;
				}
				cmds[i] = cmd[i].split(" ");
				if(!isInteger(cmds[i][0]) || !isInteger(cmds[i][1]) || !properSymbol(cmds[i][2]) || !properColor(cmds[i][3])){
					return false;
				}
				executed = true;
			}
			int[] stonesIndex = new int[cmds.length];
			for ( int k = 0; k < cmds.length; k++) {
				int count = 0;
				for(int j = 0; j < player.getStones().size(); j++) {
					Cube playerStone = (Cube) player.getStones().get(j);
					if ( playerStone.getSymbol().equals(cmds[k][2]) && playerStone.getColor().equals(cmds[k][3]) && count ==0 ) {
						stonesIndex[index] = j;
						index++;
						count++;
					}
				}
			}
						
			for(int i = 0; i < stonesIndex.length; i++){
				placeCube(stonesIndex[i], new Position(Integer.parseInt(cmds[i][0]), Integer.parseInt(cmds[i][1])));
			}
		}else if(turn.startsWith("+")){			//TAUSCHEN
			ArrayList<Stone> swapStones = new ArrayList<Stone>();
			String cmd[] = turn.substring(2).split("\\|");
			String cmds[][] = new String[cmd.length][2];
			for(int i = 0; i < cmd.length; i++){
				if(cmd[i].split(" ").length != 2)
					return false;
				cmds[i] = cmd[i].split(" ");
				if(!properSymbol(cmds[i][0]) || !properColor(cmds[i][1])){
					return false;
				}
				swapStones.add(new Stone(cmds[i][0], cmds[i][1]));
				executed = true;
			}
			int[] stonesIndex = new int[swapStones.size()];
			for ( int k = 0; k < swapStones.size(); k++) {
				int count = 0;
				for(int j = 0; j < player.getStones().size(); j++) {
					Cube playerStone = (Cube) player.getStones().get(j);
					if ( playerStone.getSymbol().equals(swapStones.get(k).getSymbol()) && playerStone.getColor().equals(swapStones.get(k).getColor())&& count ==0 ) {
						stonesIndex[index] = j;
						index++;
						count++;
					}
				}
			}
			swapStones(stonesIndex);
		}else{
			if (turn.startsWith("*")) {
				String cmd[] = turn.substring(2).split("\\|");
				String cmds[][] = new String[cmd.length][2];
				for(int i = 0; i < cmd.length; i++){
					if(cmd[i].split(" ").length != 2)
						return false;
					cmds[i] = cmd[i].split(" ");
					if(!properSymbol(cmds[i][0]) || !properColor(cmds[i][1])){
						return false;
					}
					executed = true;
				}
				for ( int k = 0; k < cmds.length; k++) {
					int count = 0;
					for(int j = 0; j < player.getStones().size(); j++) {
						Cube playerStone = (Cube) player.getStones().get(j);
						if ( playerStone.getSymbol().equals(cmds[k][0]) && playerStone.getColor().equals(cmds[k][1])&& count ==0 ) {
							
							index = j;
							count++;
						}
					}
				}
				rollCube(index);
			} else{
				return false;
			}
		}
		
		if(executed){
			return endTurn();
		}else{
			return false;
		}
	}
	
	private boolean isInteger(String i){
		try{
			Integer.parseInt(i);
		}catch(NumberFormatException e){
			return false;
		}
		return true;
	}
	
	private boolean properColor(String c){
		for(int i = 0; i < colors.length; i++){
			if(c.toLowerCase().equals(colors[i]))
				return true;
		}
		System.out.println("NOT PROPER COLOR");
		return false;
	}
	
	private boolean properSymbol(String s){
		for(int i = 0; i < symbols.length; i++){
			if(s.toLowerCase().equals(symbols[i]))
				return true;
		}
		System.out.println("NOT PROPER SYMBOL");
		return false;
	}
}
