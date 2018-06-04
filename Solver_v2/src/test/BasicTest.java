package test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import core.Solver;
import parser.Parser;

public class BasicTest {

	private List<String> createTestData() {
		List<String> testData = new ArrayList<>();
		testData.add("1+2+3+4+5");
		testData.add("15");
		testData.add("0-1-2-3-4-5");
		testData.add("-15");
		testData.add("1*2*3*4*5");
		testData.add("120");
		testData.add("1/2/3/4/5");
		testData.add("0.008333333333333333");
		
		testData.add("a-2-a-6+8");
		testData.add("a - 2 - a + 2");
		
		testData.add("a-20-22");
		testData.add("a - 42");
		testData.add("a+20+22");
		testData.add("a + 42");
		return testData;
	}

	@Test
	public void testAll() {
		List<String> testData = createTestData();
		for (int i = 0; i < testData.size(); i += 2) {
			assertEquals(testData.get(i + 1),
					Parser.convertToString(Solver.simplify(Parser.parseToObjects(testData.get(i)))));
		}
	}
}
