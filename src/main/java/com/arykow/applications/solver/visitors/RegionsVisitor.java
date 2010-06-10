package com.arykow.applications.solver.visitors;

public interface RegionsVisitor {
	public RegionVisitor createRegionVisitor(Integer index);
}