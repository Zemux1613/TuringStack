package de.turingStack.analyse.abstraction.commands;

import de.turingStack.analyse.abstraction.pasing.Command;
import de.turingStack.analyse.abstraction.pasing.CommandLine;
import de.turingStack.analyse.abstraction.scanner.TokenCategory;

import de.turingStack.languageFeatures.RegisterProvider;
import de.turingStack.variables.VariableProvider;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.StringJoiner;

public class Print extends Command {

    public Print() {
        super("print",
                List.of(Arrays.asList(TokenCategory.KEYWORD, TokenCategory.NAME, TokenCategory.LINEBREAK)));
    }

    @Override
    public void execute(CommandLine commandLine) {
        commandLine.getFirstOf(TokenCategory.KEYWORD).ifPresent(token -> {
            if (!token.content().equals(this.getName())) {
                return;
            }
            commandLine.getFirstOf(TokenCategory.NAME).ifPresent(valueToken -> {
                RegisterProvider.getRegister(valueToken.content()).ifPresentOrElse(register -> {
                    Iterator iterator = register.iterator();
                    StringJoiner joiner = new StringJoiner(", ");
                    while (iterator.hasNext()) {
                        String string = iterator.next().toString();
                        VariableProvider
                                .getVariableByName(string)
                                .ifPresentOrElse(
                                        variable -> joiner.add(variable.value().toString()),
                                        () -> joiner.add(string));
                    }
                    System.out.println(
                            "Content of register " + register.getRegisterName() + ": [" + joiner + "]");
                }, () -> VariableProvider.getVariableByName(valueToken.content()).ifPresentOrElse(variable -> {
                    System.out.println("Variable '" + variable.name() + "' has value: '" + variable.value() + "'");
                }, () -> System.out.println("No variable/register found")));
            });
        });
    }
}
