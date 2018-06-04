package main;

import gui.Gui;

public class GameLoop extends Thread {
	private Gui gui;
	
	public GameLoop(Gui gui) {
		this.gui = gui;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(15);
				gui.getGameCanvas().getLogic().process();
				gui.getGameCanvas().repaint();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
