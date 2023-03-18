package de.turingStack.analyse.abstraction;

import lombok.Getter;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
public abstract class Phase {

    private final int prioritiy;
    private final List<KeyValue> contentVariables;

    public Phase(final int prioritiy) {
        this.prioritiy = prioritiy;
        this.contentVariables = new CopyOnWriteArrayList<>();
    }

    public abstract CompletableFuture<Void> start();

    public abstract <T> CompletableFuture<T> end();

    protected Optional<KeyValue> getValue(final String key) {
        return this.contentVariables.stream().filter(keyValue -> keyValue.getKey().equals(key)).findFirst();
    }
}