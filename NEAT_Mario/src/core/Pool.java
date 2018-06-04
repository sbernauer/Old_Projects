package core;

import static core.Constants.COUNT_SPECIES;
import static core.Constants.STARTING_VALUE_WEIGHT;

import java.awt.Font;
import java.awt.Graphics2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import render.Renderer;

public class Pool {
	private List<Network> networks = new ArrayList<>();

	private int generation = 0;
	private int innovation = 0;

	private int tmp;

	private List<Gen> addedGensForInnovationDetection = new ArrayList<>();

	public Pool() {
		networks = IntStream.range(0, COUNT_SPECIES).mapToObj(i -> new Network(this, true))
				.collect(Collectors.toList());
	}

	public void evaluate() {
		calculateAllNetworks();

		networks.stream().forEach(network -> {
			if (Math.random() < 0.5)
				network.makeConnectionWithNeuron((int) (Math.random() * network.getNeurons().size()),
						(int) (Math.random() * network.getNeurons().size()));
		});

		detectDuplicatedInnovations();
		addedGensForInnovationDetection.clear();

//		networks.set(0, crossover(networks.get(0), networks.get(1)));

		generation++;
	}

	public void calculateAllNetworks() {
		Stream<Network> networksStream = Constants.USE_PARALLEL_STREAM_FOR_CALCULATION ? networks.parallelStream()
				: networks.stream();

		networksStream.forEach(network -> {
			network.calculate();
			network.calculateFitness();
		});
	}

	private void detectDuplicatedInnovations() {
		networks.parallelStream().forEach(network -> {
			network.getGenome().setGenes(network.getGenome().getGenes().stream().map(gen -> {
				if (!addedGensForInnovationDetection.stream()
						.anyMatch(gen2 -> gen.getInputNeuronId() == gen2.getInputNeuronId()
								&& gen.getOutputNeuronId() == gen2.getOutputNeuronId())) {
				}
				return addedGensForInnovationDetection.stream()
						.filter(gen2 -> gen.getInputNeuronId() == gen2.getInputNeuronId()
								&& gen.getOutputNeuronId() == gen2.getOutputNeuronId())
						.findFirst().orElse(gen);
			}).collect(Collectors.toList()));
		});
	}

	public void addedGenForInnovationDetection(Gen gen) {
		if (!addedGensForInnovationDetection.stream().anyMatch(gen2 -> gen.getInputNeuronId() == gen2.getInputNeuronId()
				&& gen.getOutputNeuronId() == gen2.getOutputNeuronId())) {
			addedGensForInnovationDetection.add(gen);
		}
	}

	public void render(Graphics2D g) {
		for (int i = 0; i < networks.size() && i < Constants.RENDER_COUNT_NETWORK; i++) {
			Renderer.renderNetwork(g, networks.get(i), i);
		}
		if (Constants.RENDER_NETWORK_FITNESS_FOR_ALL) {
			g.setFont(new Font("Arial", Font.PLAIN, 20));
			g.drawString("Generation: " + generation, 1400, 20);
			for (int i = 0; i < networks.size(); i++) {
				g.drawString(i + ": " + new DecimalFormat("#.###").format(networks.get(i).getGenome().getFitness()),
						1400 + ((int) ((i * 20) / 840)) * 100, 50 + (i * 20) % 840);
			}
		}
	}

	public Network crossover(Network network1, Network network2) {
		System.out.println("[BEFORE] Network 1: " + network1);
		System.out.println("[BEFORE] Network 2: " + network1);

		// genome1 must be fitter
		final Genome parent1 = network1.getGenome().getFitness() > network2.getGenome().getFitness()
				? network2.getGenome() : network1.getGenome();
		final Genome parent2 = network2.getGenome().getFitness() > network1.getGenome().getFitness()
				? network1.getGenome() : network2.getGenome();
		Network child = new Network(this, false);
		
		//Crossover Neurons
		for(int i = 0; i < network1.getNeurons().size(); i++) {
			child.getNeurons().add(network1.getNeurons().get(i));
		}
		
		//Crossover Genes
		for (tmp = 0; tmp < innovation; tmp++) {
			Gen gen1 = parent1.getGenes().stream().filter(gen -> gen.getInnovation() == tmp).findFirst().orElse(null);
			Gen gen2 = parent2.getGenes().stream().filter(gen -> gen.getInnovation() == tmp).findFirst().orElse(null);
			if (gen1 != null && gen2 != null) {
				if (Math.random() < 0.5) {
					child.getGenome().addGen(gen1);
				} else {
					child.getGenome().addGen(gen2);
				}
			} else if (gen1 != null && gen2 == null) {
				child.getGenome().addGen(gen1);
			} else if (gen1 == null && gen2 != null) {
				if (parent1.getFitness() == parent2.getFitness()) {
					child.getGenome().addGen(gen2);
				}
			}
		}

		System.out.println("[AFTER] Network Child: " + child);

		child.calculateFitness();
		return child;
	}

	public int getAndIncreaseInnovationNumber() {
		return innovation++;
	}

	public List<Network> getNetworks() {
		return networks;
	}
}
