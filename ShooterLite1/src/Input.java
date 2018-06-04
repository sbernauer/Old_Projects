import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Input implements MouseListener, MouseMotionListener {

	private Control theControl;

	public Input() {
	}

	public Input(Control theControl) {
		this.theControl = theControl;
	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		theControl.setMouse(e.getX(), e.getY());

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

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
		theControl.setMouseGedrueckt(true);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		theControl.setMouseGedrueckt(false);
	}
}
