package de.turingStack.analyse.scanner;

import de.turingStack.TuringStack;
import de.turingStack.analyse.AnalyseService;
import de.turingStack.analyse.Constants;
import de.turingStack.analyse.abstraction.Phase;
import lombok.Cleanup;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class FileScanner extends Phase {

    private final ConcurrentHashMap<File, String> fileContent = new ConcurrentHashMap<>();

    public FileScanner() {
        super(1);
    }

    /**
     * Read everything in the file
     */
    public void readAll() {
        try {
            final File targetFile = TuringStack.getAnalyseService().getTargetFile();
            @Cleanup
            BufferedReader bufferedReader = new BufferedReader(new java.io.FileReader(targetFile));
            StringBuilder content = new StringBuilder();
            while (bufferedReader.ready()) {
                content.append(bufferedReader.readLine());
            }
            AnalyseService.getStorage().put(Constants.SCANNED_FILES, List.of(content.toString()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void start() {
        fileContent.clear();
        this.readAll();
    }

    @Override
    public void end() {
    }
}
