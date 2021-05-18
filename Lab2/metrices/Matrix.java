package metrices;

import java.lang.System;
import java.lang.Exception;
import java.util.Random;

public class Matrix implements Addable<Matrix> {
    public int[][] numbers; // the number in the matrix
    public int rows; // number of rows
    public int cols; // number of cols

    static final class MultiplicationException extends Exception {
        public String errorMessage;

        public MultiplicationException(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public void message() {
            System.out.printf("Multiplication Exception, error message: %s \n\n\n", errorMessage);
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
                    "Addition must be between 2 metrices that have the same order (i.e., same number of rows and columns)\n\n\n");
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
        // Size of the flattened array = rows*cols
        if (flattened.length < this.rows * this.cols)
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

        // Numbers array should carr1y its transpose
        this.numbers = transposed;
    }

    public Matrix multiply(Matrix mat) throws MultiplicationException {
        if (this.cols != mat.rows) {
            throw new MultiplicationException("Exception occured while trying to multiply two matrices of dimensions ("
                    + this.rows + ", " + this.cols + ") and (" + mat.rows + ", " + mat.cols
                    + "). The number of columns in matrix 1 should be equal to the number of rows in matrix 2");
        }

        Matrix result = new Matrix(this.rows, mat.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < mat.cols; j++) {
                for (int k = 0; k < mat.rows; k++) {
                    result.numbers[i][j] += this.numbers[i][k] * mat.numbers[k][j];
                }
            }
        }

        return result;
    }

    private static void separate() {
        System.out.print("====================================================\n");
    }

    private static void doMultiplication(Matrix m1, Matrix m2, boolean printResult) {
        Matrix result;
        // Multiply m1 with m2 and print the result.
        try {
            final long start = System.nanoTime();
            result = m1.multiply(m2);
            final long end = System.nanoTime();

            long timeNeeded = ((end - start));
            System.out.print("Time needed = " + timeNeeded + " ns\n\n");
            if (printResult)
                result.print();
            separate();
        } catch (MultiplicationException e) {
            e.message();
            separate();
        }
    }

    public static void main(String[] args) {
        final int sz = 500;
        Matrix m1 = new Matrix(3, 4), m2 = new Matrix(4, 2), m3 = new Matrix(2, 5), m4 = new Matrix(sz, sz),
                m5 = new Matrix(sz, sz);

        int[] arr1 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
        int[] arr2 = new int[sz * sz];
        Random rand = new Random();
        for (int i = 0; i < sz * sz; i++)
            arr2[i] = rand.nextInt();

        // Set the numbers of m1, m2 and m3 with arr1.
        m1.setNumbers(arr1);
        m2.setNumbers(arr1);
        m3.setNumbers(arr1);
        m4.setNumbers(arr2);
        m5.setNumbers(arr2);

        // Multiply m1 with m2 and print the result.
        System.out.print("\nMultiply m1(3, 4) with m2(4, 2) and print the result:\n\n");
        doMultiplication(m1, m2, true);

        // Multiply m1 with m3 and print the result.
        System.out.print("\nMultiply m1(3, 4) with m3(2, 5) and print the result:\n\n");
        doMultiplication(m1, m3, true);

        // Multiply m4 with m5 and print the result.
        System.out.print("\nMultiply m4(500, 500) with m5(500, 500) and print the result:\n\n");
        doMultiplication(m4, m5, false);
    }
}
