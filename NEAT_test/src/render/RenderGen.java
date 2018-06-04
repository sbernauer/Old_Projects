package render;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import core.Constants;
import core.Gen;

public class RenderGen extends RenderObject {
	private Gen gen;

	private RenderNeuron startRenderObject;
	private RenderNeuron endRenderObject;

	public RenderGen(Gen gen, RenderNeuron startRenderObject, RenderNeuron endRenderObject) {
		this.gen = gen;
		this.startRenderObject = startRenderObject;
		this.endRenderObject = endRenderObject;
	}

	@Override
	void render(Graphics2D g, int idInList) {
		g.setColor(Color.getHSBColor((float) gen.getWeight() / 3, 1f, 1f));
		g.setStroke(new BasicStroke(1f));
//		g.drawLine(startRenderObject.getX(), startRenderObject.getY(), endRenderObject.getX(), endRenderObject.getY());

		g.setColor(Color.BLACK);
		g.setFont(Constants.FONT_GEN);
		g.drawString("" + gen.getWeight(), (startRenderObject.getX() + endRenderObject.getX()) / 2,
				(startRenderObject.getY() + endRenderObject.getY()) / 2);
	}
}
