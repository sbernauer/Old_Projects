package com.jlmd.simpleneuralnetwork.example;

import com.jlmd.simpleneuralnetwork.neuralnetwork.entity.Result;
import com.jlmd.simpleneuralnetwork.neuralnetwork.utils.DataUtils;
import com.jlmd.simpleneuralnetwork.neuralnetwork.NeuralNetwork;
import com.jlmd.simpleneuralnetwork.neuralnetwork.entity.Error;
import com.jlmd.simpleneuralnetwork.neuralnetwork.callback.INeuralNetworkCallback;

/**
 * @author jlmd
 */
public class Main {
	public static void main(String[] args) {
		System.out.println("Starting neural network sample... ");

		// float[][] x = DataUtils.readInputsFromFile("data/x.txt");
		// int[] t = DataUtils.readOutputsFromFile("data/t.txt");

		float[][] input = new float[][] { { 0, 0, 1 }, //
				{ 0, 1, 1 }, //
				{ 1, 0, 1 }, //
				{ 1, 1, 1 } };

		int[] expectedOutput = new int[] { 0, 1, 1,0 };

		NeuralNetwork neuralNetwork = new NeuralNetwork(input, expectedOutput, new INeuralNetworkCallback() {
			@Override
			public void success(Result result) {
				float[] valueToPredict = new float[] { -0.205f, 0.780f };
				System.out.println("Success percentage: " + result.getSuccessPercentage());
				System.out.println("Predicted result: " + result.predictValue(valueToPredict));
			}

			@Override
			public void failure(Error error) {
				System.out.println("Error: " + error.getDescription());
			}
		});

		neuralNetwork.startLearning();
	}
}
