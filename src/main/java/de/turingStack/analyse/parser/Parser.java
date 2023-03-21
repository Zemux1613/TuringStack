package de.turingStack.analyse.parser;

import de.turingStack.analyse.AnalyseService;
import de.turingStack.analyse.abstraction.Phase;
import de.turingStack.analyse.abstraction.pasing.Command;
import de.turingStack.analyse.scanner.tokens.Token;
import de.turingStack.analyse.scanner.tokens.TokenCategory;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

public class Parser extends Phase {
    private static final CopyOnWriteArrayList<Command> commands = new CopyOnWriteArrayList<>();

    public Parser() {
        super(3);
    }

    private static Command getInstance(Class<? extends Command> aClass) {
        try {
            return aClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException ex) {
            ex.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static void loadCommands() {
        new Reflections("de.turingStack.analyse")
                .getSubTypesOf(Command.class)
                .stream()
                .map(Parser::getInstance)
                .peek(command -> System.out.println("[" + Parser.class.getSimpleName() + "] The compiler has loaded the command " + command.getName()))
                .forEach(commands::add);
    }

    @Override
    public void start() {
        final List<Token> tokens = (List<Token>) AnalyseService.getStorage().get(Scanner.class.getSimpleName());
        final List<Token> tokenStream = tokens.stream().sorted(Comparator.comparingInt(Token::id)).toList();
        if (tokenStream.isEmpty()) return;

        ArrayList<Integer> startedStatements = new ArrayList<>();
        startedStatements.add(tokenStream.get(0).id());
        tokenStream.forEach(token -> {
            if (token.category() == TokenCategory.LINEBREAK) {
                if (tokenStream.size() >= token.id() + 1) {
                    startedStatements.add(token.id() + 1);
                }
            }
        });
    }

    @Override
    public void end() {

    }
}
