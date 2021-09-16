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
import com.github.walderson.navinha.component.PlayerComponent;

public class GameFactory implements EntityFactory {
	public enum EntityType {
		BACKGROUND, CENTER, PLAYER, ENEMY, BULLET
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
