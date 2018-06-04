package core;

import java.awt.Font;

public class Constants {
	public static final boolean USE_PARALLEL_STREAM_FOR_CALCULATION = false;
	
	public static final int COUNT_INPUTS = 2;
	public static final int COUNT_OUTPUTS = 1;

	public static final int COUNT_SPECIES = 2;

	public static final double STARTING_VALUE_WEIGHT = 1;
	public static final boolean STARTING_VALUE_ENABLED = true;

	public static final double RENDER_SCALE = 3;
	public static final int RENDER_COUNT_NETWORK = 3;
	
	public static final boolean RENDER_NETWORK = true;
	public static final boolean RENDER_NETWORK_GENS = true;
	public static final boolean RENDER_NETWORK_OUTPUT = false;
	public static final boolean RENDER_NETWORK_FITNESS = false;
	public static final boolean RENDER_NETWORK_FITNESS_FOR_ALL = true;
	
	public static final Font FONT_NEURON = new Font("Arial", Font.PLAIN, 6);
	public static final Font FONT_GEN = new Font("Arial", Font.PLAIN, 6);
}
