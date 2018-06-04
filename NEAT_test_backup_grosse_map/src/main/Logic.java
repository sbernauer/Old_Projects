package main;

import java.awt.geom.Area;
import java.io.IOException;

import cars.Car;
import cars.NeuralCar;
import cars.SimpleCar;
import constants.Constants;
import gui.Gui;
import utils.ShapeCreator;

public class Logic {
	private Gui gui;

	private Area street;

	private SimpleCar simpleCar;
	private NeuralCar neuralCar;

	public double TODO_speed = Constants.CAR_SPEED;

	public Logic(Gui gui) {
		this.gui = gui;

		loadStreetShape();

		simpleCar = new SimpleCar(this);
		neuralCar = new NeuralCar(this);
	}

	public void process() {
		simpleCar.processMovement();
		neuralCar.processMovement();

		testCollision(simpleCar);
		testCollision(neuralCar);
	}

	private void testCollision(Car car) {
		Area carArea = new Area(car.getShape());
		carArea.intersect(street);
		boolean crash = !carArea.equals(new Area(car.getShape()));
		if (crash) {
			gameover();
		}
	}

	private void gameover() {
		System.exit(0);
		System.out.println("game over");
	}

	private void loadStreetShape() {
		try {
			street = (Area) ShapeCreator.createShapeFromImage("street.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Gui getGui() {
		return gui;
	}

	public Area getStreet() {
		return street;
	}

	public Car getSimpleCar() {
		return simpleCar;
	}
	
	public Car getNeuralCar() {
		return neuralCar;
	}
}
