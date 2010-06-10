package com.arykow.applications.solver.visitors;

public interface CellsVisitor {
	public abstract CellVisitor createCellVisitor(int index);
}
