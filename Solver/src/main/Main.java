package main;

import java.applet.Applet;
import java.awt.Button;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.TextArea;
import java.awt.TextField;
import java.util.List;

import core.Solver;
import objects.CalcObject;
import parser.Parser;

public class Main extends Applet {
	private static final long serialVersionUID = 1L;
	
	private TextField tfInput = new TextField(80);
	private Button btStart = new Button("LOS");
	private TextArea taResult = new TextArea();
	
	
	/*
	 * FIXME
	 * bei z.B. a-2-2 kommt a-0 raus
	 */
	
	@Override
	public void init() {
		setSize(800, 400);
		setLayout(new FlowLayout());
		add(tfInput);
		add(btStart);
		add(taResult);
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
