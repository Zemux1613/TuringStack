package de.turingStack.analyse;

import de.turingStack.analyse.abstraction.KeyValue;
import de.turingStack.analyse.abstraction.Phase;
import de.turingStack.analyse.scanner.FileScanner;
import org.reflections.Reflections;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.LinkedList;

public class AnalyseService {
    private final LinkedList<Phase> analysePhases = new LinkedList<>();
    private final File[] filesToCompile;
    
    public AnalyseService(File[] filesToCompile) {
        this.filesToCompile = filesToCompile;
        init();
    }

    public void init() {
        new Reflections("de.turingStack.analyse")
                .getSubTypesOf(Phase.class)
                .stream()
                .map(this::getPhase)
                .peek(phase -> System.out.println("The compiler has loaded the analysis phase " + phase.getClass().getSimpleName()))
                .forEach(this.analysePhases::add);
    }

    public void executeAnalyse() {
        this.analysePhases.stream().sorted(Comparator.comparingInt(Phase::getPrioritiy)).toList().forEach(this::handlePhase);
    }

    private void handlePhase(Phase phase) {
        System.out.println("Start the analysis phase " + phase.getClass().getSimpleName());
        setupPhaseContent(phase);
        phase
                .start()
                .whenComplete((unused, throwable) -> phase
                        .end()
                        .whenComplete((o, throwable1) -> System.out.println(o.getClass().getSimpleName())));
    }

    private void setupPhaseContent(Phase phase) {
        if (phase instanceof FileScanner) {
            phase.getContentVariables().add(new KeyValue("files", this.filesToCompile));
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
