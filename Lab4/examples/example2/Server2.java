package example2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server2 {

	public static final int UPPERCASE_PORT = 6666;
	public static final int LOWERCASE_PORT = 6667;

	public static int clientNumber = 0; // to keep track of the number of clients connecting to the server.

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("The server started .. ");

		new Thread() {
			public void run() {
				try {
					ServerSocket ss = new ServerSocket(UPPERCASE_PORT);
					while (true) {
						new CaseChanger(ss.accept(), clientNumber++).start();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();

		new Thread() {
			public void run() {
				try {
					ServerSocket ss = new ServerSocket(LOWERCASE_PORT);
					while (true) {
						new CaseChanger(ss.accept(), clientNumber++).start();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();

	}

	private static class CaseChanger extends Thread {
		Socket socket;
		int clientNo;

		public CaseChanger(Socket s, int clientNo) {
			this.socket = s;
			this.clientNo = clientNo;
			System.out.println("Connection with Client #" + clientNo + "at socket " + socket);
		}

		public void run() {
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
				PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);

				// Send a welcome message to the client.
				out.println("Hello, you are client #" + clientNo + ".");

				String message = null;
				while (true) {
					if ((message = br.readLine()) == null || message.equals("."))
						break;
					String convertedMessage = null;
					if (this.socket.getLocalPort() == UPPERCASE_PORT)
						convertedMessage = message.toUpperCase();
					else
						convertedMessage = message.toLowerCase();

					out.println(convertedMessage);
				}
				out.close();
				br.close();
				// socket.close();
				// System.out.println("Connection with Client #" + this.clientNo + "
				// finished..");
			} catch (IOException e) {
				System.out.println("Error handling client# " + this.clientNo + ": " + e);
			} finally {
				try {
					socket.close();
				} catch (IOException e) {
					System.out.println("Couldn't close a socket, what's going on?");
				}
				System.out.println("Connection with client# " + this.clientNo + " closed");
			}
		}
	}

}
