package view;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class GameScreenExperimental implements Screen, InputProcessor{
	class Position{
		private float x, y;
		
		public Position(){
			x = y = 0;
		}
		
		public Position(float x, float y){
			this.x = x;
			this.y = y;
		}
		
		public void setX(float x){
			this.x = x;
		}
		
		public void setY(float y){
			this.y = y;
		}
		
		public float getX(){
			return x;
		}
		
		public float getY(){
			return y;
		}
	}
	
	private OrthographicCamera camera;
	private Stage stage;
	private Image iBackground;
	private ArrayList<Position> realPos;
	private ArrayList<Position> modelPos;
	private float offsetX;
	private float offsetY;
	private float downX;
	private float downY;
	private boolean pan;
	
	@Override
	public void show() {
		camera = new OrthographicCamera(MainGame.S_WIDTH, MainGame.S_HEIGHT);
		StretchViewport viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
		stage = new Stage(viewport);
		iBackground = new Image(new Texture(Gdx.files.internal("img/table.png")));
		iBackground.setSize(MainGame.S_WIDTH*3,MainGame.S_HEIGHT*3);
		iBackground.setPosition(MainGame.S_WIDTH/2-iBackground.getWidth()/2, MainGame.S_HEIGHT/2-iBackground.getHeight()/2);
		stage.addActor(iBackground);
		
		realPos = new ArrayList<Position>();
		modelPos = new ArrayList<Position>();
		offsetX = offsetY = 0;
		pan = false;
		
//		InputMultiplexer multiplexer = new InputMultiplexer(this, detector);
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Vector3 touch = new Vector3(screenX, screenY, 0);
		camera.unproject(touch);
		
		downX = touch.x;
		downY = touch.y;
		Image im = new Image(new Texture(Gdx.files.internal("gameStonePattern/stern_blau.png")));
		im.setSize(50, 50);
		float posX = touch.x - im.getWidth()/2;
		float posY = touch.y - im.getHeight()/2;;
		float modelPosX = 0;
		float modelPosY = 0;
		if(realPos.isEmpty()){
			im.setPosition(posX, posY);
			realPos.add(new Position(posX, posY));
			modelPos.add(new Position(0,0));
			stage.addActor(im);
		}else{
			boolean set = false;
			boolean notExists = true;
			float x = 0;
			float y = 0;
			for(int i = 0; i < realPos.size(); i++){
				x = realPos.get(i).getX();
				y = realPos.get(i).getY();
				modelPosX = modelPos.get(i).getX();
				modelPosY = modelPos.get(i).getY();
				
				if(posX <= (x-25) && posX >= (x-75) && posY <= (y+25) && posY >= (y-25)){
					posX = x - 50;
					posY = y;
					modelPosX -= 1;
					set = true;
				}else if(posX >= (x+25) && posX <= (x+75) && posY <= (y+25) && posY >= (y-25)){
					posX = x + 50;
					posY = y;
					modelPosX += 1;
					set = true;
				}else if(posX <= (x+25) && posX >= (x-25) && posY >= (y+25) && posY <= (y+75)){
					posX = x;
					posY = y + 50;
					modelPosY += 1;
					set = true;
				}else if(posX <= (x+25) && posX >= (x-25) && posY <= (y-25) && posY >= (y-75)){
					posX = x;
					posY = y - 50;
					modelPosY -= 1;
					set = true;
				}
				if( posX == x && posY == y){
					notExists = false;
					break;
				}
			}
			if(set && notExists){
				im.setPosition(posX, posY);
				realPos.add(new Position(posX, posY));
				modelPos.add(new Position(modelPosX, modelPosY));
				stage.addActor(im);
//				System.out.println(positions.size());
			}
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		pan = false;
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		Vector3 touchDrag = new Vector3(screenX, screenY, 0);
		camera.unproject(touchDrag);
		
		float deltaX = -downX + touchDrag.x;
		float deltaY = -downY + touchDrag.y;
		if(Math.abs(deltaX) >= 10 || Math.abs(deltaY) >= 10 || pan){
			pan = true;
			downX = touchDrag.x;
			downY = touchDrag.y;
			camera.translate(-deltaX, deltaY);
			camera.update();
			offsetX = Gdx.graphics.getWidth()/2 - camera.position.x;
			offsetY = Gdx.graphics.getHeight()/2 - camera.position.y;
		}
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		if(camera.zoom + amount > 0 && camera.zoom + amount< 4)
			camera.zoom += amount;
		return false;
	}
	
}