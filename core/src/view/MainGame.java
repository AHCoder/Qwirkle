package view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;

import control.GameController;

public class MainGame extends Game {
	
	public final static float S_WIDTH = 1920;
	public final static float S_HEIGHT = 1080;
	
	public OrthographicCamera cam;
	
	private GameController gameCtrl;
	
	@Override
	public void create () {
		
		cam = new OrthographicCamera(S_WIDTH, S_HEIGHT);
        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
        cam.update();
        
        Assets.load();
        Assets.manager.finishLoading();
        
        // MainGame wird uebergeben
        gameCtrl = new GameController();
        
		setScreen(new MainScreen(this));
	}

	@Override
	public void render () {
		
		super.render();
	}
	
	@Override
	public void resize(int width, int height) {

		super.resize(width, height);
	}

	@Override
	public void pause() {

		super.pause();
	}

	@Override
	public void resume() {

		super.resume();
	}

	@Override
	public void dispose() {

		super.dispose();
		Assets.dispose();
	}

	public GameController getGameController() {
		
		return gameCtrl;
	}

	public void setGameController(GameController gameCtrl) {
		
		this.gameCtrl = gameCtrl;
	}
}
