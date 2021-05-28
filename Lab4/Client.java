import java.io.*;
import java.net.*;
import java.util.*;

public class Client {

    public static final int TRANSPOSE_PORT = 8000;
    public static final int DETERMINATE_PORT = 8080;

    // All functions are static so I can call them inside the main
    private static Matrix readMatrix() throws UnknownHostException, IOException, InterruptedException,
            NumberFormatException, ClassNotFoundException {
        Scanner in = new Scanner(System.in);

        int rows, cols;
        System.out.println("Please Enter The Matrix Details");

        System.out.printf("Rows = ");
        rows = in.nextInt();

        System.out.printf("Cols = ");
        cols = in.nextInt();

        System.out.printf("Enter the matrix values: ");
        int mat[][] = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                mat[i][j] = in.nextInt();
            }
        }
        Matrix matrix = new Matrix(rows, cols, mat);

        in.close();
        return matrix;
    }

    public static void main(String[] args) throws Exception {
        Matrix mat = readMatrix();

        Scanner in = new Scanner(System.in);
        System.out.printf("Choose which operation to perform: \n");
        System.out.printf("Enter 1 for Transpose, 2 for Determinate: ");
        int choice = in.nextInt();
        in.close();

        mat.print();

        if (choice == 1) {
            Socket socket = new Socket("localhost", TRANSPOSE_PORT);

            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

            outputStream.writeObject(mat); // Serialize and send to the server

            // Sleep for a random interval of time
            Thread.sleep((new Random()).nextInt(10000));

            // Deserialize the recieved transpose
            Matrix trans;
            try {
                trans = (Matrix) inputStream.readObject();
                System.out.println("Client: Recieved Transpose from server");
            } catch (ClassNotFoundException e) {
                System.out.println("Client: Failed to receive the transpose");
                trans = mat;
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("Client: Failed to close the transpose socket");
                }
            }

            trans.print();

        } else if (choice == 2) {
            Socket socket = new Socket("localhost", DETERMINATE_PORT);
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream()); // Output Stream in the
                                                                                                // Client and Input
                                                                                                // Stream in the Server
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            outputStream.writeObject(mat); // Serialize and send to the server

            // Sleep for a random interval of time
            Thread.sleep((new Random()).nextInt(10000));

            int det = 0;
            try {
                String res = inputReader.readLine();
                try {
                    det = Integer.parseInt(res);
                } catch (NumberFormatException e) {
                    System.out.println("Client: Wrong format");
                }
            } catch (IOException e) {
                System.out.println("Client: Failed to receive the determinate");
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("Client: Failed to close the determinate socket");
                }
            }

            System.out.println("Client: Recieved Determinate from server");
            System.out.printf("Determinate  = %d\n", det);

        } else {
            System.out.println("Unknown Choice\n");
        }

    }

}
