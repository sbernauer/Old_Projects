package main;

import java.util.Arrays;

import org.neuroph.core.NeuralNetwork;

public class TEMP {
	public static void main(String[] args) {
		NeuralNetwork network = NeuralNetwork.load("Car_v01.nnet");
		network.setInput(0, 0, 0, 0, 0, 0, 0, 0);
		network.calculate();
		double[] output = network.getOutput();
		System.out.println(output.length);
		System.out.println("-------------------");
		System.out.println(output[0]);
		System.out.println(output[1]);
		System.out.println("-------------------");
	}
}
