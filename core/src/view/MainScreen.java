package view;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class MainScreen implements Screen {
	
	private MainGame mainMenu;
	
	private SpriteBatch batch;
	private Sprite MAIN_SCREEN;
	
	private Stage stage;
	private Skin skin;
	private Table table;
	private TextButton newGame, loadGame, highScore, manual, exit;
	
	public MainScreen(MainGame mainMenu) {
		
		this.mainMenu = mainMenu;
	}

	@Override
	public void show() {
		
		batch = new SpriteBatch();
		
		MAIN_SCREEN = new Sprite(new Texture(Gdx.files.internal("img/main_screen.jpg")));
		MAIN_SCREEN.setSize(MainGame.S_WIDTH, MainGame.S_HEIGHT);

		stage = new Stage(new StretchViewport(MainGame.S_WIDTH, MainGame.S_HEIGHT));
		
		Gdx.input.setInputProcessor(stage);
		
		skin = new Skin(Gdx.files.internal("testUI/uiskin.json"));
		
		table = new Table();
		table.setPosition(1440, 540);		
		
		newGame = new TextButton("NEUES SPIEL", skin);
		newGame.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				stage.addAction(sequence(moveTo(0, stage.getHeight(), .5f), run(new Runnable() {

					@Override
					public void run() {
						mainMenu.setScreen(new SettingsScreen(mainMenu));
					}
				})));
			}
		});
		newGame.pad(20);
		
		loadGame = new TextButton("SPIEL LADEN", skin);
		
		//Namen der Spieler werden korrekt angezeigt, aber die gelegten Steine nicht!
		loadGame.addListener(new ClickListener(){

			@Override
			public void clicked(InputEvent event, float x, float y) {
				stage.addAction(run(new Runnable() {

					@Override
					public void run() {
						try{
							mainMenu.getGameController().getIoController().load();
							mainMenu.setScreen(new GameScreen(mainMenu));
						}catch(IOException e){
							new Dialog("FEHLER BEIM LADEN", skin){
								{
									text("Ihr Spiel konnte nicht geladen werden!");
									button("   OK   ", "OK");
								}
							}.show(stage);
						}
					}
				}));
			}
		});
		loadGame.pad(20);
		
		highScore = new TextButton("HIGHSCORE", skin);
		highScore.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				stage.addAction(sequence(moveTo(0, stage.getHeight(), .5f), run(new Runnable() {

					@Override
					public void run() {
						
						mainMenu.setScreen(new ScoreScreen(mainMenu));
					}
				})));
			}
		});
		highScore.pad(20);
		
		manual = new TextButton("ANLEITUNG", skin);
		manual.pad(20);
		manual.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				stage.addAction(sequence(moveTo(0, stage.getHeight(), .5f), run(new Runnable() {

					@Override
					public void run() {
						mainMenu.setScreen(new Manual(mainMenu));
					}
					
				})));
			}
		});
		
		exit = new TextButton("BEENDEN", skin);
		exit.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				stage.addAction(sequence(moveTo(0, stage.getHeight(), .5f), run(new Runnable() {

					@Override
					public void run() {
						Gdx.app.exit();
					}
					
				})));
			}
		});
		exit.pad(20);
		
		table.add(newGame).width(125).spaceBottom(15).row();
		table.add(loadGame).width(125).spaceBottom(15).row();
		table.add(highScore).width(125).spaceBottom(15).row();
		table.add(manual).width(125).spaceBottom(15).row();
		table.add(exit).width(125);
		
		stage.addActor(table);
	}

	@Override
	public void render(float delta) {
		
		mainMenu.cam.update();
		batch.setProjectionMatrix(mainMenu.cam.combined);
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		MAIN_SCREEN.draw(batch);
		batch.end();
		
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		
		mainMenu.cam.viewportWidth = MainGame.S_WIDTH;
        mainMenu.cam.viewportHeight = MainGame.S_WIDTH * height/width;
        mainMenu.cam.update();
        
        stage.getViewport().update(width, height, true);
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		
		batch.dispose();
		stage.dispose();
		skin.dispose();
		MAIN_SCREEN.getTexture().dispose();
	}

}