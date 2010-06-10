package com.arykow.applications.solver.visitors;

public interface CellVisitor {
	public abstract void visitValue(Integer value);
	public abstract RegionVisitor createRowRegionVisitor();
	public abstract RegionVisitor createColumnRegionVisitor();
	public abstract RegionVisitor createSquareRegionVisitor();
}