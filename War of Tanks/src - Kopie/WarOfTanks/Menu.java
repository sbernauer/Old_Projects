package WarOfTanks;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Menu {
	private Control theControl;

	private URL codeBase;

	private Image tank_1, tank_2, pipe_left, pipe_right;

	private int mouseX, mouseY;
	private double rotation;

	public Menu() {

	}

	public Menu(Control theControl, URL codeBase) {
		this.theControl = theControl;
		this.codeBase = codeBase;

		try {
			tank_1 = ImageIO.read(new URL(codeBase, "chassis_green_1.png"));
			tank_2 = ImageIO.read(new URL(codeBase, "chassis_green_2.png"));
			pipe_left = ImageIO.read(new URL(codeBase, "pipe_left.png"));
			pipe_right = ImageIO.read(new URL(codeBase, "pipe_right.png"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void paint(Graphics g) {
		// Draw pipe
		Graphics2D g2 = (Graphics2D) g;
		AffineTransform turn = new AffineTransform();
		turn.translate(445, 375);
		turn.scale(350 / 39, 250 / 36);
		turn.rotate(rotation, 350 / 2 / (350 / 39), (250 + 60) / 2 / (250 / 36));
		g2.drawImage(pipe_right, turn, theControl);

		g.drawImage(tank_1, 425, 375, 350, 250, null);
	}

	public void mouseMoved(int x, int y) {
		mouseX = x;
		mouseY = y;
		rotation = Math.atan2(mouseY - 500, mouseX - 600) + Math.PI / 2;
		rotation = (rotation > Math.PI / 2 && rotation <= Math.PI) ? Math.PI / 2 : rotation;
		rotation = (rotation > Math.PI && rotation <= Math.PI * 1.5) ? -Math.PI / 2 : rotation;
		theControl.repaint();
	}
}