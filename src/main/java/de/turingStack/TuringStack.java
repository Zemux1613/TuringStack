package de.turingStack;

import de.turingStack.analyse.AnalyseService;
import de.turingStack.analyse.parser.Parser;
import lombok.Getter;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TuringStack {

  @Getter
  private static AnalyseService analyseService;

  public static void main(String[] args) {
    if (args.length == 0 && System.getenv("projectPath") == null) {
      Logger.getLogger(TuringStack.class.getSimpleName())
          .log(Level.INFO, "Please choose a Path! (java -jar turingStackCompiler.java <PATH>)");
      System.exit(-1);
    }
    String projectPath = "";
    if (System.getenv("projectPath") != null) {
      projectPath = System.getenv("projectPath");
    }
    if (args.length > 0) {
      projectPath = args[0];
    }

    Logger.getLogger(TuringStack.class.getSimpleName())
        .log(Level.INFO, "Scann '" + projectPath + "' for files..");

    final File[] filesToCompile = new File(projectPath).listFiles(
        (dir, name) -> name.matches(".*\\.tss"));

    if (filesToCompile.length == 0) {
      Logger.getLogger(TuringStack.class.getSimpleName())
          .log(Level.INFO, "Theire are no TuringStack files on '" + projectPath + "'!");
      System.exit(-2);
    }

    AnalyseService.init();
    Parser.loadCommands();

    for (final File file : filesToCompile) {
      analyseService = new AnalyseService(file);
      analyseService.executeAnalyse();
    }
  }
}
