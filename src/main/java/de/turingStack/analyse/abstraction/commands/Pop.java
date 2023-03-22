package de.turingStack.analyse.abstraction.commands;

import de.turingStack.analyse.abstraction.pasing.Command;
import de.turingStack.analyse.abstraction.pasing.CommandLine;
import de.turingStack.analyse.abstraction.scanner.Token;
import de.turingStack.analyse.abstraction.scanner.TokenCategory;
import de.turingStack.stack.RegisterProvider;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Pop extends Command {
    public Pop() {
        super("pop", List.of(Arrays.asList(TokenCategory.KEYWORD, TokenCategory.NAME, TokenCategory.LINEBREAK)));
    }

    @Override
    public void execute(CommandLine commandLine) {
        commandLine.getFirstOf(TokenCategory.KEYWORD).ifPresent(token -> {
            if (token.content().equals(this.getName())) {
                return;
            }
            commandLine
                    .getFirstOf(TokenCategory.NAME)
                    .stream()
                    .map(Token::content)
                    .map(RegisterProvider::getRegister)
                    .map(register -> register.orElse(null))
                    .findFirst()
                    .ifPresent(Stack::pop);
        });
    }
}
