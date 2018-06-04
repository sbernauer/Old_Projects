package render;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import core.Constants;
import core.Neuron;

public class RenderNeuron extends RenderObject {
	private int x;
	private int y;
	private Neuron neuron;

	public RenderNeuron(int x, int y, Neuron neuron) {
		this.x = x;
		this.y = y;
		this.neuron = neuron;
	}

	@Override
	public void render(Graphics2D g, int idInList) {
		g.setColor(Color.getHSBColor((float) neuron.getValue() / 3, 1f, 1f));
		g.setStroke(new BasicStroke(1f));
		g.drawOval(x - 3, y - 3, 6, 6);

		g.setColor(Color.BLACK);
		g.setFont(Constants.FONT_NEURON);
		g.drawString(idInList + ": " + neuron.getValue(), x, y);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Neuron getNeuron() {
		return neuron;
	}
}
