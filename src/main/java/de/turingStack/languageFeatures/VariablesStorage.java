package de.turingStack.languageFeatures;

import de.turingStack.languageFeatures.objects.Variable;

import java.util.Optional;
import java.util.concurrent.CopyOnWriteArraySet;

public class VariablesStorage {
    private static final CopyOnWriteArraySet<Variable> variables = new CopyOnWriteArraySet<>();

    public static <T> boolean declareAndInitVariable(final String name, final T value) {
        if (variables.stream().map(Variable::getName).anyMatch(varName -> varName.equals(name))) {
            return false;
        }
        variables.add(new Variable(name, value));
        return true;
    }

    public static Optional<Variable> getVariableByName(final String name) {
        return variables.stream().filter(variable -> variable.getName().equals(name)).findFirst();
    }
}
