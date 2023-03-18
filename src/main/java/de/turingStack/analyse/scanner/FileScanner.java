package de.turingStack.analyse.scanner;

import de.turingStack.analyse.abstraction.Phase;
import lombok.Cleanup;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class FileScanner extends Phase {

    private final Map<File, String> fileContent = new ConcurrentHashMap<>();

    public FileScanner() {
        super(1);
    }

    public void readAll() {
        this.getValue("files").ifPresent(keyValue -> {
            Arrays.stream((File[]) keyValue.getValue()).forEach(file -> {
                try {
                    @Cleanup
                    BufferedReader bufferedReader = new BufferedReader(new java.io.FileReader(file));
                    String content = "";
                    while (bufferedReader.ready()) {
                        content += bufferedReader.readLine();
                    }
                    fileContent.put(file, content);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        });
    }

    @Override
    public CompletableFuture<Void> start() {
        return CompletableFuture.runAsync(this::readAll);
    }

    @Override
    public void end() {
    }
}
