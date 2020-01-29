package controller;

import java.io.IOException;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.User;

public class WelcomeController {

    @FXML
    private Text profileName;
	
	@FXML
	private JFXHamburger hamburger;

	@FXML
	private JFXDrawer drawer;

	@FXML
	private AnchorPane rootP;

	static AnchorPane root;

	static Main main;
	
	//private static User user;

	public void setMain(Main main) {

		this.main = main;
	}

	public void initialize() throws IOException {
				
		root = rootP;
		
		profileName.setText(controller.Main.getUser().getName());
		
		System.out.println("Setting Ham");
		VBox sidePane = FXMLLoader.load(getClass().getResource("/view/SidePanelContentView.fxml"));

		//controller.SidePanelContentController.setUser(user);
		
		drawer.setSidePane(sidePane);

		HamburgerBackArrowBasicTransition basicTransition = new HamburgerBackArrowBasicTransition(hamburger);
		basicTransition.setRate(-1);
		hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->{
			basicTransition.setRate(basicTransition.getRate()*-1);
			basicTransition.play();

			if(drawer.isShown()){
				drawer.close();
			}else{
				drawer.open();
			}
		});
	}
}
