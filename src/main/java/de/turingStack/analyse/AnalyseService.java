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

    /**
     * Loads all compiler phases once into memory
     */
    public static void init() {
        new Reflections("de.turingStack.analyse")
                .getSubTypesOf(Phase.class)
                .stream()
                .map(AnalyseService::getPhase)
                .peek(phase -> System.out.println("[" + AnalyseService.class.getSimpleName()
                        + "] The compiler has loaded the analysis phase " + phase.getClass().getSimpleName()))
                .forEach(analysePhases::add);
    }

    /**
     * Creates a new instance of a given class
     *
     * @param aClass Class from which a new instance is to be created
     * @return Returns a new instance of the passed class
     */
    private static Phase getPhase(Class<? extends Phase> aClass) {
        try {
            return aClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Start the compiler for the file passed in the construktur
     */
    public void executeAnalyse() {
        analysePhases.stream().sorted(Comparator.comparingInt(Phase::getPrioritiy)).toList()
                .forEach(this::handlePhase);
    }

    /**
     * Start and stope a passed phase
     *
     * @param phase Phase which should be treated
     */
    private void handlePhase(Phase phase) {
        System.out.println("\nStart the analysis phase " + phase.getClass().getSimpleName());
        phase.start();
        System.out.println("Completion analysis phase " + phase.getClass().getSimpleName());
        phase.end();
    }
}