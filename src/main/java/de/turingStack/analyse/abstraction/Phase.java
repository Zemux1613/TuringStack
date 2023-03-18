package de.turingStack.analyse.abstraction;

import lombok.Getter;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

@Getter
public abstract class Phase {

    private final int prioritiy;
    private final List<KeyValue> contentVariables;
    private final CountDownLatch latch;

    public Phase(final int prioritiy) {
        this.prioritiy = prioritiy;
        this.contentVariables = new CopyOnWriteArrayList<>();
        this.latch = new CountDownLatch(1);
    }

    public abstract CompletableFuture<Void> start();

    public abstract <T> CompletableFuture<T> end();

    protected Optional<KeyValue> getValue(final String key) {
        return this.contentVariables.stream().filter(keyValue -> keyValue.getKey().equals(key)).findFirst();
    }
}