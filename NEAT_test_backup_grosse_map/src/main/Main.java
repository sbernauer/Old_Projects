package main;
import gui.Gui;

public class Main {
	public static void main(String[] args) {		
		Gui gui = new Gui();
		gui.display();
		
		GameLoop gameLoop = new GameLoop(gui);
		gameLoop.start();
	}
}
