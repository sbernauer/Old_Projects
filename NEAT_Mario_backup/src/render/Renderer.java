package render;

import static core.Constants.COUNT_INPUTS;
import static core.Constants.COUNT_OUTPUTS;
import static core.Constants.RENDER_NETWORK;
import static core.Constants.RENDER_NETWORK_GENS;
import static core.Constants.RENDER_NETWORK_OUTPUT;
import static core.Constants.RENDER_NETWORK_FITNESS;

import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import core.Gen;
import core.Network;

import static core.Constants.RENDER_SCALE;

public class Renderer {

	public static final void renderNetwork(Graphics2D g, Network network, int speciesNumber) {
		g.translate(speciesNumber * 150 * RENDER_SCALE, speciesNumber * 40 * RENDER_SCALE);
		g.scale(RENDER_SCALE, RENDER_SCALE);

		if (RENDER_NETWORK) {
			List<RenderNeuron> renderNeurons = new ArrayList<>();

			// Input neurons
			for (int i = 0; i < COUNT_INPUTS; i++) {
				renderNeurons.add(new RenderNeuron(10 + i * 100, 10, network.getNeurons().get(i)));
			}

			// Output neurons
			for (int i = COUNT_INPUTS; i < COUNT_INPUTS + COUNT_OUTPUTS; i++) {
				renderNeurons.add(new RenderNeuron(10 + (i - COUNT_INPUTS) * 100, 80, network.getNeurons().get(i)));
			}

			// Hidden neurons
			for (int i = COUNT_INPUTS + COUNT_OUTPUTS; i < network.getNeurons().size(); i++) {
				renderNeurons.add(new RenderNeuron((int) (Math.random() * 45 + 30), (int) (Math.random() * 30 + 20),
						network.getNeurons().get(i)));
			}

			List<RenderGen> renderGens = new ArrayList<>();

			if (RENDER_NETWORK_GENS) {
				for (Gen gen : network.getGenome().getGenes()) {
					if (gen.isEnabled()) {
						RenderNeuron startRenderNeuron = (RenderNeuron) renderNeurons.stream()
								.map(renderObject -> (RenderNeuron) renderObject)
								.filter(renderNeuron -> renderNeuron.getNeuron() == gen.getInputNeuron()).findFirst()
								.orElse(null);

						RenderNeuron endRenderNeuron = (RenderNeuron) renderNeurons.stream()
								.map(renderObject -> (RenderNeuron) renderObject)
								.filter(renderNeuron -> renderNeuron.getNeuron() == gen.getOutputNeuron()).findFirst()
								.orElse(null);

						if (startRenderNeuron != null && endRenderNeuron != null) {
							renderGens.add(new RenderGen(gen, startRenderNeuron, endRenderNeuron));
						} else {
							System.err.println("Was ist da los? Ein Gen ohne passendes Neuron...");
						}
					}
				}
			}
			for (int i = 0; i < renderNeurons.size(); i++) {
				renderNeurons.get(i).render(g, i);
			}
			for (int i = 0; i < renderGens.size(); i++) {
				renderGens.get(i).render(g, i);
			}
		}

		g.setFont(new Font("Arial", Font.BOLD, 20));
		if (RENDER_NETWORK_OUTPUT) {
			g.drawString("O: " + network.getOutput(), 10, 100);
		}
		if (RENDER_NETWORK_FITNESS) {
			g.drawString("F: " + network.getGenome().getFitness(), 10, 120);
		}
		g.scale(1 / RENDER_SCALE, 1 / RENDER_SCALE);
		g.translate(-speciesNumber * 150 * RENDER_SCALE, -speciesNumber * 40 * RENDER_SCALE);
	}
}
