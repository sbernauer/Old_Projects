package WarOfTanks;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Input implements MouseListener, MouseMotionListener, KeyListener {
	private Control theControl;
	private Menu theMenu;

	public Input() {

	}

	public Input(Control theControl, Menu theMenu) {
		this.theControl = theControl;
		this.theMenu = theMenu;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_A) {
			theControl.setDrivingLeft(true);
		} else if (e.getKeyCode() == KeyEvent.VK_D) {
			theControl.setDrivingRight(true);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_A) {
			theControl.setDrivingLeft(false);
		} else if (e.getKeyCode() == KeyEvent.VK_D) {
			theControl.setDrivingRight(false);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (!theControl.menuIsOpen) {
			theControl.Tanks[theControl.currentPlayerID].setAimX((int) (e.getX() - (theControl.Tanks[theControl.currentPlayerID].getX() + 20)));
			theControl.Tanks[theControl.currentPlayerID].setAimY((int) (e.getY() - (theControl.Tanks[theControl.currentPlayerID].getY() + 16)));
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (theControl.menuIsOpen) {
			theMenu.mouseMoved(e.getX(), e.getY());
		} else {
			if (e.getY() >= 500 && e.getX() >= 150 && e.getX() <= 1050) {
				theControl.weaponPanelAnimationDirection = true;
			} else {
				theControl.weaponPanelAnimationDirection = false;
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			// theControl.theWorldgenerator.render();
			// theControl.imgBackground =
			// theControl.theWorldgenerator.getImage();
			if (theControl.weaponPanelAnimation == 180) {
				int x = 170, y = 710 - theControl.weaponPanelAnimation;
				for (int i = 0; i <= theControl.Tanks[theControl.currentPlayerID].getNumberWeaponNames() - 1; i++) {
					if (e.getX() >= x && e.getX() <= x + 170 && e.getY() >= y && e.getY() <= y + 60) {
						theControl.Tanks[theControl.currentPlayerID].setSelectedWeapon(i);
						break;
					}
					if (i == 4) {
						y += 73;
						x = -4;
					}
					x += 174;
				}
			}
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			theControl.Tanks[theControl.currentPlayerID].setFuel(150);
			theControl.currentPlayerID = theControl.currentPlayerID == 3 ? 0 : theControl.currentPlayerID + 1;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}
}
