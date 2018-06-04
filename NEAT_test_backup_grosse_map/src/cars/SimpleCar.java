package cars;

import java.awt.Color;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

import constants.Constants;
import main.Logic;
import main.Sensor;

public class SimpleCar extends Car {
	private double sum;

	private String log;

	public SimpleCar(Logic logic) {
		super(logic);
	}

	@Override
	public List<Boolean> calculateKeyPresses(List<Sensor> sensors) {
		sum = 0;
		for (int i = 0; i < sensors.size(); i++) {
			double product = sensors.get(i).getValue() / Constants.GAP_SENSOR_LENGTHS.get(i);
			if (i < sensors.size() / 2) {
				sum += product;
			} else {
				sum -= product;
			}
		}
		// Loggen für Trainingstabelle
		log = "";
		sensors.stream().forEach(s -> log += s.getValue() + " ");
		log += (sum < 0 ? "1" : "0") + " " + (sum > 0 ? "1" : "0") + "\n";
		try {
			Files.write(Paths.get("trainings_data.txt"), log.getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (sum < 0) {
			return Arrays.asList(true, false);
		} else if (sum > 0) {
			return Arrays.asList(false, true);
		} else {
			return Arrays.asList(false, false);
		}
	}
	
	@Override
	protected Color getCarColor() {
		return Color.BLUE;
	}
}
