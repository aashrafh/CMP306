import java.io.*;
import java.net.*;

public class Server {

    public static final int TRANSPOSE_PORT = 6666;
    public static final int DETERMINATE_PORT = 6667;

    public static int clientNumber = 0; // to keep track of the number of clients connecting to the server.

    public static void main(String[] args) throws IOException {
        System.out.println("The server started .. ");

        // Transpose Thread
        new Thread() {
            public void run() {
                try {
                    // 2 try blocks so I can handle both catching the IOException and closing the
                    // listener in the finally block
                    ServerSocket transposeListener = new ServerSocket(TRANSPOSE_PORT);
                    try {
                        while (true) {
                            new MatrixHandler(transposeListener.accept(), clientNumber++).start();
                        }
                    } finally {
                        transposeListener.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();

        // Determinate Thread
        new Thread() {
            public void run() {
                try {
                    // 2 try blocks so I can handle both catching the IOException and closing the
                    // listener in the finally block
                    ServerSocket determinateListener = new ServerSocket(TRANSPOSE_PORT);
                    try {
                        while (true) {
                            new MatrixHandler(determinateListener.accept(), clientNumber++).start();
                        }
                    } finally {
                        determinateListener.close();
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
        String listener;

        public MatrixHandler(Socket socket, int clientNo) {
            this.socket = socket;
            this.clientNo = clientNo;

            this.listener = this.socket.getLocalPort() == TRANSPOSE_PORT ? "Transopose Listener"
                    : "Determinate Listener";
            System.out.printf("Connection with Client %d, at Socket %d to listener: %s\n", clientNo, socket, listener);
        }

        public void run() {
            try {
                ObjectInputStream inputStream = new ObjectInputStream(this.socket.getInputStream());
                ObjectOutputStream outputStream = new ObjectOutputStream(this.socket.getOutputStream());

                try {
                    Matrix mat = (Matrix)inputStream.readObject();  // Deserialize: cast the bytes back to Matrix type
                    
                }
            } catch (IOException e) {
                System.out.printf("%s: Error handling client#%d \n", this.listener, this.clientNo);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.printf("%s: Couldn't close a socket\n", this.listener);
                }
                System.out.printf("%s: Connection with client#%d has completed and closed\n", this.listener,
                        this.clientNo);
            }
        }
    }

}
