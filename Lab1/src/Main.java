
// import Lab1.src.metrices.src.metrices.src.metrices.metrices.IdentityMatrix;
import metrices.Matrix;
import metrices.IdentityMatrix;

public class Main {
    public static void main(String[] args) {
        Matrix a = new Matrix(2, 2);
        int[] numbers = { 1, 2, 3, 4 };
        a.setNumbers(numbers);
        a.print();
    }
}
