package objects;

import java.util.List;

public class Minus implements CalcObject {

	@Override
	public List<CalcObject> process(List<CalcObject> list) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getClass().equals(Minus.class)) {
				CalcObject first = list.get(i - 1);
				CalcObject secound = list.get(i + 1);
				if (first.getClass().equals(Number.class) && secound.getClass().equals(Number.class)) {
					if (i - 2 >= 0 && list.get(i - 2).getClass().equals(Minus.class)) {
						list.set(i, new Number(((Number) first).getValue() + ((Number) secound).getValue()));
					} else {
						list.set(i, new Number(((Number) first).getValue() - ((Number) secound).getValue()));
					}
					list.remove(i + 1);
					list.remove(i - 1);
					i--;
				}
			}
		}
		return list;
	}

	@Override
	public String asString() {
		return "-";
	}
}
