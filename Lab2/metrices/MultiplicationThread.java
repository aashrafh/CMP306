package metrices;

public class MultiplicationThread implements Runnable {
    private Matrix A, B, result;

    public MultiplicationThread(Matrix firstMatrix, Matrix secondMatrix) {
        this.A = firstMatrix;
        this.B = secondMatrix;
        this.result = new Matrix(A.rows, B.cols);
    }

    private void multiplyUtil(int start, int end) {
        for (int i = start; i < end; i++) {
            for (int j = 0; j < B.cols; j++) {
                for (int k = 0; k < B.rows; k++) {
                    this.result.numbers[i][j] += this.A.numbers[i][k] * this.B.numbers[k][j];
                }
            }
        }
    }

    public void run() {
        int name = Integer.parseInt(Thread.currentThread().getName());
        int start = (name == 1) ? 0 : (A.rows / 2 + 1);
        int end = (name == 1) ? (A.rows / 2 + 1) : A.rows;
        multiplyUtil(start, end);
    }
}
