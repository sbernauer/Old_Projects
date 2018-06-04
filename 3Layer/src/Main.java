import java.util.Random;

public class Main {
	public static void main(String[] args) {
		double[][] X = new double[][] { { 0, 0, 1 }, //
				{ 0, 1, 1 }, //
				{ 1, 0, 1 }, //
				{ 1, 1, 1 } };

		double[][] y = new double[][] { { 0 }, //
				{ 1 }, //
				{ 1 }, //
				{ 0 } };

		double[][] syn0 = createRandomMatrix(3, 4, 1);
		double[][] syn1 = createRandomMatrix(4, 1, 1);
		
		for(int i = 0; i <= 60000; i++) {
			
			double[][] l0 = X;
			double l1 = nonlin(Math.)
			
					l1 = nonlin(np.dot(l0,syn0))
					30.
					l2 = nonlin(np.dot(l1,syn1))
		}
	}

	private static double nonlin(double x, boolean... deriv) {
		if (deriv.length > 0 && deriv[0]) {
			return x * (1 - x);
		}
		return 1 / (1 + Math.exp(1 - x));
	}

	// private static double[][] createRandomMatrix(int rows, int columns,
	// double lowestValue, double highestValue, long... seed) {
	private static double[][] createRandomMatrix(int rows, int columns, long... seed) {
		Random random = seed.length > 0 ? new Random(seed[0]) : new Random();

		double[][] matrix = new double[rows][columns];

		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				matrix[r][c] = 2 * Math.random() - 1;
			}
		}

		return matrix;
	}
}
