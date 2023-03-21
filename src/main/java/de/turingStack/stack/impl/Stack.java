package de.turingStack.stack.impl;

import de.turingStack.stack.abstraction.IStackPattern;
import lombok.Getter;

public class Stack<T> implements IStackPattern<T> {
    @Getter
    private final String registerName;
    private final java.util.Stack<T> stack;

    public Stack(final String registerName) {
        this.registerName = registerName;
        this.stack = new java.util.Stack<T>();
    }

    @Override
    public void push(T item) {
        stack.push(item);
    }

    @Override
    public T pop() {
        return stack.pop();
    }

    @Override
    public int getSize() {
        return stack.size();
    }
}
