import mpi.*;
import java.io.*;
import java.util.Arrays;

public class MatrixDet {
    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
        // Initializes the MPI execution environment
        MPI.Init(args);
        int root = 0;
        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();
        int[] recvbuf = new int[1];
        int[] sendbuf;
        int[] detbuf;
        char[] message = new char[30];

        if (rank == root) {
            System.out.printf("I'm the root, my rank is: %d\n", rank);

            // Initialize the matrix
            Matrix mat = new Matrix(3, 3, Arrays.copyOfRange(args, 3, args.length));
            // mat.print();

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

            // ========= Gather the partial determinants from all processes =========
            sendbuf = new int[size];
            detbuf = new int[size];

            // Gathers all data from all processes in the world including the root
            MPI.COMM_WORLD.Gather(sendbuf, 0, 1, MPI.INT, detbuf, 0, 1, MPI.INT, root);

            System.out.println("The received determinants from all processes including itself: ");
            for (int i = 0; i < size; i++) {
                System.out.println(detbuf[i]);
            }
            // If the number of cores = 2 then the root prcess has to handle 2 parts of the
            // determinant
            int result = 0;
            if (size == 2) {
                result = detbuf[0] + mat.getDetPart(1) + mat.getDetPart(2);
            } else {
                for (int part : detbuf) {
                    result += part;
                }
            }
            System.out.printf("The sum of the determinants: %d\n", result);

            // Terminate the processes
            message = "You can exit now".toCharArray();
            System.out.println(message);
            MPI.COMM_WORLD.Bcast(message, 0, message.length, MPI.CHAR, root);

        } else {
            // Recieving the matrix
            MPI.COMM_WORLD.Bcast(recvbuf, 0, 1, MPI.INT, root);
            byte[] matBytes = new byte[recvbuf[0]];
            MPI.COMM_WORLD.Bcast(matBytes, 0, matBytes.length, MPI.BYTE, root);

            // Deserializing the matrix
            ByteArrayInputStream inStream = new ByteArrayInputStream(matBytes);
            ObjectInput in = null;
            Matrix mat = null;
            try {
                in = new ObjectInputStream(inStream);
                mat = (Matrix) in.readObject();
                // System.out.printf("Process %d: I recieved the matrix: \n", rank);
                // mat.print();
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException ex) {
                    System.out.println(ex);
                }
            }

            int[] dummy = new int[size];
            sendbuf = new int[1];

            switch (rank) {
                case 1:
                case 2:
                case 3:
                    sendbuf[0] = mat.getDetPart(rank - 1);
                    break;
                default:
                    sendbuf[0] = 0;
                    break;
            }

            // Send the new determinant to the root
            MPI.COMM_WORLD.Gather(sendbuf, 0, 1, MPI.INT, dummy, 0, 1, MPI.INT, root);

            // Recieve last message and terminate
            MPI.COMM_WORLD.Bcast(message, 0, 30, MPI.CHAR, root);
            String Msg = new String(message);
            System.out.println("Process " + rank + " exiting ...with message: " + Msg);
        }

        // Terminates MPI execution environment
        MPI.Finalize();
    }
}
