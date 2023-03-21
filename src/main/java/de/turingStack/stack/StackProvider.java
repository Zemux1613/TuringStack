package de.turingStack.stack;

import de.turingStack.stack.objects.Register;

import java.util.Optional;
import java.util.concurrent.CopyOnWriteArraySet;

public class StackProvider {
    private static final CopyOnWriteArraySet<Register> stacks = new CopyOnWriteArraySet<>();

    public static void addRegister(final String name) {
        if (getRegister(name).isEmpty()) {
            stacks.add(new Register(name));
        }
    }

    public static Optional<Register> getRegister(final String name) {
        return stacks.stream().filter(stack -> stack.getRegisterName().equals(name)).findFirst();
    }

    public static void deleteRegister(final String name) {
        getRegister(name).ifPresent(stacks::remove);
    }
}
