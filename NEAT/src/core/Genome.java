package core;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Genome {
	List<Gene> genes;
	int fitness = 0;
	private int adjustedFitness = 0;
	Network network;
	private int maxneuron = 0;
	private int globalRank = 0;
	private double mutationRateConnections = Constants.CHANCE_MUTATION_CONNECTION;
	private double mutationRateLinks = Constants.CHANCE_MUTATION_LINK;
	private double mutationRateBias = Constants.CHANCE_MUTATION_BIAS;
	private double mutationRateNodes = Constants.CHANCE_MUTATION_NODE;
	private double mutationRateEnable = Constants.CHANCE_MUTATION_ENABLE;
	private double mutationRateDisable = Constants.CHANCE_MUTATION_DISABLE;
	private double mutationRateStep = Constants.STEP_SIZE;
	
	public Genome() {
		
	}
	
	public static Genome copyGenome(Genome genome) {
		Genome copiedGenome = new Genome();
		copiedGenome.genes = genome.genes.stream().map(Gene::copyGene).collect(Collectors.toList());
		copiedGenome.maxneuron = genome.maxneuron;
		copiedGenome.mutationRateConnections = genome.mutationRateConnections;
		copiedGenome.mutationRateLinks = genome.mutationRateLinks;
		copiedGenome.mutationRateBias = genome.mutationRateBias;
		copiedGenome.mutationRateNodes = genome.mutationRateNodes;
		copiedGenome.mutationRateEnable = genome.mutationRateEnable;
		copiedGenome.mutationRateDisable = genome.mutationRateDisable;
		return copiedGenome;
	}
	
	public static Genome createBasicGenome() {
		Genome genome = new Genome();
		genome.maxneuron = Constants.AMOUNT_INPUTS + 1; // Wegen Bias eins mehr
		mutate(genome);
		return genome;
	}
	
//	function basicGenome()
//    local genome = newGenome()
//    local innovation = 1
//
//    genome.maxneuron = Inputs
//    mutate(genome)
//   
//    return genome
//end
	
//	function copyGenome(genome)
//    local genome2 = newGenome()
//    for g=1,#genome.genes do
//            table.insert(genome2.genes, copyGene(genome.genes[g]))
//    end
//    genome2.maxneuron = genome.maxneuron
//    genome2.mutationRates["connections"] = genome.mutationRates["connections"]
//    genome2.mutationRates["link"] = genome.mutationRates["link"]
//    genome2.mutationRates["bias"] = genome.mutationRates["bias"]
//    genome2.mutationRates["node"] = genome.mutationRates["node"]
//    genome2.mutationRates["enable"] = genome.mutationRates["enable"]
//    genome2.mutationRates["disable"] = genome.mutationRates["disable"]
//   
//    return genome2
//end
}
