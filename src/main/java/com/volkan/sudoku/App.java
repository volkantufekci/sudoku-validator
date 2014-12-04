package com.volkan.sudoku;

import org.perf4j.LoggingStopWatch;
import org.perf4j.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


public class App 
{
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    private final AtomicInteger invalidCount;
    private final String solutionFilePath;

    public App(String solutionFilePath) {
        this.solutionFilePath = solutionFilePath;
        invalidCount = new AtomicInteger(0);
    }

    public static void main( String[] args ) throws IOException {
        if (args.length != 1) {
            logger.error("Missing solution file path!\n" +
                    "Usage: java -jar sudoku-0.0.1.jar <solution_file_path>");
            System.exit(-1);
        }

        App app = new App(args[0]);
        StopWatch stopWatch = new LoggingStopWatch("File Parse");
        app.parseTheSolutionFile();
        stopWatch.stop();
    }

    private void parseTheSolutionFile() throws IOException {
        logger.info("Solutions are being read from {}", solutionFilePath);
        BufferedReader reader = new BufferedReader(new FileReader(solutionFilePath));

        int availableCpuCount = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(availableCpuCount);

        String line;
        int lineNumber = 0, skippedLineCount = 0;
        while ( (line = reader.readLine() ) != null){
            lineNumber++;

            if (shouldLineBeSkipped(line)) {
                skippedLineCount++;
                continue;
            }

            if (isValidLine(line)) {
                executorService.submit(validateSolution(line, lineNumber));
            } else {
                invalidCount.incrementAndGet();
                logger.warn("The line #{} is not well formed: {}", lineNumber, line);
            }
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            logger.error("ExecutorService is interrupted", e);
        }

        logResults(lineNumber, skippedLineCount);
    }

    private void logResults(int lineNumber, int skippedLineCount) {
        int totalLineCountOfSolutions = totalNumberOfSolutionLines(lineNumber, skippedLineCount);
        int validLineCount = validLineCount(invalidCount.get(), totalLineCountOfSolutions);
        logger.info("{} solutions are read! Valid Solution Count={}, Invalid Solution Count={}",
                totalLineCountOfSolutions, validLineCount, invalidCount.get());
    }

    private int validLineCount(int invalidLineCount, int totalLineCountOfSolutions) {
        return totalLineCountOfSolutions-invalidLineCount;
    }

    private int totalNumberOfSolutionLines(int lineNumber, int skippedLineCount) {
        return lineNumber-skippedLineCount;
    }

    private Callable validateSolution(final String line, final int lineNumber) {

        return new Callable() {
            @Override
            public Object call() throws Exception {
                Solution solution = new Solution(line);
                if (!solution.isValid()) {
                    invalidCount.incrementAndGet();
                    logger.debug("Line #{} is an invalid solution: {}", lineNumber, line);
                }

                return null;
            }
        };
    }

    private boolean shouldLineBeSkipped(String line) {
        return line.startsWith("#");
    }

    public boolean isValidLine(String line) {
        return line.matches("\\d*") && (line.length() == Solution.SIZE * Solution.SIZE);
    }


}
