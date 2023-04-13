package de.turingStack.analyse.abstraction;

import lombok.Getter;

@Getter
public abstract class Phase {

  /**
   * execute priority the lowest priority is executed at the beginning, the highest priority is
   * executed at the end
   */
  private final int prioritiy;

  public Phase(final int prioritiy) {
    this.prioritiy = prioritiy;
  }

  /**
   * Begin stage of a phase
   */
  public abstract void start();

  /**
   * End stage of a phase
   */
  public abstract void end();
}