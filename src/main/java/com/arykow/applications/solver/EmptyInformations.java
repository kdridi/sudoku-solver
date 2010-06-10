package com.arykow.applications.solver;

public interface EmptyInformations {
	public boolean isCompleted();
	public abstract void acceptEmptyInformationsVisitor(EmptyInformationsVisitor visitor);
}