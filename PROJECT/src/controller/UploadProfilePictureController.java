package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.Stage;
import model.User;

public class UploadProfilePictureController {
	
	@FXML
	ImageView profilePicture;
	
	private Main main;
	private Stage stage;
	
	private User user; 

	public void setMain(Main main, Stage genStage, User user) {
		
		this.main = main;
		this.stage = genStage;
		this.user = user;
	}
	
	@FXML
	void imageDragOver(DragEvent dragEvent) throws IOException {
		
		Dragboard dragBoard = dragEvent.getDragboard();
		
		if(dragBoard.hasFiles()) {
			
			dragEvent.acceptTransferModes(TransferMode.ANY);
		}
	}
	
	@FXML
	void dragOver(DragEvent dragEvent) {
		
		// to check if something is being dragged or is it empty
		if(dragEvent.getDragboard().hasFiles())
		// makes the component ready to accept the incoming data
		dragEvent.acceptTransferModes(TransferMode.ANY);
	}
		
    @FXML
    void imageDropped(DragEvent dragEvent) throws FileNotFoundException {
    	// list of files taken from drag event
    	List<File> fileList = dragEvent.getDragboard().getFiles();
    	// first file from list is used to create an image
    	Image image = new Image(new FileInputStream(fileList.get(0)));
    	// image then set to image viewer, this case profile picture
    	profilePicture.setImage(image);
    	      
    }
    
    @FXML
    void skipStepPressed(ActionEvent event) throws IOException {

    	// session factory for creating a new profile
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
    	
		//persisting the object
	    session.persist(user);
	    
	    // commiting the new user
	    session.getTransaction().commit();
	    session.close();
    	
    	stage.close();
    	main.loginWindow(); 
    }

    @FXML
    void submitPressed(ActionEvent event) throws IOException {    	
    	
    	// session factory for creating a new profile
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
    	
		//persisting the object
	    session.persist(user);
	    
	    // commiting the new user
	    session.getTransaction().commit();
	    session.close(); 	
    	
    	stage.close();
    	main.loginWindow();
    }
	
}
