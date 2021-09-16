package com.github.walderson.navinha.component;

import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.almasb.fxgl.dsl.FXGL.getAppWidth;
import static com.almasb.fxgl.dsl.FXGL.getDialogService;
import static com.almasb.fxgl.dsl.FXGL.getGameController;
import static com.almasb.fxgl.dsl.FXGL.geti;
import static com.almasb.fxgl.dsl.FXGL.inc;
import static com.almasb.fxgl.dsl.FXGL.spawn;

import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;

import javafx.geometry.Point2D;

public class PlayerComponent extends Component {
	private static final double ROTATION_CHANGE = 0.01;
	
	private Point2D direction = new Point2D(1, 1);
	
	@Override
	public void onUpdate(double tpf) {
		entity.translate(direction.multiply(1));
		checkForBounds();
	}

	private void checkForBounds() {
		if (entity.getX() < 0) {
			die();
		}
		if (entity.getX() + entity.getWidth() >= getAppWidth()) {
			die();
		}
		if (entity.getY() < 0) {
			die();
		}
		if (entity.getY() + entity.getHeight() >= getAppHeight()) {
			die();
		}
	}

	public void die() {
		inc("lives", -1);
		if (geti("lives") <= 0) {
			getDialogService().showMessageBox("Game Over",
					() -> getGameController().startNewGame());
			return;
		}
		
		entity.setPosition(0, 0);
		direction = new Point2D(1, 1);
	}
	
	private double decXY(double d) {
		double res = d;
		if (res > 0) {
			res -= ROTATION_CHANGE;
		} else if (res < 0) {
			res += ROTATION_CHANGE;
		}
		return res;
	}
	
	public void up() {
		if (direction.getY() > -1) {
			double newX = decXY(direction.getX());
			double newY = direction.getY() - ROTATION_CHANGE;
			direction = new Point2D(newX, newY);
		}
	}
	
	public void down() {
		if (direction.getY() < 1) {
			double newX = decXY(direction.getX());
			double newY = direction.getY() + ROTATION_CHANGE;
			direction = new Point2D(newX, newY);
		}
	}
	
	public void left() {
		if (direction.getX() > -1) {
			double newX = direction.getX() - ROTATION_CHANGE;
			double newY = decXY(direction.getY());
			direction = new Point2D(newX, newY);
		}
	}
	
	public void right() {
		if (direction.getX() < 1) {
			double newX = direction.getX() + ROTATION_CHANGE;
			double newY = decXY(direction.getY());
			direction = new Point2D(newX, newY);
		}
	}
	
	public void shoot() {
		spawn("missile", new SpawnData(
					getEntity().getPosition().getX() + 25,
					getEntity().getPosition().getY() + 25)
				.put("direction", direction));
	}
}
