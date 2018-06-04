package cars;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import main.Logic;
import main.Sensor;

public class MouseCar extends Car {

	public MouseCar(Logic logic) {
		super(logic);
	}

	@Override
	public List<Boolean> calculateKeyPresses(List<Sensor> sensors) {
		return Arrays.asList(true, false);
//		car.setSteering((double)gui.getMouseX() / 100);
	}

	@Override
	protected Color getCarColor() {
		return Color.PINK;
	}
}
