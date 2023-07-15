package de.turingStack.analyse.scanner;

import de.turingStack.analyse.AnalyseService;
import de.turingStack.analyse.Constants;
import de.turingStack.analyse.abstraction.Phase;
import de.turingStack.analyse.abstraction.scanner.Token;
import de.turingStack.analyse.abstraction.scanner.TokenCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Scanner extends Phase {

    private final List<Token> tokens = new ArrayList<>();

    public Scanner() {
        super(2);
        final Logger logger = Logger.getLogger(getClass().getSimpleName());
    }

    @Override
    public void start() {
        if (!tokens.isEmpty()) {
            tokens.clear();
        }
        AnalyseService.getStorage().get(Constants.SCANNED_FILES).stream().map(o -> (String) o)
                .forEach(fileContent -> {
                    if (fileContent.isEmpty()) {
                        return;
                    }
                    final String[] lines = fileContent.trim().split(";");
                    int id = 0;
                    for (final String line : lines) {
                        final String[] toTokenize = line.split("\\s");
                        for (String sequenz : toTokenize) {
                            boolean kill = false;
                            for (int i = 0; i < TokenCategory.values().length && !kill; i++) {
                                final TokenCategory tokenCategory = TokenCategory.values()[i];
                                if (tokenCategory.getValidationPattern().matcher(sequenz).find()) {
                                    final boolean hasLineBreak = sequenz.contains(";");
                                    tokens.add(new Token(id,
                                            hasLineBreak ? sequenz.substring(0, sequenz.length() - 1) : sequenz,
                                            tokenCategory));
                                    System.out.println(id + " | " + sequenz + " -> " + tokenCategory.name());
                                    id++;
                                    kill = true;
                                }
                            }
                        }
                        tokens.add(new Token(id, ";", TokenCategory.LINEBREAK));
                        System.out.println(id + " | ; -> " + TokenCategory.LINEBREAK.name());
                        id++;
                    }
                });
    }

    @Override
    public void end() {
        AnalyseService.getStorage().put(Constants.EXTRACTED_TOKENS, this.tokens);
    }
}
