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

public class AnalyseService {
    @Getter
    private static final ConcurrentHashMap<String, List<? extends Object>> storage = new ConcurrentHashMap<>();
    private static final List<Phase> analysePhases = new LinkedList<>();
    @Getter
    private final File targetFile;

    public AnalyseService(File filesToCompile) {
        this.targetFile = filesToCompile;
    }

    public static void init() {
        new Reflections("de.turingStack.analyse")
                .getSubTypesOf(Phase.class)
                .stream()
                .map(AnalyseService::getPhase)
                .peek(phase -> System.out.println("[" + AnalyseService.class.getSimpleName() + "] The compiler has loaded the analysis phase " + phase.getClass().getSimpleName()))
                .forEach(analysePhases::add);
    }

    private static Phase getPhase(Class<? extends Phase> aClass) {
        try {
            return aClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public void executeAnalyse() {
        analysePhases.stream().sorted(Comparator.comparingInt(Phase::getPrioritiy)).toList().forEach(this::handlePhase);
    }

    private void handlePhase(Phase phase) {
        System.out.println("\n");
        System.out.println("Start the analysis phase " + phase.getClass().getSimpleName());
        phase.start();
        System.out.println("Completion analysis phase " + phase.getClass().getSimpleName());
        phase.end();
    }
}
