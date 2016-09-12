package model;

import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import control.GameController;

public class MapTest {
	
	Stone ersterStein = new Stone("Ra","gr");
	
	
	Map map;
	Player player1 = new Player("Spieler");
	Bot bot = new Bot("Bot","Low");
	Bag bag = new Bag(108,false);
	ArrayList<Player> playerlist = new ArrayList<Player>();
	
	
	
	GameController gamectrl = new GameController();
	

	
	//Position firstPosition = map.getPosition(0, 0);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}
	
	
	
	public void printmap() {
		
		for(int i = 10;i>=-10;i--) {
			for(int j = -10;j<=10;j++) {
				if(map.getPosition(j, i) == null) {
					System.out.print("|  |");
				} else {
					if(map.getPosition(j, i).isFree()) {
						if(map.getPosition(j, i).getPossibleStones().size() == 0) {
							System.out.print("|--|");	
						} else {
						System.out.print("|++|");
						}
					} else {
						System.out.print("|"+map.getPosition(j, i).getStone().getSymbol()+"|");
					}
				}
				if(i == 10 && j == 10) {
				for(int k = 0;k<6;k++) {
					System.out.print("|" + gamectrl.getQwirkle().getPlayerList().get(1).getStones().get(k).getSymbol()+"|");
				}
				}
				
				
			}
			System.out.println();
			
			for(int j = -10;j<=10;j++) {
				if(map.getPosition(j, i) == null) {
					System.out.print("|__|");
				} else {
					if(map.getPosition(j, i).isFree()) {
						if(map.getPosition(j, i).getPossibleStones().size() == 0) {
							System.out.print("|--|");	
						} else {
						System.out.print("|++|");
						}
					} else {
						System.out.print("|"+map.getPosition(j, i).getStone().getColor()+"|");
					}
				}
				if(i == 10 && j == 10) {
				for(int k = 0;k<6;k++) {
					System.out.print("|" + gamectrl.getQwirkle().getPlayerList().get(1).getStones().get(k).getColor()+"|");
				}
				}
			}
			System.out.println();
		}
		
		//System.out.println(map.getAllPositions().size());
	}

	@Before
	public void setUp() throws Exception {
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
		
		for(int i = 0;i<symbols.size();i++){
			for (int j =0;j<colors.size();j++) {
				stones.add(new Stone(symbols.get(i),colors.get(j)));
			}
		}
		
		playerlist.add(player1);
		playerlist.add(bot);
		gamectrl.newGame(36, false, playerlist, "");
		map = gamectrl.getQwirkle().getMap();
		//printStoneList(stones);

		
		
		
		
		Boolean weiter = true;
		Stone stein;
		int xPos = 0,yPos = 0;
		while(weiter == true) {
		
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter Symbol:");
        String symbol = br.readLine();
        System.out.print("Enter Color:");
        String color = br.readLine();
        stein = new Stone(symbol,color);
        System.out.println("Enter xPos:");
        try{
            xPos = Integer.parseInt(br.readLine());
        }catch(NumberFormatException nfe){
            System.err.println("Invalid Format!");
        }
        System.out.println("Enter yPos:");
        try{
            yPos = Integer.parseInt(br.readLine());
        }catch(NumberFormatException nfe){
            System.err.println("Invalid Format!");
        }
        
        Position placePos = map.getPosition(xPos, yPos);
        if(placePos != null && map.placeStone(stein, placePos)) {
        
		printmap();
        } else {
        	System.out.println("Legen von " + symbol + " " + color + " ist an der Position (" + xPos + "," + yPos + ") nicht möglich!");
        }
        
        System.out.println("Zug beenden? j/n");
        String ende = br.readLine();
        
        if(ende.equals("j")) {
        	System.out.println(map.calculateCurrentScore() + " Punkte im Zug erlangt");
        	gamectrl.getPlayerController().endTurn();
        }
        
        printmap();
        
        
		}
        
        
		//firstPosition.setPossibleStones(stones);
		System.out.println("Sete Ra gr auf die Position (0,0)");
		//System.out.println(map.placeStone(ersterStein, firstPosition));
		//System.out.println(map.calculateCurrentScore());
		System.out.println("Setze Ra ge auf die Position (0,1)");
		System.out.println(map.placeStone(new Stone("Ra","ge"), map.getPosition(0, 1)));
		System.out.println(map.placeStone(new Stone("Ra","li"), map.getPosition(0, -1)));
		map.clearTurn();
		System.out.println(map.placeStone(new Stone("Ra","bl"), map.getPosition(0, -2)));
		System.out.println(map.placeStone(new Stone("Ra","li"), map.getPosition(1, -2)));
		System.out.println(map.calculateCurrentScore());
		map.clearTurn();
		System.out.println(map.placeStone(new Stone("Ra","or"), map.getPosition(0, -3)));
		map.clearTurn();
		System.out.println(map.placeStone(new Stone("Ra","ro"), map.getPosition(0, -4)));
		
		System.out.println(map.calculateCurrentScore());
		//printPositionList(map.getPlayableTurn());
		printStoneList(map.getPosition(0,-4).getPossibleStones());
	
	}

	private void printStoneList(ArrayList<Stone> stones) {
		System.out.println(stones.size());
		for(int i = 0;i<stones.size();i++) {
			System.out.println("Color: " + stones.get(i).getColor() + " Symbol: " + stones.get(i).getSymbol());
		}
	}
	
	private void printPositionList(ArrayList<Position> positions) {
		System.out.println(positions.size());
		for(int i = 0;i<positions.size();i++) {
			System.out.println("(" + positions.get(i).getxPos()+ "," + positions.get(i).getyPos()+")");
		}
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	

}
