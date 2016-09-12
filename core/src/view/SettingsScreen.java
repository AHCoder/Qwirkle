package view;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import java.util.ArrayList;

import model.Bot;
import model.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class SettingsScreen implements Screen {
	
	private MainGame mainGame;
	
	private SpriteBatch batch;
	private Sprite MAIN_SCREEN;
	
	private Stage stage;
	private Skin skin;
	private Table table;
	private TextureAtlas atlas;
	private TextButton buttonStart;
	private Label nameLabel1;
	
	private static TextField nameText1;
	private static TextField nameText2;
	private static TextField nameText3;
	private static TextField nameText4;
	private static TextField nameText5;
	
	private Label nameLabel2;
	private Label nameLabel3;
	private Label nameLabel4;
	private Label nameLabel5;
	
	private Table table2;
	private Table table3;
	
	private SelectBox<String> cubeOrOriginal;
	
	private Table table4;
	private Label heading;
	
	private static CheckBox checkBoxBot1;
	private static CheckBox checkBoxBot2;
	private static CheckBox checkBoxBot3;
	private static CheckBox checkBoxBot4;
	private static CheckBox checkBoxBot5;
	
	private static SelectBox<String> kiSelect1;
	private static SelectBox<String> kiSelect2;
	private static SelectBox<String> kiSelect3;
	private static SelectBox<String> kiSelect4;
	private static SelectBox<String> kiSelect5;
	
	private Label kiStrength1;
	private Label kiStrength2;
	private Label kiStrength3;
	private Label kiStrength4;
	private Label kiStrength5;
	
	private Label gameLabel;
	private TextField readBag;
	
	private Table table5;
	
	private SelectBox<String> cubeVariation;
	private SelectBox<String> stoneVariation;
	
	public final String[] selectOptions = {"Low", "Medium", "High"};
	public final String[] selectCubes = {"90", "60", "30"};
	public final String[] selectStones = {"108", "72", "36"};
	public final String[] selectGameMode = {"Qwirkle Cube", "Qwirkle Original"};
	
	private Label stoneLabel;
	
	ArrayList<Player> player = new ArrayList<Player>();

	public SettingsScreen(MainGame mainGame) {
		
		this.mainGame = mainGame;
	}

	@Override
	public void show() {

		batch = new SpriteBatch();
		
		MAIN_SCREEN = new Sprite(new Texture(Gdx.files.internal("img/main_screen.jpg")));
		MAIN_SCREEN.setSize(MainGame.S_WIDTH, MainGame.S_HEIGHT);
		
		atlas = new TextureAtlas("testUI/uiskin.atlas");
		stage = new Stage(new StretchViewport(MainGame.S_WIDTH, MainGame.S_HEIGHT));
		
		skin = new Skin(Gdx.files.internal("testUI/uiskin.json"), atlas);
		table = new Table(skin);
		table.setFillParent(true);
		table.setBounds(0, 0, 1024, 576);
		
		table2 = new Table(skin);
		table2.setFillParent(true);
		table2.setBounds(0, 0, 1024, 576);
		
		table3 = new Table(skin);
		table3.setFillParent(true);
		
		table4 = new Table(skin);
		table4.setFillParent(true);
		
		table5 = new Table(skin);
		table5.setFillParent(true);
		
		
		nameText1 = new TextField("", skin);
		nameText2 = new TextField("", skin);
		nameText3 = new TextField("", skin);
		nameText4 = new TextField("", skin);
		nameText5 = new TextField("", skin);
		
		nameLabel1 = new Label("Name des Spielers 1 :", skin);
		nameLabel1.setFontScale(2);
		nameLabel2 = new Label("Name des Spielers 2 :", skin);
		nameLabel2.setFontScale(2);
		nameLabel3 = new Label("Name des Spielers 3 :", skin);
		nameLabel3.setFontScale(2);
		nameLabel4 = new Label("Name des Spielers 4 :", skin);
		nameLabel4.setFontScale(2);
		nameLabel5 = new Label("Name des Spielers 5 :", skin);
		nameLabel5.setFontScale(2);
		
		checkBoxBot1 = new CheckBox("Bot", skin);
		checkBoxBot2 = new CheckBox("Bot", skin);
		checkBoxBot3 = new CheckBox("Bot", skin);
		checkBoxBot4 = new CheckBox("Bot", skin);
		checkBoxBot5 = new CheckBox("Bot", skin);
		
		kiStrength1 = new Label("KI-Staerke: ", skin);
		kiStrength1.setFontScale(2);
		kiStrength2 = new Label("KI-Staerke: ", skin);
		kiStrength2.setFontScale(2);
		kiStrength3 = new Label("KI-Staerke: ", skin);
		kiStrength3.setFontScale(2);
		kiStrength4 = new Label("KI-Staerke: ", skin);
		kiStrength4.setFontScale(2);
		kiStrength5 = new Label("KI-Staerke: ", skin);
		kiStrength5.setFontScale(2);
		
		kiSelect1 = new SelectBox<String>(skin);
		kiSelect2 = new SelectBox<String>(skin);
		kiSelect3 = new SelectBox<String>(skin);
		kiSelect4 = new SelectBox<String>(skin);
		kiSelect5 = new SelectBox<String>(skin);
		
		cubeVariation = new SelectBox<String>(skin);
		stoneVariation = new SelectBox<String>(skin);
		
		cubeVariation.setItems(selectCubes);
		stoneVariation.setItems(selectStones);
		
		kiSelect1.setItems(selectOptions);
		// kiSelect1.setSize(30, 400);  funktioniert nicht
		kiSelect2.setItems(selectOptions);
		kiSelect3.setItems(selectOptions);
		kiSelect4.setItems(selectOptions);
		kiSelect5.setItems(selectOptions);
		
		cubeOrOriginal = new SelectBox<String>(skin);
		cubeOrOriginal.setItems(selectGameMode);
		
		
		
		TextButton back = new TextButton("ZURUECK", skin);
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
		back.setPosition(75, 10);
		back.setSize(150, 50);
		back.pad(30);
		
		stage.addListener(new InputListener() {
			
			public boolean keyDown(InputEvent event, int keycode) {
				
				if(keycode == Keys.ESCAPE) {
					
					mainGame.setScreen(new MainScreen(mainGame));
				}
				
				return true;
			}
		});
		
		Gdx.input.setInputProcessor(stage);
		
		stage.addActor(back);
		
		createButton();
		drawTableBottomLeft();
		drawTableBottomRight();
		drawTableTopLeft();
		createHeading();
		drawTopRight();
	}
	
	public void drawTopRight(){
		stoneLabel = new Label("Cubes / Stones: ", skin);
		stoneLabel.setFontScale(2);
		
		table5.add(stoneLabel);
		table5.row();
		table5.add(cubeVariation).width(125);
		table5.getCell(cubeVariation);
		table5.add(stoneVariation).width(125);
		table5.row();
		
		table5.top().right().padTop(90).padRight(187);
		//table5.debug();
		
		stage.addActor(table5);
	}
	
	public void createHeading(){
		heading = new Label("SPIELOPTIONEN", skin);
		heading.setFontScale(3);
	
		table4.add(heading);
	
		table4.center().top().padTop(50);
		//table4.debug();
		
		stage.addActor(table4);
	}
	
	public int getBagSize(){
		int value = 0;
		try{
			if((cubeOrOriginal.getSelected()).equals("Qwirkle Cube")){
				value = Integer.parseInt(cubeVariation.getSelected());
			System.out.println(value);}
			else{
				value = Integer.parseInt(stoneVariation.getSelected());
				System.out.println(value);
		}}
		catch(NumberFormatException e){
			e.printStackTrace();
		}
		return value;
	}
	
	public boolean getVariant(){
		if((cubeOrOriginal.getSelected()).equals("Qwirkle Cube"))
			return true;
		else
			return false;
	}
	
	public ArrayList<Player> getPlayer(){
		
		ArrayList<String> name = new ArrayList<String>();
		player = mainGame.getGameController().getQwirkle().getPlayerList();
		ArrayList<Boolean> isBot = new ArrayList<Boolean>();
		ArrayList<String> kiSelect = new ArrayList<String>();
		
		kiSelect.add(kiSelect1.getSelected());
		kiSelect.add(kiSelect2.getSelected());
		kiSelect.add(kiSelect3.getSelected());
		kiSelect.add(kiSelect4.getSelected());
		kiSelect.add(kiSelect5.getSelected());
		
		isBot.add(checkBoxBot1.isChecked());
		isBot.add(checkBoxBot2.isChecked());
		isBot.add(checkBoxBot3.isChecked());
		isBot.add(checkBoxBot4.isChecked());
		isBot.add(checkBoxBot5.isChecked());
		
		name.add(nameText1.getText());
		name.add(nameText2.getText());
		name.add(nameText3.getText());
		name.add(nameText4.getText());
		name.add(nameText5.getText());
			
		for(int i = 0; i < name.size(); i++){
			
			if(!name.get(i).equals("")){
				if(isBot.get(i) == false){
					player.add(new Player(name.get(i)));
					System.out.println("Spieler lautet: " + name.get(i)+ "\n");
					System.out.println("erstes if in der getPlayer-Methode" + "\n");
				}
				else{
					player.add(new Bot(name.get(i), kiSelect.get(i)));
					System.out.println("Spieler lautet: " + name.get(i) + "und hat den Schwierigkeitsgrad: " +
					kiSelect.get(i) + "\n");
					System.out.println("zweites if in der getPlayer-Methode" + "\n");
				}
			}
		}
		
		return player;
	}
	
	
	public void createButton(){
		
		buttonStart = new TextButton("Spielen", skin);
		buttonStart.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				stage.addAction(run(new Runnable() {

					@Override
					public void run() {
						player = getPlayer();
						if(player.size() >= 2){
							mainGame.getGameController().newGame(getBagSize(), getVariant(), player, readBag.getText());
							
							//für Jenny: ausgaben der Spieler
							for(Player p : player){
								System.out.println("Player: Objekt:" + p);
								System.out.println("Name von Player: " + p.getName());
							}
							
							//kein Fehler, da 6 Steine pro Spieler direkt vom bag abgezogen werden
							System.out.println(mainGame.getGameController().getQwirkle().getBag().getAmount());
							mainGame.setScreen(new GameScreen(mainGame));
							System.out.println("Die Anzahl der Spieler ist >= 2" + "\n");
						}
						else{
							System.out.println("Die Anzahl der Spieler ist weniger als 2" + "\n");
							new Dialog("HINWEIS: SPIELERANZAHL ZU WENIG!", skin){
								
								{
									text("    Die Anzahl der Spieler ist weniger als 2    \n");
									button("Ok" , "ok");
								}

								@Override
								protected void result(Object object) {
									// TODO Auto-generated method stub
									text(object.toString());
									
								}
							}.show(stage);
							
							System.out.println("drüber");
						}
					}
				}));
			}
		});

		buttonStart.pad(20);
		
	}
	
	public void drawTableBottomLeft(){
		
		table.add(nameLabel1).left().pad(10);
		table.row();
		table.add(nameText1).width(900).height(60);
		table.getCell(nameText1).spaceBottom(15);
		table.add(checkBoxBot1).width(73).height(37);
		table.getCell(checkBoxBot1).spaceLeft(20).padBottom(18);
		table.add(kiStrength1);
		table.getCell(kiStrength1).padLeft(20).padBottom(18);
		table.add(kiSelect1);
		table.getCell(kiSelect1).padLeft(20).padBottom(18);
		table.row();
		table.add(nameLabel2).left().pad(10);
		table.row();
		table.add(nameText2).width(900).height(60);
		table.getCell(nameText2).spaceBottom(15);
		table.add(checkBoxBot2);
		table.getCell(checkBoxBot2).spaceLeft(20).padBottom(18);
		table.add(kiStrength2);
		table.getCell(kiStrength2).padLeft(20).padBottom(18);
		table.add(kiSelect2);
		table.getCell(kiSelect2).padLeft(20).padBottom(18);
		table.row();
		table.add(nameLabel3).left().pad(10);
		table.row();
		table.add(nameText3).width(900).height(60);
		table.getCell(nameText3).spaceBottom(15);
		table.add(checkBoxBot3);
		table.getCell(checkBoxBot3).spaceLeft(20).padBottom(18);
		table.add(kiStrength3);
		table.getCell(kiStrength3).padLeft(20).padBottom(18);
		table.add(kiSelect3);
		table.getCell(kiSelect3).padLeft(20).padBottom(18);
		table.row();
		table.add(nameLabel4).left().pad(10);
		table.row();
		table.add(nameText4).width(900).height(60);
		table.getCell(nameText4).spaceBottom(15);
		table.add(checkBoxBot4);
		table.getCell(checkBoxBot4).spaceLeft(20).padBottom(18);
		table.add(kiStrength4);
		table.getCell(kiStrength4).padLeft(20).padBottom(18);
		table.add(kiSelect4);
		table.getCell(kiSelect4).padLeft(20).padBottom(18);
		table.row();
		table.add(nameLabel5).left().pad(10);
		table.row();
		table.add(nameText5).width(900).height(60);
		table.getCell(nameText5).spaceBottom(15);
		table.add(checkBoxBot5);
		table.getCell(checkBoxBot5).spaceLeft(20).padBottom(2);
		table.add(kiStrength5);
		table.getCell(kiStrength5).padLeft(20).padBottom(2);
		table.add(kiSelect5);
		table.getCell(kiSelect5).padLeft(20).padBottom(2);
		table.row();
		
		table.bottom().left().padBottom(80).padLeft(70);
		
		//table.debug();
		//table.debugTable();
		
		stage.addActor(table);
	}
	
	
	public void drawTableBottomRight(){
		table2.add(buttonStart).width(125).right();
		table2.bottom().right().padRight(70).padBottom(22);
		
		readBag = new TextField("", skin);
		
		table2.add(readBag);
		
		//table2.debug();
		
		stage.addActor(table2);
	}

	
	public void drawTableTopLeft(){
		gameLabel = new Label("Spielmodus: ", skin);
		gameLabel.setFontScale(3);
		
		table3.add(gameLabel).width(200);
		table3.row();
		table3.add(cubeOrOriginal).pad(10);
		table3.top().left().padTop(90).padLeft(70);
		
		//table3.debug();
		stage.addActor(table3);
		
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
	}

	@Override
	public void resize(int width, int height) {

		mainGame.cam.viewportWidth = MainGame.S_WIDTH;
        mainGame.cam.viewportHeight = MainGame.S_WIDTH * height/width;
        mainGame.cam.update();
		stage.getViewport().update(width, height, true);
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
		stage.dispose();
		atlas.dispose();
		skin.dispose();
		
	}
}
