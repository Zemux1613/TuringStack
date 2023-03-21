package de.turingStack.stack.impl;

import de.turingStack.stack.abstraction.IStackPattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Stack<T> implements IStackPattern<T> {
    private Stack<T> stack;
    @Getter
    private final String registerName;

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
        return stack.getSize();
    }
}
