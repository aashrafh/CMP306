package example1;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client1 {

	public static void main(String[] args) {
		try {
			/*
			 * This line establishes a connection with the server with IP address
			 * "localhost" or "127.0.0.1" and port 6666, it is the same port that will be
			 * used in the server. Note that: "localhost" refers to the local computer that
			 * a program is running on. The local machine is defined as "localhost," which
			 * gives it an IP address of 127.0. 0.1.
			 */
			System.out.println("Hi I am the new client");
			Socket socket = new Socket("localhost", 6666);
			
			/* Don't forget to set the autoflush parameter of the PrintWriter object as true */
			
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			
			/* The client sends a user-input message to the server */
			
			// The following code sends one hardcoded message
			/*
			Thread.sleep(5000);
			String message = "Hello world, I am Client 1";
			out.println(message);
			System.out.println("I have sent the message to the server");
			*/
			
			// The following code sends any number of messages that the user enters in the client console
			Scanner scanner = new Scanner(System.in);
			String message = null;
			for (int i=0;(message = scanner.nextLine()) != null; i++) {
				out.println(message);
				System.out.println("I have sent message number " +(i+1)+ " to the server.");
			}
			scanner.close(); 
			
			out.close();	
			socket.close(); 

		} catch (Exception e) {
			System.out.println(e);
		} 
	}
}