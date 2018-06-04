public class Test {
	public static void main(String[] args) {
		double fitness = 0;

		// XOR --------------------------------
		// network.setInput(Arrays.asList(0., 0.));
		fitness += Math.abs(0);

		// network.setInput(Arrays.asList(0., 1.));
		fitness += Math.abs(1. - 1);

		// network.setInput(Arrays.asList(1., 0.));
		fitness += Math.abs(1. - 1);

		// network.setInput(Arrays.asList(1., 1.));
		fitness += Math.abs(0);
		// END XOR --------------------------------

		System.out.println(fitness);
		System.out.println(sigmoid(1));
	}
	
	private static double sigmoid(double value) {
		return 1 / (1 + Math.pow(Math.E, -4.9 * value));
	}
}
