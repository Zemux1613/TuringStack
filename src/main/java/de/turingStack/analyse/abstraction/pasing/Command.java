package de.turingStack.analyse.abstraction.pasing;

import de.turingStack.analyse.abstraction.scanner.Token;
import de.turingStack.analyse.abstraction.scanner.TokenCategory;
import lombok.Data;

import java.util.List;

@Data
public abstract class Command {
    private final String name;
    private Token token;
    private final List<List<TokenCategory>> syntax;

    public abstract void execute(CommandLine commandLine);
}
