package de.turingStack.analyse.scanner;

import de.turingStack.analyse.Constants;
import de.turingStack.analyse.abstraction.Phase;
import lombok.Cleanup;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class FileScanner extends Phase {

    private final ConcurrentHashMap<File, String> fileContent = new ConcurrentHashMap<>();

    public FileScanner() {
        super(1);
    }

    public void readAll() {
        this.getValue(Constants.SCANNED_FILES).ifPresent(keyValue -> Arrays.stream((File[]) keyValue.getValue()).forEach(file -> {
            try {
                @Cleanup
                BufferedReader bufferedReader = new BufferedReader(new java.io.FileReader(file));
                StringBuilder content = new StringBuilder();
                while (bufferedReader.ready()) {
                    content.append(bufferedReader.readLine());
                }
                fileContent.put(file, content.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
    }

    @Override
    public CompletableFuture<Void> start() {
        return CompletableFuture.runAsync(this::readAll);
    }

    @Override
    public CompletableFuture<ConcurrentHashMap> end() {
        final CompletableFuture<ConcurrentHashMap> future = new CompletableFuture<>();
        future.complete(fileContent);
        return future;
    }
}
