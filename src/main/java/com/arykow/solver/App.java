package com.arykow.solver;

import java.util.HashMap;
import java.util.Map;


public class App {

	public static void main(String[] args) {
		String solution = "1324423124133142";

		System.out.println(new Board(solution));

		String values = "    6 2 9 12  946756 7       65 1 8 8 7   1 5 5 3 47       8 239246  51 1 3 9    ";
		Board board = new Board(values);

		Board solutions = resolve(board);
		System.out.println(solutions);
//		EmptyInformationsVisitorBuilder.buildEmptyInformations(board).acceptEmptyInformationsVisitor(visitor)

		System.out.println(board);
		System.out.println(values);
		System.out.println(BoardFormatterFactory.newInlineFormatter().format(board));
		System.out.println(BoardFormatterFactory.newInlineFormatter().format(board.cloneBoard()));
		System.out.println(board.cloneBoard().equals(board));
		System.out.println(BoardGenerator.generateBoard());
		System.out.println(BoardGenerator.generateBoard());
		System.out.println(BoardGenerator.generateBoard());
		System.out.println(BoardGenerator.generateBoard());
		System.out.println(BoardGenerator.generateBoard());
		System.out.println(BoardGenerator.generateBoard());
	}

	private static Board resolve(Board board) {
		Board result = board;
		EmptyInformations informations = EmptyInformationsVisitorBuilder.buildEmptyInformations(result);
		if(!informations.isCompleted()) {
			final Map<Integer, Integer> values = new HashMap<Integer, Integer>();
			informations.acceptEmptyInformationsVisitor(new EmptyInformationsVisitor() {
				public void visitInformation(Integer length, Integer index, int value) {
					if(length == 1) {
						values.put(index, value);
					}
				}
			});
			if(values.isEmpty()) {
				result = null;
			} else {
				result = resolve(result.cloneBoardAndUpdateValues(values));
			}
		}
		return result;
	}

}
