package com.arykow.solver.visitors;

public interface BoardVisitor {
	public abstract void visitSize(int size);
	public abstract RegionsVisitor createRowRegionsVisitor();
	public abstract RegionsVisitor createColumnRegionsVisitor();
	public abstract RegionsVisitor createSquareRegionsVisitor();
	public abstract CellsVisitor createCellsVisitor();
}