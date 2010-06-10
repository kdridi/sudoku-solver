package com.arykow.solver;

public class BoardGenerator {
	private static String[] solutions = new String[] { "1324423124133142" };

	public static Board generateBoard() {
		char[] values = new String(solutions[Double.valueOf(Math.random() * solutions.length).intValue()]).toCharArray();
		int blanks = Double.valueOf(Math.random() * values.length).intValue();
		for (int i = 0; i < blanks;) {
			int index = Double.valueOf(Math.random() * values.length).intValue();
			if (values[index] != ' ') {
				values[index] = ' ';
				i++;
			}
		}
		return new Board(new String(values));
	}
}
