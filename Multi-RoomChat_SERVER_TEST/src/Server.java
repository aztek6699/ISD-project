import java.io.*;
import java.util.*;
import java.net.*;

public class Server {
	
	// Vector to store active clients
	static ArrayList<ClientHandler> clientList = new ArrayList<ClientHandler>();
	
	public static void main(String[] args) throws IOException 
	{
		// server is listening on port 5555
		ServerSocket serverSocket = new ServerSocket(5554);
		System.out.println("Server running\n");
		
		// used for accepting new clients
		Socket incomingClientSocket;
		
		// running infinite loop for getting client requests
		while (true) 
		{
			// Accept the incoming request
			incomingClientSocket = serverSocket.accept();
			System.out.println("New client request received : " + incomingClientSocket);
			
			// Create a new handler object for handling this request
			ClientHandler newClientHandeler = new ClientHandler(incomingClientSocket);
			
			// Create a new Thread with this object
			System.out.println("Creating thread for new client handler...");
			Thread newClientHandlerThread = new Thread(newClientHandeler);
			
			// add this client to active clients list at position clientId
			System.out.println("Adding this client to active client list...");
			clientList.add(newClientHandeler);

			System.out.println("clientList.size() = " + clientList.size());
			
			// start the newClientHandlerThread
			System.out.println("Starting client handler thread...\n");
			newClientHandlerThread.start(); 
		}
	}
}