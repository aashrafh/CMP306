import mpi.*;
import java.io.*;

public class MatrixDet {
    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
        // Initializes the MPI execution environment
        MPI.Init(args);
        int root = 0;
        int rank = MPI.COMM_WORLD.Rank();
        int[] recvbuf = new int[1];

        if (rank == root) {
            System.out.printf("I'm the root, my rank is: %v\n", rank);

            // Initialize the matrix
            Matrix mat = new Matrix(3, 3);
            mat.initializeMatrix();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ObjectOutput out = null;

            // Broadcasting the matrix to all processes
            System.out.prinln("Root: I will broadcast the matrix to all processes");
            try {
                out = new ObjectOutputStream(stream);
                out.writeObject(mat);
                out.flush();

                byte[] matBytes = stream.toByteArray();
                recvbuf[0] = matBytes.length;

                MPI.COMM_WORLD.Bcast(recvbuf, 0, 1, MPI.INT, root);
                MPI.COMM_WORLD.Bcast(matBytes, 0, matBytes.length, MPI.BYTE, root);
            } finally {
                try {
                    stream.close();
                } catch (IOException ex) {
                    System.out.println(ex);
                }
            }

        } else {

        }

        // Terminates MPI execution environment
        MPI.Finalize();
    }
}
