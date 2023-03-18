package de.turingStack.analyse;

import de.turingStack.analyse.abstraction.KeyValue;
import de.turingStack.analyse.abstraction.Phase;
import lombok.Setter;
import org.reflections.Reflections;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnalyseService {
    private final LinkedList<Phase> analysePhases = new LinkedList<>();
    @Setter
    private KeyValue lastStageValue;

    public AnalyseService(File[] filesToCompile) {
        this.lastStageValue = new KeyValue(Constants.SCANNED_FILES, filesToCompile);
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
        this.analysePhases.stream().sorted(Comparator.comparingInt(Phase::getPrioritiy)).peek(System.out::println).toList().forEach(this::handlePhase);
    }

    private void handlePhase(Phase phase) {
        new Thread(() -> {
            Logger.getLogger(this.getClass().getSimpleName()).log(Level.INFO, "Start the analysis phase " + phase.getClass().getSimpleName());
            setupPhaseContent(phase);
            phase.start().whenComplete((unused, throwable) -> endPhase(phase));
            try {
                phase.getLatch().await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    private CompletableFuture<Object> endPhase(Phase phase) {
        Logger.getLogger(this.getClass().getSimpleName()).log(Level.INFO, "Completion analysis phase " + phase.getClass().getSimpleName());
        return phase.end().whenComplete((o, throwable1) -> {
            setLastStageValue(new KeyValue(Constants.LAST_STAGE, o));
            phase.getLatch().countDown();
        });
    }

    private void setupPhaseContent(Phase phase) {
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
