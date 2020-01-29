package controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

public class HomeController {

    @FXML
    private AnchorPane root;

    @FXML
    private ListView<?> postList;
    
    public void initialize() {
    	
    	AnchorPane.setTopAnchor(root, 0.0);
    	AnchorPane.setBottomAnchor(root, 0.0);
    	AnchorPane.setRightAnchor(root, 0.0);
    	AnchorPane.setLeftAnchor(root, 0.0);   
    	}
    
    public static void setAnchors() {
    	
    }

}
