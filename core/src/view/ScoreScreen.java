package view;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import java.io.IOException;
import java.util.ArrayList;

import model.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class ScoreScreen implements Screen {
	
	private MainGame mainGame;
	
	private SpriteBatch batch;
	private Sprite MAIN_SCREEN;

	private Stage stage;
	private Skin skin;
	private Table table;
	private Label highScoreLabel, cubes;
	private Label one, two, three, four, five, six, seven, eight, nine, ten;
	private Label first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth;
	private SelectBox<String> gameType, numOfCubes, numOfTiles;
	private TextButton back;

	public ScoreScreen(MainGame mainGame) {
	
		this.mainGame = mainGame;
	}

	@Override
	public void show() {

		batch = new SpriteBatch();
		
		MAIN_SCREEN = new Sprite(new Texture(Gdx.files.internal("img/main_screen.jpg")));
		MAIN_SCREEN.setSize(MainGame.S_WIDTH, MainGame.S_HEIGHT);
		
		stage = new Stage(new StretchViewport(MainGame.S_WIDTH, MainGame.S_HEIGHT));
		Gdx.input.setInputProcessor(stage);

		skin = new Skin(Gdx.files.internal("testUI/uiskin.json"));
		
		// Ueberschrift
		highScoreLabel = new Label("HIGHSCORE LISTEN", skin);
		highScoreLabel.setPosition(MainGame.S_WIDTH/2 - highScoreLabel.getWidth()/2, MainGame.S_HEIGHT - 100);
		
		// Drop Down fuer Spieltyp
		gameType = new SelectBox<String>(skin);
		gameType.setItems(new String[] {"Qwirkle", "Qwirkle Cubes"});
		gameType.setSize(150, 33);
		gameType.setPosition(MainGame.S_WIDTH/10.6f, MainGame.S_HEIGHT/1.2f);
		
		// Label(s) fuer die Anzahl der Wuerfel/Plaetchen
		cubes = new Label("Anzahl Plaetchen/Wuerfel:", skin);
		cubes.setPosition(MainGame.S_WIDTH/10.6f, MainGame.S_HEIGHT/1.3f);
				
		// Drop Down(s) fuer die Anzahl der Wuerfel/Plaetchen
		numOfCubes = new SelectBox<String>(skin);
		numOfCubes.setItems(new String[] {"90", "60", "30"});
		numOfCubes.setSize(50, 33);
		numOfCubes.setPosition(MainGame.S_WIDTH/3, MainGame.S_HEIGHT/1.3f);
				
		numOfTiles = new SelectBox<String>(skin);
		numOfTiles.setItems(new String[] {"108", "72", "36"});
		numOfTiles.setSize(50, 33);
		numOfTiles.setPosition(MainGame.S_WIDTH/4, MainGame.S_HEIGHT/1.3f);
		
		// Tabelle fuer die 10 besten Scores
		table = new Table();
		table.setPosition(MainGame.S_WIDTH/2 - 250, MainGame.S_HEIGHT/1.6f);
		
		one = new Label("1.", skin);
		two = new Label("2.", skin);
		three = new Label("3.", skin);
		four = new Label("4.", skin);
		five = new Label("5.", skin);
		six = new Label("6.", skin);
		seven = new Label("7.", skin);
		eight = new Label("8.", skin);
		nine = new Label("9.", skin);
		ten = new Label("10.", skin);
		
		first = new Label("", skin);
		second = new Label("", skin);
		third = new Label("", skin);
		fourth = new Label("", skin);
		fifth = new Label("", skin);
		sixth = new Label("", skin);
		seventh = new Label("", skin);
		eighth = new Label("", skin);
		ninth = new Label("", skin);
		tenth = new Label("", skin);
		
		table.add(one);
		table.add(first).spaceLeft(40).row();
		table.add(two);
		table.add(second).spaceLeft(40).row();
		table.add(three);
		table.add(third).spaceLeft(40).row();
		table.add(four);
		table.add(fourth).spaceLeft(40).row();
		table.add(five);
		table.add(fifth).spaceLeft(40).row();
		table.add(six);
		table.add(sixth).spaceLeft(40).row();
		table.add(seven);
		table.add(seventh).spaceLeft(40).row();
		table.add(eight);
		table.add(eighth).spaceLeft(40).row();
		table.add(nine);
		table.add(ninth).spaceLeft(40).row();
		table.add(ten);
		table.add(tenth).spaceLeft(40).row();
		
		back = new TextButton("ZURUECK", skin);
		back.setPosition(75, 10);
		back.setSize(150, 50);
		back.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				stage.addAction(sequence(moveTo(0, stage.getHeight(), .5f), run(new Runnable() {

					@Override
					public void run() {
						mainGame.setScreen(new MainScreen(mainGame));
					}
					
				})));
			}
		});
		
		stage.addListener(new InputListener() {
			
			public boolean keyDown(InputEvent event, int keycode) {
				
				if(keycode == Keys.ESCAPE) {
					
					mainGame.setScreen(new MainScreen(mainGame));
				}
				
				return true;
			}
		});
		
		stage.addActor(highScoreLabel);
		stage.addActor(gameType);
		stage.addActor(cubes);
		stage.addActor(numOfCubes);
		stage.addActor(numOfTiles);
		stage.addActor(table);
		stage.addActor(back);

		try{
			mainGame.getGameController().loadHighscore();
		}catch(IOException e) {
			mainGame.getGameController().saveHighscore();
		}
		
	}

	@Override
	public void render(float delta) {
		
		mainGame.cam.update();
		batch.setProjectionMatrix(mainGame.cam.combined);

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		float fadeTimeAlpha = 0.5f;
		MAIN_SCREEN.setColor(1.0f, 1.0f, 1.0f, fadeTimeAlpha);
		MAIN_SCREEN.draw(batch, fadeTimeAlpha);
		batch.end();
		
		stage.act(delta);
		stage.draw();
		
		setPlayers();
	}
	
	public void setPlayers() {
		
		ArrayList<Player> highScoreCubesQuick = mainGame.getGameController().getHighscoreList().getHighscore(30),
		highScoreCubesMedium = mainGame.getGameController().getHighscoreList().getHighscore(60),
		highScoreCubesLong = mainGame.getGameController().getHighscoreList().getHighscore(90),
		highScoreQwirkleQuick = mainGame.getGameController().getHighscoreList().getHighscore(36),
		highScoreQwirkleMedium = mainGame.getGameController().getHighscoreList().getHighscore(72),
		highScoreQwirkleLong = mainGame.getGameController().getHighscoreList().getHighscore(108);
		
		ArrayList<Label> labelList = new ArrayList<Label>();
		labelList.add(first);
		labelList.add(second);
		labelList.add(third);
		labelList.add(fourth);
		labelList.add(fifth);
		labelList.add(sixth);
		labelList.add(seventh);
		labelList.add(eighth);
		labelList.add(ninth);
		labelList.add(tenth);
		
		if(gameType.getSelected() == "Qwirkle Cubes") {
			
			if(numOfCubes.getSelected() == "30") {
			
				for(int i = 0; i < highScoreCubesQuick.size(); i++) {
		
						labelList.get(i).setText(highScoreCubesQuick.get(i).getName() + ": " + highScoreCubesQuick.get(i).getScore() + " Punkte");
				}
				
			}else if(numOfCubes.getSelected() == "60") {
				
				for(int i = 0; i < highScoreCubesMedium.size(); i++) {
					
					labelList.get(i).setText(highScoreCubesMedium.get(i).getName() + ": " + highScoreCubesMedium.get(i).getScore() + " Punkte");
				}
				
			}else if(numOfCubes.getSelected() == "90") {
				
				for(int i = 0; i < highScoreCubesLong.size(); i++) {
					
					labelList.get(i).setText(highScoreCubesLong.get(i).getName() + ": " + highScoreCubesLong.get(i).getScore() + " Punkte");
				}
			}
			
		}else if(gameType.getSelected() == "Qwirkle") {
			
			if(numOfTiles.getSelected() == "36") {
				
				for(int i = 0; i < highScoreQwirkleQuick.size(); i++) {
		
						labelList.get(i).setText(highScoreQwirkleQuick.get(i).getName() + ": " + highScoreQwirkleQuick.get(i).getScore() + " Punkte");
				}
				
			}else if(numOfTiles.getSelected() == "72") {
				
				for(int i = 0; i < highScoreQwirkleMedium.size(); i++) {
		
						labelList.get(i).setText(highScoreQwirkleMedium.get(i).getName() + ": " + highScoreQwirkleMedium.get(i).getScore() + " Punkte");
				}
				
			}else if(numOfTiles.getSelected() == "108") {
				
				for(int i = 0; i < highScoreQwirkleLong.size(); i++) {
		
						labelList.get(i).setText(highScoreQwirkleLong.get(i).getName() + ": " + highScoreQwirkleLong.get(i).getScore() + " Punkte");
				}
			}
		}
	}

	@Override
	public void resize(int width, int height) {
		
		mainGame.cam.viewportWidth = MainGame.S_WIDTH;
        mainGame.cam.viewportHeight = MainGame.S_WIDTH * height/width;
        mainGame.cam.update();
        
        stage.getViewport().update(width, height, true);
	}

	@Override
	public void dispose() {

		batch.dispose();
		stage.dispose();
		skin.dispose();
		MAIN_SCREEN.getTexture().dispose();
	}
	
	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

}
