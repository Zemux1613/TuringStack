package de.turingStack.analyse.scanner.tokens;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.regex.Pattern;

@Getter
@AllArgsConstructor
public enum TokenCategory {
    KEYWORD(Pattern.compile("(createRegister|push|pop|var|deleteRegister|print)")),
    NAME(Pattern.compile("([a-zA-Z])")),
    NUMBER(Pattern.compile("(\\d([.,]\\d)?)+")),
    OPERATOR(Pattern.compile("(=|\\+|-|\\*|/)")),
    LINEBREAK(Pattern.compile("(;)"));

    public Pattern validationPattern;
}
