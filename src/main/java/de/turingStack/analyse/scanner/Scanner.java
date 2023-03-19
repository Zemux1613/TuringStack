package de.turingStack.analyse.scanner;

import de.turingStack.analyse.AnalyseService;
import de.turingStack.analyse.Constants;
import de.turingStack.analyse.abstraction.Phase;
import de.turingStack.analyse.scanner.tokens.Token;
import de.turingStack.analyse.scanner.tokens.TokenCategory;

import java.util.ArrayList;
import java.util.List;

public class Scanner extends Phase {

    private List<Token> tokens = new ArrayList<>();

    public Scanner() {
        super(2);
    }

    @Override
    public void start() {
        AnalyseService.getStorage().get(Constants.SCANNED_FILES).stream().map(o -> (String) o).forEach(fileContent -> {
            final String[] lines = fileContent.split(";");
            int id = 0;
            for (final String line : lines) {
                final String[] toTokenize = line.split("\\s");
                for (String sequenz : toTokenize) {
                    boolean kill = false;
                    for (int i = 0; i < TokenCategory.values().length && !kill; i++) {
                        final TokenCategory tokenCategory = TokenCategory.values()[i];
                        if (tokenCategory.getValidationPattern().matcher(sequenz).find()) {
                            tokens.add(new Token(id, sequenz.contains(";") ? sequenz.substring(0,sequenz.length()-1) : sequenz, tokenCategory));
                            System.out.println(id + " | " + sequenz + " -> " + tokenCategory.name());
                            id++;
                            if(sequenz.contains(";")){
                                tokens.add(new Token(id, ";", TokenCategory.LINEBREAK));
                                id++;
                            }
                            kill = true;
                        }
                    }
                }
            }
        });
    }

    @Override
    public void end() {

    }
}
