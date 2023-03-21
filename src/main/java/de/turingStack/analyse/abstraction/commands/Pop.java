package de.turingStack.analyse.abstraction.commands;

import de.turingStack.analyse.abstraction.pasing.Command;
import de.turingStack.analyse.abstraction.pasing.CommandLine;
import de.turingStack.analyse.abstraction.scanner.TokenCategory;

import java.util.Arrays;
import java.util.List;

public class Pop extends Command {
    public Pop() {
        super("pop", List.of(Arrays.asList(TokenCategory.KEYWORD, TokenCategory.NAME, TokenCategory.LINEBREAK)));
    }

    @Override
    public void execute(CommandLine commandLine) {

    }
}
