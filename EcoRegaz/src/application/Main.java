package application;

import java.awt.Toolkit;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import persisters.MainDB;

public class Main extends Application {

	/**
	 * 1.0.0.0: Prima versione
	 */
	public final static String VERSION = "1.0.0.0";

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
		 * Load del homePrincipale con la chiamata al costruttore personalizzata.
		 * Lo homePrincipale è un BorderPane con al centro un AnchorPane con tutti i nodi.
		 * Vengono caricati anche lo screen di aggiungi ricetta e di vedi ricetta.
		 */
		BorderPane homePrincipale = null;
		try {
			homePrincipale = FXMLLoader.<BorderPane>load(getClass().getResource(homePrincipaleURL), null, null, e -> {
				return new homePrincipaleController(ricettePersister);
			});
		} catch (IOException e) {
			AlertPanel.says(AlertType.ERROR, "ERRORE: nella load di homePrincipale o screen_AggiungiRicetta o screen_VediRicette", Optional.of(e));
		}

		/*
		 * Creazione della Scene principale con all'interno lo homePrincipale, ossia tutta la grafica
		 */
		Scene primaryScene = new Scene(homePrincipale);

		/*
		 * Inserimento del CSS all'interno di primaryScene
		 */
		if (CSS_ABILITATO) {
			primaryScene.getStylesheets().add(getClass().getResource(CSSbootstrap3URL).toExternalForm());
		}

		/*
		 * Impostazioni dello stage primario (primaryStage).
		 * Tale stage appare al centro dello schermo, non massimizzato e con dimensione massima pari alla dimensione massima dello schermo dell'user.
		 */
		primaryStage.setTitle("Ricettario");

		primaryStage.getIcons().add(new Image("images/boil.png"));

		primaryStage.setMinWidth(MIN_WIDTH);
		primaryStage.setMinHeight(MIN_HIGHT);
		primaryStage.setMaxHeight(Toolkit.getDefaultToolkit().getScreenSize().getHeight());
		primaryStage.setMaxWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth());
		primaryStage.setMaximized(false);

		primaryStage.centerOnScreen();

		primaryStage.setScene(primaryScene);

		// Alla chiusura del primaryStage, viene invocato il metodo di chiusura del database
		primaryStage.setOnCloseRequest(event -> {
			try {
				ricettePersister.closeDatabase();
			} catch (SQLException e1) {
				AlertPanel.says(AlertType.ERROR, "ERRORE: in chiusura del database", Optional.of(e1));
			}
		});

		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

}
