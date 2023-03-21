package de.turingStack.analyse.parser.commands;

import de.turingStack.analyse.abstraction.pasing.Command;
import de.turingStack.analyse.scanner.tokens.TokenCategory;

import java.util.Arrays;

public class DeleteRegister extends Command {

    public DeleteRegister() {
        super("deleteRegister", Arrays.asList(Arrays.asList(TokenCategory.KEYWORD, TokenCategory.NAME)));
    }

    @Override
    protected void execute() {

    }
}
