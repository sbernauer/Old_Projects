package core;

public class Neuron {
	private double value;
	
	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "NEURON[" + value + "]";
	}
}
