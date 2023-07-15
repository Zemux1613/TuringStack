package de.turingStack.languageFeatures.objects;

import lombok.Data;

import java.util.Stack;

@Data
public class Register extends Stack {

    private final String registerName;

    public Register(final String registerName) {
        this.registerName = registerName;
    }

    /**
     * pop last item from stack
     *
     * @return last item of stack
     */
    @Override
    public synchronized Object pop() {
        System.out.println("pop from register " + this.registerName);
        return super.pop();
    }

    /**
     * push items to stack
     *
     * @param item the item to be pushed onto this stack.
     * @return item
     */
    @Override
    public Object push(Object item) {
        System.out.println("push " + item.toString() + " to register " + this.registerName);
        return super.push(item);
    }
}
