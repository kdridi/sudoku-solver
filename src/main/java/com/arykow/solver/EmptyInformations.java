package com.arykow.solver;

public interface EmptyInformations {
	public boolean isCompleted();
	public abstract void acceptEmptyInformationsVisitor(EmptyInformationsVisitor visitor);
}