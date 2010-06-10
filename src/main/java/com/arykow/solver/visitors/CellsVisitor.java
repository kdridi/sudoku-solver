package com.arykow.solver.visitors;

public interface CellsVisitor {
	public abstract CellVisitor createCellVisitor(int index);
}
