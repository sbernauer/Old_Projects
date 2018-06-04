package objects;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import static constants.Constants.BULLET_RADIUS;
import static constants.Constants.BULLET_SPEED;

public class Shot {
	private double xPos;
	private double yPos;
	private double xSpeed;
	private double ySpeed;
	
	private Shape shape;

	public Shot(double xPos, double yPos, double direction) {
		this.xPos = xPos - BULLET_RADIUS / 2;
		this.yPos = yPos - BULLET_RADIUS / 2;
		xSpeed = BULLET_SPEED * Math.cos(direction);
		ySpeed = BULLET_SPEED * Math.sin(direction);
		
		calculateShape();
	}

	public void processMovement() {
		xPos += xSpeed;
		yPos += ySpeed;
		
		calculateShape();
	}
	
	private void calculateShape() {
		shape = new Ellipse2D.Double(xPos, yPos, BULLET_RADIUS, BULLET_RADIUS);
	}

	// GETTERS AND SETTERS

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

	public Shape getShape() {
		return shape;
	}
}
