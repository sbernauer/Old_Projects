package core;

import static core.Constants.COUNT_INPUTS;
import static core.Constants.COUNT_OUTPUTS;
import static core.Constants.STARTING_VALUE_ENABLED;
import static core.Constants.STARTING_VALUE_WEIGHT;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Network {
	private List<Neuron> neurons = new ArrayList<>();
	private Genome genome = new Genome();

	private int currentInnovation;

	public Network() {
		for (int i = 0; i < COUNT_INPUTS; i++) {
			neurons.add(new Neuron());
		}

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
		// TODO Siggi
		neuron.setValue(value);
	}

	public void makeConnection(Neuron neuron1, Neuron neuron2, double weight) {
		// TODO prüfen, ob schon da
		genome.addGen(new Gen(neuron1, neuron2, weight, STARTING_VALUE_ENABLED, currentInnovation++));
	}

	public void makeConnectionWithNeuron(Neuron neuron1, Neuron neuron2) {
		double originalWeight = 1;

		Gen originalGen = genome.getGenes().stream()
				.filter(gen -> gen.getInputNeuron() == neuron1 && gen.getOutputNeuron() == neuron2).findFirst()
				.orElse(null);

		if (originalGen != null) {
			originalWeight = originalGen.getWeight();
			System.out.println("Deaktiviere Gen: " + originalGen);
			originalGen.setEnabled(false);
		}

		Neuron neuronBetween = new Neuron();
		neurons.add(neuronBetween);
		makeConnection(neuron1, neuronBetween, 1);
		makeConnection(neuronBetween, neuron2, originalWeight);

		calculate(neuronBetween);
		calculate(neuron2);
	}

	public List<Neuron> getNeurons() {
		return neurons;
	}

	public Genome getGenome() {
		return genome;
	}
}
