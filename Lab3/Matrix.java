import java.util.Scanner;
import java.io.Serializable;

public class Matrix implements Serializable {
    int n, m;
    int matrix[][];

    Matrix(int n, int m) {
        this.n = n;
        this.m = m;
    }

    void initializeMatrix() {
        System.out.prinln("Please enter the elements of the matrix: ");
        Scanner in = new Scanner(System.in);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrix[i][j] = in.nextInt();
            }
        }
    }
}
