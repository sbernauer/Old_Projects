package objects;

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
}
