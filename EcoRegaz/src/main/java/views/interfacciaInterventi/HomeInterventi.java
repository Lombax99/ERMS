package main.java.views.interfacciaInterventi;

import java.net.URL;
import java.util.ResourceBundle;

import main.java.views.GestoreInterfacce;
import main.java.views.Utility_SidePanel;

import com.jfoenix.controls.JFXDrawer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * Classe che si limita a ridirezionare a GestioneInterfacce l'apertura delle View di Aggiunta e Visualizzazione Interventi
 *
 */
public class HomeInterventi implements Initializable {


	/**
	 * Metodo handler del bottone Aggiungi Intervento. <br>
	 */
	@FXML
	private void openAggiungiInterventoHandler(ActionEvent event) {

		GestoreInterfacce.getInstance().initViewAggiuntaIntervento();

	}


	/**
	 * Metodo handler del bottone Vedi Interventi. <br>
	 */
	@FXML
	private void openVediInterventiHandler(ActionEvent event) {

		GestoreInterfacce.getInstance().initViewVediInterventi();

	}



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
