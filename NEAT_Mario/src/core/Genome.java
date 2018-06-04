package core;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Genome {
	private List<Gen> genes = new ArrayList<>();
	
	private double fitness = 0;

	public Genome() {
	}

	public Genome(List<Gen> genes) {
		this.genes = genes;
	}

	public List<Gen> getGenes() {
		return genes;
	}

	public void addGen(Gen gen) {
		genes.add(gen);
	}

	public void setGenes(List<Gen> genes) {
		this.genes = genes;
	}
	
	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	@Override
	public String toString() {
		return "GENOM(" + genes.size() + " Stück)["
				+ (genes.stream().map(gen -> gen.toString()).collect(Collectors.joining(" ")) + "]");
	}
}
