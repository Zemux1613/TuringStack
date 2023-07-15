package de.turingStack.languageFeatures;

import de.turingStack.languageFeatures.objects.Register;

import java.util.Optional;
import java.util.concurrent.CopyOnWriteArraySet;

public class RegisterProvider {

  private static final CopyOnWriteArraySet<Register> registers = new CopyOnWriteArraySet<>();

  /**
   * register a new stack
   *
   * @param name stack name
   */
  public static void addRegister(final String name) {
    if (getRegister(name).isEmpty()) {
      registers.add(new Register(name));
      System.out.println("Create register " + name);
    }
  }

  /**
   * search for a register and return Optional<Register>
   *
   * @param name stack name
   * @return Register optional if stack is known, else empty optional
   */
  public static Optional<Register> getRegister(final String name) {
    return registers.stream().filter(stack -> stack.getRegisterName().equals(name)).findFirst();
  }

  /**
   * delete a known stack
   *
   * @param name stack name
   */
  public static void deleteRegister(final String name) {
    getRegister(name).ifPresent(registers::remove);
    System.out.println("Delete register " + name);
  }
}
