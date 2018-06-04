package objects;

public class Multiply implements CalcObject {
	
	public static Number multiply(Number first, Number secound) {
		return new Number(first.getValue() * secound.getValue()); //TODO
	}

	@Override
	public String asString() {
		return "*";
	}
}
