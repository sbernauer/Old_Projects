package constants;

import java.awt.Color;

public abstract class Constants {
	public static final boolean DRAW_SHAPES = true; // false
	public static final boolean DRAW_IMAGES = true; // true
	public static final boolean DRAW_INFO = true; // false

	public static final int RENDER_RESOLUTION_X = 1920; // NICHT VERÄNDERN
	public static final int RENDER_RESOLUTION_Y = 1080; // NICHT VERÄNDERN

	public static final double SPEED_MAX = 20;
	public static final double SPEED_ACCELERATION = 1;
	public static final double SPEED_DECELERATION = 2;

	public static final double MAX_ALLOWED_BULLETS= 5;
	public static final double BULLET_RADIUS = 25;
	public static final double BULLET_SPEED = 5;
	public static final Color BULLET_COLOR = Color.red;
	public static final double GUN_LENGTH = 85;
}
