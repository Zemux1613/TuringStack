package de.turingStack.analyse.parser.commands;

import de.turingStack.analyse.abstraction.pasing.Command;
import de.turingStack.analyse.abstraction.pasing.CommandLine;
import de.turingStack.analyse.scanner.tokens.TokenCategory;

import java.util.Arrays;

public class Push extends Command {
    public Push() {
        super("push", Arrays.asList(
                Arrays.asList(TokenCategory.KEYWORD, TokenCategory.NAME, TokenCategory.NAME, TokenCategory.LINEBREAK),
                Arrays.asList(TokenCategory.KEYWORD, TokenCategory.NAME, TokenCategory.NUMBER, TokenCategory.LINEBREAK))
        );
    }

    @Override
    public void execute(CommandLine commandLine) {

    }
}
