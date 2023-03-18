package de.turingStack.analyse.scanner;

import de.turingStack.analyse.Constants;
import de.turingStack.analyse.abstraction.Phase;

import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Scanner extends Phase {

    public Scanner() {
        super(2);
    }

    @Override
    public CompletableFuture<Void> start() {
        return CompletableFuture.runAsync(() -> this.getValue(Constants.LAST_STAGE).ifPresent(keyValue -> {
            final ConcurrentHashMap<File, String> value = (ConcurrentHashMap<File, String>) keyValue.getValue();
            value.keySet().forEach(key -> Logger.getLogger(Scanner.class.getSimpleName()).log(Level.INFO, key.getPath() + " | " + value.get(key)));
        }));
    }

    @Override
    public CompletableFuture<Void> end() {
        final CompletableFuture<Void> voidCompletableFuture = new CompletableFuture<>();
        voidCompletableFuture.completeAsync(() -> null);
        return voidCompletableFuture;
    }
}
