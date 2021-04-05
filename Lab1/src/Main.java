
// import Lab1.src.metrices.src.metrices.src.metrices.metrices.IdentityMatrix;
import metrices.Matrix;
import metrices.IdentityMatrix;

public class Main {
    public static void print(int[][] numbers) {
        for (int[] row : numbers) {
            for (int x : row) {
                System.out.print(x + " ");
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    public static void main(String[] args) {
        Matrix a = new Matrix(2, 2);
        int[] aNumbers = { 1, 2, 3, 4 };
        a.setNumbers(aNumbers);

        System.out.println("The A matrix before transpose:\n");
        a.print();
        int[][] aTranspose = a.transpose();
        System.out.println("The A matrix after transpose:\n");
        print(aTranspose);

        Matrix b = new Matrix(3, 2);
        int[] bNumbers = { 1, 2, 3, 4, 5, 6 };
        b.setNumbers(bNumbers);

        System.out.println("The B matrix before transpose:\n");
        b.print();
        int[][] bTranspose = b.transpose();
        System.out.println("The B matrix after transpose:\n");
        print(bTranspose);

        int[][] addA = { { 5, 6 }, { 3, 4 } };
        int[][] aResult = a.add(addA);
        System.out.println("The A matrix after addition:\n");
        print(aResult);

        int[][] addB = { { 1, 2 }, { 3, 4 }, { 5, 6 } };
        int[][] bResult = b.add(addB);
        System.out.println("The B matrix after add it to itself:\n");
        print(bResult);

    }
}
