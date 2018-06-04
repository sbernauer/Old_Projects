

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Graphics2D;

import core.Pool;

public class Main extends Applet {
	private Pool pool = new Pool();

	@Override
	public void init() {
		setSize(1900, 900);

		long startTime = System.currentTimeMillis();
		pool = new Pool();
		System.out.println("Zeit erzeugen: " + (System.currentTimeMillis() - startTime));

		startTime = System.currentTimeMillis();
		for (int i = 0; i < 3; i++) {
			pool.evaluate();
		}
		System.out.println("Zeit calculate: " + (System.currentTimeMillis() - startTime));
		
		System.out.println("Tmp: " + pool.getNetworks().get(0).getGenome().getFitness());
	}

	@Override
	public void paint(Graphics g) {
		long startTime = System.currentTimeMillis();
		pool.render((Graphics2D) g);
		System.out.println("Zeit rendern: " + (System.currentTimeMillis() - startTime));
	}
}
