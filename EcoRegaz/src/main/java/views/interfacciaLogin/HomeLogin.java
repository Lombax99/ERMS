package main.java.views.interfacciaLogin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.java.application.AlertPanel;
import main.java.application.Main;
import main.java.controllers.backup.BackupController;
import main.java.controllers.login.LoginController;
import main.java.persisters.MainDB;
import main.java.views.interfacciaHome.HomePrincipale;

public class HomeLogin {
	
	public final static String homePrincipaleURL = "/main/java/views/interfacciaHome/HomePrincipale.fxml";

	/**
	 * E' proprio la stessa istanza dello stage principale, quello di base.
	 */
	Stage primaryStage;

	LoginController loginController;

	/**
	 * Costruttore che riceve il primaryStage per poter generare HomePrincipale. <br>
	 * Inizializza LoginController e PersisterLog.
	 */
	public HomeLogin(Stage primaryStage) {
		this.primaryStage = primaryStage;
		loginController = new LoginController();
	}

	/**
	 * Handler del pulsante "Ok". <br>
	 * La password viene verificata via il loginController. <br>
	 * Se è falsa, appare un AlertPanel e il campo viene liberato. <br>
	 * Se è vera, viene eseguito il metodo initHomePrincipale(). <br>
	 * In entrambi i casi, viene scritto un log via logPersister.
	 * 
	 * @throws InterruptedException 
	 */
	@FXML
	private void verificaCredenzialeHandler(ActionEvent event) throws InterruptedException {
		if (loginController.verificaCredenziali(passwordField.getText())) {
			initHomePrincipale();
		} else {
			passwordField.clear();
			AlertPanel.saysInfo("PASSWORD ERRATA", "Riprovare");
			TimeUnit.SECONDS.sleep(3);
		}
	}
	
	
	/**
	 * Metodo che viene invocato quando viene cliccato il pulsante ENTER.
	 * 
	 * @throws InterruptedException
	 */
	@FXML
	void verificaCredenzialeENTER(KeyEvent event) throws InterruptedException {
		if (event.getCode() == KeyCode.ENTER) {
			verificaCredenzialeHandler(new ActionEvent());
		}
	}

	/**
	 * 1) Load di HomePrincipale <br>
	 * 2) Creazione della Scene di HomePrincipale <br>
	 * 3) Set della Scene nel primaryStage <br>
	 * 4) Connessione con database <br>
	 * 5) Settaggio in chiusura del primaryStage: avvio backup <br>
	 * 6) Settaggio in chiusura del primaryStage: chiusura del database
	 */
	private void initHomePrincipale() {
		
		/*
		 * Load del homePrincipale.
		 * Set del primaryStage.
		 */
		AnchorPane homePrincipale = null;
		try {
			homePrincipale = FXMLLoader.<AnchorPane>load(getClass().getResource(homePrincipaleURL), null, null, e -> {
				return new HomePrincipale();
			});
			
			HomePrincipale.getInstance().setPrimaryStage(primaryStage);
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
			
			/*
			 * Avvio Backup
			 */
			try {
				BackupController.getInstance().avviaBackup();
			} catch(SQLException e) {
				AlertPanel.saysError("ERRORE: in fase di backup del database", e);
			}
			
			/*
			 * Chiusura database
			 */
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
