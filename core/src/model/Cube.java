package model;

import java.util.Random;

public class Cube extends Stone {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean rolled;
	
	/**
	 * Konstruktor
	 * 
	 * @param symbol 
	 * @param color
	 */
	public Cube(String symbol, String color) {
		super(symbol, color);
		this.setRolled(false);
	}

	/**
	 * Würfelt einen Würfel indem eine Zufallszahl zwischen 1 und 6 berechnet wird und per switch case
	 * das Symbol gesetzt wird 
	 */
	public void roll() {
		if ( ! this.isRolled()) {
			int random = new Random().nextInt(6) + 1;
			switch(random) {
				case 1: this.setSymbol("St"); break;
				case 2: this.setSymbol("Ra"); break;
				case 3: this.setSymbol("Kr"); break;
				case 4: this.setSymbol("Qu"); break;
				case 5: this.setSymbol("Bl"); break;
				case 6: this.setSymbol("So"); break;
			}
			this.setRolled(true);
			return;
		} else {
			System.err.println("Würfel schon gewürfelt!");
			return;
		}
	}

	public boolean isRolled() {
		return rolled;
	}

	public void setRolled(boolean rolled) {
		this.rolled = rolled;
	}
}
