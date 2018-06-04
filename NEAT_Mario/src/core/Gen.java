package core;

public class Gen {
	private int inputNeuronId;
	private int outputNeuronId;
	private double weight;
	private boolean enabled;
	private int innovation;

	public Gen(int inputNeuronId, int outputNeuronId, double weight, boolean enabled, int innovation) {
		this.inputNeuronId = inputNeuronId;
		this.outputNeuronId = outputNeuronId;
		this.weight = weight;
		this.enabled = enabled;
		this.innovation = innovation;
	}

	public int getInputNeuronId() {
		return inputNeuronId;
	}

	public int getOutputNeuronId() {
		return outputNeuronId;
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
		return "GEN[" + innovation + "; " + inputNeuronId + "; " + outputNeuronId + "]";
	}
}
