package render;

import static core.Constants.COUNT_INPUTS;
import static core.Constants.COUNT_OUTPUTS;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import core.Gen;
import core.Network;

import static core.Constants.RENDER_SCALE;

public class Renderer {

	public static final void renderNetwork(Graphics2D g, Network network) {
		g.scale(RENDER_SCALE, RENDER_SCALE);

		List<RenderObject> renderObjects = new ArrayList<>();

		// Input neurons
		for (int i = 0; i < COUNT_INPUTS; i++) {
			renderObjects.add(new RenderNeuron(10 + i * 100, 10, network.getNeurons().get(i)));
		}

		// Output neurons
		for (int i = COUNT_INPUTS; i < COUNT_INPUTS + COUNT_OUTPUTS; i++) {
			renderObjects.add(new RenderNeuron(10 + (i - COUNT_INPUTS) * 100, 80, network.getNeurons().get(i)));
		}

		// Hidden neurons
		for (int i = COUNT_INPUTS + COUNT_OUTPUTS; i < network.getNeurons().size(); i++) {
			renderObjects.add(new RenderNeuron((int) (Math.random() * 45 + 30), (int) (Math.random() * 30 + 20),
					network.getNeurons().get(i)));
		}

		for (Gen gen : network.getGenome().getGenes()) {
			if (gen.isEnabled()) {
				RenderNeuron startRenderNeuron = (RenderNeuron) renderObjects.stream()
						.map(renderObject -> (RenderNeuron) renderObject)
						.filter(renderNeuron -> renderNeuron.getNeuron() == gen.getInputNeuron()).findFirst()
						.orElse(null);

				RenderNeuron endRenderNeuron = (RenderNeuron) renderObjects.stream()
						.map(renderObject -> (RenderNeuron) renderObject)
						.filter(renderNeuron -> renderNeuron.getNeuron() == gen.getOutputNeuron()).findFirst()
						.orElse(null);

				if (startRenderNeuron != null && endRenderNeuron != null) {
					renderObjects.add(new RenderGen(gen, startRenderNeuron, endRenderNeuron));
				} else {
					System.err.println("Was ist da los? Ein Gen ohne passendes Neuron...");
				}
			}
		}

		for (int i = 0; i < renderObjects.size(); i++) {
			renderObjects.get(i).render(g, i);
		}
		// renderObjects.forEach(r -> r.render(g));
	}
}
