package main;

import java.applet.Applet;
import java.awt.Button;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.TextField;
import java.util.List;

import core.Solver;
import objects.CalcObject;
import parser.Parser;

public class Main extends Applet {
	private static final long serialVersionUID = 1L;
	
	private TextField tfInput = new TextField(50);
	private Button btStart = new Button("LOS");
	private TextArea taResult = new TextArea();
		
	@Override
	public void init() {
		setSize(1000, 600);
		setLayout(new FlowLayout());
		add(tfInput);
		add(btStart);
		add(taResult);
		
		tfInput.setFont(new Font("Arial", Font.PLAIN, 30));
		taResult.setFont(new Font("Arial", Font.PLAIN, 25));
}
	
	@Override
	public boolean action(Event evt, Object what) {
		List<CalcObject> list = Parser.parseToObjects(tfInput.getText());
		list = Solver.simplify(list);
		String result = Parser.convertToString(list);
		taResult.setText(result);
		return true;
	}
}
