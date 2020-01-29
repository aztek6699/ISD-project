package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Client {
	
	ObjectOutputStream objectOutputStream;
	ObjectInputStream objectInputStream;
	
	User clientId;

	public Client() {
		
	}

	public Client(Socket socket) throws IOException {
		
		super();

		this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
		this.objectInputStream = new ObjectInputStream(socket.getInputStream());
		
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
    	
		// Fetch from DB
		Query query = session.createQuery("from User where userId='" + objectInputStream.readInt() + "'");
		
		this.clientId = (User) query.list().get(0);
	}

	public ObjectOutputStream getObjectOutputStream() {
		return objectOutputStream;
	}

	public ObjectInputStream getObjectInputStream() {
		return objectInputStream;
	}

	public User getClientId() {
		return clientId;
	}

	public void setObjectOutputStream(ObjectOutputStream objectOutputStream) {
		this.objectOutputStream = objectOutputStream;
	}

	public void setObjectInputStream(ObjectInputStream objectInputStream) {
		this.objectInputStream = objectInputStream;
	}

	public void setClientId(User clientId) {
		this.clientId = clientId;
	}
}
