package controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.FetchType;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.User;
import model.UserFriend;

public class ProfileController {

    @FXML
    private Text profileUsername;

    @FXML
    private Text profileName;

    @FXML
    private Text profileEmail;

    @FXML
    private Text profileDOB;

    @FXML
    private JFXButton btnAddFriend;

    private User friend;
	private UserFriend userFriend;
	
	private List<UserFriend> friendList;
	private boolean isFriend;
	
    @FXML
    void addFriend(ActionEvent event) {
    	
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		userFriend = new UserFriend(controller.Main.getUser(), friend);
		
    	if(!isFriend) {
    			
    	    //persisting the object
    	    session.persist(userFriend);
    	}
    	else {
    		
    		session.delete(userFriend);
    	}	
    	
    	// committing/deleting the userFriend
    	session.getTransaction().commit();	

    	//update friend list of static user variable in main
    	Query updateFriendList = session.createQuery("from User where userId = '" + controller.Main.getUser().getUserId() + "'");
    	
    	controller.Main.setUser((User) updateFriendList.list().get(0));
    	
    	session.close();
    }

	public void setUser(User friend, boolean isFriend) {
    	
    	this.isFriend = isFriend;
    	this.friend = friend;
    	
    	profileUsername.setText(friend.getUsername());
    	profileName.setText(friend.getName());
    	profileEmail.setText(friend.getEmail());
    	profileDOB.setText(friend.getDateOfBirth());
    		
    		System.out.println("viewing profile id: " + friend.getUserId() + " and name: " + friend.getName() + "\n");
    	
    		if(isFriend) {
    			
    			btnAddFriend.setText("Delete Friend");
    		}
    		else if(!isFriend) {
    			
    			btnAddFriend.setText("Add Friend");
    		}
    	}
    }
//}