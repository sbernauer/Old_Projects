package core;

public abstract class Tools {
	private static int innovation = 0;
	
	public static double sigmoid(double x) {
		return 2 / 1+(Math.exp(-49*x))-1; //TODO überprüfen
	}
	
	public static int newInnovation() {
		return innovation++;
	}
}
