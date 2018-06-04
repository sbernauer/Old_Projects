package gui;

import static constants.Constants.RENDER_RESOLUTION_X;
import static constants.Constants.RENDER_RESOLUTION_Y;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import constants.Constants;
import main.Logic;

public class GameCanvas extends JComponent {
	private Gui gui;
	private Logic logic;
	
	public GameCanvas(Gui gui) {
		this.gui = gui;
		logic = new Logic(gui);

		setFocusable(true);

		addKeyListener(gui);
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
		g.setColor(Color.BLACK);
		g.draw(logic.getStreet());
		
		logic.getSimpleCar().render(g);
		logic.getNeuralCar().render(g);
		
		if(Constants.SHOW_INFO) {
			g.setColor(Color.GRAY);
			g.setFont(new Font("Arial", Font.PLAIN, 25));
			g.drawString("speed: " + logic.TODO_speed, 5, 20);
		}
	}

	public Logic getLogic() {
		return logic;
	}
}