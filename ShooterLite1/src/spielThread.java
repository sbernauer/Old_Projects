public class spielThread implements Runnable {
	private Control theControl;

	private boolean aktiv;
	
	spielThread(Control theControl) {
		this.theControl = theControl;
	}

	public void starteSchleife() {
		aktiv = true;
		new Thread(this).start();
	}
	
	public void stoppeSchleife() {
		aktiv = false;
	}

	public void run() {
		long letzteZeit = System.currentTimeMillis();
		final int zielFPS = 60;
		final long optimalZeit = 1000000 / zielFPS;
		long momentaneZeit;
		long updateDauer;
		double delta;
		while (aktiv) {
			momentaneZeit = System.currentTimeMillis();
			updateDauer = momentaneZeit - letzteZeit;
			letzteZeit = momentaneZeit;
			delta = updateDauer;
			theControl.KoordinatenAktualisieren();
			theControl.repaint();

			try {
				float warten = (letzteZeit - System.currentTimeMillis() + optimalZeit) / 1000;
				if (warten < 0)
					warten = 16;
				Thread.sleep((long) warten);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
