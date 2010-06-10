package com.arykow.solver.visitors;

public class BoardVisitorAdapter implements BoardVisitor {
	public void visitSize(int size) {
	}

	public RegionsVisitor createColumnRegionsVisitor() {
		return null;
	}

	public RegionsVisitor createRowRegionsVisitor() {
		return null;
	}

	public RegionsVisitor createSquareRegionsVisitor() {
		return null;
	}

	public CellsVisitor createCellsVisitor() {
		return null;
	}
}
