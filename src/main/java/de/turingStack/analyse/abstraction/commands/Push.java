package de.turingStack.analyse.abstraction.commands;

import de.turingStack.analyse.abstraction.pasing.Command;
import de.turingStack.analyse.abstraction.pasing.CommandLine;
import de.turingStack.analyse.abstraction.scanner.Token;
import de.turingStack.analyse.abstraction.scanner.TokenCategory;

import de.turingStack.stack.RegisterProvider;
import de.turingStack.stack.objects.Register;
import java.util.Arrays;
import java.util.Optional;
import java.util.Stack;

public class Push extends Command {

  public Push() {
    super("push", Arrays.asList(
        Arrays.asList(TokenCategory.KEYWORD, TokenCategory.NAME, TokenCategory.NAME,
            TokenCategory.LINEBREAK),
        Arrays.asList(TokenCategory.KEYWORD, TokenCategory.NAME, TokenCategory.NUMBER,
            TokenCategory.LINEBREAK))
    );
  }

  @Override
  public void execute(CommandLine commandLine) {
    commandLine.getFirstOf(TokenCategory.KEYWORD).ifPresent(token -> {
      if (!token.content().equals(this.getName())) {
        return;
      }
      Optional<Register> registerOptional = commandLine
          .getFirstOf(TokenCategory.NAME)
          .stream()
          .map(Token::content)
          .map(RegisterProvider::getRegister)
          .map(register -> register.orElse(null))
          .findFirst();

      if (commandLine.tokens().stream().map(Token::category).toList().contains(TokenCategory.NUMBER)) {
        commandLine.getFirstOf(TokenCategory.NUMBER)
            .ifPresent(value -> registerOptional
                .ifPresent(
                    register -> register.push(value.content())
                )
            );
      } else {
        Token value = commandLine.tokens().get(2);
        registerOptional.ifPresent(register -> register.push(value.content()));
      }
    });
  }
}
