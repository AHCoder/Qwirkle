package model;

import java.io.Serializable;
import java.util.Stack;

import control.GameController;
import control.IOController;

public class QwirkleStack implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GameController gameCtrl;
	private Stack<Qwirkle> stack;
	
	public QwirkleStack(Qwirkle qwirkle, GameController gameCtrl) throws NullPointerException {
		stack = new Stack<Qwirkle>();
		if (gameCtrl == null)
			throw new NullPointerException("Referenz ist null");
		else
			this.gameCtrl = gameCtrl;
	}
	
	public void push() {
		IOController ioCtrl = gameCtrl.getIoController();
		ioCtrl.save();
		Qwirkle currentQwirkle = ioCtrl.loadQwirkle();
		this.stack.push(currentQwirkle);
	}
	
	public Qwirkle pop() {
		return (Qwirkle) this.stack.pop();
	}
	
}
