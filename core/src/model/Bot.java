package model;

public class Bot extends Player {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String difficulty;
	private boolean isBot;
	
	public Bot(String name, String difficulty) {
		super(name);
		this.difficulty = difficulty;
		isBot = true;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}
	
	public boolean isBot(){
		return isBot;
	}

}
