package objects;

import java.util.List;

public class Variable implements CalcObject {
	private String name;
	private double value;
	
	public Variable(String name) {
		this.name = name;
	}
	
	public Variable(String name, double value) {
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public double getValue() {
		return value;
	}
	
	public void setValue(double value) {
		this.value = value;
	}

	@Override
	public String asString() {
		return name;
//		return "[VAR " + name + " : " + value + "]";
	}

	@Override
	public List<CalcObject> process(List<CalcObject> list) {
		// TODO Auto-generated method stub
		return null;
	}
}
