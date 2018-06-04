package WarOfTanks;

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
			if(theControl.weaponPanelAnimation == 180) {
				int x = 525, y = 695 - theControl.weaponPanelAnimation;
				for (int i = 0; i <= theControl.Tanks[theControl.currentPlayerID].getNumberWeaponNames() - 1; i++) {
					if(e.getX() >= x && e.getX() <= x + 87 && e.getY() >= y && e.getY() <= y + 54) {
						theControl.infoWeapon = i;
						break;
					}
					if (i == 4) {
						y += 57;
						x = 525 - 92;
					}
					x += 92;
				}
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (theControl.menuIsOpen) {
			theMenu.mouseClicked(true);
		} else {
			if (e.getButton() == MouseEvent.BUTTON1) {
				
				
				if(theControl.weaponPanelAnimation == 180) {
					int x = 525, y = 695 - theControl.weaponPanelAnimation;
					for (int i = 0; i <= theControl.Tanks[theControl.currentPlayerID].getNumberWeaponNames() - 1; i++) {
						if(e.getX() >= x && e.getX() <= x + 87 && e.getY() >= y && e.getY() <= y + 54) {
							theControl.Tanks[theControl.currentPlayerID].setSelectedWeapon(i);
							break;
						}
						if (i == 4) {
							y += 57;
							x = 525 - 92;
						}
						x += 92;
					}
				}
			} else if (e.getButton() == MouseEvent.BUTTON3) {
				theControl.Tanks[theControl.currentPlayerID].setFuel(150);
				theControl.currentPlayerID = theControl.currentPlayerID == 3 ? 0 : theControl.currentPlayerID + 1;
				theControl.infoWeapon = theControl.Tanks[theControl.currentPlayerID].getSelectedWeapon();
			}
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
