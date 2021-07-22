package main.java.views.interfacciaIscritti;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDrawer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import main.java.views.Utility_SidePanel;

public class HomeIscritti implements Initializable{

	@FXML
	private JFXDrawer sidePanel;
	@FXML
	private Button exitButton;


	public HomeIscritti() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		Utility_SidePanel.initialize(exitButton, sidePanel);
	}

}
