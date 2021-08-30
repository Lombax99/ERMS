package main.java.views.interfacciaRendicontazione;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDrawer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import main.java.views.Utility_SidePanel;

public class HomeRendicontazione implements Initializable{

	@FXML
	private JFXDrawer sidePanel;
	@FXML
	private Button exitButton;
	
	
	public HomeRendicontazione() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		Utility_SidePanel.initialize(exitButton, sidePanel);
	}

}
