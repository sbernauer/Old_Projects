package constants;

import java.util.Arrays;
import java.util.List;

public abstract class Constants {
	public static final int RENDER_RESOLUTION_X = 1920; // NICHT VERÄNDERN
	public static final int RENDER_RESOLUTION_Y = 1080; // NICHT VERÄNDERN
	
	public static final boolean SHOW_INFO = true;

	public static final double CAR_LENGTH = 76;
	public static final double CAR_SPEED = 0;
	public static final int CAR_START_X = 200;
	public static final int CAR_START_Y = 200;
	public static final double CAR_STEERING_CHANGING = 0.1;

	public static final int COUNT_SENSORS = 8;
	public static final double ANGLE_BETWEEN_SENSORS = Math.PI / 7;
	public static final List<Double> GAP_SENSOR_LENGTHS = Arrays.asList(100.0, 125.0, 150.0, 200.0, 200.0, 150.0, 125.0,
			100.0); // Starting from the left to the right facing the front
	public static final double STEP_MEASURING_SENSORS = 0.05;

}
