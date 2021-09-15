package com.github.walderson.navinha;

import java.awt.Dimension;
import java.awt.Toolkit;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;

public class GameApp extends GameApplication {

	private static final GameFactory FACTORY = new GameFactory();
	
	private static int screenWidth;
	private static int screenHeight;
	
	private Entity player;
	
    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(screenWidth);
        settings.setHeight(screenHeight);
        settings.setFullScreenAllowed(true);
        settings.setFullScreenFromStart(true);
        settings.setTitle(" Navinha - FXGL ");
    }

    public static void main(String[] args) {
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	screenWidth = (int) screenSize.getWidth();
    	screenHeight = (int) screenSize.getHeight();

    	launch(args);
    }
    
    @Override
    protected void initGame() {
    	FXGL.getGameWorld().addEntityFactory(FACTORY);
    	this.player = FXGL.spawn("snoopy", 0, 0);
    }
}