package de.turingStack.stack.abstraction;

public interface IStackPattern<T> {
    void push(T item);

    T pop();

    int getSize();
}
