package controller;

import java.io.IOException;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.User;

public class SignUpController {
	
    @FXML
    private JFXTextField edtName;

    @FXML
    private JFXTextField edtUsername;

    @FXML
    private JFXTextField edtEmail;

    @FXML
    private JFXTextField edtDOB;

    @FXML
    private JFXPasswordField edtPassword;
    
    @FXML
    private JFXPasswordField edtReconfirmPassword;
    
	private Main main;
	private Stage stage;

	public void setMain(Main main, Stage genStage) {
		
		this.main  = main;
		this.stage = genStage;		
	}
	
	@FXML
	public void btnSubmit() throws IOException {
		
		User user = null;
		
		// session factory for getting the input username
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
    	
		// Fetch from DB
		Query query = session.createQuery("from User where username = '" + edtUsername.getText() + "'");
		if(query.list().size() > 0) {
			
			user = (User) query.list().get(0);
			user.toString();
		}
		
		// passwords dont match
		if(!(edtPassword.getText().equals(edtReconfirmPassword.getText()))) {
			
			Alert alert  = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("Sign Up Failed");
			alert.setContentText("Passwords do not match! Try again!");
			alert.setTitle("Alert!");
			alert.showAndWait();
		}
		// username is taken
		else if(user != null) {
			
			Alert alert  = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("Sign Up Failed");
			alert.setContentText("Username already taken! Try again!");
			alert.setTitle("Alert!");
			alert.showAndWait();
		}
		// everything checks out 
		else {
			
			User newUser = new User(edtName.getText(), edtUsername.getText(), edtPassword.getText(), edtEmail.getText(), edtDOB.getText());
			
	    	stage.close();
	    	main.uploadProfilePictureWindow(newUser);
		}
		
	}
	
	@FXML
	public void cancel() throws IOException {
    	stage.close();
    	main.loginWindow(); 
	}
}
