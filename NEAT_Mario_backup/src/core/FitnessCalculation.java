package core;

import java.util.Arrays;

public class FitnessCalculation {

	public static final double calculateFitness(Network network) {
		double fitness = 0;

		// XOR --------------------------------
		network.setInput(Arrays.asList(0., 0.));
		network.calculate();
		fitness += Math.abs(1. - network.getOutput().get(0));

		network.setInput(Arrays.asList(0., 1.));
		network.calculate();
		fitness += Math.abs(network.getOutput().get(0));

		network.setInput(Arrays.asList(1., 0.));
		network.calculate();
		fitness += Math.abs(network.getOutput().get(0));

		network.setInput(Arrays.asList(1., 1.));
		network.calculate();
		fitness += Math.abs(1. - network.getOutput().get(0));

		fitness *= fitness;
		// END XOR --------------------------------

		return fitness;
	}
}
