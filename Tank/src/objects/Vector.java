package objects;

public class Vector {
	private double rotation;
	private double resLength;

	public Vector() {
	}

	public Vector createFromCartesich(double xLength, double yLength) {
		resLength = Math.sqrt(xLength * xLength + yLength * yLength);
		if (xLength != 0 || yLength != 0) {
			rotation = Math.atan2(yLength, xLength);
		}
		return this;
	}

	public Vector createFromPolar(double rotation, double resLength) {
		this.rotation = rotation;
		this.resLength = resLength;
		return this;
	}

	public double getXLength() {
		return resLength * Math.cos(rotation);
	}

	public double getYLength() {
		return resLength * Math.sin(rotation);
	}

	public double getRotation() {
		return rotation;
	}

	public double getResLength() {
		return resLength;
	}
}
