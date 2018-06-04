package parser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import objects.CalcObject;
import objects.Divide;
import objects.Minus;
import objects.Multiply;
import objects.Number;
import objects.Plus;
import objects.Variable;

public abstract class Parser {
	private Parser() {
	}

	public static List<CalcObject> parseToObjects(String equation) {
		List<CalcObject> list = new ArrayList<>();

		for (int i = 0; i < equation.length(); i++) {
			String character = equation.substring(i, i + 1);

			if (character.matches("[0-9]")) {
				String currentNumberCharacter;
				String number = "";
				while (i < equation.length()
						&& (currentNumberCharacter = equation.substring(i, i + 1)).matches("[0-9.]")) {
					number += currentNumberCharacter;
					i++;
				}
				list.add(new Number(Double.parseDouble(number)));
				i--;
			}

			else if (character.matches("[a-zA-Z]")) {
				String currentVariableCharacter;
				String variableName = "";
				while (i < equation.length()
						&& (currentVariableCharacter = equation.substring(i, i + 1)).matches("[a-zA-Z]")) {
					variableName += currentVariableCharacter;
					i++;
				}
				list.add(new Variable(variableName));
				i--;
			}

			else if (character.equals("+")) {
				list.add(new Plus());
			}

			else if (character.equals("-")) {
				list.add(new Minus());
			}

			else if (character.equals("*")) {
				list.add(new Multiply());
			}

			else if (character.equals("/")) {
				list.add(new Divide());
			}

			else {
				throw new ParsingException("Cannot bla bla");
			}
		}
		return list;
	}

	public static String convertToString(List<CalcObject> list) {
		return list.stream().map(CalcObject::asString).collect(Collectors.joining(" "));
	}
}
