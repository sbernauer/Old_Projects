package main;

import gui.GUI;

public class GameLoop extends Thread {
	private GUI gui;
	
	public GameLoop(GUI gui) {
		this.gui = gui;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(33);
				gui.getGameCanvas().getLogic().process();
				gui.getGameCanvas().repaint();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
