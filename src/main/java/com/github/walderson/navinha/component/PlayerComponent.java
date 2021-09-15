package com.github.walderson.navinha.component;

import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.almasb.fxgl.dsl.FXGL.getAppWidth;
import static com.almasb.fxgl.dsl.FXGL.getDialogService;
import static com.almasb.fxgl.dsl.FXGL.getGameController;
import static com.almasb.fxgl.dsl.FXGL.geti;
import static com.almasb.fxgl.dsl.FXGL.inc;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;

import javafx.geometry.Point2D;

public class PlayerComponent extends Component {
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
		if (entity.getX() >= getAppWidth()) {
			die();
		}
		if (entity.getY() < 0) {
			die();
		}
		if (entity.getY() >= getAppHeight()) {
			die();
		}
	}

	private void die() {
		inc("lives", -1);
		if (geti("lives") <= 0) {
			getDialogService().showMessageBox("Game Over",
					() -> getGameController().startNewGame());
			return;
		}
		
		entity.setPosition(0, 0);
		direction = new Point2D(1, 1);
	}
}
