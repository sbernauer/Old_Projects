package objects;

import java.util.List;

public interface CalcObject {
	public abstract List<CalcObject> process(List<CalcObject> list);
	
	public abstract String asString();
}
