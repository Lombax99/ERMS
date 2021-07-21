package main.java.views.interfacciaInterventi;

import java.net.URL;
import java.util.ResourceBundle;

import main.java.views.Utility_SidePanel;

import com.jfoenix.controls.JFXDrawer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class HomeInterventi implements Initializable {






	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		/*
		 * Set up del sidePanel
		 */
		Utility_SidePanel.initialize(exitButton, sidePanel);

	}
	
	
	/*
	 * Definizione dei componenti grafici
	 */
	@FXML
	private JFXDrawer sidePanel;
	@FXML
	private Button exitButton;

}
