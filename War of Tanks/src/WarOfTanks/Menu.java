package WarOfTanks;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Menu {
	private Control theControl;

	private URL codeBase;

	private Image tank_1, tank_2, pipe_left, pipe_right,sign,ground;

	private int mouseX, mouseY;
	private double rotation, rotationSign=0;
	private boolean rotationRight = true;

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
			sign = ImageIO.read(new URL(codeBase, "sign.png"));
			//ground = ImageIO.read(new URL(codeBase, "ground.png"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void paint(Graphics g) {
		if(rotationSign >= 10) {
			rotationRight=false;
			rotationSign = 9.9;
		} 
		if(rotationSign <= -10) {
			rotationRight = true;
		}
		if(rotationRight){
			rotationSign+=0.3;
		} else {
			rotationSign-=0.3;
		}
		
		
		
		//draw background
		g.setColor(Color.blue);
		g.fillRect(0, 0, 1200, 680);
		// Draw pipe
		Graphics2D g2 = (Graphics2D) g;
		AffineTransform turn = new AffineTransform();
		turn.translate(431, 375);
		turn.scale(350 / 39, 250 / 36);
		turn.rotate(rotation, 350 / 2 / (350 / 39), (250 + 60) / 2 / (250 / 36));
		g2.drawImage(pipe_right, turn, theControl);
		

		g.drawImage(tank_1, 425, 375, 350, 250, null);
	
		Graphics2D g3 = (Graphics2D) g;
		AffineTransform turn2 = new AffineTransform();
		turn2.translate(300, -100);
		turn2.scale(0.7,0.7);
		turn2.rotate(Math.toRadians(rotationSign), 450, 30);
		g3.drawImage(sign, turn2, theControl);
		
		//g.drawImage(sign, 600 - 400, -50, 600+200,300, null);
	
		g.setColor(Color.green);
		g.fillRect(0, 625, 1200, 100);
		
	}

	public void mouseMoved(int x, int y) {
		mouseX = x;
		mouseY = y;
		rotation = Math.atan2(mouseY - 500, mouseX - 590) + Math.PI / 2;
		rotation = (rotation > Math.PI / 2 && rotation <= Math.PI) ? Math.PI / 2 : rotation;
		rotation = (rotation > Math.PI && rotation <= Math.PI * 1.5) ? -Math.PI / 2 : rotation;
//		theControl.repaint();
	}
	
	public void mouseClicked(boolean click) {
		if(click) {
			theControl.menuIsOpen = false;
			theControl.repaint();
		}
	}
}