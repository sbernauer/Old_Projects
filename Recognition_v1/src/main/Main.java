package main;

import org.encog.ml.factory.method.FeedforwardFactory;
import org.encog.ml.model.config.FeedforwardConfig;
import org.encog.neural.thermal.HopfieldNetwork;

public class Main {
	public static void main(String[] args) {
		HopfieldNetwork network = new HopfieldNetwork(4);
		
		
		final boolean[] pattern1 = {true, true, false, false};
		final boolean[] pattern2 = {true, false, false, false};
		boolean[] result;
		
		network.
	}
}
