package de.turingStack.analyse.abstraction.commands;

import de.turingStack.analyse.abstraction.pasing.Command;
import de.turingStack.analyse.abstraction.pasing.CommandLine;
import de.turingStack.analyse.abstraction.scanner.Token;
import de.turingStack.analyse.abstraction.scanner.TokenCategory;
import de.turingStack.languageFeatures.RegisterProvider;

import java.util.Arrays;
import java.util.List;

public class CreateRegister extends Command {
    public CreateRegister() {
        super("createRegister", List.of(Arrays.asList(TokenCategory.KEYWORD, TokenCategory.NAME, TokenCategory.LINEBREAK)));
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
                    .forEach(RegisterProvider::addRegister);
        });
    }
}
