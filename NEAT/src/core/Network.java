package core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Network {
	private ArrayList<Neuron> neurons;
	
	public Network(Genome genome) {
		for(int i = 0; i < Constants.AMOUNT_INPUTS; i++) {
			neurons.add(new Neuron());
		}
		
		for(int i = 0; i < Constants.AMOUNT_OUTUTS; i++) {
			neurons.add(new Neuron());
		}
		
		Collections.sort(genome.genes, (g1, g2) -> g1.out - g2.out); //TODO Überprüfen, obs hier wirklich aufsteigend sein soll
		
		for(Gene gene : genome.genes) {
			if(gene.enabled) {
				if(neurons.get(gene.out) == null) {
					neurons.set(gene.out, new Neuron());
				}
				Neuron neuron = neurons.get(gene.out);
				neuron.incoming.add(gene);
				if(neurons.get(gene.into) == null) {
					neurons.set(gene.into, new Neuron());
				}
			}
		}
		
		genome.network = this;
	}
	
	public static List<Double> evaluateNetwork(Network network, List<Integer> inputs) {
		inputs.add(1);
		
		if(inputs.size() != Constants.AMOUNT_INPUTS) {
			System.out.println("Incorrect number of neural network inputs.");
			return null;
		}
		
		for(int i = 0; i < inputs.size(); i++) {
			network.neurons.get(i).value = inputs.get(i);
		}
		
		for(Neuron neuron : network.neurons) {
			int sum = 0;
			for(Gene incoming : neuron.incoming) {
				sum += incoming.weight * network.neurons.get(incoming.into).value;
			}
			if(neuron.incoming.size() > 0) {
				neuron.value = Tools.sigmoid(sum);
			}
		}
		
		List<Double> outputs = network.neurons.stream().skip(Constants.AMOUNT_INPUTS + 1).map(n -> n.value).collect(Collectors.toList());
		return outputs;
	}
}
