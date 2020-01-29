package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
	
	ObjectOutputStream objectOutputStream;
	ObjectInputStream objectInputStream;
	
	int clientId;

	public Client() {
		
	}

	public Client(Socket socket, int clientId) throws IOException {
		
		super();

		this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
		this.objectInputStream = new ObjectInputStream(socket.getInputStream());
		
		this.clientId = clientId;
	}

	public ObjectOutputStream getObjectOutputStream() {
		return objectOutputStream;
	}

	public ObjectInputStream getObjectInputStream() {
		return objectInputStream;
	}

	public int getClientId() {
		return clientId;
	}

	public void setObjectOutputStream(ObjectOutputStream objectOutputStream) {
		this.objectOutputStream = objectOutputStream;
	}

	public void setObjectInputStream(ObjectInputStream objectInputStream) {
		this.objectInputStream = objectInputStream;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
}
