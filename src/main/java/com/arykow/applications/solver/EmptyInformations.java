package com.arykow.applications.solver;

import java.util.Map;
import java.util.Set;

public interface EmptyInformations {
	public abstract Map<Integer, Map<Integer, Set<Integer>>> getInformations();
	public abstract boolean isSolved();
	public abstract void acceptEmptyInformationsVisitor(EmptyInformationsVisitor visitor);
	public abstract boolean isValid();
}