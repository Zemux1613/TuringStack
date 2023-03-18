package de.turingStack.analyse.abstraction;

import lombok.Data;

@Data
public class KeyValue<T> {
    private final String key;
    private final T value;
}
