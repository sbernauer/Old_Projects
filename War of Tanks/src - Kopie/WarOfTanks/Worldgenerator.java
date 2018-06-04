package WarOfTanks;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Worldgenerator {
	private Control theControl;

	private URL codeBase;

	private BufferedImage img;
	private Color colorGround;

	private int x, y;
	private double a, b, c, d, x2, y2;

	public Worldgenerator() {

	}

	public Worldgenerator(Control theControl, URL codeBase) {
		this.theControl = theControl;
		this.codeBase = codeBase;
	}

	public void render() {
		colorGround = new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255), 255);

		a = Math.random() - 0.5;
		b = Math.random() * 8 - 4;
		c = Math.random() * 20 - 10;
		d = Math.random() * 200 + 80;
		if (a < 0) {
			d = (680 - d);
		} else {
			d += 160;
		}

		try {
			img = ImageIO.read(new URL(codeBase, "background.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (x = 0; x < 1200; x++) {
			x2 = (x - 600);
			x2 = x2 / 110;
			y2 = (int) (a * Math.pow(x2, 4) + b * Math.pow(x2, 3) + c * x2 + d);
			y2 = y2 < 0 ? 0 : y2;
			for (y = 679; y >= y2; y--) {
				img.setRGB(x, y, colorGround.getRGB());
			}
		}
		for(x = 0; x < 1200; x++) {
			if(img.getRGB(x, 0) == colorGround.getRGB()) {
				render();
				return;
			}
			if(img.getRGB(x, 579) != colorGround.getRGB()) {
				render();
				return;
			}
		}
		placeTanks();
	}

	private void placeTanks() {
		x2 = (240 - 600);
		x2 = x2 / 110;
		y2 = (int) (a * Math.pow(x2, 4) + b * Math.pow(x2, 3) + c * x2 + d);
		theControl.Tanks[0].move(240 - 39 / 2, y2 - 36, 0);

		x2 = (480 - 600);
		x2 = x2 / 110;
		y2 = (int) (a * Math.pow(x2, 4) + b * Math.pow(x2, 3) + c * x2 + d);
		theControl.Tanks[2].move(480 - 39 / 2, y2 - 36, 0);

		x2 = (720 - 600);
		x2 = x2 / 110;
		y2 = (int) (a * Math.pow(x2, 4) + b * Math.pow(x2, 3) + c * x2 + d);
		theControl.Tanks[3].move(720 - 39 / 2, y2 - 36, 0);

		x2 = (960 - 600);
		x2 = x2 / 110;
		y2 = (int) (a * Math.pow(x2, 4) + b * Math.pow(x2, 3) + c * x2 + d);
		theControl.Tanks[1].move(960 - 39 / 2, y2 - 36, 0);

		for (int id = 0; id <= theControl.numberPlayers - 1; id++) {
			orientTank(id, true);
		}
	}

	public BufferedImage getImage() {
		return img;
	}

	public Color getGroundColor() {
		return colorGround;
	}

	private void orientTank(int TankID, boolean setShootingDirection) {
		int x_left, x_right, y_left = 0, y_right = 0;
		x_left = (int) theControl.Tanks[TankID].getX() + 10;
		x_right = (int) theControl.Tanks[TankID].getX() + 29;

		x_left = x_left < 0 ? 0 : x_left > 1199 ? 1199 : x_left;
		x_right = x_right < 0 ? 0 : x_right > 1199 ? 1199 : x_right;
		// Left
		for (int y = (int) theControl.Tanks[TankID].getY(); y >= 0 && y <= 679 && y <= (int) theControl.Tanks[TankID].getY() + 36 + 36; y++) {
			if (img.getRGB(x_left, y) == getGroundColor().getRGB()) {
				y_left = y;
				break;
			}
		}
		// Right
		for (int y = (int) theControl.Tanks[TankID].getY(); y >= 0 && y <= 679 && y <= (int) theControl.Tanks[TankID].getY() + 36 + 36; y++) {
			if (img.getRGB(x_right, y) == getGroundColor().getRGB()) {
				y_right = y;
				break;
			}
		}
		theControl.Tanks[TankID].setRotation(Math.atan2(y_right - y_left, 19));
		if (setShootingDirection) {
			theControl.Tanks[TankID].setShootingDirection(Math.atan2(y_right - y_left, 19) + Math.PI / 4);
		}
	}
}
