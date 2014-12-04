package com.volkan.sudoku;

import junit.framework.TestCase;


public class SolutionFileParserTest extends TestCase
{
    SolutionFileParser solutionFileParser;
    String validLine;

    public SolutionFileParserTest(String testName)
    {
        super( testName );
        solutionFileParser = new SolutionFileParser("");
        validLine = "123456789578139624496872153952381467641297835387564291719623548864915372235748916";
    }

    public void testIsValidLine_shouldReturnFalse_ForLongerLine() {
        String longLine = "1234567895781396244968721539523814676412978353875642917196235488649153722357489161";
        assertFalse(solutionFileParser.isValidLine(longLine));
    }

    public void testIsValidLine_shouldReturnFalse_ForShorterLine() {
        String shortLine = "1";
        assertFalse(solutionFileParser.isValidLine(shortLine));
    }

    public void testIsValidLine_ShouldReturnFalse_ForNonDigitValuesInLine() {
        assertFalse(solutionFileParser.isValidLine("xyz"));
    }

    public void testIsValidLine_ShouldReturnTrue_ForValidLengthDigitValuesInLine() {
        assertTrue(solutionFileParser.isValidLine(validLine));
    }
}
