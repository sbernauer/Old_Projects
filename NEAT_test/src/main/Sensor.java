package main;

import java.awt.geom.Line2D;

import constants.Constants;

public class Sensor {
	private Logic logic;

	private Line2D line;
	private double value = 1;

	public Sensor(Logic logic) {
		this.logic = logic;
	}

	public void measureValue() {
		for (double step = 0; step < 1; step += Constants.STEP_MEASURING_SENSORS) {
			double x = line.getX1() + (line.getX2() - line.getX1()) * step;
			double y = line.getY1() + (line.getY2() - line.getY1()) * step;
			if (!logic.getStreet().contains(x, y)) {
				value = step;
				return;
			}
		}
		value = 1;
	}

	public Line2D getLine() {
		return line;
	}

	public void setLine(Line2D line) {
		this.line = line;
	}

	public double getValue() {
		return value;
	}
}
