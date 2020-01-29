package controller;

import java.io.IOException;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.User;

public class LoginController {

    @FXML
    private TextField edtUsername;
    @FXML
    private PasswordField edtPassword;
    @FXML
    private JFXButton loginButton;
    @FXML
    private Hyperlink signUpLink;
    @FXML
    private Hyperlink forgotPasswordLink;
	
    private Main main;
	private Stage stage;
      
	public void setMain(Main main, Stage genStage) {
		this.main  = main;
		this.stage = genStage;
		
	}
	
	@FXML
	void initilize() {
		
	}
	
	@FXML
	void performLogin(ActionEvent event) throws IOException {
			
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
    	
		// Fetch from DB
		Query query = session.createQuery("from User where username='" + edtUsername.getText() + "' And password = '"+edtPassword.getText() + "'");
	
		// Check if a user is actually returned
    	if(query.list().size() > 0) {
			
    		// Cast it to User Class
    		User user = (User) query.list().get(0);
    		
    		controller.Main.setUser(user);
    		
        	stage.close();
        	main.welcomeWindow();
        	
        }else {
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("Incorrect username or password!");
			alert.setContentText("Login Failed");
			alert.setTitle("Alert!");
			alert.showAndWait();
		}
    	
	    session.getTransaction().commit();
	    session.close(); 
	}

    @FXML
    void callForgotPassword(ActionEvent event) {

    }

    @FXML
    void callSignUp(ActionEvent event) throws IOException {
    	stage.close();
    	main.signUpWindow();
    }


}
