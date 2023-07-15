package de.turingStack.languageFeatures;

import de.turingStack.languageFeatures.objects.Register;

import java.util.Optional;
import java.util.concurrent.CopyOnWriteArraySet;

public class RegisterProvider {
    private static final CopyOnWriteArraySet<Register> registers = new CopyOnWriteArraySet<>();

    public static void addRegister(final String name) {
        if (getRegister(name).isEmpty()) {
            registers.add(new Register(name));
            System.out.println("Create register " + name);
        }
    }

    public static Optional<Register> getRegister(final String name) {
        return registers.stream().filter(stack -> stack.getRegisterName().equals(name)).findFirst();
    }

    public static void deleteRegister(final String name) {
        getRegister(name).ifPresent(registers::remove);
    }
}
