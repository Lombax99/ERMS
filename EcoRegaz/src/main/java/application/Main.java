package main.java.application;

import java.awt.Toolkit;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.java.views.interfacciaLogin.HomeLogin;

public class Main extends Application {

	/**
	 * 1.0.0.0: Prima versione
	 */
	public final static String VERSION = "1.0.0.0";

	public final static String homeLoginURL = "/main/java/views/interfacciaLogin/HomeLogin.fxml";

	public final static String CSSbootstrap3URL = "/main/resources/bootstrap3.css";

	public final static Integer MIN_WIDTH = 720;
	public final static Integer MIN_HIGHT = 480;

	@Override
	public void start(Stage primaryStage) {

		/*
		 * Impostazioni dello stage primario (primaryStage).
		 * Tale stage appare al centro dello schermo, non massimizzato e con dimensione massima pari alla dimensione massima dello schermo dell'user.
		 */
		primaryStage.setTitle("ERMS");

		// TODO: aggiungere l'icona del programma
		// primaryStage.getIcons().add(new Image("path"));

		primaryStage.setMinWidth(MIN_WIDTH);
		primaryStage.setMinHeight(MIN_HIGHT);
		primaryStage.setMaxHeight(Toolkit.getDefaultToolkit().getScreenSize().getHeight());
		primaryStage.setMaxWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth());
		primaryStage.setMaximized(false);

		primaryStage.centerOnScreen();

		/*
		 * Load di HomeLogin con la chiamata al costruttore personalizzata.
		 */
		AnchorPane homeLogin = null;
		try {
			homeLogin = FXMLLoader.<AnchorPane>load(getClass().getResource(homeLoginURL), null, null, e -> {
				return new HomeLogin(primaryStage);
			});
		} catch (IOException e) {
			AlertPanel.saysError("ERRORE: nella load di HomeLogin", e);
		}

		/*
		 * Creazione della Scene del login e inserimento del CSS
		 */
		Scene loginScene = new Scene(homeLogin);
		loginScene.getStylesheets().add(getClass().getResource(CSSbootstrap3URL).toExternalForm());

		/*
		 * Set della loginScene e visualizzazione del primaryStage
		 */
		primaryStage.setScene(loginScene);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

}
