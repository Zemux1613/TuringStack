package de.turingStack;

import de.turingStack.analyse.AnalyseService;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TuringStack {
    public static void main(String[] args) {
        if (args.length == 0 && System.getenv("projectPath") == null) {
            Logger.getLogger(TuringStack.class.getSimpleName()).log(Level.INFO, "Please choose a Path! (java -jar turingStackCompiler.java <PATH>)");
            System.exit(-1);
        }
        String projectPath = "";
        if (System.getenv("projectPath") != null) {
            projectPath = System.getenv("projectPath");
        }
        if (args.length > 0) {
            projectPath = args[0];
        }

        final File[] filesToCompile = new File(projectPath).listFiles((dir, name) -> name.matches(".*\\.tss"));

        AnalyseService analyseService = new AnalyseService(filesToCompile);
        analyseService.executeAnalyse();

    }
}
