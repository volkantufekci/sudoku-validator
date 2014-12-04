package com.volkan.sudoku;

import junit.framework.TestCase;

public class SolutionTest extends TestCase{
    private static final int SIZE = 9;


    public void testConstructBoardFromLine() {
        int [][] actual = createValidSolution().getBoard();

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
        assertTrue(createValidSolution().isValidFromRowPerspective());
    }

    public void testIsValidFromRowPerspective_ShouldReturnFalse_ForInValidRow() {
        Solution invalidSolution = createInvalidSolution();
        assertFalse(invalidSolution.isValidFromRowPerspective());
    }

    public void testIsValidFromColPerspective_ShouldReturnTrue_ForValidCol() {
        assertTrue(createValidSolution().isValidFromColPerspective());
    }

    public void testIsValidFromColPerspective_ShouldReturnFalse_ForInValidCol() {
        Solution invalidSolution = createInvalidSolution();
        assertFalse(invalidSolution.isValidFromColPerspective());
    }

    public void testIsValidFromBoxPerspective_ShouldReturnTrue_ForValidInput() {
        assertTrue(createValidSolution().isValidFromBoxPerspective());
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

    private Solution createValidSolution() {
        String line = "123456789578139624496872153952381467641297835387564291719623548864915372235748916";
        return new Solution(line);
    }
}
