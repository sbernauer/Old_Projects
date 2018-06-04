package core;

import java.text.DecimalFormat;

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
		return "NEURON[" + new DecimalFormat("#.###").format(value) + "]";
	}
}
