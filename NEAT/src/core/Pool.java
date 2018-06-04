package core;

import java.util.List;

public class Pool {
	private List<Species> species;
	private int generation = 0;
	private int innovation = Constants.AMOUNT_OUTUTS;
	private int currentSpecies = 1;
	private int currentGenome = 1;
	private int currentFrame = 0;
	private int maxFitness = 0;
	
	public Pool() {
		
	}
}
