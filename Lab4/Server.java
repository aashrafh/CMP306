import java.io.*;
import java.net.*;

public class Server {

    public static final int TRANSPOSE_PORT = 8000;
    public static final int DETERMINATE_PORT = 8080;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("The server started .. ");

        // Transpose Thread
        new Thread() {
            public void run() {
                int clientNumber = 0; // to keep track of the number of clients connecting to the server.
                try {
                    ServerSocket transposeListener = new ServerSocket(TRANSPOSE_PORT);
                    while (true) {
                        (new MatrixHandler(transposeListener.accept(), clientNumber++)).start();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();

        // Determinate Thread
        new Thread() {
            public void run() {
                int clientNumber = 0; // to keep track of the number of clients connecting to the server.
                try {
                    ServerSocket determinateListener = new ServerSocket(DETERMINATE_PORT);
                    while (true) {
                        (new MatrixHandler(determinateListener.accept(), clientNumber++)).start();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    private static class MatrixHandler extends Thread {
        Socket socket;
        int clientNo;

        public MatrixHandler(Socket socket, int clientNo) {
            this.socket = socket;
            this.clientNo = clientNo;

            System.out.printf("Connection with Client %d\n", clientNo);
        }

        private Matrix getTranspose(Matrix mat) {
            int rows = mat.getRows();
            int cols = mat.getCols();
            int matrix[][] = mat.getMat();
            System.out.println("basha");

            int transpose[][] = new int[cols][rows];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    transpose[j][i] = matrix[i][j];
                }
            }
            return (new Matrix(cols, rows, transpose));
        }

        private int[][] calcCoFactors(int matrix[][], int r, int c, int rows) {
            int coFactors[][] = new int[rows - 1][rows - 1];
            int row = 0, col = 0;

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < rows; j++) {
                    if (i != r && j != c) {
                        coFactors[row][col] = matrix[i][j];
                        col++;
                        if (col == rows - 1) {
                            col = 0;
                            row++;
                        }
                    }
                }
            }

            return coFactors;
        }

        private int calcDeterminate(int matrix[][], int rows) {
            int det = 0;

            if (rows == 1)
                return matrix[0][0];

            int sign = 1;
            for (int i = 0; i < rows; i++) {
                int coFactors[][] = calcCoFactors(matrix, 0, i, rows);
                det += sign * matrix[0][i] * calcDeterminate(coFactors, rows - 1);
                sign *= -1;
            }
            return det;
        }

        private int getDeterminate(Matrix mat) {
            int rows = mat.getRows();
            int cols = mat.getCols();
            int matrix[][] = mat.getMat();

            if (rows != cols) {
                throw new IllegalArgumentException(
                        "The Determinant of a Matrix is a real number that can be defined for square matrices only\n");
            } else {
                return calcDeterminate(matrix, rows);
            }

        }

        public void run() {
            try {
                if (this.socket.getLocalPort() == TRANSPOSE_PORT) {
                    ObjectInputStream inputStream = new ObjectInputStream(this.socket.getInputStream());
                    ObjectOutputStream outputStream = new ObjectOutputStream(this.socket.getOutputStream());

                    try {
                        Matrix mat = (Matrix) inputStream.readObject(); // Deserialize: cast the bytes back to Matrix
                                                                        // type

                        System.out.println("Trans....");
                        Matrix transpose = getTranspose(mat);
                        outputStream.writeObject(transpose);

                    } finally {
                        System.out.printf("Operation with client#%d comleted\n", this.clientNo);
                    }
                } else {
                    ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                    PrintWriter outputWriter = new PrintWriter(socket.getOutputStream(), true);

                    System.out.println("Det....");
                    try {
                        Matrix mat = (Matrix) inputStream.readObject(); // Deserialize: cast the bytes back to Matrix
                        int det = getDeterminate(mat);
                        outputWriter.println(Integer.toString(det)); // Serialize
                    } finally {
                        System.out.printf("Operation with client#%d comleted\n", this.clientNo);
                    }
                }
            } catch (Exception e) {
                System.out.printf("Error handling client#%d \n", this.clientNo);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.printf("Couldn't close a socket\n");
                }
                System.out.printf("Connection with client#%d closed\n", this.clientNo);
            }
        }
    }

}
