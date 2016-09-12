package view;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;

import java.util.ArrayList;

import model.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class GameScreen implements Screen, InputProcessor {
	
	/*TODO: Würfeln button implementieren 
	 * 		Würfel würfeln
	 * 
	 * 		kioutputlabel zug zurückgeben (befindet sich in ok button
	 * 		
	 * 
	 */
	
	private class Position{
		private float x;
		private float y;
		
		public Position(float x, float y){
			this.x = x;
			this.y = y;
		}
		
		public float getX(){
			return x;
		}
		
		public float getY(){
			return y;
		}
	}
	
	private MainGame mainMenu;
	private OrthographicCamera cam2;
	
	private SpriteBatch batch;
	private Sprite map, bag;
	
	private Skin skin;
	private Stage stage, stage2;
	private Table table, table2;
	private TextButton save, takeBack, tip, exit, endTurn, rollAndSwap, ok;
	private Label numInBag, turn, label1, label2, label3, label4, label5;
	private TextField turnTextfield;

	private ArrayList<model.Position> modelPos;
	private ArrayList<Position> realPos;
	private Vector3 originDown;
	private Vector3 lastTouchDown;
	private int stoneSize;
	private int panSensitivity;
	private boolean pan;
	
	private float downLimit;
	private float upLimit;
	private float leftLimit;
	private float rightLimit;
	
	private int screenLimitX;
	private int screenLimitY;
	
	private ArrayList<Player> playerList;
	private Player actualPlayer;
	private boolean nextPlayerMark;
	
	public float zoom = 1.0f;
	private TextButton inputHelp;
	private TextButton swap;
	private Label kiOutputLabel;
	
	private String codeStones[][];
	private Image playerStones[][];
	private int stoneIndex;
	private int playerStone[];
	private ArrayList<model.Position> placedStones;
	private float pixelStoneOriginX;
	private float pixelStoneOriginY;
	private ArrayList<Image> placedStonesActors;
	private Player lastCurrentPlayer;
	
	public GameScreen(MainGame mainMenu) {
		this.mainMenu = mainMenu;
		modelPos = new ArrayList<model.Position>();
		realPos = new ArrayList<Position>();
	}
	
	@Override
	public void show() {
		nextPlayerMark = false;
		placedStonesActors = new ArrayList<Image>();
		pixelStoneOriginX = 900;
		pixelStoneOriginY = 500;
		lastTouchDown = new Vector3();
		originDown = new Vector3();
		stoneSize = 100;
		panSensitivity = 15;
		pan = false;
		screenLimitX = 600;
		screenLimitY = 300;
		
		cam2 = new OrthographicCamera(MainGame.S_WIDTH, MainGame.S_HEIGHT);
        cam2.position.set(cam2.viewportWidth / 2f, cam2.viewportHeight / 2f, 0);
        cam2.update();
        upLimit = downLimit = cam2.position.y;
        leftLimit = rightLimit = cam2.position.x;
		
		batch = new SpriteBatch();
		stage2 = new Stage(new StretchViewport(MainGame.S_WIDTH, MainGame.S_HEIGHT, cam2));
		
		skin = new Skin(Gdx.files.internal("testUI/uiskin.json"));
		
		map = new Sprite(new Texture(Gdx.files.internal("img/table.png")));
		map.setSize(MainGame.S_WIDTH, MainGame.S_HEIGHT);
		
		bag = new Sprite(new Texture(Gdx.files.internal("img/bag.png")));
		bag.setPosition(MainGame.S_WIDTH - 250, MainGame.S_HEIGHT/2);
		
		stage = new Stage(new StretchViewport(MainGame.S_WIDTH, MainGame.S_HEIGHT));
		table = new Table();
		table2 = new Table();
		
		InputMultiplexer multiplexer = new InputMultiplexer(stage, stage2, this);
		Gdx.input.setInputProcessor(multiplexer);
		
		playerList = mainMenu.getGameController().getQwirkle().getPlayerList();
		actualPlayer = mainMenu.getGameController().getQwirkle().getCurrentPlayer();
		
		codeStones = new String[playerList.size()][6];
		playerStones = new Image[playerList.size()][6];
		playerStone = new int[playerList.size()];
		
		label1 = new Label("", skin);
		label2 = new Label("", skin);
		label3 = new Label("", skin);
		label4 = new Label("", skin);
		label5 = new Label("", skin);
		

		
		//------------------------Zug Eingabe--------------------------------------------------------------
		turnTextfield = new TextField("", skin);
		turnTextfield.setPosition(MainGame.S_WIDTH - (turnTextfield.getWidth()*3), 50);
		
		turn = new Label("Zug:", skin);
		turn.setPosition(MainGame.S_WIDTH - (turnTextfield.getWidth()*3), 90);
		turn.setColor(Color.BLACK);
		
		ok = new TextButton("OK", skin);
		ok.setPosition(MainGame.S_WIDTH - (turnTextfield.getWidth()) + 60, 50);
		ok.setSize(50, turnTextfield.getHeight());
		ok.addListener(new ClickListener(){

			@Override
			public void clicked(InputEvent event, float x, float y) {
				String turn = turnTextfield.getText();
				//echo um zu testen
				kiOutputLabel.setText(turn);
				
				
				if(!turn.equals("")){
					if(mainMenu.getGameController().getPlayerController().doTurn(turn) == false){
						new Dialog("PARSE-FEHLER", skin){
							{
								text("Die Eingabe war nicht korrekt!\n");
								text("Unter Hilfe wird die genaue Eingabe beschrieben.");
								button("   OK   ", "OK");
							}
						}.show(stage);
					}
				}
				else{
					new Dialog("FEHLERHAFTE EINGABE", skin){
						{
							text("Die Eingabe war nicht korrekt!\n");
							text("Unter Hilfe wird die genaue Eingabe beschrieben.");
							button("   OK   ", "OK");
						}
					}.show(stage);
				}
			}
		});
		
		kiOutputLabel = new Label("", skin);
		kiOutputLabel.setPosition(MainGame.S_WIDTH - (turnTextfield.getWidth()) -146, 50);
		kiOutputLabel.setSize(50, turnTextfield.getHeight());
		kiOutputLabel.setColor(Color.BLACK);
		
		inputHelp = new TextButton("Hilfe", skin);
		inputHelp.setPosition(MainGame.S_WIDTH - (turnTextfield.getWidth()) + 5, 50);
		inputHelp.setSize(50, turnTextfield.getHeight());
		inputHelp.addListener(new ClickListener(){

			@Override
			public void clicked(InputEvent event, float x, float y) {

				new Dialog("Eingabehilfe", skin){
					
					{
						String text = "               BSP: Spielzug: # 0 0 St ro|0 1 St bl|0 2 St gr \n\n"
								+ "Ein Spielzug beginnt mit dem Hash-Zeichen, gefolgt"
								+ " von \n den abgelegten Spielsteinen mit ihren Positionen.\n"
								+ " Die Position (0, 0) ist die Stelle, an der der erste"
								+ " Stein\n platziert wurde, (0, 1) liegt rechts davon, (0, -1)"
								+ " links davon\n, analog (1, 0) fuer oberhalb und (-1, 0) fuer unterhalb.\n";
						text(text);
						
						button("   OK   " , "OK");
					}

					@Override
					protected void result(Object object) {
						
					}
				}.show(stage);
			}
			
		});
	
		
		stage.addActor(turnTextfield);
		stage.addActor(turn);
		stage.addActor(ok);
		stage.addActor(inputHelp);
		stage.addActor(kiOutputLabel);
		
		
		save = new TextButton("Speichern", skin);
		save.addListener(new ClickListener(){

			@Override
			public void clicked(InputEvent event, float x, float y) {
				mainMenu.getGameController().getIoController().save();
			}
		});
		
		takeBack = new TextButton("Zug zur. nehmen", skin);
		takeBack.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				run(new Runnable() {

					@Override
					public void run() {
						
						mainMenu.getGameController().getPlayerController().undoTurn(mainMenu.getGameController().getQwirkle().getCurrentPlayer());
					}
					
				});
			}
		});
		
		tip = new TextButton("Tipp", skin);
		tip.addListener(new ClickListener(){

			@Override
			public void clicked(InputEvent event, float x, float y) {
				stage.addAction(run(new Runnable() {

					@Override
					public void run() {
						new Dialog("TIPP", skin){
							
							{
								text("Der naechstbeste Schritt waere: \n\n\n");
								text(mainMenu.getGameController().getPlayerController().showHint());
								button("   OK   " , "OK");
							}
						}.show(stage);
					
					}
					
				}));
				
			}
			
			
		});
		
		exit = new TextButton("Beenden", skin);
		exit.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				stage.addAction(run(new Runnable() {

					@Override
					public void run() {
						new Dialog("Spiel Beenden", skin){
							
							{
								text("   Willst du wirklich beenden? \n\n\n");
								
								button("    Ja    " , "yes");
								button("  Nein  ", "no");
							}
							
							@Override 
							protected void result(Object object) { 
								if(object.equals("yes")) 
									Gdx.app.exit(); 
								} 
						}.show(stage);
						
					}
					
				}));
			}
		});
		

		numInBag = new Label("", skin);
		numInBag.setPosition(MainGame.S_WIDTH - 300, MainGame.S_HEIGHT - 500);
		numInBag.setColor(Color.BLACK);
		numInBag.setFontScale(2);
		
		endTurn = new TextButton("Zug beenden", skin);
		endTurn.addListener(new ClickListener(){

			@Override
			public void clicked(InputEvent event, float x, float y) {
				ArrayList<model.Position> amount = mainMenu.getGameController().getQwirkle().getMap().getPlacedStones();
				if(amount != null){
					if(mainMenu.getGameController().getPlayerController().endTurn() == false){
						
						new Dialog("SPIEL IST ZUENDE", skin){
							
							{
								text("Es sind keine weitere Zuege moeglich.\n\n");
								text("Das Spiel ist beendet");
								text("Der Gewinner ist: "+ winner().getName());
								button("   OK   ", "OK");
							}
							@Override
							protected void result(Object object) {
								mainMenu.setScreen(new ScoreScreen(mainMenu));
							}
						}.show(stage);
					}else{
						nextPlayerMark = true;
					}
				}else{
					new Dialog("Stein setzen", skin){
						
						{
							text("Sie muessen mind. einen Stein/Wuerfel setzen.\n\n");
							button("   OK   ", "OK");
						}
						@Override
						protected void result(Object object) {
							mainMenu.setScreen(new ScoreScreen(mainMenu));
						}
					}.show(stage);
				}
			}
		});
		
		
		
		rollAndSwap = new TextButton(getVariantForButton(), skin);
		rollAndSwap.addListener(new ClickListener(){

			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(stoneIndex != -1){
					if(mainMenu.getGameController().getQwirkle().isCubesVariant()){
						mainMenu.getGameController().getPlayerController().rollCube(stoneIndex);
						System.out.println("rollcube wird benutzt!");
					}
					else{
						int[] stonesIndex = {stoneIndex};
						mainMenu.getGameController().getPlayerController().swapStones(stonesIndex);
						System.out.println("swapstones wird benutzt!");
					}
				}
				else{
					new Dialog("WUERFEL WURDE NICHT AUSGEWÄHLT!", skin){
						{
							text("Bitte waehlen sie einen Wuerfel.\n\n");
							button("   OK   ", "OK");
						}
						@Override
						protected void result(Object object) {
						}
					}.show(stage);
				}
					
			}
		});
		
		
		table.add(save).width(150).spaceRight(15);
		table.add(takeBack).width(150).spaceRight(15);
		table.add(tip).width(150).spaceRight(15);
		table.add(exit).width(150);
		table.setPosition(MainGame.S_WIDTH/2 - table.getWidth()/2, MainGame.S_HEIGHT - 20);
		
		table2.add(endTurn).width(150).spaceBottom(15).row();
		table2.add(rollAndSwap).width(150).spaceBottom(15).row();
		table2.add(swap).width(150).row();
		table2.setPosition(MainGame.S_WIDTH - 150, MainGame.S_HEIGHT - 590);
				
		stage.addActor(numInBag);
		stage.addActor(table);
		stage.addActor(table2);
		
		setPlayerLabels();
		drawPlayerStones();
		placedStones = mainMenu.getGameController().getQwirkle().getMap().getPlacedStones();
	}
	
	public String getVariantForButton(){
		String variantName = "";
		if(mainMenu.getGameController().getQwirkle().isCubesVariant()){
			variantName = "Wuerfeln";
		}
		else{
			variantName = "Tauschen";
		}
		
		return variantName;
	}
	
	@Override
	public void render(float delta) {
		updateNumInBag();
		
		if(nextPlayerMark){
			setPlayerLabels();
		}
		
		if(havePlayerStoneChanged()){
			updatePlayerStones();
		}
		
//		if(hasMapChanged()){
			updateRealMap();
//		}
			
		kiOutputLabel.setText(mainMenu.getGameController().getBotController().getTurn());
		
		mainMenu.cam.update();
		cam2.update();
		
		batch.setProjectionMatrix(mainMenu.cam.combined);

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		map.draw(batch);
		bag.draw(batch);
		batch.end();
		
		stage2.act(delta);
		stage2.draw();
		
		stage.act(delta);
		stage.draw();
		
		
	}

	@Override
	public void resize(int width, int height) {

		mainMenu.cam.viewportWidth = MainGame.S_WIDTH;
        mainMenu.cam.viewportHeight = MainGame.S_WIDTH * height/width;
        mainMenu.cam.update();
        
        stage.getViewport().update(width, height, true);
        stage2.getViewport().update(width, height, true);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	public void hide() {

	}

	@Override
	public void dispose() {

		batch.dispose();
		stage.dispose();
		stage2.dispose();
		skin.dispose();
		map.getTexture().dispose();
		bag.getTexture().dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		lastTouchDown.set(screenX, screenY, 0);
		originDown.set(screenX, screenY, 0);
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			
			Vector3 touchPos = new Vector3(screenX, screenY, 0);
			cam2.unproject(touchPos);
			
			placeStone(touchPos);
		}
		
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		pan = false;
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		moveCamera(screenX, screenY);
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		float zoom = 0.1f;
		if(amount == 1 && cam2.zoom + zoom <= 2) {
			cam2.zoom += zoom;
		}else if(amount == -1 && cam2.zoom - zoom >= .5) {
			cam2.zoom -= zoom;
		}
		return true;
	}
	
	public boolean placeStone(Vector3 touch){
		float realPosX = touch.x;
		float realPosY = touch.y;
		int modelPosX = 0;
		int modelPosY = 0;
		boolean set = false;
		boolean exists = false;
		
		//Algorithmus zur Berechnung der gemeinten Position
		if(!realPos.isEmpty()){
			for(int i = 0; i < realPos.size(); i++){
				float setRealX = realPos.get(i).getX();
				float setRealY = realPos.get(i).getY();
				int setModelX = modelPos.get(i).getxPos();
				int setModelY = modelPos.get(i).getyPos();
				if(!set){
					if(realPosX <= (setRealX-stoneSize/2) && realPosX >= (setRealX-1.5*stoneSize) && realPosY <= (setRealY+stoneSize/2) && realPosY >= (setRealY-stoneSize/2)){
						realPosX = setRealX - stoneSize;
						realPosY = setRealY;
						modelPosX = setModelX - 1;
						modelPosY = setModelY;
						if(realPosX < leftLimit)
							leftLimit = realPosX;
						set = true;
					}else if(realPosX >= (setRealX+stoneSize/2) && realPosX <= (setRealX+1.5*stoneSize) && realPosY <= (setRealY+stoneSize/2) && realPosY >= (setRealY-stoneSize/2)){
						realPosX = setRealX + stoneSize;
						realPosY = setRealY;
						modelPosX = setModelX + 1;
						modelPosY = setModelY;
						if(realPosX > rightLimit)
							rightLimit = realPosX;
						set = true;
					}else if(realPosX <= (setRealX+stoneSize/2) && realPosX >= (setRealX-stoneSize/2) && realPosY >= (setRealY+stoneSize/2) && realPosY <= (setRealY+1.5*stoneSize)){
						realPosX = setRealX;
						realPosY = setRealY + stoneSize;
						modelPosX = setModelX;
						modelPosY = setModelY + 1;
						if(realPosY > upLimit)
							upLimit = realPosY;
						set = true;
					}else if(realPosX <= (setRealX+stoneSize/2) && realPosX >= (setRealX-stoneSize/2) && realPosY <= (setRealY-stoneSize/2) && realPosY >= (setRealY-1.5*stoneSize)){
						realPosX = setRealX;
						realPosY = setRealY - stoneSize;
						modelPosX = setModelX;
						modelPosY = setModelY - 1;
						if(realPosY < downLimit)
							downLimit = realPosY;
						set = true;
					}
				}
				
				if(realPosX == setRealX && realPosY == setRealY){
					exists = true;
					break;
				}
			}
		}else{
			set = true;
			pixelStoneOriginX = leftLimit = rightLimit = realPosX;
			pixelStoneOriginY = upLimit = downLimit = realPosY;
		}
		
		//Stein setzen
		if(set && !exists && stoneIndex != -1 && mainMenu.getGameController().getPlayerController().placeCube(stoneIndex, new model.Position(modelPosX, modelPosY))){
			stoneIndex = -1;
//			Image stone = new Image(selectedImage.getDrawable());
//			selectedImage = null;
//			
//			stone.setSize(stoneSize, stoneSize);
//			stone.setPosition(realPosX - stone.getWidth()/2, realPosY - stone.getHeight()/2);
//			realPos.add(new Position(realPosX, realPosY));
//			modelPos.add(new model.Position(modelPosX, modelPosY));
//			
//			stage2.addActor(stone);
		}
		return false;
	}
	
	public void moveCamera(int x, int y){
		Vector3 newPosition = getNewPosition(x, y);
		Vector3 newPos = new Vector3(x, y, 0);
		if(pan || Math.abs(newPos.x - originDown.x) >= panSensitivity || Math.abs(newPos.y - originDown.y) >= panSensitivity){
			if(newPosition.x + screenLimitX > leftLimit && 
				newPosition.x - screenLimitX < rightLimit){
				cam2.position.x = newPosition.x;
				pan = true;
			}
			if(newPosition.y - screenLimitY < upLimit && 
				newPosition.y + screenLimitY > downLimit){
				cam2.position.y = newPosition.y;
			}
		}
		
		lastTouchDown.set(x, y, 0);
	}
	
	public String getTurn(){
		return turnTextfield.getText();
	}
	

	public Vector3 getNewPosition(int x, int y){
		Vector3 newPosition = lastTouchDown;
		newPosition.sub(x, y, 0);
		newPosition.y = -newPosition.y;
		newPosition.add(cam2.position);
		return newPosition;
	}
	
	public void updatePlayerStones(){
		removePlayerStones();
		drawPlayerStones();
	}
	
	public void drawPlayerStones() {
		
		for(int i = 0; i < playerList.size(); i++) {
			stoneIndex = -1;
			playerStone[i] = playerList.get(i).getStones().size();
			for(int j = 0; j < playerList.get(i).getStones().size(); j++) {
				
				String symbol = playerList.get(i).getStones().get(j).getSymbol();
				String color = playerList.get(i).getStones().get(j).getColor();
				String combo = symbol + "_" + color;
				
				codeStones[i][j] = combo;
				
				playerStones[i][j] = new Image((Texture) Assets.manager.get(Assets.map.get(combo)));
				playerStones[i][j].setSize(stoneSize/2, stoneSize/2);
				switch(i) {
					case 0:
						playerStones[i][j].setY(MainGame.S_HEIGHT / 6 * 5 - 100);
						break;
					case 1:
						playerStones[i][j].setY(MainGame.S_HEIGHT / 6 * 4 - 100);
						break;
					case 2:
						playerStones[i][j].setY(MainGame.S_HEIGHT / 6 * 3 - 100);
						break;
					case 3:
						playerStones[i][j].setY(MainGame.S_HEIGHT / 6 * 2 - 100);
						break;
					case 4:
						playerStones[i][j].setY(MainGame.S_HEIGHT / 6 * 1 - 100);
						break;
						
				}
				switch(j) {
					case 0:
						playerStones[i][j].setX(100);
						break;
					case 1:
						playerStones[i][j].setX(150);
						break;
					case 2:
						playerStones[i][j].setX(200);
						break;
					case 3:
						playerStones[i][j].setX(250);
						break;
					case 4:
						playerStones[i][j].setX(300);
						break;
					case 5:
						playerStones[i][j].setX(350);
						break;
				}
				Player currentPlayer = mainMenu.getGameController().getQwirkle().getCurrentPlayer();
				ArrayList<Player> player = mainMenu.getGameController().getQwirkle().getPlayerList();
				if(player.get(i).equals(currentPlayer)){
					playerStones[i][j].addListener(new ClickListener(){
						@Override
						public void clicked(InputEvent event, float x, float y) {
							for(int i = 0; i < playerStones.length; i++){
								for(int j = 0; j < playerStones[i].length; j++){
									if(playerStones[i][j].equals((Image) event.getListenerActor())){
										updatePlayerStones();
										stoneIndex = j;
										playerStones[i][j].setSize(stoneSize, stoneSize);
									}
								}
							}
						}
					});
				}
				stage.addActor(playerStones[i][j]);
			}
		}
	}
	
	public void removePlayerStones(){
		for(int i = 0; i < playerStones.length; i++){
			for(int j = 0; j < playerStones[i].length; j++){
				playerStones[i][j].remove();
			}
		}
	}
	
	public Player winner(){
		ArrayList<Player> playerList = mainMenu.getGameController().getQwirkle().getPlayerList();
		Player winner = mainMenu.getGameController().getQwirkle().getCurrentPlayer();
		Player score;
		for(int i = 0; i < playerList.size(); i++){
			score = playerList.get(i);
			if(score.getScore() > winner.getScore()){
				winner = score;
			}
		}
		return winner;
	}
	
	public boolean havePlayerStoneChanged(){
		if(lastCurrentPlayer != mainMenu.getGameController().getQwirkle().getCurrentPlayer()){
			lastCurrentPlayer = mainMenu.getGameController().getQwirkle().getCurrentPlayer();
			return true;
		}
		
		for(int i = 0; i < playerList.size(); i++){
			if(playerStone[i] != playerList.get(i).getStones().size() ){
				return true;
			}
			for(int j = 0; j < playerList.get(i).getStones().size(); j++){
				String symbol = playerList.get(i).getStones().get(j).getSymbol();
				String color = playerList.get(i).getStones().get(j).getColor();
				String combo = symbol + "_" + color;
				
				if(!codeStones[i][j].equals(combo)){
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean hasMapChanged(){
		ArrayList<model.Position> modelMap = mainMenu.getGameController().getQwirkle().getMap().getPlacedStones();
		if(modelMap.size() != placedStones.size()){
			return true;
		}
		//TODO
		System.out.println("FALSE");
		return false;
	}
	
	public void updateRealMap(){
		
		//CLEAR ACTORS
		for(int i = 0; i < placedStonesActors.size(); i++){
			placedStonesActors.get(i).remove();
		}
		placedStonesActors.clear();
		
		ArrayList<model.Position> modelMap = mainMenu.getGameController().getQwirkle().getMap().getPlacedStones();
		realPos.clear();
		modelPos.clear();
		for(int i = 0; i < modelMap.size(); i++){
			int x = modelMap.get(i).getxPos();
			int y = modelMap.get(i).getyPos();
			modelPos.add(new model.Position(x, y));
			realPos.add(new Position(pixelStoneOriginX + x * stoneSize, pixelStoneOriginY + y * stoneSize));
			String code = modelMap.get(i).getStone().getSymbol() + "_" + modelMap.get(i).getStone().getColor();
			Image currentStone = new Image((Texture) Assets.manager.get(Assets.map.get(code)));
			currentStone.setSize(stoneSize, stoneSize);
			currentStone.setPosition(realPos.get(i).getX() - currentStone.getWidth()/2, realPos.get(i).getY() - currentStone.getHeight()/2);
			placedStonesActors.add(currentStone);
			stage2.addActor(placedStonesActors.get(i));
		}
	}
	
	public void setPlayerLabels(){
		
			nextPlayerMark = false;
			actualPlayer = mainMenu.getGameController().getQwirkle().getCurrentPlayer();
			int playerCount = playerList.size();
			int counter = 5;
			
			for(int i=0; i<playerCount; i++){
				String name = playerList.get(i).getName();
				int points = playerList.get(i).getScore();
				
				switch(i){
				case 0:
					if(playerList.get(i).isBot()){
						label1.setText(name + " (Bot)    " + points);
					}else{
						label1.setText(name + "    " + points);
					}
					label1.setPosition(100, MainGame.S_HEIGHT / 6 * counter);
					label1.setFontScale(2);
					if(playerList.get(i).equals(actualPlayer)){
						label1.setColor(Color.RED);
					}else{
						label1.setColor(Color.BLACK);
					}
					stage.addActor(label1);
					break;
				case 1:
					if(playerList.get(i).isBot()){
						label2.setText(name + " (Bot)    " + points);
					}else{
						label2.setText(name + "    " + points);
					}
					label2.setPosition(100, MainGame.S_HEIGHT / 6 * counter);
					label2.setFontScale(2);
					if(playerList.get(i).equals(actualPlayer)){
						label2.setColor(Color.RED);
					}else{
						label2.setColor(Color.BLACK);
					}
					stage.addActor(label2);
					break;
				case 2:
					if(playerList.get(i).isBot()){
						label3.setText(name + " (Bot)    " + points);
					}else{
						label3.setText(name + "    " + points);
					}
					label3.setPosition(100, MainGame.S_HEIGHT / 6 * counter);
					label3.setFontScale(2);
					if(playerList.get(i).equals(actualPlayer)){
						label3.setColor(Color.RED);
					}else{
						label3.setColor(Color.BLACK);
					}
					stage.addActor(label3);
					break;
				case 3:
					if(playerList.get(i).isBot()){
						label4.setText(name + " (Bot)    " + points);
					}else{
						label4.setText(name + "    " + points);
					}
					label4.setPosition(100, MainGame.S_HEIGHT / 6 * counter);
					label4.setFontScale(2);
					if(playerList.get(i).equals(actualPlayer)){
						label4.setColor(Color.RED);
					}else{
						label4.setColor(Color.BLACK);
					}
					stage.addActor(label4);
					break;
				case 4:
					if(playerList.get(i).isBot()){
						label5.setText(name + " (Bot)    " + points);
					}else{
						label5.setText(name + "    " + points);
					}
					label5.setPosition(100, MainGame.S_HEIGHT / 6 * counter);
					label5.setFontScale(2);
					if(playerList.get(i).equals(actualPlayer)){
						label5.setColor(Color.RED);
					}else{
						label5.setColor(Color.BLACK);
					}
					stage.addActor(label5);
					break;
				}
				
				counter--;
			}
			
	}
	
	public void updateNumInBag(){
		numInBag.setText(Integer.toString(mainMenu.getGameController().getQwirkle().getBag().getAmount()));
	}
	
}