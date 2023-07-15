package de.turingStack.analyse.abstraction.pasing;

import de.turingStack.analyse.abstraction.scanner.TokenCategory;
import lombok.Data;

import java.util.List;

@Data
public abstract class Command {

    private final String name;
    private final List<List<TokenCategory>> syntax;
    //private Token token;

    public abstract void execute(CommandLine commandLine);
}
