package de.turingStack.analyse.abstraction;

import lombok.Getter;

@Getter
public abstract class Phase {

    private final int prioritiy;

    public Phase(final int prioritiy) {
        this.prioritiy = prioritiy;
    }

    public abstract void start();

    public abstract void end();
}