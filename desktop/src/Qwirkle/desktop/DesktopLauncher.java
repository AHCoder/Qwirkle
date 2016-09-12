package Qwirkle.desktop;

import java.awt.Toolkit;

import view.MainGame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	
	public static void main (String[] arg) {
		
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Qwirkle Cubes:  SoPra-Gruppe 01";
		config.vSyncEnabled = true;
		config.fullscreen = true;
		config.width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		config.height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		new LwjglApplication(new MainGame(), config);
	}
}
