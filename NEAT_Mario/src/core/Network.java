package core;

import static core.Constants.COUNT_INPUTS;
import static core.Constants.COUNT_OUTPUTS;
import static core.Constants.STARTING_VALUE_ENABLED;
import static core.Constants.STARTING_VALUE_WEIGHT;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Network {
	private Pool pool;
	private Genome genome = new Genome();

	private int neuronCount = 0;
	private List<Neuron> neurons = new ArrayList<>();

	public Network(Pool pool, boolean createNeurons) {
		this.pool = pool;

		if (createNeurons) {
			neurons = IntStream.range(0, COUNT_INPUTS).mapToObj(i -> new Neuron(neuronCount++))
					.collect(Collectors.toList());

			for (int a = COUNT_INPUTS; a < COUNT_INPUTS + COUNT_OUTPUTS; a++) {
				Neuron outputNeuron = new Neuron(neuronCount++);
				neurons.add(outputNeuron);
				for (int b = 0; b < COUNT_INPUTS; b++) {
					makeConnection(b, neuronCount - 1, STARTING_VALUE_WEIGHT);
				}
			}
		}
	}

	public void calculate() {
		neurons.stream().skip(COUNT_INPUTS).map(Neuron::getId).forEach(this::calculate);
	}

	public void setInput(List<Double> values) {
		if (values.size() != COUNT_INPUTS) {
			System.err.println("Falsche Anzahl an Inputs");
			return;
		}
		for (int i = 0; i < COUNT_INPUTS; i++) {
			getNeuronFromId(i).get().setValue(values.get(i));
		}
	}

	public List<Double> getOutput() {
		return neurons.stream().skip(neurons.size() - COUNT_OUTPUTS).map(Neuron::getValue).collect(Collectors.toList());
	}

	public void calculate(int neuronId) {
		double value = genome.getGenes().stream().filter(g -> g.isEnabled() && g.getOutputNeuronId() == neuronId)
				.mapToDouble(g -> g.getWeight() * getNeuronFromId(g.getInputNeuronId()).get().getValue()).sum();
		value = sigmoid(value);
		getNeuronFromId(neuronId).get().setValue(value);
	}

	public Optional<Neuron> getNeuronFromId(int neuronId) {
		return neurons.stream().filter(neuron -> neuron.getId() == neuronId).findFirst();
	}

	private Optional<Gen> getGenFromInputAndOutputNeuronId(int inputNeuronId, int outputNeuronId) {
		return genome.getGenes().stream()
				.filter(gen -> gen.getInputNeuronId() == inputNeuronId && gen.getOutputNeuronId() == outputNeuronId)
				.findFirst();
	}

	public void makeConnection(int inputNeuronId, int outputNeuronId, double weight) {
		if (!getGenFromInputAndOutputNeuronId(inputNeuronId, outputNeuronId).isPresent()) {
			Gen addedGen = new Gen(inputNeuronId, outputNeuronId, weight, STARTING_VALUE_ENABLED,
					pool.getAndIncreaseInnovationNumber());
			genome.addGen(addedGen);
			pool.addedGenForInnovationDetection(addedGen);
		}
	}

	public void makeConnectionWithNeuron(int inputNeuronId, int outputNeuronId) {
		double originalWeight = 1;

		Gen originalGen = getGenFromInputAndOutputNeuronId(inputNeuronId, outputNeuronId).orElse(null);
		if (originalGen != null) {
			originalWeight = originalGen.getWeight();
		}

		// TODO Gen disablen

		Neuron neuronBetween = new Neuron(neuronCount++);
		neurons.add(neuronBetween);
		makeConnection(inputNeuronId, neuronCount - 1, 1);
		makeConnection(neuronCount - 1, outputNeuronId, originalWeight);

		calculate(neuronCount - 1);
		calculate(outputNeuronId);
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

	@Override
	public String toString() {
		return "NETWORK[Neurons: " + neurons + "; Genome: " + genome + "]";
	}
}
