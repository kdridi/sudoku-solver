package com.arykow.solver.visitors;

public class CellVisitorAdapter implements CellVisitor {
	public RegionVisitor createColumnRegionVisitor() {
		return null;
	}

	public RegionVisitor createRowRegionVisitor() {
		return null;
	}

	public RegionVisitor createSquareRegionVisitor() {
		return null;
	}

	public void visitValue(Integer value) {
	}
}
