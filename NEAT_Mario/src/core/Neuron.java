package core;

public class Neuron {
	private int id;
	private double value;

	@SuppressWarnings("unused")
	private Neuron() {
	}

	public Neuron(int id) {
		this.id = id;
	}

	public Neuron(int id, double value) {
		this.id = id;
		this.value = value;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "NEURON[" + id + ";" + value + "]";
	}
}
