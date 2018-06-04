package WarOfTanks;

public class GameThread {
	private Control theControl;

	public GameThread() {

	}

	public GameThread(Control theControl) {
		this.theControl = theControl;
	}

	public void Thread() {
		try {
			Thread.sleep(18);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		theControl.updateCoordinates();
		theControl.repaint();
	}
}
