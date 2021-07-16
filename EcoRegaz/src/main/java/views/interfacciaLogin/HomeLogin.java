package main.java.views.interfacciaLogin;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.java.application.AlertPanel;
import main.java.application.Main;
import main.java.controllers.login.LoginController;
import main.java.persisters.MainDB;
import main.java.persisters.logPersister.PersisterLog;
import main.java.views.interfacciaHome.HomePrincipale;

public class HomeLogin {
	
	public final static String homePrincipaleURL = "/main/java/views/interfacciaHome/HomePrincipale.fxml";

	/**
	 * E' proprio la stessa istanza dello stage principale, quello di base.
	 */
	Stage primaryStage;

	LoginController loginController;
	PersisterLog logPersister;

	/**
	 * Costruttore che riceve il primaryStage per poter generare HomePrincipale. <br>
	 * Inizializza LoginController e PersisterLog.
	 */
	public HomeLogin(Stage primaryStage) {
		this.primaryStage = primaryStage;
		loginController = new LoginController();
		logPersister = new PersisterLog();
	}

	/**
	 * Handler del pulsante "Ok". <br>
	 * La password viene verificata via il loginController. <br>
	 * Se è falsa, appare un AlertPanel e il campo viene liberato. <br>
	 * Se è vera, viene eseguito il metodo initHomePrincipale(). <br>
	 * In entrambi i casi, viene scritto un log via logPersister.
	 */
	@FXML
	private void verificaCredenzialeHandler(ActionEvent event) {
		if (loginController.verificaCredenziali(passwordField.getText())) {
			logPersister.scritturaEntry(LocalDateTime.now(), true);
			initHomePrincipale();
		} else {
			logPersister.scritturaEntry(LocalDateTime.now(), false);
			passwordField.clear();
			AlertPanel.saysInfo("PASSWORD ERRATA", "Riprovare");
		}
	}

	/**
	 * 1) Load di HomePrincipale <br>
	 * 2) Creazione della Scene di HomePrincipale <br>
	 * 3) Set della Scene nel primaryStage <br>
	 * 4) Settaggio del database: connessione e impostazioni alla chiusura <br>
	 */
	private void initHomePrincipale() {
		
		/*
		 * Load del homePrincipale con la chiamata al costruttore personalizzata.
		 */
		AnchorPane homePrincipale = null;
		try {
			homePrincipale = FXMLLoader.<AnchorPane>load(getClass().getResource(homePrincipaleURL), null, null, e -> {
				return new HomePrincipale();
			});
		} catch (IOException e) {
			AlertPanel.saysError("ERRORE: nella load di HomePrincipale", e);
		}
		
		
		/*
		 * Creazione della Scene di homePrincipale e inserimento del CSS
		 */
		Scene homePrincipaleScene = new Scene(homePrincipale);
		homePrincipaleScene.getStylesheets().add(getClass().getResource(Main.CSSbootstrap3URL).toExternalForm());
		
		
		/*
		 * Set della homePrincipaleScene nel primaryStage
		 */
		primaryStage.setScene(homePrincipaleScene);
		
		
		/*
		 * Inizializzazione DB
		 */
		try {
			MainDB.openDatabase();
		} catch (Exception e) {
			AlertPanel.saysError("ERRORE: nell'apertura della connessione col database", e);
		}
		
		
		/*
		 * Impostazioni DB alla chiusura del primaryStage
		 */
		primaryStage.setOnCloseRequest(event -> {
			try {
				MainDB.closeDatabase();
			} catch (SQLException e) {
				AlertPanel.saysError("ERRORE: in chiusura del database", e);
			}
		});
	}

	@FXML
	PasswordField passwordField;

}
