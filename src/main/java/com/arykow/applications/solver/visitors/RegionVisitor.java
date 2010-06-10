package com.arykow.applications.solver.visitors;

public interface RegionVisitor {
	public abstract CellVisitor createCellVisitor(int index);
}