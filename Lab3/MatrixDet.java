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
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            ObjectOutput out = null;

            // Broadcasting the matrix to all processes and serializing the matrix
            System.out.println("Root: I will broadcast the matrix to all processes");
            try {
                out = new ObjectOutputStream(outStream);
                out.writeObject(mat);
                out.flush();

                byte[] matBytes = outStream.toByteArray();
                recvbuf[0] = matBytes.length;

                MPI.COMM_WORLD.Bcast(recvbuf, 0, 1, MPI.INT, root);
                MPI.COMM_WORLD.Bcast(matBytes, 0, matBytes.length, MPI.BYTE, root);
            } finally {
                try {
                    outStream.close();
                } catch (IOException ex) {
                    System.out.println(ex);
                }
            }

        } else {
            // Recieving the matrix
            MPI.COMM_WORLD.Bcast(recvbuf, 0, 1, MPI.INT, root);
            byte[] matBytes = new byte[recvbuf[0]];
            MPI.COMM_WORLD.Bcast(matBytes, 0, matBytes.length, MPI.BYTE, root);

            // Deserializing the matrix
            ByteArrayInputStream inStream = new ByteArrayInputStream(matBytes);
            ObjectInput in = null;
            try {
                in = new ObjectInputStream(inStream);
                Matrix mat = (Matrix) in.readObject();
                System.out.printf("Process %v: I recieved the matrix: \n");
                mat.print();
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException ex) {
                    System.out.println(ex);
                }
            }
            System.out.println("Process " + rank + " exiting ...");
        }

        // Terminates MPI execution environment
        MPI.Finalize();
    }
}
