package de.turingStack.analyse.scanner;

import de.turingStack.analyse.Constants;
import de.turingStack.analyse.abstraction.Phase;

import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class Scanner extends Phase {

    public Scanner() {
        super(2);
    }

    @Override
    public CompletableFuture<Void> start() {
        return CompletableFuture.runAsync(() -> {
            this.getValue(Constants.LAST_STAGE).ifPresent(keyValue -> {
                final ConcurrentHashMap<File, String> value = (ConcurrentHashMap<File, String>) keyValue.getValue();
                value.keySet().forEach(key -> System.out.println(key.getPath() + " | " + value.get(key)));
            });
        });
    }

    @Override
    public CompletableFuture<Void> end() {
        return new CompletableFuture<>();
    }
}
