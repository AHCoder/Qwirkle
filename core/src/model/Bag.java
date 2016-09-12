package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Bag implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8166983945696515014L;

	private int amount;

	private ArrayList<Stone> bagStones;
	private int bagSize;

	/**
	 * Konstruktor zu generieren des Bags wenn eine Anzhal (int) übergeben wird
	 * 
	 * @param size
	 */
	public Bag(int size, boolean cubesVariant) {
		this.amount = size;
		int count = 0;
		bagSize=size;
		bagStones = new ArrayList<Stone>();
		
		String[] color = {"gr","ge","ro","bl","li","or"};
		String[] symbol = {"St","Ra","Kr","Qu","Bl","So"};
		
		if ( cubesVariant == false) {
			count = size/36;
			for(int a = 0; a < count; a++) {
				for (int i = 0; i < color.length; i++) {
					for (int k = 0; k < symbol.length; k++) {
						bagStones.add(new Cube(symbol[k],color[i]));
					}	
				}
			}
		} else {
			count = size/30;
			for(int a = 0; a < count; a++) {
				for (int i = 0; i < color.length; i++) {
					for (int k = 0; k < symbol.length-1; k++) {
						bagStones.add(new Cube(symbol[k],color[i]));
					}	
				}
			}
		}		
		bagStones = mixBag(bagStones);
		
	}

	/**
	 * Konstruktor zum generieren des Bags, wenn ein String übergeben wird
	 * 
	 * @param stoneList
	 */
	public Bag(String stoneList) {
		bagStones = new ArrayList<Stone>();
		String[] newBag = stoneList.substring(2).split("\\|");
		
		for (int i = 0; i < newBag.length; i++) {
			String[] newStone = newBag[i].split(" ");
			bagStones.add(new Cube(newStone[0], newStone[1]));
		}
		amount = bagStones.size();
	}
	
	public ArrayList<Stone> mixBag(ArrayList<Stone> stones) {
		ArrayList<Stone> mixedStones = new ArrayList<Stone>();
		
		while (!stones.isEmpty()) {
			int random = new Random().nextInt(stones.size());
			mixedStones.add(stones.get(random));
			stones.remove(random);
		}
		
		return mixedStones;
	}

	/**
	 * Methode zum Tauschen der Steine
	 * 
	 * @param stones ArrayList an Steinen, die getauscht werden sollen
	 * @return gibt die neuen Steine in einer ArrayList zurück
	 * @preconditions Bag darf nicht leer sein
	 * @postconditions der amount des Bags verändert sich nicht
	 */
	public ArrayList<Stone> swapStones(ArrayList<Stone> stones) {
		ArrayList<Stone> newStones = new ArrayList<Stone>();
		int size = stones.size();
		addStones(stones);
		
		if (bagStones.size() >= size) {
			for (int i=0; i<size; i++) {
				
				newStones.add(bagStones.get(i));
			}
		}
		removeFirstStones(size);
		return newStones;
	}
	
	/**
	 * Hängt die Steine aus der übergebenen ArrayList den Bag an
	 * 
	 * @param stones ArrayList an Steinen die dem Bag angehängt werden sollen
	 * @postconditions der Bag enthält, die Anzahl der Steine in der Liste, mehr an Steinen
	 */
	public void addStones(ArrayList<Stone> stones) {
		for(int i = 0; i<stones.size(); i++) {
			Stone oldStone = stones.get(i);
			bagStones.add(oldStone);
			this.amount++;
			
		}
	}
	
	/**
	 * Löscht so viele Steine vorne aus dem Bag, wie die Zahl die übergeben wird
	 * 
	 * @param num Anzahl an Steinen die vorne aus dem Bag gelöscht werden sollen
	 * @preconditions der Bag muss mindestens einen Stein enthalten und darf nicht null sein
	 */
	public void removeFirstStones(int num){
		if(!bagStones.isEmpty()|| bagStones != null) {
			for(int i = 0; i < num; i++) {
				bagStones.remove(0);
				this.amount--;
			}
		}
	}
	
	/**
	 * Methode zum Steine nachziehen, löscht den ersten Stein aus dem Bag
	 * 
	 * @return gibt den ersten Stein aus dem Bag wieder
	 * 			wenn keine Steine mehr im Bag vorhanden sind wird null zurückgegeben
	 * @postconditions der Bag enthält einen Stein weniger
	 */
	public Cube drawStone() {
		if (!bagStones.isEmpty() && amount != 0) {
			Cube actualStone = (Cube) bagStones.get(0);
			removeFirstStones(1);
			return actualStone;
		} else {
			return null;
		}
		
	}

	/**
	 * @return bagStones
	 */
	public ArrayList<Stone> getBagStones() {
		return bagStones;
	}

	/**
	 * @param bagStones the bagStones to set
	 */
	public void setBagStones(ArrayList<Stone> bagStones) {
		this.bagStones = bagStones;
	}

	/**
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	/**
	 * @return the starting size of the bag
	 */
	public int getSize(){
		return bagSize;
	}
	
	
}
