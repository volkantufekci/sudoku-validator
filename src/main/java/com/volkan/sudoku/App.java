package com.volkan.sudoku;

import org.perf4j.LoggingStopWatch;
import org.perf4j.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


public class App 
{
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    private final AtomicInteger invalidCount;

    public App() {
        invalidCount = new AtomicInteger(0);
    }

    public static void main( String[] args ) throws IOException {
        App app = new App();
        StopWatch stopWatch = new LoggingStopWatch("File Parse");
        app.parseTheSolutionFile();
        stopWatch.stop();
    }

    private void parseTheSolutionFile() throws IOException {
//        FileReader reader = new FileReader("/Users/vol/Downloads/samples.txt");
        List<String> lines = Files.readAllLines(
                Paths.get("/Users/vol/Downloads/samples.txt"), StandardCharsets.UTF_8);

        int availableCpuCount = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(availableCpuCount);

//        String line;
        int lineNumber = 0, skippedLineCount = 0;
//        while ( (line = reader.readLine() ) != null){
        for (String line : lines){
            lineNumber++;

            if (shouldLineBeSkipped(line)) {
                skippedLineCount++;
                continue;
            }

            if (isValidLine(line)) {
//                executorService.submit(validateSolution(line, lineNumber));
                validateSolution2(line, lineNumber);
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

    private void validateSolution2(final String line, final int lineNumber) {
        Solution solution = new Solution(line);
        if (!solution.isValid()) {
            invalidCount.incrementAndGet();
            logger.info("Line #{} is an invalid solution: {}", lineNumber, line);
        }
    }

    private Callable validateSolution(final String line, final int lineNumber) {
        Callable callable = new Callable() {
            @Override
            public Object call() throws Exception {
                Solution solution = new Solution(line);
                if (!solution.isValid()) {
                    invalidCount.incrementAndGet();
                    logger.info("Line #{} is an invalid solution: {}", lineNumber, line);
                }

                return null;
            }
        };

        return callable;
    }

    private boolean shouldLineBeSkipped(String line) {
        return line.startsWith("#");
    }

    public boolean isValidLine(String line) {
        return line.length() == Solution.SIZE * Solution.SIZE;
    }


}
