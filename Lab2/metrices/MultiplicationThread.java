package metrices;

import java.util.Random;
import java.lang.System;

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

    private static void separate() {
        System.out.print("====================================================\n");
    }

    private static void doMultiplication(Matrix m1, Matrix m2, boolean printResult) throws Exception {
        // Create two threads of type MultiplicationThreads. Both threads are
        // initialized with the same m1 and m2.
        MultiplicationThread mt = new MultiplicationThread(m1, m2);
        Thread t1 = new Thread(mt);
        Thread t2 = new Thread(mt);

        // Set their names 1 and 2 respectively.
        t1.setName("1");
        t2.setName("2");

        // Start the two threads.
        final long start = System.nanoTime();
        t1.start();
        t2.start();

        // Wait for them to join the current thread so we can print the result and
        // calculate the time needed for multiplication.
        t1.join();
        t2.join();
        final long end = System.nanoTime();
        long timeNeeded = ((end - start));
        System.out.print("Time needed = " + timeNeeded + " ns\n\n");
        if (printResult)
            mt.result.print();
        separate();
    }

    public static void main(String[] args) throws Exception {
        final int sz = 500;

        Matrix m1 = new Matrix(3, 4), m2 = new Matrix(4, 2), m4 = new Matrix(sz, sz), m5 = new Matrix(sz, sz);

        int[] arr1 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
        int[] arr2 = new int[sz * sz];
        Random rand = new Random();
        for (int i = 0; i < sz * sz; i++)
            arr2[i] = rand.nextInt();

        // Set the numbers of m1, m2 and m3 with arr1.
        m1.setNumbers(arr1);
        m2.setNumbers(arr1);
        m4.setNumbers(arr2);
        m5.setNumbers(arr2);

        /**
         * Multithreading effect appears with higher matrices sizes (i.e. (500, 500))
         * But with small sizes (i.e. (3,4) and (3, 2)) multithreading just makes
         * unneeded overhead
         */

        System.out.print("\nMultiply m1(3, 4) with m2(4, 2) and print the result:\n\n");
        doMultiplication(m1, m2, false);

        System.out.print("\nMultiply m4(500, 500) with m5(500, 500) and print the result:\n\n");
        doMultiplication(m4, m5, false);
    }
}
