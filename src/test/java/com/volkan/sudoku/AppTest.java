package com.volkan.sudoku;

import junit.framework.TestCase;


public class AppTest extends TestCase
{
    private static final int SIZE = 9;
    App app;
    String validLine;
    Solution solution;

    public AppTest( String testName )
    {
        super( testName );
        app = new App("");
        validLine = "123456789578139624496872153952381467641297835387564291719623548864915372235748916";
        solution = new Solution(validLine);
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

    public void testConstructBoardFromLine() {
        int [][] actual = solution.getBoard();

        int [][] expected = {   {1,2,3, 4,5,6, 7,8,9},
                                {5,7,8, 1,3,9, 6,2,4},
                                {4,9,6, 8,7,2, 1,5,3},
                                {9,5,2, 3,8,1, 4,6,7},
                                {6,4,1, 2,9,7, 8,3,5},
                                {3,8,7, 5,6,4, 2,9,1},
                                {7,1,9, 6,2,3, 5,4,8},
                                {8,6,4, 9,1,5, 3,7,2},
                                {2,3,5, 7,4,8, 9,1,6}};

        boolean flag = true;
        for (int row = 0; row < SIZE; row++){
            if (flag) {
                for (int col = 0; col < SIZE; col++) {
                    if (expected[row][col] != actual[row][col]) {
                        flag = false;
                        break;
                    }
                }
            } else {
                break;
            }
        }
        assertTrue(flag);
    }

    public void testIsValidFromRowPerspective_ShouldReturnTrue_ForValidRow() {
        assertTrue(solution.isValidFromRowPerspective());
    }

    public void testIsValidFromRowPerspective_ShouldReturnFalse_ForInValidRow() {
        Solution invalidSolution = createInvalidSolution();
        assertFalse(invalidSolution.isValidFromRowPerspective());
    }

    public void testIsValidFromColPerspective_ShouldReturnTrue_ForValidCol() {
        assertTrue(solution.isValidFromColPerspective());
    }

    public void testIsValidFromColPerspective_ShouldReturnFalse_ForInValidCol() {
        Solution invalidSolution = createInvalidSolution();
        assertFalse(invalidSolution.isValidFromColPerspective());
    }

    public void testIsValidFromBoxPerspective_ShouldReturnTrue_ForValidInput() {
        assertTrue(solution.isValidFromBoxPerspective());
    }

    public void testIsValidFromBoxPerspective_ShouldReturnFalse_ForInValidInput() {
        Solution solution = createInvalidSolution();

        assertFalse(solution.isValidFromBoxPerspective());
    }

    private Solution createInvalidSolution() {
        //2nd and 3rd numbers are changed as wrong values intentionally
        String line = "111456789578139624496872153952381467641297835387564291719623548864915372235748916";
        return new Solution(line);
    }
}
