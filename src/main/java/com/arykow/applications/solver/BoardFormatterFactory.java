package com.arykow.applications.solver;

import com.arykow.applications.solver.visitors.BoardVisitor;
import com.arykow.applications.solver.visitors.BoardVisitorAdapter;
import com.arykow.applications.solver.visitors.CellVisitor;
import com.arykow.applications.solver.visitors.CellVisitorAdapter;
import com.arykow.applications.solver.visitors.CellsVisitor;
import com.arykow.applications.solver.visitors.RegionVisitor;
import com.arykow.applications.solver.visitors.RegionsVisitor;

public class BoardFormatterFactory {

	public interface BoardFormatter {
		public abstract String format(Board board);
	}

	private static class TableFormatVisitor extends BoardVisitorAdapter {
		private int size;
		private int length;
		private char[][] values;

		public void visitSize(int size) {
			this.size = size * (size + 1) + 1;
			this.length = size;
			this.values = new char[this.size][this.size];
			for (int rowIndex = 0; rowIndex < this.size; rowIndex++) {
				for (int columnIndex = 0; columnIndex < this.size; columnIndex++) {
					boolean rowFlag = (rowIndex % (size + 1)) == 0;
					boolean columnFlag = (columnIndex % (size + 1)) == 0;
					if (rowFlag) {
						if (columnFlag) {
							this.values[rowIndex][columnIndex] = '+';
						} else {
							this.values[rowIndex][columnIndex] = '-';
						}
					} else {
						if (columnFlag) {
							this.values[rowIndex][columnIndex] = '|';
						} else {
							this.values[rowIndex][columnIndex] = ' ';
						}
					}
				}
			}
		}

		public RegionsVisitor createRowRegionsVisitor() {
			return new RegionsVisitor() {
				public RegionVisitor createRegionVisitor(Integer index) {
					final int rowIndex = calculateIndex(index);
					return new RegionVisitor() {
						public CellVisitor createCellVisitor(int index) {
							final int columnIndex = calculateIndex(index);
							return new CellVisitor() {
								public void visitValue(Integer value) {
									if (value != null) {
										values[rowIndex][columnIndex] = value.toString().charAt(0);
									}
								}

								public RegionVisitor createSquareRegionVisitor() {
									return null;
								}

								public RegionVisitor createRowRegionVisitor() {
									return null;
								}

								public RegionVisitor createColumnRegionVisitor() {
									return null;
								}
							};
						}
					};
				}

			};
		}

		private int calculateIndex(Integer index) {
			int remainder = index % length;
			int quotient = (index - remainder) / length;
			return quotient * (length + 1) + (remainder + 1);
		}

		public String toString() {
			StringBuffer buffer = new StringBuffer();
			for (char[] value : values) {
				buffer.append(new String(value)).append('\n');
			}
			return buffer.toString();
		}
	}

	private static class InlineFormatVisitor extends BoardVisitorAdapter {
		private char[] values;

		public void visitSize(int size) {
			this.values = new char[size * size * size * size];
		}

		public CellsVisitor createCellsVisitor() {
			return new CellsVisitor() {
				public CellVisitor createCellVisitor(final int index) {
					return new CellVisitorAdapter() {
						public void visitValue(Integer value) {
							values[index] = (value == null ? ' ' : String.valueOf(value).charAt(0));
						}
					};
				}
			};
		}

		@Override
		public String toString() {
			return new String(values);
		}
	}

	private static abstract class AbstractFormatter implements BoardFormatter {
		private final BoardVisitor visitor;

		public AbstractFormatter(BoardVisitor visitor) {
			super();
			this.visitor = visitor;
		}

		public final String format(Board board) {
			String result = null;
			if (board == null) {
				result = "null";
			} else {
				board.acceptBoardVisitor(visitor);
				result = visitor.toString();
			}
			return result;
		}
	}

	private static class TableFormatter extends AbstractFormatter {
		public TableFormatter() {
			super(new TableFormatVisitor());
		}

	}

	private static class InlineFormatter extends AbstractFormatter {
		public InlineFormatter() {
			super(new InlineFormatVisitor());
		}

	}

	public static BoardFormatter newTableFormatter() {
		return new TableFormatter();
	}

	public static BoardFormatter newInlineFormatter() {
		return new InlineFormatter();
	}
}
