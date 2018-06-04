package cars;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import constants.Constants;
import main.Logic;
import main.Sensor;
import utils.ShapeCreator;

public abstract class Car {
	private Logic logic;
	
	private double posX = Constants.CAR_START_X + Math.random() * 50 - 25; // TODO
	private double posY = Constants.CAR_START_Y + Math.random() * 50 - 25; // TODO
	private double steering;

	private Shape shape;
	private Shape originalShape;

	private List<Sensor> sensors = new ArrayList<>();

	protected abstract List<Boolean> calculateKeyPresses(List<Sensor> sensors);
	protected abstract Color getCarColor();

	public Car(Logic logic) {
		this.logic = logic;
		
		try {
			originalShape = ShapeCreator.createShapeFromImage("car_shape.png");
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		shape = originalShape;

		for (int i = 0; i < Constants.COUNT_SENSORS; i++) {
			sensors.add(new Sensor(logic));
		}

		calcuateSensorsLines();
		calcuateSensorsWeihts();
	}

	public void processMovement() {
//		posX += Math.cos(steering) * Constants.CAR_SPEED;
//		posY += Math.sin(steering) * Constants.CAR_SPEED;		
		posX += Math.cos(steering) * logic.TODO_speed;
		posY += Math.sin(steering) * logic.TODO_speed;

		AffineTransform transform = new AffineTransform();
		transform.translate(posX, posY);
		transform.rotate(steering, originalShape.getBounds().getCenterX(), originalShape.getBounds().getCenterY());
		shape = transform.createTransformedShape(originalShape);

		calcuateSensorsLines();
		calcuateSensorsWeihts();

		List<Boolean> keyPresses = calculateKeyPresses(sensors);
		if (keyPresses.get(0)) {
			steering += Constants.CAR_STEERING_CHANGING;
		}
		if (keyPresses.get(1)) {
			steering -= Constants.CAR_STEERING_CHANGING;
		}
	}

	private void calcuateSensorsLines() {
		for (int i = 0; i < Constants.COUNT_SENSORS; i++) {
			double rotation = steering - Math.PI / 2 + i * Constants.ANGLE_BETWEEN_SENSORS;
			double startX = shape.getBounds().getCenterX() + Math.cos(steering) * Constants.CAR_LENGTH / 2;
			double startY = shape.getBounds().getCenterY() + Math.sin(steering) * Constants.CAR_LENGTH / 2;
			double endX = startX + Math.cos(rotation) * Constants.GAP_SENSOR_LENGTHS.get(i);
			double endY = startY + Math.sin(rotation) * Constants.GAP_SENSOR_LENGTHS.get(i);

			sensors.get(i).setLine(new Line2D.Double(startX, startY, endX, endY));
		}
	}

	private void calcuateSensorsWeihts() {
		sensors.stream().forEach(Sensor::measureValue);
	}

	public void render(Graphics2D g) {
		g.setColor(getCarColor());
		g.setStroke(new BasicStroke(1));
		g.fill(shape);

		g.setStroke(new BasicStroke(4));
		g.setFont(new Font("Arial", Font.PLAIN, 25));
		sensors.stream().forEach(s -> {
			g.setColor(Color.getHSBColor((float) (s.getValue() * 0.4), 0.9f, 0.9f));
			g.draw(s.getLine());
			g.drawString(String.valueOf((double) Math.round(s.getValue() * 10) / 10), (int) s.getLine().getX2(),
					(int) s.getLine().getY2());
		});
	}

	public double getPosX() {
		return posX;
	}

	public double getPosY() {
		return posY;
	}

	public double getSteering() {
		return steering;
	}

	public void setSteering(double steering) {
		this.steering = steering;
	}

	public Shape getShape() {
		return shape;
	}
}
