package core;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Graphics2D;

import render.Renderer;

public class Main extends Applet {
	private Network network = new Network();

	@Override
	public void init() {
		setSize(1800, 900);

		network.getNeurons().get(0).setValue(1);
		network.getNeurons().get(1).setValue(1);

		network.makeConnectionWithNeuron(network.getNeurons().get(0), network.getNeurons().get(4));
		network.makeConnectionWithNeuron(network.getNeurons().get(2), network.getNeurons().get(3));

		long startTime = System.currentTimeMillis();

		network.calculate();

		System.out.println("Zeit calculate: " + (System.currentTimeMillis() - startTime));
	}

	@Override
	public void paint(Graphics g) {
		long startTime = System.currentTimeMillis();

		Renderer.renderNetwork((Graphics2D) g, network);

		System.out.println("Zeit rendern: " + (System.currentTimeMillis() - startTime));
	}
}
