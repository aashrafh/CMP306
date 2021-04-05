
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
        a.setNumbers(new int[] { 1, 2, 3, 4 });

        System.out.println("The A matrix before transpose:\n");
        a.print();
        System.out.println("The A matrix after transpose:\n");
        print(a.transpose());

        Matrix b = new Matrix(3, 2);
        b.setNumbers(new int[] { 1, 2, 3, 4, 5, 6 });

        System.out.println("The B matrix before transpose:\n");
        b.print();
        System.out.println("The B matrix after transpose:\n");
        print(b.transpose());

        System.out.println("The A matrix after addition:\n");
        print(a.add(new int[][] { { 5, 6 }, { 3, 4 } }));

        System.out.println("The B matrix after add it to itself:\n");
        print(b.add(new int[][] { { 1, 2 }, { 3, 4 }, { 5, 6 } }));

        IdentityMatrix identity = new IdentityMatrix(3, 3);
        identity.setNumbers(new int[] { 1, 0, 0, 0, 1, 0, 0, 0, 1 });

        System.out.println("The identity matrix before transpose:\n");
        identity.print();

        System.out.println("The identity matrix after transpose:\n");
        print(identity.transpose());

    }
}
