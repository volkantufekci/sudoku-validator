package com.volkan.sudoku;

import java.util.*;

/**
 * Created by vol on 01/12/14.
 */
public class Solution {
    public static final int SIZE = 9;
    public static final int boxSize = 3;
    private int[][] board;
    SortedSet<Integer> possibleNumbersSet = new TreeSet<Integer>();


    public Solution(String line) {
        board = new int[SIZE][SIZE];
        fillTheBoard(line);
        possibleNumbersSet.addAll(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
    }

    private void fillTheBoard(String line) {
        for (int row = 0; row < SIZE; row++){
            for (int col = 0; col < SIZE; col++) {
                board[row][col] =  Character.getNumericValue(line.charAt((row*9)+col));
            }
        }
    }

    public boolean isValid() {
        return isValidFromRowPerspective() && isValidFromColPerspective() && isValidFromBoxPerspective();
    }

    public int[][] getBoard() {
        return board;
    }

    public boolean isValidFromRowPerspective() {
        for (int row = 0; row < SIZE; row++){
            SortedSet<Integer> tempSet = new TreeSet<Integer>();
            tempSet.addAll(possibleNumbersSet);
            for (int col = 0; col < SIZE; col++) {
                if (duplicateNumberObserved(tempSet, row, col)) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean isValidFromColPerspective() {
        for (int col = 0; col < SIZE; col++){
            SortedSet<Integer> tempSet = new TreeSet<Integer>();
            tempSet.addAll(possibleNumbersSet);
            for (int row = 0; row < SIZE; row++) {
                if (duplicateNumberObserved(tempSet, row, col)) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean duplicateNumberObserved(SortedSet<Integer> set, int row, int col) {
        return !set.remove(board[row][col]);
    }

    public boolean isValidFromBoxPerspective() {
        for (int rowOuter = 0; rowOuter < SIZE; rowOuter+=boxSize){
            for (int colOuter = 0; colOuter < SIZE; colOuter+=boxSize) {

                SortedSet<Integer> tempSet = new TreeSet<Integer>();
                tempSet.addAll(possibleNumbersSet);

                for (int row = rowOuter; row - rowOuter < boxSize; row++){
                    for (int col = colOuter; col - colOuter < boxSize; col++) {
                        if (duplicateNumberObserved(tempSet, row, col)) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }
}
