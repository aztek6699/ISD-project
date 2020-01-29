import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import model.Client;
import model.Message;

// ClientHandler class
class ClientHandler implements Runnable 
{
	
	Client client = null;
	
	Message message = null;
	
	// constructor
	public ClientHandler(Socket clientSocket) throws IOException {
		
		client = new Client(clientSocket);
		
	}

	@Override
	public void run() {
		
		while (true) {
			
			try {
				
				// listening for incoming messages
				message = (Message) client.getObjectInputStream().readObject();
				System.out.println(client.getClientId().getUserId() + " got a new message with sender id = " + message.getSenderId().getUserId() + " and reciever id = " + message.getRecieverId().getUserId() + " and message = " + message.getMessage() + " at time = " + message.getSentTime());
				
				System.out.println("Saving message to database");
				
				
			} catch (ClassNotFoundException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			}
			
			// sending message to appropriate client by getting receiver id from message and using that
			// to figure out which client to send to(makes sense to me)
			try {
			
				for(int i = 0; i < Server.clientList.size(); i++) {
					
					//System.out.println("in client handler of clientId: " + client.getClientId().getUserId() + " comparing clientList clientId at: " + i + " and recieverId: " + message.getRecieverId().getUserId());
					
					if(Server.clientList.get(i).getClient().getClientId().getUserId() == message.getRecieverId().getUserId()) {
						
						System.out.println("Sending message from: " + client.getClientId().getUserId() + " to: " + Server.clientList.get(i).getClient().getClientId().getUserId());
						Server.clientList.get(i).getClient().getObjectOutputStream().writeObject(message);
						Server.clientList.get(i).getClient().getObjectOutputStream().flush();
					}
				
				}
				
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}
	
	public Client getClient() {
		
		return client;
	}
}