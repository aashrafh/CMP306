import mpi.*;

public class MatrixDet {
    public static void main(String[] args) {
        // Initializes the MPI execution environment
        MPI.Init(args);
        int root = 0;
        int rank = MPI.COMM_WORLD.Rank();

        if (rank == root) {
            System.out.printf("I'm the root, my rank is: %v\n", rank);

            // Initialize the matrix
            Matrix mat = new Matrix(3, 3);
            mat.initializeMatrix();

            // Broadcasting the matrix to all processes
            System.out.prinln("Root: I will broadcast the matrix to all processes");
            MPI.COMM_WORLD.Bcast(message, 0, message.length, MPI.CHAR, 0);

        } else {

        }

        // Terminates MPI execution environment
        MPI.Finalize();
    }
}
