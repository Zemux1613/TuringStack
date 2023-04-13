package de.turingStack.analyse.parser;

import de.turingStack.analyse.AnalyseService;
import de.turingStack.analyse.Constants;
import de.turingStack.analyse.abstraction.Phase;
import de.turingStack.analyse.abstraction.pasing.Command;
import de.turingStack.analyse.abstraction.pasing.CommandLine;
import de.turingStack.analyse.parser.exception.InvallidSyntaxException;
import de.turingStack.analyse.abstraction.scanner.Token;
import de.turingStack.analyse.abstraction.scanner.TokenCategory;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Parser extends Phase {

  private static final CopyOnWriteArrayList<Command> commands = new CopyOnWriteArrayList<>();

  public Parser() {
    super(3);
  }

  /**
   * Creates a new instance of a given class
   *
   * @param aClass Class from which a new instance is to be created
   * @return Returns a new instance of the passed class
   */
  private static Command getInstance(Class<? extends Command> aClass) {
    try {
      return aClass.getDeclaredConstructor().newInstance();
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
             NoSuchMethodException ex) {
      ex.printStackTrace();
      throw new RuntimeException();
    }
  }

  /**
   * Loads all defined commands once
   */
  public static void loadCommands() {
    new Reflections("de.turingStack.analyse")
        .getSubTypesOf(Command.class)
        .stream()
        .map(Parser::getInstance)
        .peek(command -> System.out.println(
            "[" + Parser.class.getSimpleName() + "] The compiler has loaded the command "
                + command.getName()))
        .forEach(commands::add);
  }

  @Override
  public void start() {
    // load tokens
    final List<Token> tokens = (List<Token>) AnalyseService.getStorage()
        .get(Constants.EXTRACTED_TOKENS);

    // load CommandLines
    ArrayList<CommandLine> commandLines = new ArrayList<>();
    int lineCount = 1;
    CommandLine cmdLine = new CommandLine(new ArrayList<>(), lineCount);
    for (Token token : tokens.stream().sorted(Comparator.comparingInt(Token::id)).toList()) {
      cmdLine.tokens().add(token);
      if (token.category() == TokenCategory.LINEBREAK) {
        commandLines.add(cmdLine);
        lineCount++;
        cmdLine = new CommandLine(new ArrayList<>(), lineCount);
      }
    }

    // SYNTAX CHECK
    commandLines.forEach(commandLine -> {
      boolean match = false;
      for (Command command : commands) {
        if (!match) {
          match = command
              .getSyntax()
              .stream()
              .anyMatch(tokenCategories ->
                  compareList(commandLine.tokens().stream().map(Token::category).toList(),
                      tokenCategories));
        }
      }
      if (!match) {
        try {
          StringBuilder line = new StringBuilder();
          commandLine.tokens().stream().map(Token::content)
              .forEach(s -> line.append(s).append(" "));
          throw new InvallidSyntaxException(
              "Their is a syntax error in Line " + commandLine.lineCount() + "....\n\nLine: "
                  + line);
        } catch (InvallidSyntaxException e) {
          throw new RuntimeException(e);
        }
      }
    });

    // EXECUTE COMMAND
    commandLines.forEach(commandLine -> commands.forEach(command -> command.getSyntax()
        .forEach(tokenCategories -> compareSyntax(commandLine, command, tokenCategories))));
  }

  /**
   * This method is a helper method to execute a command when it is found syntactically.
   *
   * @param commandLine     Current command line which is checked by pattern maching with the
   *                        command chain of the passed command.
   * @param command         Object of a command which is checked for equality and executed if it is
   *                        equal.
   * @param tokenCategories A command chain of the passed command
   */
  private void compareSyntax(CommandLine commandLine, Command command,
      List<TokenCategory> tokenCategories) {
    if (compareList(commandLine.tokens().stream().map(Token::category).toList(), tokenCategories)) {
      command.execute(commandLine);
    }
  }

  /**
   * @param left  The element on the left side which is to be checked
   * @param right The element on the right side which is to be checked
   * @return True when equality prevails, false otherwise
   */
  private boolean compareList(List<TokenCategory> left, List<TokenCategory> right) {
      if (left.size() != right.size()) {
          return false;
      }
    for (int i = 0; i < left.size(); i++) {
      if (left.get(i) != right.get(i)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public void end() {

  }
}
