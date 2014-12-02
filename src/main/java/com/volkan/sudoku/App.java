package com.volkan.sudoku;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class App 
{
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main( String[] args ) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("/Users/vol/Downloads/samples.txt"));
        String line;
        int lineNumber = 0;
        while ( (line = reader.readLine() ) != null){
            lineNumber++;

            if (lineShouldBeSkipped(line)) {
                continue;
            }

            if (isValidLine(line)) {
                validateSolution(line, lineNumber);
            } else {
                logger.warn("The line #{} is not well formed: {}", lineNumber, line);
            }
        }

        System.out.println(lineNumber + " solutions are read!");
    }

    private static void validateSolution(String line, int lineNumber) {
        Solution solution = new Solution(line);
        if (!solution.isValid()) {
            logger.info("Line #{} is an invalid solution: {}", lineNumber, line);
        }
    }

    private static boolean lineShouldBeSkipped(String line) {
        return line.startsWith("#");
    }

    public static boolean isValidLine(String line) {
        return line.length() == Solution.SIZE * Solution.SIZE;
    }


}
