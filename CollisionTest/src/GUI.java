
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class GUI extends JFrame {
	private GameCanvas gameCanvas;

	private int width;
	private int height;

	public GUI(int width, int height) {
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.width = width;
		this.height = height;
	}

	public void display() {
		gameCanvas = new GameCanvas(this);
		add(gameCanvas);
		pack();
		setVisible(true);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}

class GameCanvas extends JComponent implements MouseMotionListener {
	private GUI gui;
	
	private Ellipse2D testEllipse;

	public GameCanvas(GUI gui) {
		this.gui = gui;
		
		addMouseMotionListener(this);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(gui.getWidth(), gui.getHeight());
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		Area testArea;
		try {
			testArea = ShapeCreator.createAreaFromImage("images/shape_tank_top.png");

			g2d.draw(testArea);
			g2d.draw(testEllipse);
			
			if(testIntersection(testEllipse, testArea)) {
				g2d.drawString("Ohhhh", 100, 100);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean testIntersection(Shape shapeA, Shape shapeB) {
		Area area = new Area(shapeA);
		area.intersect(new Area(shapeB));
		return !area.isEmpty();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		testEllipse = new Ellipse2D.Double(e.getX(), e.getY(), 50, 50);
		repaint();
	}
}
