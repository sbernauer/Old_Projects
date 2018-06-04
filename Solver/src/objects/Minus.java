package objects;

public class Minus implements CalcObject {
	
	public static Number minus(Number first, Number secound) {
		return new Number(first.getValue() - secound.getValue()); //TODO
	}

	@Override
	public String asString() {
		return "-";
	}
}
