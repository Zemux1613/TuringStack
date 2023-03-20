package de.turingStack.analyse.parser.commands;

import de.turingStack.analyse.abstraction.pasing.Command;
import de.turingStack.analyse.scanner.tokens.TokenCategory;

import java.util.Arrays;
import java.util.List;

public class Print extends Command {

    public Print() {
        super("print", List.of(Arrays.asList(TokenCategory.KEYWORD, TokenCategory.NAME)));
    }

}