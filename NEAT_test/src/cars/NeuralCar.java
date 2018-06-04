package cars;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.neuroph.core.NeuralNetwork;

import core.Network;
import main.Logic;
import main.Sensor;
import render.Renderer;

public class NeuralCar extends Car {
	private Network network = new Network();

	public NeuralCar(Logic logic) {
		super(logic);
	}

	@Override
	public void render(Graphics2D g) {
		super.render(g);
		Renderer.renderNetwork(g, network);

		// network.makeConnection(network.getNeurons().get(0), network.getNeurons().get(8), 42);
	}

	@Override
	public List<Boolean> calculateKeyPresses(List<Sensor> sensors) {
		List<Double> inputs = sensors.stream().map(Sensor::getValue).collect(Collectors.toList());
		
		System.out.print("Input: ");
		inputs.forEach(i -> System.out.print(i + " "));
		network.setInput(inputs);
		network.calculate();
		List<Double> output = network.getOutput();
//		output = Arrays.asList(0d, 1d);
		System.out.print("Output: ");
		output.forEach(o -> System.out.print(o + " "));
		System.out.println("\n-----------------");
		return output.stream().map(o -> o > 8).collect(Collectors.toList());
	}

	@Override
	protected Color getCarColor() {
		return Color.GREEN;
	}
}
