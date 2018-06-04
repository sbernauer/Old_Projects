package WarOfTanks;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;

public class Control extends Applet {
	private Input theInput;
	private GameThread theGameThread;
	private Worldgenerator theWorldgenerator;
	private Menu theMenu;

	private URL codeBase;

	// Graphic Buffers
	private Image dbImage;
	private Graphics dbg;

	// Game variables
	public Tank[] Tanks = new Tank[4];

	public int numberPlayers = 4, currentPlayerID, weaponPanelAnimation = 0, infoWeapon = 0;
	private double shotX, shotY, shotPower, shotDirection;

	private boolean drivingRight, drivingLeft;
	public boolean menuIsOpen = true, weaponPanelAnimationDirection;

	private Color colorHP, colorCircle, colorAim, colorPlayerList;

	// Images
	private Image[] Images = new Image[15];

	public Control() {

	}

	public Control(URL codeBase) {
		this.codeBase = codeBase;
	}

	public BufferedImage imgBackground;

	@Override
	public void init() {
		this.setSize(1200, 680);

		codeBase = getCodeBase();
		Images[0] = getImage(codeBase, "chassis_green_1.png");
		Images[1] = getImage(codeBase, "chassis_green_2.png");
		Images[2] = getImage(codeBase, "chassis_red_1.png");
		Images[3] = getImage(codeBase, "chassis_red_2.png");
		Images[4] = getImage(codeBase, "chassis_blue_1.png");
		Images[5] = getImage(codeBase, "chassis_blue_2.png");
		Images[6] = getImage(codeBase, "chassis_purple_1.png");
		Images[7] = getImage(codeBase, "chassis_purple_2.png");
		Images[8] = getImage(codeBase, "pipe_left.png");
		Images[9] = getImage(codeBase, "pipe_right.png");
		Images[10] = getImage(codeBase, "lives.png");
		Images[11] = getImage(codeBase, "fuel.png");
		Images[12] = getImage(codeBase, "weapon_panel.png");

		Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(getImage(codeBase, "cursor.png"), new Point(this.getX() + 15, this.getY() + 16), "img");
		setCursor(cursor);

		colorCircle = new Color(144, 144, 144, 80);
		colorAim = new Color(255, 255, 255, 170);
		colorPlayerList = new Color(100, 100, 100, 80);

		// Create Menu
		theMenu = new Menu(this, codeBase);

		// Game control
		theInput = new Input(this, theMenu);
		addMouseListener(theInput);
		addMouseMotionListener(theInput);
		addKeyListener(theInput);

		// TEST---------------
		Tanks[0] = new Tank(codeBase, "Dawidoooo", 250, 150, 0, 100, 100, 0, 250);
		Tanks[1] = new Tank(codeBase, "No0bSeb", 50, 200, 3, 150, 100, 0, 250);
		Tanks[2] = new Tank(codeBase, "DerKillerKing", 250, 150, 2, 300, 300, 0, 250);
		Tanks[3] = new Tank(codeBase, "Sebidooo", 125, 150, 1, 400, 100, 0, 250);
		currentPlayerID = 0;
		// --------------------
		try {
			theWorldgenerator = new Worldgenerator(this, new URL(codeBase, "background.png"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		theWorldgenerator.render();
		imgBackground = theWorldgenerator.getImage();
		// Thread
		theGameThread = new GameThread(this);
		theGameThread.Thread();
	}

	public void updateCoordinates() {
		if (drivingRight) {
			int x_left, x_right, x_middle, y_left = 0, y_right = 0, y_middle = -1;
			x_left = (int) Tanks[currentPlayerID].getX() + 10;
			x_middle = (int) (Tanks[currentPlayerID].getX() + 20 + 3);
			x_right = (int) Tanks[currentPlayerID].getX() + 29;

			x_left = x_left < 0 ? 0 : x_left > 1199 ? 1199 : x_left;
			x_middle = x_middle < 0 ? 0 : x_middle > 1199 ? 1199 : x_middle;
			x_right = x_right < 0 ? 0 : x_right > 1199 ? 1199 : x_right;
			// Left
			for (int y = (int) Tanks[currentPlayerID].getY(); y >= 0 && y <= 679 && y <= (int) Tanks[currentPlayerID].getY() + 36 + 36; y++) {
				if (imgBackground.getRGB(x_left, y) == theWorldgenerator.getGroundColor().getRGB()) {
					y_left = y;
					break;
				}
			}
			// Right
			for (int y = (int) Tanks[currentPlayerID].getY(); y >= 0 && y <= 679 && y <= (int) Tanks[currentPlayerID].getY() + 36 + 36; y++) {
				if (imgBackground.getRGB(x_right, y) == theWorldgenerator.getGroundColor().getRGB()) {
					y_right = y;
					break;
				}
			}
			// Middle
			for (int y = (int) Tanks[currentPlayerID].getY() - 25; y >= 0 && y <= 679 && y <= (int) Tanks[currentPlayerID].getY() + 36 + 61; y++) {
				if (imgBackground.getRGB(x_middle, y) == theWorldgenerator.getGroundColor().getRGB()) {
					y_middle = y;
					break;
				}
			}
			if (y_middle != -1 && (y_right - y_left > 0 || Math.abs(y_right - y_left) < 35)) {
				Tanks[currentPlayerID].setRotation(Math.atan2(y_right - y_left, 19));
				Tanks[currentPlayerID].move(Tanks[currentPlayerID].getX() + 3, y_middle - 36, 3);
			}
		} else if (drivingLeft) {
			int x_left, x_right, x_middle, y_left = 0, y_right = 0, y_middle = -1;
			x_left = (int) Tanks[currentPlayerID].getX() + 10;
			x_middle = (int) (Tanks[currentPlayerID].getX() + 20 - 3);
			x_right = (int) Tanks[currentPlayerID].getX() + 29;

			x_left = x_left < 0 ? 0 : x_left > 1199 ? 1199 : x_left;
			x_middle = x_middle < 0 ? 0 : x_middle > 1199 ? 1199 : x_middle;
			x_right = x_right < 0 ? 0 : x_right > 1199 ? 1199 : x_right;
			// Left
			for (int y = (int) Tanks[currentPlayerID].getY(); y >= 0 && y <= 679 && y <= (int) Tanks[currentPlayerID].getY() + 36 + 36; y++) {
				if (imgBackground.getRGB(x_left, y) == theWorldgenerator.getGroundColor().getRGB()) {
					y_left = y;
					break;
				}
			}
			// Right
			for (int y = (int) Tanks[currentPlayerID].getY(); y >= 0 && y <= 679 && y <= (int) Tanks[currentPlayerID].getY() + 36 + 36; y++) {
				if (imgBackground.getRGB(x_right, y) == theWorldgenerator.getGroundColor().getRGB()) {
					y_right = y;
					break;
				}
			}
			// Middle
			for (int y = (int) Tanks[currentPlayerID].getY() - 25; y >= 0 && y <= 679 && y <= (int) Tanks[currentPlayerID].getY() + 36 + 61; y++) {
				if (imgBackground.getRGB(x_middle, y) == theWorldgenerator.getGroundColor().getRGB()) {
					y_middle = y;
					break;
				}
			}
			if (y_middle != -1 && (y_left - y_right > 0 || Math.abs(y_left - y_right) < 35)) {
				Tanks[currentPlayerID].setRotation(Math.atan2(-(y_left - y_right), 19));
				Tanks[currentPlayerID].move(Tanks[currentPlayerID].getX() - 3, y_middle - 36, 3);
			}
		}
	}

	@Override
	public void paint(Graphics g) {
		if (menuIsOpen) {
			dbImage = createImage(this.getSize().width, this.getSize().height);
			dbg = dbImage.getGraphics();
			theMenu.paint(dbg);
			g.drawImage(dbImage, 0, 0, this);
			theGameThread.Thread();
			return;
		}
		g.drawImage(imgBackground, 0, 0, this);

		// Set shooting direction and power
		shotPower = 2 * Math.hypot(Tanks[currentPlayerID].getAimX(), Tanks[currentPlayerID].getAimY());
		shotPower = shotPower > 350 ? 350 : shotPower;
		shotDirection = Math.atan2(-Tanks[currentPlayerID].getAimY(), Tanks[currentPlayerID].getAimX());
		Tanks[currentPlayerID].setShootingDirection(-shotDirection + Math.PI / 2);

		// Aim
		g.setColor(colorAim);
		g.fillArc((int) Tanks[currentPlayerID].getX() + 20 - (int) (shotPower / 2), (int) Tanks[currentPlayerID].getY() + 18 - (int) (shotPower / 2), (int) shotPower, (int) shotPower, (int) Math.toDegrees(shotDirection) - 10, 20);

		g.setFont(new Font("default", Font.BOLD, 12));
		for (Tank tank : Tanks) {
			// Draw pipe
			Graphics2D g2 = (Graphics2D) g;
			AffineTransform turn = new AffineTransform();
			turn.translate(tank.getX(), tank.getY());
			turn.rotate(tank.getShootingDirection(), 39 / 2, 36 / 2 + 5);
			if (tank.getShootingDirection() >= 0 && tank.getShootingDirection() <= Math.PI) {
				g2.drawImage(Images[9], turn, this); // right
			} else {
				g2.drawImage(Images[8], turn, this); // left
			}

			// Draw chassis
			turn = new AffineTransform();
			turn.translate(tank.getX(), tank.getY());
			turn.rotate(tank.getRotation(), 39 / 2, 36 / 2);
			switch (tank.getColor()) {
			case 0: // green
				if (tank.getAndIncFrameNumber() == 0) {
					g2.drawImage(Images[0], turn, this);
				} else {
					g2.drawImage(Images[1], turn, this);
				}
				break;
			case 1: // red
				if (tank.getAndIncFrameNumber() == 0) {
					g2.drawImage(Images[2], turn, this);
				} else {
					g2.drawImage(Images[3], turn, this);
				}
				break;
			case 2: // blue
				if (tank.getAndIncFrameNumber() == 0) {
					g2.drawImage(Images[4], turn, this);
				} else {
					g2.drawImage(Images[5], turn, this);
				}
				break;
			case 3: // purple
				if (tank.getAndIncFrameNumber() == 0) {
					g2.drawImage(Images[6], turn, this);
				} else {
					g2.drawImage(Images[7], turn, this);
				}
				break;
			}

			// Draw name
			g.setColor(Color.BLACK);
			g.drawString(tank.getPlayerName(), (int) (tank.getX() + 39 / 2 - tank.getPlayerName().length() * 3.1), (int) tank.getY() - 10);

			// Draw HP Border
			g.drawLine((int) tank.getX() - 1, (int) tank.getY() - 7, (int) tank.getX() - 1, (int) tank.getY() - 2);
			g.drawLine((int) tank.getX() + 40, (int) tank.getY() - 7, (int) tank.getX() + 40, (int) tank.getY() - 2);

			// Draw HP
			double percent = ((double) tank.getHealth() / tank.getMaxHealth());
			try {
				colorHP = new Color((int) (percent < 0.5 ? 255 : (1 - percent) * 510), (int) (percent > 0.5 ? 255 : percent * 510), 0, 255);
			} catch (Exception e) {
				colorHP = new Color(255, 0, 0, 255);
			}
			g.setColor(colorHP);
			g.drawLine((int) tank.getX(), (int) tank.getY() - 5, (int) (((double) tank.getHealth() / tank.getMaxHealth()) * 39 + tank.getX()), (int) tank.getY() - 5);
			g.drawLine((int) tank.getX(), (int) tank.getY() - 4, (int) (((double) tank.getHealth() / tank.getMaxHealth()) * 39 + tank.getX()), (int) tank.getY() - 4);
		}

		// Draw Circle for player tank
		g.setColor(colorCircle);
		g.fillOval((int) Tanks[currentPlayerID].getX() - 175 + 20, (int) Tanks[currentPlayerID].getY() - 175 + 18, 350, 350);
		g.setColor(Color.white);
		g.drawOval((int) Tanks[currentPlayerID].getX() - 175 + 20, (int) Tanks[currentPlayerID].getY() - 175 + 18, 350, 350);

		// Draw PlayerList
		g.setFont(new Font("default", Font.PLAIN, 12));
		g.setColor(colorPlayerList);
		g.fillRoundRect(990, 10, 200, 75, 25, 25);
		g.setColor(Color.black);
		g.drawRoundRect(990, 10, 200, 75, 25, 25);

		g.drawString("1.) Sebidooo", 1000, 30);
		g.drawString("2.) TheKillerKing", 1000, 45);
		g.drawString("3.) Dawidoooo", 1000, 60);
		g.drawString("4.) N00bi", 1000, 75);

		// Draw Weapon Panel
		if (weaponPanelAnimationDirection) {
			weaponPanelAnimation = weaponPanelAnimation < 180 ? weaponPanelAnimation + 30 : 180;
		} else {
			weaponPanelAnimation = weaponPanelAnimation > 0 ? weaponPanelAnimation - 30 : 0;
		}

		g.drawImage(Images[12], 150, 680 - weaponPanelAnimation, 900, 180, null);

		// Draw aviable Weapons on Weapon Panel
		g.setColor(Color.white);
		int x = 525, y = 695 - weaponPanelAnimation;
		for (int i = 0; i <= Tanks[currentPlayerID].getNumberWeaponNames() - 1; i++) {
			if (i == Tanks[currentPlayerID].getSelectedWeapon()) {
				g.drawImage(Tanks[currentPlayerID].getWeaponImages_selected()[i], x, y, 87, 54, null);
			} else {
				g.drawImage(Tanks[currentPlayerID].getWeaponImages()[i], x, y, 87, 54, null);
			}
			if (i == 4) {
				y += 57;
				x = 525 - 92;
			}
			x += 92;
		}

		// Draw info for weapon
		g.setFont(new Font("default", Font.BOLD, 16));
		g.drawImage(Tanks[currentPlayerID].getWeaponImages()[infoWeapon], 160, 798 - weaponPanelAnimation, 87, 54, null);
		g.drawString(Tanks[currentPlayerID].getWeaponNames()[0][infoWeapon], 210, 710 - weaponPanelAnimation);
		
		// Draw HP and Fuel
		g.setColor(Color.black);
		g.drawImage(Images[10], 5, 590, 40, 40, null);
		g.drawImage(Images[11], 5, 635, 40, 40, null);
		g.drawString(String.valueOf(Tanks[currentPlayerID].getHealth()), 50, 615);
		g.drawString(String.valueOf(Tanks[currentPlayerID].getFuel()), 50, 660);

		theGameThread.Thread();
	}

	public void update(Graphics g) {
		if (dbImage == null) {
			dbImage = createImage(this.getSize().width, this.getSize().height);
			dbg = dbImage.getGraphics();
		}
		paint(dbg);
		g.drawImage(dbImage, 0, 0, this);
	}

	public void mouseDragged(int x, int y) {
		Tanks[currentPlayerID].setAimX((int) (x - (Tanks[currentPlayerID].getX() + 20)));
		Tanks[currentPlayerID].setAimY((int) (y - (Tanks[currentPlayerID].getY() + 16)));
	}

	public void setDrivingRight(boolean driving) {
		if (driving) {
			setDrivingLeft(false);
		}
		drivingRight = driving;
	}

	public void setDrivingLeft(boolean driving) {
		if (driving) {
			setDrivingRight(false);
		}
		drivingLeft = driving;
	}

}
