package metrices;

import java.lang.System;

public class Matrix implements Addable<int[][]> {
    protected int[][] numbers; // the number in the matrix
    protected int m; // number of rows
    protected int n; // number of cols

    public Matrix(int m, int n) {
        this.m = m;
        this.n = n;
        numbers = new int[m][n];
    }

    public int[][] add(int[][] nums) {
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                numbers[i][j] += nums[i][j];
            }
        }
        return numbers;
    }

    public boolean setNumbers(int[] rows) {
        if (rows.length != this.m * this.n)
            return false;

        for (int i = 0; i < m; i++) {
            System.arraycopy(rows, (i * n), numbers[i], 0, n);
        }

        return true;
    }

    public void print() {
        for (int[] row : numbers) {
            for (int x : row) {
                System.out.print(x + " ");
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    public int[][] transpose() {
        int[][] transposed = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                transposed[i][j] = numbers[j][i];
            }
        }
        return transposed;
    }
}
