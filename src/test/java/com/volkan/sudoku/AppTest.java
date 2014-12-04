package com.volkan.sudoku;

import junit.framework.TestCase;


public class AppTest extends TestCase
{
    App app;
    String validLine;

    public AppTest( String testName )
    {
        super( testName );
        app = new App("");
        validLine = "123456789578139624496872153952381467641297835387564291719623548864915372235748916";
    }

    public void testIsValidLine_shouldReturnFalse_ForLongerLine() {
        String longLine = "1234567895781396244968721539523814676412978353875642917196235488649153722357489161";
        assertFalse(app.isValidLine(longLine));
    }

    public void testIsValidLine_shouldReturnFalse_ForShorterLine() {
        String shortLine = "1";
        assertFalse(app.isValidLine(shortLine));
    }

    public void testIsValidLine_ShouldReturnFalse_ForNonDigitValuesInLine() {
        assertFalse(app.isValidLine("xyz"));
    }

    public void testIsValidLine_ShouldReturnTrue_ForValidLengthDigitValuesInLine() {
        assertTrue(app.isValidLine(validLine));
    }
}
