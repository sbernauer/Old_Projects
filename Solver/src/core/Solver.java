package core;

import java.util.List;

import objects.CalcObject;
import objects.Divide;
import objects.Minus;
import objects.Multiply;
import objects.Number;
import objects.Plus;

public abstract class Solver {
	private Solver() {
	}
	
	public static List<CalcObject> simplify(List<CalcObject> list) {
		// Dot calc
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).getClass().equals(Multiply.class)) {
				CalcObject first = list.get(i - 1);
				CalcObject secound = list.get(i + 1);
				if(first.getClass().equals(Number.class) && secound.getClass().equals(Number.class)) {
					list.set(i, Multiply.multiply((Number)first, (Number)secound));
					list.remove(i + 1);
					list.remove(i - 1);
					i--;
				}
			}
			if(list.get(i).getClass().equals(Divide.class)) {
				CalcObject first = list.get(i - 1);
				CalcObject secound = list.get(i + 1);
				if(first.getClass().equals(Number.class) && secound.getClass().equals(Number.class)) {
					list.set(i, Divide.divide((Number)first, (Number)secound));
					list.remove(i + 1);
					list.remove(i - 1);
					i--;
				}
			}
		}
		// Line calc
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).getClass().equals(Plus.class)) {
				CalcObject first = list.get(i - 1);
				CalcObject secound = list.get(i + 1);
				if(first.getClass().equals(Number.class) && secound.getClass().equals(Number.class)) {
					list.set(i, Plus.plus((Number)first, (Number)secound));
					list.remove(i + 1);
					list.remove(i - 1);
					i--;
				}
			}
			if(list.get(i).getClass().equals(Minus.class)) {
				CalcObject first = list.get(i - 1);
				CalcObject secound = list.get(i + 1);
				if(first.getClass().equals(Number.class) && secound.getClass().equals(Number.class)) {
					list.set(i, Minus.minus((Number)first, (Number)secound));
					list.remove(i + 1);
					list.remove(i - 1);
					i--;
				}
			}
		}
		return list;
	}
}
