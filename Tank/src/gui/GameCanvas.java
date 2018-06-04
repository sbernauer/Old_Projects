package gui;

import static constants.Constants.BULLET_COLOR;
import static constants.Constants.DRAW_IMAGES;
import static constants.Constants.DRAW_INFO;
import static constants.Constants.DRAW_SHAPES;
import static constants.Constants.RENDER_RESOLUTION_X;
import static constants.Constants.RENDER_RESOLUTION_Y;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import main.Logic;

public class GameCanvas extends JComponent {
	private GUI gui;
	private Logic logic;

	private Image imgBackground;
	private Image imgTankBottom;
	private Image imgTankTopGreen;
	private Image imgTankTopRed;

	public GameCanvas(GUI gui) {
		this.gui = gui;
		logic = new Logic(gui, this);

		setFocusable(true);

		loadImages();

		addKeyListener(gui);
		addMouseListener(gui);
		addMouseMotionListener(gui);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(gui.getWidth(), gui.getHeight());
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		g2d.scale(gui.getWidth() / RENDER_RESOLUTION_X, gui.getHeight() / RENDER_RESOLUTION_Y);

		paintInFullHd(g2d);
	}

	private void paintInFullHd(Graphics2D g) {
		g.drawImage(imgBackground, 0, 0, RENDER_RESOLUTION_X, RENDER_RESOLUTION_Y, null);

		if (DRAW_IMAGES) {
			g.drawImage(imgTankBottom, logic.getOwnTank().getTransformBottom(), null);
			g.drawImage(imgTankTopGreen, logic.getOwnTank().getTransformTop(), null);

			g.setColor(BULLET_COLOR);
			logic.getShots().stream().forEach(s -> g.fill(s.getShape()));
		}

		if (DRAW_SHAPES) {
			g.setColor(Color.RED);
			g.setStroke(new BasicStroke(4));
			g.draw(logic.getOwnTank().getShapeBottom());
			g.draw(logic.getOwnTank().getShapeTop());
			logic.getShots().stream().forEach(s -> g.draw(s.getShape()));
		}

		if (DRAW_INFO) {
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial", Font.PLAIN, 25));
			g.drawString("xPos: " + logic.getOwnTank().getXPos(), 5, 20);
			g.drawString("yPos: " + logic.getOwnTank().getYPos(), 5, 40);
			g.drawString("xSpeed: " + logic.getOwnTank().getXSpeed(), 5, 60);
			g.drawString("ySpeed: " + logic.getOwnTank().getYSpeed(), 5, 80);
			g.drawString("rotation: " + logic.getOwnTank().getRotation(), 5, 100);
			g.drawString("speedInDirection: " + logic.getOwnTank().getSpeedInDirection(), 5, 120);
		}
	}

	private void loadImages() {
		try {
			imgBackground = ImageIO.read(new File("images/background.png"));

			imgTankBottom = ImageIO.read(new File("images/tank_bottom.png"));
			imgTankTopGreen = ImageIO.read(new File("images/tank_top_green.png"));
			imgTankTopRed = ImageIO.read(new File("images/tank_top_red.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Logic getLogic() {
		return logic;
	}

	public Image getImgBackground() {
		return imgBackground;
	}

	public Image getImgTankBottom() {
		return imgTankBottom;
	}

	public Image getImgTankTopGreen() {
		return imgTankTopGreen;
	}

	public Image getImgTankTopRed() {
		return imgTankTopRed;
	}
}