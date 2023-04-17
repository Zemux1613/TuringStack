package de.turingStack.analyse.abstraction.pasing;

import de.turingStack.analyse.abstraction.scanner.Token;
import de.turingStack.analyse.abstraction.scanner.TokenCategory;

import java.util.List;
import java.util.Optional;

public record CommandLine(List<Token> tokens, int lineCount) {

  /**
   * get first element token in TokenCategory
   * @param tokenCategory TokenCategory of target token
   * @return Optional<Token> of founding result
   */
  public Optional<Token> getFirstOf(TokenCategory tokenCategory) {
    if (tokens.stream().map(Token::category).anyMatch(category -> category == tokenCategory)) {
      for (Token token : tokens) {
        if (token.category() == tokenCategory) {
          return Optional.of(token);
        }
      }
    }
    return Optional.empty();
  }
}