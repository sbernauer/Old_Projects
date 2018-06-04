package objects;

import static constants.Constants.SPEED_ACCELERATION;
import static constants.Constants.GUN_LENGTH;
import static constants.Constants.SPEED_DECELERATION;
import static constants.Constants.SPEED_MAX;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.io.IOException;

import gui.GUI;
import main.Logic;
import tools.ShapeCreator;

public class Tank {
	private GUI gui;
	private Logic logic;

	private Shape shapeBottomOriginal;
	private Shape shapeTopOriginal;
	private Shape shapeBottom;
	private Shape shapeTop;

	private AffineTransform transformTop;
	private AffineTransform transformBottom;

	private double xPos = 50;
	private double yPos = 50;
	private double xSpeed;
	private double ySpeed;
	private double rotation;
	private double speedInDirection;
	private double rotationTop;

	public Tank(GUI gui, Logic logic) {
		this.gui = gui;
		this.logic = logic;

		init();
	}

	public void processMovement() {
		processMovingKeys();
		rotateTankTop();

		calculateCartesischFromPolar();
		xPos += xSpeed;
		yPos += ySpeed;

		// Rotate Bottom Transform and Shape
		transformBottom = new AffineTransform();
		transformBottom.translate(xPos, yPos);
		transformBottom.rotate(rotation + Math.PI / 2, shapeBottomOriginal.getBounds().getCenterX(),
				shapeBottomOriginal.getBounds().getCenterY());
		shapeBottom = transformBottom.createTransformedShape(shapeBottomOriginal);

		// Rotate Top Transform and Shape
		transformTop = new AffineTransform();
		transformTop.translate(xPos, yPos);
		transformTop.rotate(rotationTop + Math.PI / 2, shapeTopOriginal.getBounds().getCenterX(),
				shapeTopOriginal.getBounds().getCenterY());
		shapeTop = transformTop.createTransformedShape(shapeTopOriginal);
	}

	private void calculatePolarFromCartesisch() {
		speedInDirection = Math.sqrt(xSpeed * xSpeed + ySpeed * ySpeed);
		if (xSpeed != 0 || ySpeed != 0) {
			rotation = Math.atan2(ySpeed, xSpeed);
		}
	}

	private void calculateCartesischFromPolar() {
		xSpeed = speedInDirection * Math.cos(rotation);
		ySpeed = speedInDirection * Math.sin(rotation);
	}

	private void roundXYSpeedToZero() {
		if (Math.abs(xSpeed) > 0 && Math.abs(xSpeed) < 0.001) {
			xSpeed = 0;
		}
		if (Math.abs(ySpeed) > 0 && Math.abs(ySpeed) < 0.001) {
			ySpeed = 0;
		}
	}

	public void shoot() {
		logic.addShot(new Shot(calculateXShootPoint(), calculateYShootPoint(), rotationTop));
	}

	private void processMovingKeys() {
		if (gui.isKeyPressedLeft()) {
			calculateCartesischFromPolar();
			xSpeed -= SPEED_ACCELERATION;
			if (xSpeed < -SPEED_MAX) {
				xSpeed = -SPEED_MAX;
			}
			roundXYSpeedToZero();
			calculatePolarFromCartesisch();
		}

		if (gui.isKeyPressedRight()) {
			calculateCartesischFromPolar();
			xSpeed += SPEED_ACCELERATION;
			if (xSpeed > SPEED_MAX) {
				xSpeed = SPEED_MAX;
			}
			roundXYSpeedToZero();
			calculatePolarFromCartesisch();
		}

		if (gui.isKeyPressedUp()) {
			calculateCartesischFromPolar();
			ySpeed -= SPEED_ACCELERATION;
			if (ySpeed < -SPEED_MAX) {
				ySpeed = -SPEED_MAX;
			}
			roundXYSpeedToZero();
			calculatePolarFromCartesisch();
		}

		if (gui.isKeyPressedDown()) {
			calculateCartesischFromPolar();
			ySpeed += SPEED_ACCELERATION;
			if (ySpeed > SPEED_MAX) {
				ySpeed = SPEED_MAX;
			}
			roundXYSpeedToZero();
			calculatePolarFromCartesisch();
		}

		// Panzer wieder abbremsen, wenn keine Taste gedrückt
		if (!gui.isKeyPressedLeft() && !gui.isKeyPressedRight()) {
			calculateCartesischFromPolar();
			if (xSpeed > 0) {
				xSpeed -= SPEED_DECELERATION;
				if (xSpeed < 0) {
					xSpeed = 0;
				}
			} else if (xSpeed < 0) {
				xSpeed += SPEED_DECELERATION;
				if (xSpeed > 0) {
					xSpeed = 0;
				}
			}
			roundXYSpeedToZero();
			calculatePolarFromCartesisch();
		}
		if (!gui.isKeyPressedUp() && !gui.isKeyPressedDown()) {
			calculateCartesischFromPolar();
			if (ySpeed > 0) {
				ySpeed -= SPEED_ACCELERATION;
				if (ySpeed < 0) {
					ySpeed = 0;
				}
			} else if (ySpeed < 0) {
				ySpeed += SPEED_ACCELERATION;
				if (ySpeed > 0) {
					ySpeed = 0;
				}
			}
			roundXYSpeedToZero();
			calculatePolarFromCartesisch();
		}
	}

	private void rotateTankTop() {
		rotationTop = Math.atan2(gui.getYMouse() - shapeTop.getBounds().getCenterY(),
				gui.getXMouse() - shapeTop.getBounds().getCenterX());
	}

	private void init() {
		try {
			shapeBottomOriginal = ShapeCreator.createShapeFromImage("images/shape_tank_bottom.png");
			shapeTopOriginal = ShapeCreator.createShapeFromImage("images/shape_tank_top.png");

			shapeBottom = shapeBottomOriginal;
			shapeTop = shapeTopOriginal;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private double calculateXShootPoint() {
		return shapeTop.getBounds().getCenterX() + Math.cos(rotationTop) * GUN_LENGTH; // TODO
	}

	private double calculateYShootPoint() {
		return shapeTop.getBounds().getCenterY() + Math.sin(rotationTop) * GUN_LENGTH; // TODO
	}

	// GETTERS AND SETTERS

	public Shape getShapeBottom() {
		return shapeBottom;
	}

	public Shape getShapeTop() {
		return shapeTop;
	}

	public AffineTransform getTransformTop() {
		return transformTop;
	}

	public AffineTransform getTransformBottom() {
		return transformBottom;
	}

	public double getXPos() {
		return xPos;
	}

	public double getYPos() {
		return yPos;
	}

	public double getXSpeed() {
		return xSpeed;
	}

	public double getYSpeed() {
		return ySpeed;
	}

	public double getRotation() {
		return rotation;
	}

	public double getSpeedInDirection() {
		return speedInDirection;
	}

	public double getRotationTop() {
		return rotationTop;
	}
}
