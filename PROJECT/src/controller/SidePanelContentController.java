package controller;

import com.jfoenix.controls.JFXButton;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import model.User;

public class SidePanelContentController implements Initializable {

    @FXML
    private JFXButton btnHome;
    
    @FXML
    private JFXButton btnChat;
    
    @FXML
    private JFXButton btnSearch;
    
    @FXML
    private JFXButton exit;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	
    }

    @FXML
    private void changeScreen(ActionEvent event) throws IOException {
        
    	JFXButton btn = (JFXButton) event.getSource();
        System.out.println(btn.getText());
        
        switch(btn.getText())
        {
            case "Home":
            	
            	FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/HomeView.fxml"));
            	AnchorPane root = loader.load();
            	WelcomeController.root.getChildren().setAll(root); 
            	break;
                
            case "Chat":
            	
            	FXMLLoader loader1 = new FXMLLoader(Main.class.getResource("/view/ChatView.fxml"));
            	AnchorPane root1 = loader1.load();
            	WelcomeController.root.getChildren().setAll(root1);
                break;
                
            case "Search" :
            	
            	FXMLLoader loader2 = new FXMLLoader(Main.class.getResource("/view/SearchView.fxml"));
            	AnchorPane root2 = loader2.load();
            	WelcomeController.root.getChildren().setAll(root2);            	
        }
    }

    @FXML
    private void exit(ActionEvent event) {
        System.exit(0);
    }

}
