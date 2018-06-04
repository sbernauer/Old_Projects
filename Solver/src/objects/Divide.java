package objects;

public class Divide implements CalcObject {
	
	public static Number divide(Number first, Number secound) {
		return new Number(first.getValue() * secound.getValue()); //TODO
	}

	@Override
	public String asString() {
		return "/";
	}
}
