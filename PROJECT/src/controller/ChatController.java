package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.Client;
import model.Message;
import model.User;
import model.UserFriend;

public class ChatController implements Runnable{

    @FXML
    private AnchorPane root;

    @FXML
    private VBox displayFriendList;

    @FXML
    private TabPane tabPane;
    
    private List<UserFriend> friendList;
    
	Socket socket;
    ObjectOutputStream objectOutputStream;
    ObjectInputStream objectInputStream;
	
    private void connectToServer() {

		// try/catch for connecting to the server and getting input/output streams
		try {

			System.out.println("Attempting connection to server at port: 5555");
			socket = new Socket("localhost", 5554);
			System.out.println("Connected to server at port: 5555");

			objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectInputStream = new ObjectInputStream(socket.getInputStream());
			
			// sending client object to server
			objectOutputStream.writeInt(Main.getUser().getUserId());
			objectOutputStream.flush();
		} catch (UnknownHostException e) {
		
			e.printStackTrace();
		} catch (IOException e) {
		
			e.printStackTrace();
		}		
	}
    
    // populate the friends list as buttons, when buttons pressed creates new tab with a ListView to display messages,
    // a TextArea to type messages and a button for sending messages
    void populateFriendList() {
    	
    	friendList = controller.Main.getUser().getFriendList();

    	displayFriendList.getChildren().clear();

		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
    	for(int i = 0; i < friendList.size(); i++) {
    		
    		// logging stuff
    		System.out.println("\nfetching then setting button to username: " + friendList.get(i).getFriend().getUsername() +
    						   " by getting userID: " + friendList.get(i).getFriend().getUserId() + " from friendList");
    		//Fetch user by userId from user_friend 
    		Query query = session.createQuery("from User where userId = '" + friendList.get(i).getFriend().getUserId() + "'");
    		
    		User friend = (User) query.list().get(0);
    		
    		JFXButton btnStartChat = new JFXButton(friend.getUsername());
    		btnStartChat.setMaxWidth(Double.MAX_VALUE);
    		btnStartChat.setStyle("-fx-font: 15xp; -fx-background-color: #60b0f4; -fx-text-fill: #ffffff;");
    		
    		final int pos = i;
    		
    		// setting button onAction
    		btnStartChat.setOnAction(actionEvent ->  {
    			
    			// create a new tab and sets tab to username of selected friend and sets closable to true
    			Tab newTab = new Tab(friendList.get(pos).getFriend().getUsername());
    			
    			// creating a new int variable to hold the userId of this friend (used for checking if the message sent from server
    			// meant for this friend)
    			User friendId = friendList.get(pos).getFriend();
    			
    			// be able to close the tab
    			newTab.setClosable(true);
    			
    			// start adding things to the newTab
    			// declaring list view to display sent and received messages
    			ListView<String> messageDisplayArea = new ListView<String>();
    			
        		//Fetch user by userId from user_friend 
//        		Query query2 = session.createQuery("from Message where sender_id = '" + controller.Main.getUser().getUserId() + "' OR receiver_id = '" + controller.Main.getUser().getUserId());
        		
//        		for(Message temp: (Message)query2.list()) {
//        			
//        			System.out.println("message = '" + temp.getMessage() + "' sent to = " + temp.getRecieverId());
//        		}
    			    			
    			// declaring a textArea to type messages in 
    			TextArea typeMessage = new TextArea();
    			
    			// declaring a new button to send typed messages
    			JFXButton btnSend = new JFXButton("Send");
    			    			
    			// setting action listener for btnSend
    			btnSend.setOnAction(actionEvent1 ->  {
    				
    				// new message object created with senderId: logged in user's ID, recieverId: friendId, message: text from typeMessage
    				Message outgoingMessage = new Message(Main.getUser() /*senderId*/, friendId /* recieverId */, typeMessage.getText());
    				
    				System.out.println("sending message to: " + friendId.getUserId());
    				
    				// try/catch for sending message to server
    				try {
						objectOutputStream.writeObject(outgoingMessage);
	    				objectOutputStream.flush();
					} catch (Exception e) {
						e.printStackTrace();
					}
    				
    				messageDisplayArea.getItems().add("sent: " + outgoingMessage.getMessage());
    				
    			});
    			
    			// delcaring new hbox to hold typeMessage
    			HBox hBox = new HBox();
    			
    			// setting the typeMessage node in hBox to aways grow horizontally
    			hBox.setHgrow(typeMessage, Priority.ALWAYS);
    			
    			// adding typeMessage and btnSend to hBox
    			hBox.getChildren().addAll(typeMessage, btnSend);
    			
    			// declaring a new VBox to hold list view and hbox containing typeMessage and btnSend
    			VBox vBox = new VBox();
    			
    			// setting the messageDisplyaArea node to always grow vertically
    			vBox.setVgrow(messageDisplayArea, Priority.ALWAYS);
    			
    			// adding messageDisplayArea and hBox to the vBox
    			vBox.getChildren().addAll(messageDisplayArea, hBox);
    			
    			// adding vBox to newTab
    			newTab.setContent(messageDisplayArea);
    			newTab.setContent(vBox); 
    			// end adding stuff to newTab
    			
    			// add tab to the tab pane
    			tabPane.getTabs().add(newTab);
    			
    			// solution from: https://examples.javacodegeeks.com/desktop-java/javafx/javafx-concurrency-example/
    		    // XXX: garbage solution as mentioned in above URL
    			// start a thread to listen for messages from the server
    			Runnable listenerTask = new Runnable() {

    				@Override
    				public void run() {
    					
    					System.out.println("ListenerTask thread started");
    					
    					while(true) {
    						
    						try {
    							
    							// used for getting messages from the server
    							Message incomingMessage = (Message) objectInputStream.readObject();
    							
    							// checking if the message gotten from server meant for this friend (by comparing friendId (declared above)
    							// with incomingMessage's senderId field
    							if(incomingMessage.getSenderId().getUserId() == friendId.getUserId()) {
    							
    							    session.persist(incomingMessage);
    							    
    							    // commiting the new user
    							    session.getTransaction().commit();
    								
	    							// adding the received message from server to this messageDisplayArea
	    							//TODO: set alignment of received message to the right
	    							messageDisplayArea.getItems().add("recieved: " + incomingMessage.getMessage());
    							}
    						
    						} catch (ClassNotFoundException e) {
    							
    							e.printStackTrace();
    						} catch (IOException e) {

    							e.printStackTrace();
    						}
    					}
    				}
    			};
    			
    			// creating then starting the listenerTask thread
    			System.out.println("Starting ListnerThread");
    			Thread listenerTaskThread = new Thread(listenerTask);
    			listenerTaskThread.setDaemon(true);
    			listenerTaskThread.start();
    			
    		});
    		
    		// adding the btnStartChat to VBox displayFriendList
    		displayFriendList.getChildren().add(btnStartChat);
    		
    	}
    	
    	//session.close();
    }
    
    // checking for new messages from the server
    @Override
    public void run() {
    	
    }

    public void initialize() {
    	
    	AnchorPane.setTopAnchor(root, 0.0);
    	AnchorPane.setBottomAnchor(root, 0.0);
    	AnchorPane.setRightAnchor(root, 0.0);
    	AnchorPane.setLeftAnchor(root, 0.0);
    	
    	connectToServer();
    	
    	populateFriendList();
    }

    @FXML
    void search(ActionEvent event) {

    }

}