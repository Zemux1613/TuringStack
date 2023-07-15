package de.turingStack.variables;

import de.turingStack.variables.exceptions.VariableAlreadyExistsException;
import de.turingStack.variables.objects.Variable;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArraySet;

public class VariableProvider {

  private static CopyOnWriteArraySet<Variable> variables = new CopyOnWriteArraySet<>();

  public static Optional<Variable> getVariableByName(final String name) {
    return variables.stream().filter(variable -> variable.name().equals(name)).findFirst();
  }

  public static <T> void addVariable(final String name, final T value)
      throws VariableAlreadyExistsException {
    if (getVariableByName(name).stream().noneMatch(variable -> variable.name().equals(name))) {
      variables.add(new Variable(name, value));
    } else {
      throw new VariableAlreadyExistsException(name);
    }
  }
}
