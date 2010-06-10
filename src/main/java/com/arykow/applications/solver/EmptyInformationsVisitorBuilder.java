package com.arykow.applications.solver;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.arykow.applications.solver.visitors.BoardVisitor;
import com.arykow.applications.solver.visitors.BoardVisitorAdapter;
import com.arykow.applications.solver.visitors.CellVisitor;
import com.arykow.applications.solver.visitors.CellVisitorAdapter;
import com.arykow.applications.solver.visitors.CellsVisitor;
import com.arykow.applications.solver.visitors.RegionVisitor;
import com.arykow.applications.solver.visitors.RegionVisitorAdapter;

public class EmptyInformationsVisitorBuilder {
	private EmptyInformationsVisitorBuilder() {
		super();
	}

	private final Map<Integer, Set<Integer>> values = new HashMap<Integer, Set<Integer>>();
	private final BoardVisitor indexCollector = new BoardVisitorAdapter() {
		public CellsVisitor createCellsVisitor() {
			return new CellsVisitor() {
				public CellVisitor createCellVisitor(final int index) {
					return new CellVisitorAdapter() {
						public void visitValue(Integer value) {
							if (value == null) {
								values.put(index, new HashSet<Integer>());
							}
						}
					};
				}
			};
		}
	};
	private final BoardVisitor valuesCollector = new BoardVisitorAdapter() {
		private int size;

		public void visitSize(int size) {
			this.size = size;
		}

		private void update(Set<Integer> set) {
			for (int value = 0; value < size * size; value++) {
				set.add(value + 1);
			}
		}

		public CellsVisitor createCellsVisitor() {
			return new CellsVisitor() {
				public CellVisitor createCellVisitor(final int cellIndex) {
					CellVisitor result = null;
					if (values.containsKey(cellIndex)) {
						update(values.get(cellIndex));
						final RegionVisitor regionVisitor = new RegionVisitorAdapter() {
							public CellVisitor createCellVisitor(int index) {
								return new CellVisitorAdapter() {
									public void visitValue(Integer value) {
										values.get(cellIndex).remove(value);
									}
								};
							}
						};
						result = new CellVisitorAdapter() {
							public RegionVisitor createColumnRegionVisitor() {
								return regionVisitor;
							}

							public RegionVisitor createRowRegionVisitor() {
								return regionVisitor;
							}

							public RegionVisitor createSquareRegionVisitor() {
								return regionVisitor;
							}
						};
					}
					return result;
				}

			};
		}
	};

	private static class EmptyInformationsImpl implements EmptyInformations {
		private final Map<Integer, Map<Integer, Set<Integer>>> informations;

		private EmptyInformationsImpl(Map<Integer, Map<Integer, Set<Integer>>> informations) {
			super();
			this.informations = informations;
		}

		public boolean isCompleted() {
			return informations.isEmpty();
		}

		public void acceptEmptyInformationsVisitor(EmptyInformationsVisitor visitor) {
			for (Integer length : informations.keySet()) {
				Map<Integer, Set<Integer>> values = informations.get(length);
				for (Integer index : values.keySet()) {
					Set<Integer> set = values.get(index);
					for (int value : set) {
						visitor.visitInformation(length, index, value);
					}
				}
			}
		}

	}

	public static EmptyInformations buildEmptyInformations(final Board board) {
		return new EmptyInformationsImpl(new HashMap<Integer, Map<Integer, Set<Integer>>>() {
			private static final long serialVersionUID = 5197185060154103199L;
			{
				EmptyInformationsVisitorBuilder builder = new EmptyInformationsVisitorBuilder();
				board.acceptBoardVisitor(builder.indexCollector);
				board.acceptBoardVisitor(builder.valuesCollector);
				for (Integer index : builder.values.keySet()) {
					Set<Integer> set = builder.values.get(index);
					insert(set.size(), index, set);
				}
			}

			private void insert(int key, Integer index, Set<Integer> values) {
				if (!containsKey(key)) {
					put(key, new HashMap<Integer, Set<Integer>>());
				}
				get(key).put(index, values);
			}
		});
	}

}
