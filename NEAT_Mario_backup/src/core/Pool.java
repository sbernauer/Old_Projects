package core;

import static core.Constants.COUNT_SPECIES;

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

	private List<Gen> addedGensForInnovationDetection = new ArrayList<>();

	public Pool() {
		networks = IntStream.range(0, COUNT_SPECIES).mapToObj(i -> new Network(this)).collect(Collectors.toList());
	}

	public void evaluate() {
		calculateAllNetworks();
		networks.get(0).setGenome(crossover(networks.get(0).getGenome(), networks.get(1).getGenome()));

		networks.stream().forEach(network -> {
			if (Math.random() < 0.5)
				network.makeConnectionWithNeuron(
						network.getNeurons().get((int) (Math.random() * network.getNeurons().size())),
						network.getNeurons().get((int) (Math.random() * network.getNeurons().size())));
		});

		System.out.println("Vor detection:   " + networks.get(0).getGenome());
		detectDuplicatedInnovations();
		System.out.println("After detection: " + networks.get(0).getGenome());
		addedGensForInnovationDetection.clear();

		generation++;
	}

	private void calculateAllNetworks() {
		Stream<Network> networksStream = Constants.USE_PARALLEL_STREAM_FOR_CALCULATION ? networks.parallelStream()
				: networks.stream();

		networksStream.forEach(network -> {
			network.calculate();
			network.calculateFitness();
		});
	}

	private void detectDuplicatedInnovations() {
		networks.parallelStream().forEach(network -> {
			network.getGenome().getGenes()
					.stream().map(
							gen -> addedGensForInnovationDetection.stream()
									.filter(gen2 -> gen.getInputNeuron().equals(gen2.getInputNeuron())
											&& gen.getOutputNeuron().equals(gen2.getOutputNeuron()))
									.findFirst().orElse(gen));
		});
	}

	public void addedGenForInnovationDetection(Gen gen) {
		if (!addedGensForInnovationDetection.stream()
				.anyMatch(gen2 -> gen.getInputNeuron().equals(gen2.getInputNeuron())
						&& gen.getOutputNeuron().equals(gen2.getOutputNeuron()))) {
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

	public Genome crossover(Genome genome1, Genome genome2) {
		final Genome parent1 = genome2.getFitness() > genome1.getFitness() ? genome2 : genome1;
		final Genome parent2 = genome2.getFitness() > genome1.getFitness() ? genome1 : genome2;

		// parent1.setGenes(parent1.getGenes().stream().sorted((gen1, gen2) -> gen1.getInnovation() -
		// gen2.getInnovation())
		// .collect(Collectors.toList()));
		// System.out.println(parent1.getGenes());

		// Genome child = new Genome();
		// int maxLength = Math.max(parent1.getGenes().size(), parent2.getGenes().size());
		// for (int i = 0; i < maxLength; i++) {
		// if (parent1.getGenes().get(i).getInnovation() == parent2.getGenes().get(i).getInnovation()) {
		// // Matching genes
		// child.addGen(Math.random() < 0.5 ? parent1.getGenes().get(i) : parent2.getGenes().get(i));
		// } else {
		// // Disjoint genes
		// child.addGen(gen);
		// }
		// }

		// System.out.println("Parent 1: " + parent1);
		// System.out.println("Parent 2: " + parent2);
		// System.out.println("Child: " + child);
		// return parent1;

		List<Gen> joiningGenes = parent1.getGenes().stream().filter(gen1 -> {
			return parent2.getGenes().parallelStream().anyMatch(gen2 -> gen1.getInnovation() == gen2.getInnovation());
		}).collect(Collectors.toList());

		System.out.println(parent1);
		System.out.println(parent2);
		System.out.println(joiningGenes);

		return parent1;
		// List<Gen> genes = Stream.concat(parent1.getGenes().stream(), parent2.getGenes().stream())
		// .collect(Collectors.toList());
		// return child;

		// Genome childGenome = new Genome();
		// for (int i = 0; i < genome1.getGenes().size(); i++) {
		// if (genome1.getGenes().get(i).getInnovation() == genome2.getGenes().get(i).getInnovation()) {
		//
		// }
		// }
		// return childGenome;
	}

	public int getAndIncreaseInnovationNumber() {
		return innovation++;
	}

	public List<Network> getNetworks() {
		return networks;
	}
}
