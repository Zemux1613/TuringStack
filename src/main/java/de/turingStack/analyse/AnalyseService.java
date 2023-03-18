package de.turingStack.analyse;

import de.turingStack.analyse.abstraction.KeyValue;
import de.turingStack.analyse.abstraction.Phase;
import de.turingStack.analyse.scanner.FileScanner;
import org.reflections.Reflections;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnalyseService {
    private final LinkedList<Phase> analysePhases = new LinkedList<>();
    private final File[] filesToCompile;
    private KeyValue lastStageValue;

    public AnalyseService(File[] filesToCompile) {
        this.filesToCompile = filesToCompile;
        init();
    }

    public void init() {
        new Reflections("de.turingStack.analyse")
                .getSubTypesOf(Phase.class)
                .stream()
                .map(this::getPhase)
                .peek(phase -> Logger.getLogger(this.getClass().getSimpleName()).log(Level.INFO, "The compiler has loaded the analysis phase " + phase.getClass().getSimpleName()))
                .forEach(this.analysePhases::add);
    }

    public void executeAnalyse() {
        this.analysePhases.stream().sorted(Comparator.comparingInt(Phase::getPrioritiy)).toList().forEach(this::handlePhase);
    }

    private void handlePhase(Phase phase) {
        Logger.getLogger(this.getClass().getSimpleName()).log(Level.INFO, "Start the analysis phase " + phase.getClass().getSimpleName());
        setupPhaseContent(phase);
        phase.start().whenCompleteAsync((unused, throwable) ->
                phase.end()
                        .whenCompleteAsync((o, throwable1) -> this.lastStageValue = new KeyValue(Constants.LAST_STAGE, o)));
    }

    private void setupPhaseContent(Phase phase) {
        if (phase instanceof FileScanner) {
            phase.getContentVariables().add(new KeyValue(Constants.SCANNED_FILES, this.filesToCompile));
        }
        if (this.lastStageValue != null) {
            phase.getContentVariables().add(this.lastStageValue);
        }
    }

    private Phase getPhase(Class<? extends Phase> aClass) {
        try {
            return aClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
