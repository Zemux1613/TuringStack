package de.turingStack.stack.objects;

import lombok.Data;

import java.util.Stack;

@Data
public class Register extends Stack {
    private final String registerName;

    public Register(final String registerName) {
        this.registerName = registerName;
    }

    @Override
    public synchronized Object pop() {
        System.out.println("pop from register " + this.registerName);
        return super.pop();
    }

    @Override
    public Object push(Object item) {
        System.out.println("push to register " + this.registerName);
        return super.push(item);
    }
}
