package view;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class Manual implements Screen {

	private MainGame mainGame;
	private Stage stage;
	private Image first, second, third, fourth;
	private Skin skin;
	private TextButton back;
	
	public Manual(MainGame mainGame) {
		
		this.mainGame = mainGame;
	}
	
	@Override
	public void show() {

		stage = new Stage(new StretchViewport(MainGame.S_WIDTH, MainGame.S_HEIGHT));
		Gdx.input.setInputProcessor(stage);
		
		skin = new Skin(Gdx.files.internal("testUI/uiskin.json"));
		
		first = new Image(new Texture(Gdx.files.internal("img/qwirkle_1.png")));
		second = new Image(new Texture(Gdx.files.internal("img/qwirkle_2.png")));
		third = new Image(new Texture(Gdx.files.internal("img/qwirklecubes_1.png")));
		fourth = new Image(new Texture(Gdx.files.internal("img/qwirklecubes_2.png")));
		
		first.setSize(900, 900);
		first.setPosition(0, 0);
		
		second.setSize(900, 900);
		second.setPosition(0, 0);
		
		fourth.setPosition(960, 0);
		
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
		
		//stage.addActor(first);
		//stage.addActor(second);
		stage.addActor(third);
		stage.addActor(fourth);
		stage.addActor(back);
		
	}

	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		
		stage.getViewport().update(width, height);
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

		stage.dispose();
	}

}
