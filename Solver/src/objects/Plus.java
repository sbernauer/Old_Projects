package objects;

public class Plus implements CalcObject {
	
	public static Number plus(Number first, Number secound) {
		return new Number(first.getValue() + secound.getValue()); //TODO
	}
	
	@Override
	public String asString() {
		return "+";
	}
}
