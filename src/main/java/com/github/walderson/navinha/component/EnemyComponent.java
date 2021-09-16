package com.github.walderson.navinha.component;

import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.almasb.fxgl.dsl.FXGL.getAppWidth;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.component.Component;

import javafx.geometry.Point2D;

public class EnemyComponent extends Component {
	private final Point2D direction = new Point2D(FXGLMath.random(-1.0, 1.0), FXGLMath.random(-1.0, 1.0));
	
	@Override
	public void onUpdate(double tpf) {
		entity.translate(direction.multiply(3));
		checkForBounds();
	}

	private void checkForBounds() {
		if (entity.getX() < 0)
			remove();
		if (entity.getX() + entity.getWidth() >= getAppWidth())
			remove();
		if (entity.getY() < 0)
			remove();
		if (entity.getY() + entity.getHeight() >= getAppHeight())
			remove();
	}

	private void remove() {
		entity.removeFromWorld();
	}
}
