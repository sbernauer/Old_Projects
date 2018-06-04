package constants;

import java.util.Arrays;
import java.util.List;

public abstract class Constants {
	public static final int RENDER_RESOLUTION_X = 1920; // NICHT VER�NDERN
	public static final int RENDER_RESOLUTION_Y = 1080; // NICHT VER�NDERN

	public static final boolean RENDER_IMAGE = true;
	public static final boolean RENDER_SHAPE = true;
	public static final boolean RENDER_SENSORS = true;
	public static final boolean RENDER_INFO = true;

	public static final double CAR_LENGTH = 38;
	public static final double CAR_SPEED = 0;
	public static final int CAR_START_X = 850;
	public static final int CAR_START_Y = 925;
	public static final double CAR_STEERING_CHANGING = 0.1;

	public static final int COUNT_SENSORS = 8;
	public static final double ANGLE_BETWEEN_SENSORS = Math.PI / 7;
//	public static final List<Double> GAP_SENSOR_LENGTHS = Arrays.asList(100.0, 125.0, 150.0, 200.0, 200.0, 150.0, 125.0,
//	100.0); // Starting from the left to the right facing the front
	public static final List<Double> GAP_SENSOR_LENGTHS = Arrays.asList(100.0/3, 125.0/3, 150.0/3, 200.0/3, 200.0/3, 150.0/3, 125.0/3,
	100.0/3); // Starting from the left to the right facing the front
	public static final double STEP_MEASURING_SENSORS = 0.05;

}
