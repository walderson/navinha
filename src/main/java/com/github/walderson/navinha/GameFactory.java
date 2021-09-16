package com.github.walderson.navinha;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;
import static com.almasb.fxgl.dsl.FXGL.texture;

import com.almasb.fxgl.dsl.components.AutoRotationComponent;
import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.IrremovableComponent;
import com.github.walderson.navinha.component.EnemyComponent;
import com.github.walderson.navinha.component.PlayerComponent;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class GameFactory implements EntityFactory {
	public enum EntityType {
		BACKGROUND, PORTAL, PLAYER, ENEMY, BULLET
	}
	
	@Spawns("background")
	public Entity newBackground(SpawnData data) {
		return entityBuilder(data)
				.type(EntityType.BACKGROUND)
				.view(new Rectangle(data.<Integer>get("width"),
						data.<Integer>get("height"),
						Color.LIGHTBLUE))
				.with(new IrremovableComponent())
				.zIndex(-100)
				.build();
	}

	@Spawns("portal")
	public Entity newPortal(SpawnData data) {
		return entityBuilder(data)
				.type(EntityType.PORTAL)
				.collidable()
				.view(new Circle(data.<Integer>get("x"),
						data.<Integer>get("y"),
						data.<Integer>get("radius"),
						Color.DIMGREY))
				.with(new IrremovableComponent())
				.zIndex(-99)
				.build();
	}

	@Spawns("snoopy")
	public Entity newPlayer(SpawnData data) {
		return entityBuilder(data)
				.type(EntityType.PLAYER)
				.viewWithBBox(texture("snoopy.png", 80, 80))
				.collidable()
				.with(new AutoRotationComponent().withSmoothing())
				.with(new PlayerComponent())
				.build();
	}

	@Spawns("redbaron")
	public Entity newEnemy(SpawnData data) {
		return entityBuilder(data)
				.type(EntityType.ENEMY)
				.viewWithBBox(texture("redbaron.png", 100, 60))
				.collidable()
				.with(new AutoRotationComponent().withSmoothing())
				.with(new EnemyComponent())
				.build();
	}

	@Spawns("missile")
	public Entity newBullet(SpawnData data) {
		return entityBuilder(data)
				.type(EntityType.BULLET)
				.viewWithBBox(texture("missile.png", 100, 50))
				.collidable()
				.with(new ProjectileComponent(data.get("direction"), 350),
						new OffscreenCleanComponent())
				.build();
	}
}
