package core;

import java.util.List;

import objects.CalcObject;
import objects.Divide;
import objects.Minus;
import objects.Multiply;
import objects.Plus;

public abstract class Solver {
	private static Plus plus = new Plus();
	private static Minus minus = new Minus();
	private static Multiply multiply = new Multiply();
	private static Divide divide = new Divide();
	
	private Solver() {
	}
	
	public static List<CalcObject> simplify(List<CalcObject> list) {
		multiply.process(list);
		divide.process(list);
		plus.process(list);
		minus.process(list);
		return list;
	}
}
