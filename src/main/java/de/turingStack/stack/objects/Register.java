package de.turingStack.stack.objects;

import lombok.Data;

import java.util.Stack;

@Data
public class Register extends Stack {
    private final String registerName;
    public Register(final String registerName) {
        this.registerName = registerName;
    }
}
