package metrices;

public class MultiplicationThread implements Runnable {
    private Matrix A, B, result;

    public MultiplicationThread(Matrix firstMatrix, Matrix secondMatrix) {
        this.A = firstMatrix;
        this.B = secondMatrix;
        this.result = new Matrix(A.rows, B.cols);
    }

    public void run() {

    }
}
