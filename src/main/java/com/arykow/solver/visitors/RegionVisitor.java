package com.arykow.solver.visitors;

public interface RegionVisitor {
	public abstract CellVisitor createCellVisitor(int index);
}