package gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

public class Gui extends JFrame implements KeyListener, MouseMotionListener {
	private int mouseX;
	private int mouseY;

	private GameCanvas gameCanvas;

	public Gui() {
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void display() {
		gameCanvas = new GameCanvas(this);
		add(gameCanvas);
		pack();
		setVisible(true);
	}

	public GameCanvas getGameCanvas() {
		return gameCanvas;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent event) {
		mouseX = event.getX();
		mouseY = event.getY();
	}

	@Override
	public void keyPressed(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.VK_W) {
			gameCanvas.getLogic().TODO_speed++;
		} else if (event.getKeyCode() == KeyEvent.VK_S) {
			gameCanvas.getLogic().TODO_speed--;
		}
	}

	@Override
	public void keyReleased(KeyEvent event) {
	}

	@Override
	public void keyTyped(KeyEvent event) {
	}

	public int getMouseX() {
		return mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}
}
