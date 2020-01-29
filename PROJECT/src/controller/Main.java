package controller;

import java.io.IOException;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Message;
import model.User;
import model.UserFriend;

public class Main extends Application{

	Stage genStage;
	
	private static User user;

	@Override
	public void start(Stage primaryStage) throws Exception {
		 
		this.genStage = primaryStage;
		loginWindow();
	}
	
	void loginWindow() throws IOException{
		
		System.out.println("loginWindow called");
		
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/LoginView.fxml"));
		AnchorPane pane = loader.load();
		Scene scene = new Scene(pane);
		LoginController controller = loader.getController();
		controller.setMain(this, genStage);
		genStage.setScene(scene);
		genStage.show();		
	}
	
	public void signUpWindow() throws IOException {
		
		System.out.println("signUpWindow called");
		
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/SignUpView.fxml"));
		AnchorPane pane = loader.load();
		Scene scene = new Scene(pane);
		SignUpController controller = loader.getController();
		Stage stage = new Stage();
		controller.setMain(this, stage);
		stage.setScene(scene);
		stage.show();
	}
	
	public void uploadProfilePictureWindow(User user) throws IOException {
		
		System.out.println("uploadProfilePictureWindow called");
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/UploadProfilePicture.fxml"));
		AnchorPane pane = loader.load();
		Scene scene = new Scene(pane);
		UploadProfilePictureController controller = loader.getController();
		Stage stage = new Stage();
		controller.setMain(this, stage, user);
		stage.setScene(scene);
		stage.show();
	}
	
	public void welcomeWindow() throws IOException {
		
		System.out.println("welcomeWindow called");
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/WelcomeView.fxml"));
		BorderPane pane = loader.load();
		Scene scene = new Scene(pane);
		WelcomeController controller = loader.getController();
		Stage stage = new Stage();
		controller.setMain(this);
		stage.setScene(scene);
		stage.show();
		
		// formatting output to read easier
		System.out.println();
		System.out.println("user friend list check: ");
		List<UserFriend> friendList;
		friendList = (List<UserFriend>)getUser().getFriendList();
		
		for(int i = 0; i < friendList.size(); i++) {
			System.out.println("user_id: " + friendList.get(i).getUser().getUserId() + "; friend_id: " + friendList.get(i).getFriend().getUserId());
		}
		
		System.out.println("user message list check: ");
		List<Message> messageList = (List<Message>)getUser().getMessageList();
		
		for(int i = 0; i < messageList.size(); i++) {
			System.out.println("user_id: " + messageList.get(i).getSenderId().getUserId() + "; receiverId: " + messageList.get(i).getRecieverId().getUserId() + "; message = " + messageList.get(i).getMessage());
		}
		
		System.out.println("user message list2 check: ");
		List<Message> messageList2 = (List<Message>)getUser().getMessageList2();
		for(int j = 0; j < messageList2.size(); j++) {
			System.out.println("user_id: " + messageList2.get(j).getSenderId().getUserId() + "; recieverId: " + messageList2.get(j).getRecieverId().getUserId() + "; message = " + messageList2.get(j).getMessage());
		}
		
		// formatting output to read easier
		System.out.println();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	public static void setUser(User user1) {
		System.out.println("setting static user in main, name: " + user1.getName() + " and id: " + user1.getUserId());
		user = user1;
	}
	
	public static User getUser() {
		return user;
	}

}
