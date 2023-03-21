package de.turingStack.analyse.abstraction.pasing;

import de.turingStack.analyse.abstraction.scanner.Token;

import java.util.List;

public record CommandLine (List<Token> tokens, int lineCount) {
}
