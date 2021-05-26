package example2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client2 {

	public static final int UPPERCASE_PORT = 6666;
	public static final int LOWERCASE_PORT = 6667;

	public static void main(String[] args) throws UnknownHostException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Enter the port you want to connect to: (1) UPPERCASE (2) lowercase");
		Scanner scanner = new Scanner(System.in);

		int port = (scanner.nextInt()) == 1 ? UPPERCASE_PORT : LOWERCASE_PORT;

		Socket s = new Socket("localhost", port);

		PrintWriter out = new PrintWriter(s.getOutputStream(), true);
		BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader socketReader = new BufferedReader(new InputStreamReader(s.getInputStream()));
		String message = null;
		
		// Consume the initial welcoming messages from the server
        System.out.println("The server sent the following message: " + socketReader.readLine());
		
		while(true)
        {
            System.out.println("Enter the message to be sent to the server or a period '.' to quit");
            if((message = consoleReader.readLine()) == null)
                break;
            out.println(message); //send message to server
            if(message.equals("."))
            	break;
            String convertedMessage = socketReader.readLine();           
			System.out.println("The received message = " + convertedMessage + "\n");
        }
		System.out.println("I am exiting now, bye.");
		scanner.close();
		out.close();
		consoleReader.close();
		s.close();

	}

}
