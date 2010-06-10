package com.arykow.applications.solver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class App {

	public static void main(String[] args) {
		int index = 0;
		Set<Board> resolve = resolve(new Board(args[0]));
		System.out.println("--**********----------------------------------------------------------------------------------");
		System.out.println("--**********----------------------------------------------------------------------------------");
		System.out.println("--**********----------------------------------------------------------------------------------");
		System.out.println("--**********----------------------------------------------------------------------------------");
		System.out.println("--**********----------------------------------------------------------------------------------");
		System.out.println("--**********----------------------------------------------------------------------------------");
		System.out.println("--**********----------------------------------------------------------------------------------");
		System.out.println("--**********----------------------------------------------------------------------------------");
		System.out.println("--**********----------------------------------------------------------------------------------");
		System.out.println("--**********----------------------------------------------------------------------------------");
		System.out.println("--**********----------------------------------------------------------------------------------");
		System.out.println("--**********----------------------------------------------------------------------------------");
		for (Board board : resolve) {
			System.out.println(board);
			System.out.println("-- " + (++index) + " ----------------------------------------------------------------------------------");
		}
	}

	private static Set<Board> resolve(Board board) {
		Map<Integer, Board> boards = new HashMap<Integer, Board>();
		boards.put(indexxxxx++, board.cloneBoard(true));

		Set<Board> solutions = new HashSet<Board>();

		while (solutions.isEmpty() && !boards.isEmpty()) {
			updateSolutions(boards, solutions);
		}

		return solutions;
	}

	private static int indexxxxx = 0;

	public static void updateSolutions(final Map<Integer, Board> boards, Set<Board> solutions) {

		class Updater {

			private final Collection<Integer> invalids = new ArrayList<Integer>();
			private final Map<Integer, Map<Integer, Set<Integer>>> values = new HashMap<Integer, Map<Integer, Set<Integer>>>();
			private final Map<Integer, Board> next = new HashMap<Integer, Board>();

			public void update() {
				for (Integer index : invalids) {
					boards.remove(index);
				}
				for (Integer index : new ArrayList<Integer>(boards.keySet())) {
					if (next.size() < 10) {
						Board board = boards.remove(index);
						if (values.containsKey(index)) {
							for (Integer cell : values.get(index).keySet()) {
								for (Integer value : values.get(index).get(cell)) {
									Board aaa = board.cloneBoardAndUpdateValue(true, cell, value);
									if (aaa.isValid()) {
										next.put(indexxxxx++, aaa);
										System.out.println(String.format("%09d : %s", next.size(), aaa.createInlineString(false)));
									}
								}
							}
						}
					}
				}
				boards.putAll(next);
			}
		}
		final Updater updater = new Updater();

		for (final int index : boards.keySet()) {
			Board solution = boards.get(index);
			EmptyInformations informations = EmptyInformationsVisitorBuilder.buildEmptyInformations(solution);

			if (informations.isSolved()) {
				solutions.add(solution);
			} else if (!informations.isValid() || contains(solutions, solution)) {
				updater.invalids.add(index);
			} else {
				final Map<Integer, Set<Integer>> values = new HashMap<Integer, Set<Integer>>();
				informations.acceptEmptyInformationsVisitor(new EmptyInformationsVisitor() {
					public void visitInformation(Integer length, Integer indexx, int value) {
						if (!values.containsKey(indexx)) {
							values.put(indexx, new TreeSet<Integer>());
						}
						values.get(indexx).add(value);
					}
				});
				updater.values.put(index, values);
			}
		}

		updater.update();

	}

	private static boolean contains(Set<Board> solutions, Board solution) {
		boolean result = false;
		char[] string = solution.createInlineString(false).toCharArray();
		for(Board board : solutions) {
			char[] target = board.createInlineString(false).toCharArray();
			boolean value = true;
			for (int index = 0; value && index < string.length; index++) {
				char src = string[index];
				char dst = target[index];
				if(src != ' ') {
					value = src != dst;
				}
			}
			result = value;
			if(result) {
				break;
			}
		}
		return result;
	}

}
