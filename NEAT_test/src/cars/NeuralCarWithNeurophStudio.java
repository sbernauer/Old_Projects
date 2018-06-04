package cars;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.neuroph.core.NeuralNetwork;

import main.Logic;
import main.Sensor;

public class NeuralCarWithNeurophStudio extends Car {
	private NeuralNetwork network = NeuralNetwork.load("Car_v02.nnet");

	public NeuralCarWithNeurophStudio(Logic logic) {
		super(logic);
	}

	@Override
	public List<Boolean> calculateKeyPresses(List<Sensor> sensors) {
		double[] inputs = (double[]) sensors.stream().mapToDouble(Sensor::getValue).toArray();
		
		network.setInput(inputs);
		network.calculate();
		double[] output = network.getOutput();
		List<Boolean> result = new ArrayList<>();
		for (double d : output) {
			if(d > 0.5 && d <= 1){
				result.add(true);
			} else {
				result.add(false);
			}
		}
		return result;
	}

	@Override
	protected Color getCarColor() {
		return Color.GREEN;
	}
}
