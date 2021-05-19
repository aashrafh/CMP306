import java.util.Scanner;
import java.io.Serializable;

public class Matrix implements Serializable {
    int n, m;
    int matrix[][];

    public Matrix(int n, int m, String[] mat) {
        this.n = n;
        this.m = m;
        matrix = new int[n][m];
        initializeMatrix(mat);
    }

    public void initializeMatrix(String[] mat) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrix[i][j] = Integer.parseInt(mat[(i * 3) + j].trim());
            }
        }
    }

    public void print() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.printf("%d ", matrix[i][j]);
            }
            System.out.printf("\n");
        }
    }
}
