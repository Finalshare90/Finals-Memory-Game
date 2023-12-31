package com.finalshare;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setMaximized(true);
		config.setWindowSizeLimits(1024, 600, 1366, 768);
		config.setTitle("Final's Memory Game");
		new Lwjgl3Application(new MemoryGame(), config);
	}
}
