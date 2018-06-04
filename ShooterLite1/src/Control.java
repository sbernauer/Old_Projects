import java.applet.Applet;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;

public class Control extends Applet {

	private Image dbImage;
	private Graphics dbg;
	private Input theInput;
	private spielThread theThread;
	
	private Image imgBackground, imgSchuss, imgSpieler;
	
	private boolean mausGedrueckt, SchussSichtbar = false;
	private double Drehwinkel;
	private int x = 0, y = 0, xSpieler = 1000, ySpieler = 500, xMouse, yMouse;
	float xSchuss, ySchuss, xSchussAbgeschossen, ySchussAbgeschossen, xSchussGeschw, ySchussGeschw;

	public Control() {
	}

	public void init() {
		this.setSize(getMaximumSize());
		//"Visitenkarten" mit Input und Thread austauschen
		theInput = new Input(this);
		theThread = new spielThread(this);
		//MausListener aus Klasse Input setzen
		addMouseListener(theInput);
		addMouseMotionListener(theInput);
		//Bilder einlesen
		imgBackground = getImage(getCodeBase(), "background.png");
		imgSchuss = getImage(getCodeBase(), "schuss.png");
		imgSpieler = getImage(getCodeBase(), "Spieler.png");

		Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(getImage(getCodeBase(), "fadenkreuz.png"), new Point(this.getX() + 15, this.getY() + 16), "img");
		setCursor(cursor);

		theThread.starteSchleife();

	}

	public void KoordinatenAktualisieren() {

		// Maus koordinaten 
		y = yMouse;
		x = xMouse;

		// Drehwinkel des spielers
		Drehwinkel = Math.atan2(y - ySpieler - imgSpieler.getHeight(this) / 2, x - xSpieler - imgSpieler.getWidth(this) / 2);

		// Schuss erzeugen
		if (mausGedrueckt && !SchussSichtbar) {
			SchussSichtbar = true;
			xSchuss = (float) (xSpieler + 40 + 100 * Math.cos(Drehwinkel));
			ySchuss = (float) (ySpieler + 85 + 100 * Math.sin(Drehwinkel));
			// Schussgeschwindigkeit
			xSchussGeschw = (float) (10 * Math.cos(Drehwinkel));
			ySchussGeschw = (float) (10 * Math.sin(Drehwinkel));
		}

		// Schuss bewegen
		xSchuss = xSchuss + xSchussGeschw;
		ySchuss = ySchuss + ySchussGeschw;

		if (xSchuss < 0 - imgSchuss.getWidth(this) || xSchuss > getWidth() || ySchuss < 0 - imgSchuss.getHeight(this) || ySchuss > getHeight()) {
			SchussSichtbar = false;
		}
	}

	public void paint(Graphics g) {
		//Hintergrundbild zeichnen
		g.drawImage(imgBackground, 0, 0, getWidth(), getHeight(), this);
		
		// Spieler drehen
		Graphics2D g2 = (Graphics2D) g;
		AffineTransform turn = new AffineTransform();
		turn.translate(xSpieler, ySpieler);
		turn.rotate((Drehwinkel + Math.PI / 2), (imgSpieler.getWidth(this) / 2), (imgSpieler.getHeight(this) / 2));
		g2.drawImage(imgSpieler, turn, this);

		//Schuss zeichnen
		if (SchussSichtbar) {
			g.drawImage(imgSchuss, (int) xSchuss, (int) ySchuss, this);
		}

	}

	public void setMouse(int x, int y) {
		xMouse = x;
		yMouse = y;
	}

	public void setMouseGedrueckt(boolean gedrueckt) {
		mausGedrueckt = gedrueckt;
	}

	public void update(Graphics g) {
		if (dbImage == null) {
			dbImage = createImage(this.getSize().width, this.getSize().height);
			dbg = dbImage.getGraphics();
		}
		dbg.setColor(getBackground());
		dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);
		dbg.setColor(getForeground());
		paint(dbg);
		g.drawImage(dbImage, 0, 0, this);
	}

}
