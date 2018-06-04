package core;

public class Gen {
	private Neuron inputNeuron;
	private Neuron outputNeuron;
	private double weight;
	private boolean enabled;
	private int innovation;

	public Gen(Neuron inputNeuron, Neuron outputNeuron, double weight, boolean enabled, int innovation) {
		this.inputNeuron = inputNeuron;
		this.outputNeuron = outputNeuron;
		this.weight = weight;
		this.enabled = enabled;
		this.innovation = innovation;
	}

	public Neuron getInputNeuron() {
		return inputNeuron;
	}

	public Neuron getOutputNeuron() {
		return outputNeuron;
	}

	public double getWeight() {
		return weight;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public int getInnovation() {
		return innovation;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "GEN[" + innovation + "; " + inputNeuron + "; " + outputNeuron + "; " + weight + "]";
	}
}
