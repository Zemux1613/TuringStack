package de.turingStack.analyse.abstraction.commands;

import de.turingStack.analyse.abstraction.pasing.Command;
import de.turingStack.analyse.abstraction.pasing.CommandLine;
import de.turingStack.analyse.abstraction.scanner.TokenCategory;

import java.util.Arrays;

public class DeleteRegister extends Command {

    public DeleteRegister() {
        super("deleteRegister", Arrays.asList(Arrays.asList(TokenCategory.KEYWORD, TokenCategory.NAME, TokenCategory.LINEBREAK)));
    }

    @Override
    public void execute(CommandLine commandLine) {

    }
}
