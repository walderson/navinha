package com.github.walderson.navinha.component;

import com.almasb.fxgl.entity.component.Component;

import javafx.geometry.Point2D;

public class PlayerComponent extends Component {
	private Point2D direction = new Point2D(1, 1);
	
	@Override
	public void onUpdate(double tpf) {
		entity.translate(direction.multiply(1));
	}
}
