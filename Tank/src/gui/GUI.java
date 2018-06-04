package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class GUI extends JFrame implements KeyListener, MouseListener, MouseMotionListener {
	private int xMouse;
	private int yMouse;
	
	private boolean keyPressedLeft;
	private boolean keyPressedRight;
	private boolean keyPressedUp;
	private boolean keyPressedDown;

	private GameCanvas gameCanvas;

	public GUI() {
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
	public void mouseDragged(MouseEvent arg0) {
	}

	@Override
	public void mouseMoved(MouseEvent event) {
		xMouse = event.getX();
		yMouse = event.getY();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent event) {
		if(SwingUtilities.isLeftMouseButton(event)) {
			mouseMoved(event);
			gameCanvas.getLogic().getOwnTank().shoot();
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	@Override
	public void keyPressed(KeyEvent event) {
		switch (event.getKeyCode()) {
		case KeyEvent.VK_A:
			keyPressedLeft = true;
			break;
		case KeyEvent.VK_D:
			keyPressedRight = true;
			break;
		case KeyEvent.VK_W:
			keyPressedUp = true;
			break;
		case KeyEvent.VK_S:
			keyPressedDown = true;
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent event) {
		switch (event.getKeyCode()) {
		case KeyEvent.VK_A:
			keyPressedLeft = false;
			break;
		case KeyEvent.VK_D:
			keyPressedRight = false;
			break;
		case KeyEvent.VK_W:
			keyPressedUp = false;
			break;
		case KeyEvent.VK_S:
			keyPressedDown = false;
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent event) {
		if (event.getKeyChar() == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
	}

	public boolean isKeyPressedLeft() {
		return keyPressedLeft;
	}

	public boolean isKeyPressedRight() {
		return keyPressedRight;
	}

	public boolean isKeyPressedUp() {
		return keyPressedUp;
	}

	public boolean isKeyPressedDown() {
		return keyPressedDown;
	}
	
	public int getXMouse() {
		return xMouse;
	}
	
	public int getYMouse() {
		return yMouse;
	}
}
