package objects;

import java.util.List;

public class Number implements CalcObject {
	private double value;
	
	public Number(double value) {
		this.value = value;
	}
	
	public double addToValue(double add) {
		return value += add;
	}

	public void setValue(double value) {
		this.value = value;
	}
	
	public double getValue() {
		return value;
	}

	@Override
	public String asString() {
		String withKomma = String.valueOf(value);
		return value % 1 == 0 ? withKomma.substring(0, withKomma.length() - 2) : withKomma;
	}

	@Override
	public List<CalcObject> process(List<CalcObject> list) {
		// TODO Auto-generated method stub
		return null;
	}
}
