package metrices;

import java.lang.System;
import java.lang.Exception;

public class Matrix implements Addable<Matrix> {
    protected int[][] numbers; // the number in the matrix
    protected int rows; // number of rows
    protected int cols; // number of cols

    static final class MultiplicationException extends Exception {
        public String errorMessage;

        public MultiplicationException(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public void message() {
            System.out.printf("Multiplication Exception, error message: %s \n", errorMessage);
        }
    }

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        numbers = new int[rows][cols];
    }

    public Matrix add(Matrix mat) {
        if (mat.cols != this.cols || mat.rows != this.rows) {
            System.out.print(
                    "Addition must be between 2 metrices that have the same order (i.e., same number of rows and columns)\n");
        } else {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    this.numbers[i][j] += mat.numbers[i][j];
                }
            }
        }

        return this;
    }

    public boolean setNumbers(int[] flattened) {
        // Size of the flattened array = n*m
        if (flattened.length != this.rows * this.cols)
            return false;

        for (int i = 0; i < rows; i++) {
            System.arraycopy(flattened, (i * cols), numbers[i], 0, cols); // src, srcPos, dest, destPos, length
        }

        return true;
    }

    public void print() {
        /*
         * It prints the elements of the matrix each row in a separate line and each row
         * elements are separated by spaces.
         * 
         */
        for (int[] row : numbers) {
            for (int x : row) {
                System.out.print(x + " ");
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    public void transpose() {
        int[][] transposed = new int[cols][rows];
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                transposed[i][j] = numbers[j][i];
            }
        }
        // Swap number of cols and rows using Bitwise XOR
        // For more details:
        // https://www.geeksforgeeks.org/swap-two-variables-in-one-line-in-c-c-python-php-and-java/
        rows = rows ^ cols ^ (cols = rows);

        // Numbers array should carry its transpose
        this.numbers = transposed;
    }
}
