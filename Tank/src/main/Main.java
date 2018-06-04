package main;
import gui.GUI;

public class Main {
	public static void main(String[] args) {		
		GUI gui = new GUI();
		gui.display();
		
		GameLoop gameLoop = new GameLoop(gui);
		gameLoop.start();
	}
}
