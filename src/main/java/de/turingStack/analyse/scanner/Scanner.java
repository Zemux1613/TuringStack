package de.turingStack.analyse.scanner;

import de.turingStack.analyse.abstraction.Phase;

import java.util.concurrent.CompletableFuture;

public class Scanner extends Phase {

    public Scanner() {
        super(2);
    }

    @Override
    public CompletableFuture<Void> start() {
        return CompletableFuture.runAsync(() -> {
        });
    }

    @Override
    public void end() {

    }
}
