package de.turingStack.languageFeatures.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
public class Variable<T> {
    private final String name;
    private T value;
}
