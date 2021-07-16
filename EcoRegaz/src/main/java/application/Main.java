package main.java.application;

import java.awt.Toolkit;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.java.persisters.MainDB;
import main.java.views.interfacciaHome.HomePrincipale;
import main.java.views.interfacciaLogin.HomeLogin;

public class Main extends Application {

	/**
	 * 1.0.0.0: Prima versione
	 */
	public final static String VERSION = "1.0.0.0";

	public final static String homePrincipaleURL = "/view/interfacciaHome/HomePrincipale.fxml";
	public final static String homeLoginURL = "/view/interfacciaLogin/HomeLogin.fxml";
	
	public final static String CSSbootstrap3URL = "";

	public final static Integer MIN_WIDTH = 720;
	public final static Integer MIN_HIGHT = 480;

	@Override
	public void start(Stage primaryStage) {

		/*
		 * Inizializzazione DB
		 */
		try {
			MainDB.openDatabase();
		} catch (Exception e) {
			AlertPanel.saysError("ERRORE: nell'apertura della connessione col database", e);
		}
		
		/*
		 * Load di HomeLogin con la chiamata al costruttore personalizzata.
		 */
		AnchorPane homeLogin = null;
		try {
			homeLogin = FXMLLoader.<AnchorPane>load(getClass().getResource(homeLoginURL), null, null, e -> {
				return new HomeLogin();
			});
		} catch (IOException e) {
			AlertPanel.saysError("ERRORE: nella load di HomeLogin", e);
		}
		
		
		

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
		 * Creazione della Scene del login e inserimento del CSS
		 */
		Scene loginScene = new Scene(homeLogin);
		loginScene.getStylesheets().add(getClass().getResource(CSSbootstrap3URL).toExternalForm());
		

		/*
		 * Impostazioni dello stage primario (primaryStage).
		 * Tale stage appare al centro dello schermo, non massimizzato e con dimensione massima pari alla dimensione massima dello schermo dell'user.
		 */
		primaryStage.setTitle("ERMS");

		primaryStage.getIcons().add(new Image(?));

		primaryStage.setMinWidth(MIN_WIDTH);
		primaryStage.setMinHeight(MIN_HIGHT);
		primaryStage.setMaxHeight(Toolkit.getDefaultToolkit().getScreenSize().getHeight());
		primaryStage.setMaxWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth());
		primaryStage.setMaximized(false);

		primaryStage.centerOnScreen();

		primaryStage.setScene(loginScene);

		// Alla chiusura del primaryStage, viene invocato il metodo di chiusura del database
		primaryStage.setOnCloseRequest(event -> {
			try {
				MainDB.closeDatabase();
			} catch (SQLException e) {
				AlertPanel.saysError("ERRORE: in chiusura del database", e);
			}
		});

		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

}
