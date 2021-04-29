package metrices;

public class IdentityMatrix extends Matrix {
    public IdentityMatrix(int m, int n) {
        super(m, n);
    }

    public boolean setNumbers(int[] rows) {
        boolean result = super.setNumbers(rows);
        if (!result)
            return false;

        for (int i = 0; i < super.m; i++) {
            for (int j = 0; j < super.n; j++) {
                if (i == j && super.numbers[i][j] != 1)
                    return false;
                if (i != j && super.numbers[i][j] != 0)
                    return false;
            }
        }

        return true;
    }

    public int[][] transpose() {
        return numbers;
    }
}
