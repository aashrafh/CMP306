import java.io.Serializable;

public class Matrix implements Serializable {
    int n, m;
    int matrix[][];

    public Matrix(int n, int m) {
        this.n = n;
        this.m = m;
        matrix = new int[n][m];
        initializeMatrix();
    }

    public Matrix(int n, int m, int[][] mat) {
        this.n = n;
        this.m = m;
        matrix = mat;
    }

    public void initializeMatrix() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrix[i][j] = 0;
            }
        }
    }

    public int getRows() {
        return this.n;
    }

    public int getCols() {
        return this.m;
    }

    public void print() {
        System.out.println("The Matrix: "));
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.printf("%d ", matrix[i][j]);
            }
            System.out.printf("\n");
        }
    }
}
