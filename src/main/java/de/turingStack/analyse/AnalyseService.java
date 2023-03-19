package de.turingStack.analyse;

import de.turingStack.analyse.abstraction.Phase;
import lombok.Getter;
import org.reflections.Reflections;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnalyseService {
    private final List<Phase> analysePhases = new LinkedList<>();
    @Getter
    private static final ConcurrentHashMap<String, List<? extends Object>> storage = new ConcurrentHashMap<>();
    @Getter
    private File targetFile;

    public AnalyseService(File filesToCompile) {
        this.targetFile = filesToCompile;
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
        phase.start();
        Logger.getLogger(this.getClass().getSimpleName()).log(Level.INFO, "Completion analysis phase " + phase.getClass().getSimpleName());
        phase.end();
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
