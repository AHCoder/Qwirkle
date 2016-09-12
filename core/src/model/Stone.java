package model;

import java.io.Serializable;

public class Stone implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4530738789742271405L;

	private String color;

	private String symbol;

	public Stone(String symbol, String color) {
		this.symbol = symbol;
		this.color = color;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

}
