package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.User;
import model.UserFriend;

public class SearchController {

    @FXML
    private AnchorPane root;

    @FXML
    private JFXTextField searchBar;

    @FXML
    private VBox nameRow;

    @FXML
    private VBox usernameRow;

    @FXML
    private VBox buttonRow;
    
	private List<UserFriend> friendList;
        
    private ArrayList<User> searchResults;

    public void initialize() {
    	
    	AnchorPane.setTopAnchor(root, 0.0);
    	AnchorPane.setBottomAnchor(root, 0.0);
    	AnchorPane.setRightAnchor(root, 0.0);
    	AnchorPane.setLeftAnchor(root, 0.0);
    }
    
    @FXML
    void search(ActionEvent event) {
    		
    	nameRow.getChildren().clear();
    	usernameRow.getChildren().clear();
    	buttonRow.getChildren().clear();
    	
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		//Fetch from DB
		Query query = session.createQuery("from User where name = '" + searchBar.getText() + "'");
		
		searchResults = (ArrayList<User>) query.list();
		
		for(int i = 0; i < searchResults.size(); i++) {
			
			Text name = new Text(searchResults.get(i).getName());
			name.setStyle("-fx-font-size: 15px;");
			name.setFill(Color.rgb(178, 186, 187));
			
			Text username = new Text(searchResults.get(i).getUsername());
			username.setStyle("-fx-font-size: 15px;");
			username.setFill(Color.rgb(178, 186, 187));
			
			JFXButton btnViewProfile = new JFXButton("View Profile");
			btnViewProfile.prefWidth(20);
			btnViewProfile.setStyle("-fx-font: 15xp; -fx-background-color: #60b0f4; -fx-text-fill: #ffffff;");
			
			final int pos = i;
			
			btnViewProfile.setOnAction(actionEvent ->  {
					
				System.out.println("btnViewProfile called");
				
				// try/catch for the FXMLLoader.load()
				try {
					
					boolean isFriend = false;
					
					// logging stuff
					System.out.println("isFriend: " + isFriend);
					
					// get friendList from controller.Main.user then check friend.getUserId to searchResults.get(pos).getUserId() idea start
					for(UserFriend element : controller.Main.getUser().getFriendList()) {
						
						System.out.print("checking if user_friend exists for user id(logged in user id): " + element.getFriend().getUserId());
						System.out.println(" and id(searched user id): " + searchResults.get(pos).getUserId());
						
						if(element.getFriend().getUserId() == searchResults.get(pos).getUserId()) {
							
							isFriend = true;
						}
					}
					
					/* friendCheckQuery keeps returning 0, why?????
					
					// friendCheckQuery used to fetch controller.Main.getUser() and searchResult.get(pos) and if something returns then isFriend is true
					String queryToSend = "from UserFriend WHERE user = '" + controller.Main.getUser() + "' AND friend = '" + searchResults.get(pos) + "'";
					
					// logging stuff
					System.out.println("sending query: " + queryToSend);
					
					Query friendCheckQuery = session.createQuery(queryToSend);
					
					// logging stuff
					System.out.println("friendCheckQuery worked, friendCheckQuery.list().size() = " + friendCheckQuery.list().size());
					System.out.println("putting friendCheckQuery list into friendCheckList");
					
					// putting friendCheckQuery list into friendCheckList
					List<UserFriend> friendCheckList = friendCheckQuery.list();
					
					// logging stuff
					System.out.println("friendCheckList.size() = " + friendCheckList.size());
					
					friendCheckQuery idea end */					
					
					// checking if friendCheckQuery returned something, if yes then isFriend is true
					if(isFriend) {
						
						System.out.println("isFriend set to = " + isFriend);
						isFriend = true;
					}
					else {
						
						System.out.println("isFriend set to = " + isFriend);
						isFriend = false;
					}
					
			    	//showing the screen etc
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ProfileView.fxml"));
					AnchorPane root1 = loader.load();
					
					ProfileController controller = loader.<ProfileController>getController();
					controller.setUser(searchResults.get(pos), isFriend);
					
					Stage stage = new Stage();
					stage.setScene(new Scene(root1));  
					stage.show();
				
				} catch (IOException e) {
					
					e.printStackTrace();
				}
    
			});
			
			nameRow.getChildren().add(name);
			usernameRow.getChildren().add(username);
			buttonRow.getChildren().add(btnViewProfile);
			
			//session.close();
		}
		
    }

}
