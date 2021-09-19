package net.mgsx.dl18;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3WindowAdapter;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		// config.setWindowedMode(640, 480);
		config.useVsync(false);
		config.setIdleFPS(60);
		config.setForegroundFPS(60);
		DL18 game = new DL18();
		config.setWindowListener(new Lwjgl3WindowAdapter(){
			@Override
			public void filesDropped(String[] files) {
				game.filesDropped(files);
			}
		});
		new Lwjgl3Application(game, config);
	}
}
