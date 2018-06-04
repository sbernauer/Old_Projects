package main;

import static constants.Constants.MAX_ALLOWED_BULLETS;

import java.awt.Shape;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.List;

import gui.GUI;
import gui.GameCanvas;
import objects.Shot;
import objects.Tank;

public class Logic {
	private GUI gui;
	private GameCanvas gameCanvas;

	private Tank ownTank;

	private List<Shot> shots = new ArrayList<>();

	public Logic(GUI gui, GameCanvas gameCanvas) {
		this.gui = gui;
		this.gameCanvas = gameCanvas;

		ownTank = new Tank(gui, this);
	}

	public void process() {
		// Processing movements
		ownTank.processMovement();
		shots.stream().forEach(Shot::processMovement);
		
		//Detecting collisions

		if(shots.stream().anyMatch(s -> testIntersection(ownTank.getShapeBottom(), s.getShape()))) {
			System.exit(0);
		}
	}

	public Tank getOwnTank() {
		return ownTank;
	}

	public void addShot(Shot shot) {
		if (shots.size() < MAX_ALLOWED_BULLETS) {
			shots.add(shot);
		}
	}

	public static boolean testIntersection(Shape shapeA, Shape shapeB) {
		Area areaA = new Area(shapeA);
		areaA.intersect(new Area(shapeB));
		return !areaA.isEmpty();
	}

	// GETTERS AND SETTERS

	public List<Shot> getShots() {
		return shots;
	}
}
