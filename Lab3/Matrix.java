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

    public int getDetPart(int part) {
        int result = 0;
        int a = part == 0 ? 1 : 0;
        int b = part == 2 ? 1 : 2;

        // result = (matrix[0][0] * ((matrix[1][1] * matrix[2][2]) - (matrix[1][2] *
        // matrix[2][1])));
        // result = (-1 * matrix[0][1] * ((matrix[1][0] * matrix[2][2]) - (matrix[1][2]
        // * matrix[2][0])));
        // result = (matrix[0][2] * ((matrix[1][0] * matrix[2][1]) - (matrix[1][1] *
        // matrix[2][0])));
        result = (matrix[0][part] * ((matrix[1][a] * matrix[2][b]) - (matrix[1][b] * matrix[2][a])));

        return part == 1 ? -1 * result : result;
    }
}
