package de.turingStack.variables.exceptions;

public class VariableAlreadyExistsException extends Throwable {

  public VariableAlreadyExistsException(String variableName) {
    super(variableName);
  }
}
