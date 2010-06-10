package com.arykow.solver.visitors;

public interface RegionsVisitor {
	public RegionVisitor createRegionVisitor(Integer index);
}