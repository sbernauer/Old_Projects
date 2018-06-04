package core;

import static core.Constants.COUNT_INPUTS;
import static core.Constants.COUNT_OUTPUTS;
import static core.Constants.STARTING_VALUE_ENABLED;
import static core.Constants.STARTING_VALUE_WEIGHT;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Network {
	private Pool pool;

	private List<Neuron> neurons;
	private Genome genome = new Genome();

	public Network(Pool pool) {
		this.pool = pool;

		neurons = IntStream.range(0, COUNT_INPUTS).mapToObj(i -> new Neuron()).collect(Collectors.toList());

		for (int a = COUNT_INPUTS; a < COUNT_INPUTS + COUNT_OUTPUTS; a++) {
			Neuron outputNeuron = new Neuron();
			neurons.add(outputNeuron);
			for (int b = 0; b < COUNT_INPUTS; b++) {
				makeConnection(neurons.get(b), outputNeuron, STARTING_VALUE_WEIGHT);
			}
		}
	}

	public void calculate() {
		neurons.stream().skip(COUNT_INPUTS).forEach(this::calculate);
	}

	public void setInput(List<Double> values) {
		if (values.size() != COUNT_INPUTS) {
			System.err.println("Falsche Anzahl an Inputs");
			return;
		}
		for (int i = 0; i < COUNT_INPUTS; i++) {
			neurons.get(i).setValue(values.get(i));
		}
	}

	public List<Double> getOutput() {
		return neurons.stream().skip(neurons.size() - COUNT_OUTPUTS).map(Neuron::getValue).collect(Collectors.toList());
	}

	public void calculate(Neuron neuron) {
		double value = genome.getGenes().stream().filter(g -> g.isEnabled() && g.getOutputNeuron() == neuron)
				.mapToDouble(g -> g.getWeight() * g.getInputNeuron().getValue()).sum();
		value = sigmoid(value);
		neuron.setValue(value);
	}

	public void makeConnection(Neuron inputNeuron, Neuron outputNeuron, double weight) {
		if (!genome.getGenes().stream()
				.anyMatch(gen -> gen.getInputNeuron() == inputNeuron && gen.getOutputNeuron() == outputNeuron)) {
			Gen addedGen = new Gen(inputNeuron, outputNeuron, weight, STARTING_VALUE_ENABLED,
					pool.getAndIncreaseInnovationNumber());
			genome.addGen(addedGen);
			pool.addedGenForInnovationDetection(addedGen);
		}
	}

	public void makeConnectionWithNeuron(Neuron inputNeuron, Neuron outputNeuron) {
		double originalWeight = 1;

		Gen originalGen = genome.getGenes().stream()
				.filter(gen -> gen.getInputNeuron() == inputNeuron && gen.getOutputNeuron() == outputNeuron).findFirst()
				.orElse(null);

		if (originalGen != null) {
			originalWeight = originalGen.getWeight();
			originalGen.setEnabled(false);
		}

		Neuron neuronBetween = new Neuron();
		neurons.add(neuronBetween);
		makeConnection(inputNeuron, neuronBetween, 1);
		makeConnection(neuronBetween, outputNeuron, originalWeight);

		calculate(neuronBetween);
		calculate(outputNeuron);
	}

	public void calculateFitness() {
		genome.setFitness(FitnessCalculation.calculateFitness(this));
	}

	private static double sigmoid(double value) {
		return 1 / (1 + Math.pow(Math.E, -4.9 * value));
	}

	public List<Neuron> getNeurons() {
		return neurons;
	}

	public Genome getGenome() {
		return genome;
	}

	public void setGenome(Genome genome) {
		this.genome = genome;
	}
}
