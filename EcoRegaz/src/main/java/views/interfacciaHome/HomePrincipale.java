package main.java.views.interfacciaHome;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.java.application.AlertPanel;
import main.java.application.Main;
import main.java.views.interfacciaInterventi.HomeInterventi;

public class HomePrincipale {
	
	public final static String homeAnalisiAreeVerdiURL = "/main/java/views/interfacciaAnalisi/HomeAnalisi.fxml";
	
	public final static String homeDepositiURL = "/main/java/views/interfacciaDeposito/HomeDepositi.fxml";
	
	public final static String homeInterventiURL = "/main/java/views/interfacciaInterventi/HomeInterventi.fxml";
	
	public final static String homeIscrittiURL = "/main/java/views/interfacciaIscritti/HomeIscritti.fxml";
	
	public final static String homeRendicontazioneURL = "/main/java/views/interfacciaRendicontazione/HomeRendicontazione.fxml";
	
	public final static String homeStrumentiURL = "/main/java/views/interfacciaStrumenti/HomeStrumenti.fxml";
	
	/**
	 * E' proprio la stessa istanza dello stage principale, quello di base.
	 */
	Stage primaryStage;
	
	/**
	 * Costruttore che prende il primaryStage dalla HomeLogin
	 */
	public HomePrincipale(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}


	/**
	 * Metodo handler del bottone Interventi. <br>
	 */
	@FXML
	private void openInterventiHandler(ActionEvent event) {
		
		/*
		 * Load di homeInterventi con la chiamata al costruttore personalizzata.
		 */
		AnchorPane homeInterventi = null;
		try {
			homeInterventi = FXMLLoader.<AnchorPane>load(getClass().getResource(homeInterventiURL), null, null, e -> {
				return new HomeInterventi(primaryStage);
			});
		} catch (IOException e) {
			AlertPanel.saysError("ERRORE: nella load di HomeInterventi", e);
		}
		
		
		/*
		 * Creazione della Scene di homeInterventi e inserimento del CSS
		 */
		Scene homeInterventiScene = new Scene(homeInterventi);
		homeInterventiScene.getStylesheets().add(getClass().getResource(Main.CSSbootstrap3URL).toExternalForm());
		
		
		/*
		 * Set della homeInterventiScene nel primaryStage
		 */
		primaryStage.setScene(homeInterventiScene);
		
	}
	
	
	/**
	 * Metodo handler del bottone Rendicontazione. <br>
	 */
	@FXML
	private void openRendicontazioneHandler(ActionEvent event) {
		
	}
	
	
	/**
	 * Metodo handler del bottone AreeVerdi. <br>
	 */
	@FXML
	private void openAnalisiAreeVerdiHandler(ActionEvent event) {
		
	}
	
	
	/**
	 * Metodo handler del bottone Strumenti. <br>
	 */
	@FXML
	private void openStrumentiHandler(ActionEvent event) {
		
	}
	
	
	/**
	 * Metodo handler del bottone Depositi. <br>
	 */
	@FXML
	private void openDepositiHandler(ActionEvent event) {
		
	}
	
	
	/**
	 * Metodo handler del bottone Iscritti. <br>
	 */
	@FXML
	private void openIscrittiHandler(ActionEvent event) {
		
	}
	
	
	

}
