package com.volkan.sudoku;

import org.perf4j.LoggingStopWatch;
import org.perf4j.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main( String[] args ) throws IOException {

        if (args.length != 1) {
            logger.error("Missing solution file path!\n" +
                    "Usage: java -jar sudoku-0.0.1.jar <solution_file_path>");
            System.exit(-1);
        }

        SolutionFileParser solutionFileParser = new SolutionFileParser(args[0]);
        StopWatch stopWatch = new LoggingStopWatch("File Parse");
        solutionFileParser.parseTheSolutionFile();
        stopWatch.stop();

    }
}
