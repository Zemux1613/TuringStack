package de.turingStack.analyse.parser.commands;

import de.turingStack.analyse.abstraction.pasing.Command;
import de.turingStack.analyse.scanner.tokens.TokenCategory;

import java.util.Arrays;

public class Var extends Command {

    public Var() {
        super("var", Arrays.asList(Arrays.asList(TokenCategory.KEYWORD, TokenCategory.NAME, TokenCategory.OPERATOR, TokenCategory.NAME), Arrays.asList(TokenCategory.KEYWORD, TokenCategory.NAME, TokenCategory.OPERATOR, TokenCategory.NUMBER)));
    }
}
