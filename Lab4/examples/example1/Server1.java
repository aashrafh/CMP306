package example1;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server1 {
	public static void main(String[] args) throws IOException {
		ServerSocket ss = new ServerSocket(6666); // create a server socket with port 6666
		System.out.println("Server is started.\n");

		try {
			while (true) {
				/*
				 * The server waits for a client to send a request. When the connection request
				 * reaches, it establishes a connection with the client and returns the socket
				 * object that will be used for communication with the client.
				 */
				System.out.println("I am waiting for a client to connect.");
				Socket socket = ss.accept();
				System.out.println("A client sent request and connection is established.");
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

				String str = null;
				while ((str = in.readLine()) != null) {
					System.out.println("message= " + str);
				}
				System.out.println("I have printed all messages that was sent from that client.\n");

			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			/* Don't forget to close the server socket */
			ss.close();
		}
	}
}
