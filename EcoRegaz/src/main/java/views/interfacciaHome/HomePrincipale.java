package main.java.views.interfacciaHome;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.java.application.AlertPanel;
import main.java.views.interfacciaAnalisi.HomeAnalisi;
import main.java.views.interfacciaDepositi.HomeDepositi;
import main.java.views.interfacciaInterventi.HomeInterventi;
import main.java.views.interfacciaIscritti.HomeIscritti;
import main.java.views.interfacciaRendicontazione.HomeRendicontazione;
import main.java.views.interfacciaStrumenti.HomeStrumenti;

public class HomePrincipale {

	public final static String homeAnalisiURL = "/main/java/views/interfacciaAnalisi/HomeAnalisi.fxml";

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
	 * Pattern Singleton <br>
	 * Perché ogni volta che si torna indietro da un'altra view, l'istanza di
	 * HomePrincipale deve essere questa.
	 */
	public static HomePrincipale getInstance() {
		if (instance == null) {
			instance = new HomePrincipale();
		}
		return instance;
	}

	private static HomePrincipale instance = null;

	/**
	 * Set del primaryStage interno. <br>
	 * Questo metodo serve perché HomePrincipale è Singleton
	 */
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	/**
	 * Metodo handler del bottone Interventi. <br>
	 * Se è la prima volta che viene cliccato sul bottone, homeInterventiScene è
	 * null. <br>
	 * Viene quindi inizializzata una nuova istanza di HomeInterventi. <br>
	 * In ogni caso viene settata la homeInterventiScene.
	 */
	@FXML
	private void openInterventiHandler(ActionEvent event) {

		if (homeInterventiScene == null) {

			/*
			 * Load di homeInterventi con la chiamata al costruttore personalizzata.
			 */
			AnchorPane homeInterventi = null;
			try {
				homeInterventi = FXMLLoader.<AnchorPane>load(getClass().getResource(homeInterventiURL), null, null,
						e -> {
							return new HomeInterventi(primaryStage);
						});
			} catch (IOException e) {
				AlertPanel.saysError("ERRORE: nella load di HomeInterventi", e);
			}

			/*
			 * Creazione della Scene di homeInterventi e inserimento del CSS
			 */
			homeInterventiScene = new Scene(homeInterventi);

		}

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

		if (homeRendicontazioneScene == null) {

			/*
			 * Load di homeRendicontazione con la chiamata al costruttore personalizzata.
			 */
			AnchorPane homeRendicontazione = null;
			try {
				homeRendicontazione = FXMLLoader.<AnchorPane>load(getClass().getResource(homeRendicontazioneURL), null,
						null, e -> {
							return new HomeRendicontazione(primaryStage);
						});
			} catch (IOException e) {
				AlertPanel.saysError("ERRORE: nella load di HomeRendicontaizone", e);
			}

			/*
			 * Creazione della Scene di homeRendicontazione e inserimento del CSS
			 */
			homeRendicontazioneScene = new Scene(homeRendicontazione);

		}

		/*
		 * Set della homeRendicontazioneScene nel primaryStage
		 */
		primaryStage.setScene(homeRendicontazioneScene);

	}

	/**
	 * Metodo handler del bottone AreeVerdi. <br>
	 */
	@FXML
	private void openAnalisiAreeVerdiHandler(ActionEvent event) {

		if (homeAnalisiScene == null) {

			/*
			 * Load di homeAnalisi con la chiamata al costruttore personalizzata.
			 */
			AnchorPane homeAnalisi = null;
			try {
				homeAnalisi = FXMLLoader.<AnchorPane>load(getClass().getResource(homeAnalisiURL), null, null, e -> {
					return new HomeAnalisi(primaryStage);
				});
			} catch (IOException e) {
				AlertPanel.saysError("ERRORE: nella load di HomeAnalisi", e);
			}

			/*
			 * Creazione della Scene di homeAnalisi e inserimento del CSS
			 */
			homeAnalisiScene = new Scene(homeAnalisi);

		}

		/*
		 * Set della homeAnalisiScene nel primaryStage
		 */
		primaryStage.setScene(homeAnalisiScene);

	}

	/**
	 * Metodo handler del bottone Strumenti. <br>
	 */
	@FXML
	private void openStrumentiHandler(ActionEvent event) {

		if (homeStrumentiScene == null) {

			/*
			 * Load di homeStrumenti con la chiamata al costruttore personalizzata.
			 */
			AnchorPane homeStrumenti = null;
			try {
				homeStrumenti = FXMLLoader.<AnchorPane>load(getClass().getResource(homeStrumentiURL), null, null, e -> {
					return new HomeStrumenti(primaryStage);
				});
			} catch (IOException e) {
				AlertPanel.saysError("ERRORE: nella load di HomeStrumenti", e);
			}

			/*
			 * Creazione della Scene di homeStrumenti e inserimento del CSS
			 */
			homeStrumentiScene = new Scene(homeStrumenti);

		}

		/*
		 * Set della homeStrumentiScene nel primaryStage
		 */
		primaryStage.setScene(homeStrumentiScene);

	}

	/**
	 * Metodo handler del bottone Depositi. <br>
	 */
	@FXML
	private void openDepositiHandler(ActionEvent event) {

		if (homeDepositiScene == null) {

			/*
			 * Load di homeDepositi con la chiamata al costruttore personalizzata.
			 */
			AnchorPane homeDepositi = null;
			try {
				homeDepositi = FXMLLoader.<AnchorPane>load(getClass().getResource(homeDepositiURL), null, null, e -> {
					return new HomeDepositi(primaryStage);
				});
			} catch (IOException e) {
				AlertPanel.saysError("ERRORE: nella load di HomeDepositi", e);
			}

			/*
			 * Creazione della Scene di homeStrumenti e inserimento del CSS
			 */
			homeDepositiScene = new Scene(homeDepositi);

		}

		/*
		 * Set della homeDepositiScene nel primaryStage
		 */
		primaryStage.setScene(homeDepositiScene);
	}

	/**
	 * Metodo handler del bottone Iscritti. <br>
	 */
	@FXML
	private void openIscrittiHandler(ActionEvent event) {

		if (homeIscrittiScene == null) {

			/*
			 * Load di homeIscritti con la chiamata al costruttore personalizzata.
			 */
			AnchorPane homeIscritti = null;
			try {
				homeIscritti = FXMLLoader.<AnchorPane>load(getClass().getResource(homeIscrittiURL), null, null, e -> {
					return new HomeIscritti(primaryStage);
				});
			} catch (IOException e) {
				AlertPanel.saysError("ERRORE: nella load di HomeIscritti", e);
			}

			/*
			 * Creazione della Scene di homeIscritti e inserimento del CSS
			 */
			homeIscrittiScene = new Scene(homeIscritti);

		}

		/*
		 * Set della homeIscrittiScene nel primaryStage
		 */
		primaryStage.setScene(homeIscrittiScene);

	}

	Scene homeInterventiScene;
	Scene homeRendicontazioneScene;
	Scene homeAnalisiScene;
	Scene homeStrumentiScene;
	Scene homeDepositiScene;
	Scene homeIscrittiScene;

}
