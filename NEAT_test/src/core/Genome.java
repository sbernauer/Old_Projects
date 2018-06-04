package core;

import java.util.ArrayList;
import java.util.List;

public class Genome {
	private List<Gen> genes = new ArrayList<>();
	
	public void addGen(Gen gen) {
		genes.add(gen);
	}

	public List<Gen> getGenes() {
		return genes;
	}

	public void setGenes(List<Gen> genes) {
		this.genes = genes;
	}
}
