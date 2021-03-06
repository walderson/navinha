package com.github.walderson.navinha;

import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.almasb.fxgl.dsl.FXGL.getAppWidth;
import static com.almasb.fxgl.dsl.FXGL.getGameScene;
import static com.almasb.fxgl.dsl.FXGL.getGameWorld;
import static com.almasb.fxgl.dsl.FXGL.getUIFactoryService;
import static com.almasb.fxgl.dsl.FXGL.getWorldProperties;
import static com.almasb.fxgl.dsl.FXGL.inc;
import static com.almasb.fxgl.dsl.FXGL.onCollisionBegin;
import static com.almasb.fxgl.dsl.FXGL.onKey;
import static com.almasb.fxgl.dsl.FXGL.onKeyDown;
import static com.almasb.fxgl.dsl.FXGL.spawn;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Map;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.github.walderson.navinha.GameFactory.EntityType;
import com.github.walderson.navinha.component.PlayerComponent;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

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
    protected void initGameVars(Map<String, Object> vars) {
    	vars.put("score", 0);
    	vars.put("lives", 5);
    }
    
    @Override
    protected void initGame() {
    	getGameWorld().addEntityFactory(FACTORY);
    	spawn("background", new SpawnData(0, 0)
    			.put("width", getAppWidth())
    			.put("height", getAppHeight()));
    	int radius = 80;
    	spawn("portal", new SpawnData((getAppWidth() - radius) / 2, (getAppHeight() - radius) / 2)
    			.put("x", radius / 2)
    			.put("y", radius / 2)
    			.put("radius", radius));
    	this.player = spawn("snoopy", 0, 0);
    }
    
    @Override
    protected void initUI() {
    	Text scoreLabel = getUIFactoryService().newText("Score", Color.BLACK, 28);
    	Text scoreValue = getUIFactoryService().newText("", Color.BLACK, 28);
    	Text livesLabel = getUIFactoryService().newText("Lives", Color.BLACK, 28);
    	Text livesValue = getUIFactoryService().newText("", Color.BLACK, 28);
    	
    	scoreLabel.setTranslateX(20);
    	scoreLabel.setTranslateY(40);
    	scoreValue.setTranslateX(100);
    	scoreValue.setTranslateY(40);
    	
    	livesLabel.setTranslateX(getAppWidth() - 160);
    	livesLabel.setTranslateY(getAppHeight() - 40);
    	livesValue.setTranslateX(getAppWidth() - 80);
    	livesValue.setTranslateY(getAppHeight() - 40);
    	
    	scoreValue.textProperty().bind(getWorldProperties().intProperty("score").asString());
    	livesValue.textProperty().bind(getWorldProperties().intProperty("lives").asString());
    	
    	getGameScene().addUINodes(scoreLabel, scoreValue, livesLabel, livesValue);
    }
    
    @Override
    protected void initInput() {
    	onKey(KeyCode.LEFT, "left", ()->this.player.getComponent(PlayerComponent.class).left());
    	onKey(KeyCode.RIGHT, "right", ()->this.player.getComponent(PlayerComponent.class).right());
    	onKey(KeyCode.UP, "up", ()->this.player.getComponent(PlayerComponent.class).up());
    	onKey(KeyCode.DOWN, "down", ()->this.player.getComponent(PlayerComponent.class).down());
    	onKeyDown(KeyCode.SPACE, "bullet", ()->this.player.getComponent(PlayerComponent.class).shoot());
    }
    
    @Override
    protected void initPhysics() {
    	onCollisionBegin(EntityType.PLAYER, EntityType.PORTAL, (player, portal)->this.player.getComponent(PlayerComponent.class).die());
    	onCollisionBegin(EntityType.PLAYER, EntityType.ENEMY, (player, enemy)->{
    		this.player.getComponent(PlayerComponent.class).die();
    		inc("score", 1);
    		enemy.removeFromWorld();
    	});
    	onCollisionBegin(EntityType.BULLET, EntityType.ENEMY, (bullet, enemy)->{
    		inc("score", 1);
    		bullet.removeFromWorld();
    		enemy.removeFromWorld();
    	});
    }
    
    @Override
    protected void onUpdate(double tpf) {
    	if (getGameWorld().getEntitiesByType(EntityType.ENEMY).size() < 10) {
    		spawn("redbaron", getAppWidth() / 2, getAppHeight() / 2);
    	}
    }
}