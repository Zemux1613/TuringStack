package de.turingStack.analyse.abstraction.commands;

import de.turingStack.analyse.abstraction.pasing.Command;
import de.turingStack.analyse.abstraction.pasing.CommandLine;
import de.turingStack.analyse.abstraction.scanner.Token;
import de.turingStack.analyse.abstraction.scanner.TokenCategory;

import de.turingStack.stack.RegisterProvider;
import de.turingStack.variables.VariableProvider;
import java.util.Arrays;
import java.util.Stack;

public class Var extends Command {

  public Var() {
    super("var", Arrays.asList(
        Arrays.asList(TokenCategory.KEYWORD, TokenCategory.NAME, TokenCategory.OPERATOR,
            TokenCategory.NAME, TokenCategory.LINEBREAK),
        Arrays.asList(TokenCategory.KEYWORD, TokenCategory.NAME, TokenCategory.OPERATOR,
            TokenCategory.NUMBER, TokenCategory.LINEBREAK))
    );
  }

  @Override
  public void execute(CommandLine commandLine) {
    commandLine.getFirstOf(TokenCategory.KEYWORD).ifPresent(token -> {
      if (!token.content().equals(this.getName())) {
        return;
      }
      commandLine
          .getFirstOf(TokenCategory.NAME)
          .ifPresent(token1 -> {
            final String variableName = token1.content();
            String variableValue = commandLine.tokens().get(commandLine.tokens().size() - 2)
                .content();
            VariableProvider.addVariable(variableName, variableValue);
          });
    });
  }
}
