package de.turingStack.analyse.abstraction.pasing;

import de.turingStack.analyse.scanner.tokens.Token;
import de.turingStack.analyse.scanner.tokens.TokenCategory;
import lombok.Data;

import java.util.List;

@Data
public abstract class Command {
    private final String name;
    private Token token;
    private final List<List<TokenCategory>> syntax;

    protected abstract void execute();
}
