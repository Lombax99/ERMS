package main.java.views;

import java.io.IOException;
import java.sql.SQLException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.java.application.AlertPanel;
import main.java.controllers.backup.BackupController;
import main.java.persisters.MainDB;
import main.java.views.interfacciaAnalisi.HomeAnalisi;
import main.java.views.interfacciaDepositi.HomeDepositi;
import main.java.views.interfacciaHome.HomePrincipale;
import main.java.views.interfacciaInterventi.HomeInterventi;
import main.java.views.interfacciaIscritti.HomeIscritti;
import main.java.views.interfacciaLogin.HomeLogin;
import main.java.views.interfacciaRendicontazione.HomeRendicontazione;
import main.java.views.interfacciaStrumenti.HomeStrumenti;

public class GestoreInterfacce {

	/**
	 * Pattern Singleton. <br>
	 * E' singleton perché c'è bisogno che questa classe sopravviva per l'intera durata del programma.
	 */
	public static GestoreInterfacce getInstance() {
		if (instance == null) {
			instance = new GestoreInterfacce();
		}
		return instance;
	}


	private static GestoreInterfacce instance = null;

	/**
	 * E' proprio la stessa istanza dello stage principale, quello di base.
	 */
	Stage primaryStage;


	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}


	/*
	 * Costanti utili
	 */
	public final static String CSSbootstrap3URL = "/main/resources/bootstrap3.css";
	public final static String homePrincipaleURL = "/main/java/views/interfacciaHome/HomePrincipale.fxml";
	public final static String homeLoginURL = "/main/java/views/interfacciaLogin/HomeLogin.fxml";
	public final static String homeAnalisiURL = "/main/java/views/interfacciaAnalisi/HomeAnalisi.fxml";
	public final static String homeDepositiURL = "/main/java/views/interfacciaDeposito/HomeDepositi.fxml";
	public final static String homeInterventiURL = "/main/java/views/interfacciaInterventi/HomeInterventi.fxml";
	public final static String homeIscrittiURL = "/main/java/views/interfacciaIscritti/HomeIscritti.fxml";
	public final static String homeRendicontazioneURL = "/main/java/views/interfacciaRendicontazione/HomeRendicontazione.fxml";
	public final static String homeStrumentiURL = "/main/java/views/interfacciaStrumenti/HomeStrumenti.fxml";

	/*
	 * ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	 * ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	 * INIZIALIZZAZIONE HOME LOGIN 
	 */


	/**
	 * 1) Load di HomeLogin con la chiamata al costruttore personalizzata <br>
	 * 2) Creazione della Scene del login <br>
	 * 3) Set della loginScene e visualizzazione del primaryStage <br>
	 */
	public void initHomeLogin() {
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
		 * Creazione della Scene del login
		 */
		Scene loginScene = new Scene(homeLogin);
		// loginScene.getStylesheets().add(getClass().getResource(CSSbootstrap3URL).toExternalForm()); TODO rimuovere?

		/*
		 * Set della loginScene e visualizzazione del primaryStage
		 */
		primaryStage.setScene(loginScene);

	}


	/*
	 * ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	 * ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	 * INIZIALIZZAZIONE HOME PRINCIPALE 
	 */


	/**
	 * 1) Load di HomePrincipale <br>
	 * 2) Creazione della Scene di HomePrincipale <br>
	 * 3) Set della Scene nel primaryStage <br>
	 * 4) Connessione con database <br>
	 * 5) Settaggio in chiusura del primaryStage: avvio backup <br>
	 * 6) Settaggio in chiusura del primaryStage: chiusura del database
	 */
	public void initHomePrincipale() {

		/*
		 * Load del homePrincipale.
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
		 * Creazione della Scene di homePrincipale
		 */
		Scene homePrincipaleScene = new Scene(homePrincipale);
		// homePrincipaleScene.getStylesheets().add(getClass().getResource(CSSbootstrap3URL).toExternalForm()); TODO rimuovere?

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
			} catch (SQLException e) {
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



	/*
	 * ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	 * ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	 * INIZIALIZZAZIONE HOME INTERVENTI
	 */


	/**
	 * Variabile globale
	 */
	Scene homeInterventiScene;


	/**
	 * Alla prima chiamata della funzione, la scena è null. <br>
	 * Alle successive chiamate, viene usata la stessa istanza.
	 */
	public void initHomeInterventi() {
		if (homeInterventiScene == null) {

			/*
			 * Load di homeInterventi con la chiamata al costruttore personalizzata.
			 */
			AnchorPane homeInterventi = null;
			try {
				homeInterventi = FXMLLoader.<AnchorPane>load(getClass().getResource(homeInterventiURL), null, null, e -> {
					return new HomeInterventi();
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


	/*
	 * ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	 * ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	 * INIZIALIZZAZIONE HOME RENDICONTAZIONE
	 */

	/**
	 * Variabile globale
	 */
	Scene homeRendicontazioneScene;
	
	/**
	 * Alla prima chiamata della funzione, la scena è null. <br>
	 * Alle successive chiamate, viene usata la stessa istanza.
	 */
	public void initHomeRendicontazione() {
		
		if (homeRendicontazioneScene == null) {

			/*
			 * Load di homeRendicontazione con la chiamata al costruttore personalizzata.
			 */
			AnchorPane homeRendicontazione = null;
			try {
				homeRendicontazione = FXMLLoader.<AnchorPane>load(getClass().getResource(homeRendicontazioneURL), null, null, e -> {
					return new HomeRendicontazione();
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
	


	/*
	 * ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	 * ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	 * INIZIALIZZAZIONE HOME ANALISI
	 */
	/**
	 * Variabile globale
	 */
	Scene homeAnalisiScene;
	
	/**
	 * Alla prima chiamata della funzione, la scena è null. <br>
	 * Alle successive chiamate, viene usata la stessa istanza.
	 */
	public void initHomeAnalisi() {
		
		if (homeAnalisiScene == null) {

			/*
			 * Load di homeAnalisi con la chiamata al costruttore personalizzata.
			 */
			AnchorPane homeAnalisi = null;
			try {
				homeAnalisi = FXMLLoader.<AnchorPane>load(getClass().getResource(homeAnalisiURL), null, null, e -> {
					return new HomeAnalisi();
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


	/*
	 * ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	 * ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	 * INIZIALIZZAZIONE HOME STRUMENTI
	 */
	/**
	 * Variabile globale
	 */
	Scene homeStrumentiScene;
	
	/**
	 * Alla prima chiamata della funzione, la scena è null. <br>
	 * Alle successive chiamate, viene usata la stessa istanza.
	 */
	public void initHomeStrumenti() {
		
		if (homeStrumentiScene == null) {

			/*
			 * Load di homeStrumenti con la chiamata al costruttore personalizzata.
			 */
			AnchorPane homeStrumenti = null;
			try {
				homeStrumenti = FXMLLoader.<AnchorPane>load(getClass().getResource(homeStrumentiURL), null, null, e -> {
					return new HomeStrumenti();
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



	/*
	 * ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	 * ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	 * INIZIALIZZAZIONE HOME DEPOSITI
	 */
	/**
	 * Variabile globale
	 */
	Scene homeDepositiScene;
	
	/**
	 * Alla prima chiamata della funzione, la scena è null. <br>
	 * Alle successive chiamate, viene usata la stessa istanza.
	 */
	public void initHomeDepositi() {
		
		if (homeDepositiScene == null) {

			/*
			 * Load di homeDepositi con la chiamata al costruttore personalizzata.
			 */
			AnchorPane homeDepositi = null;
			try {
				homeDepositi = FXMLLoader.<AnchorPane>load(getClass().getResource(homeDepositiURL), null, null, e -> {
					return new HomeDepositi();
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



	/*
	 * ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	 * ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	 * INIZIALIZZAZIONE HOME ISCRITTI
	 */
	/**
	 * Variabile globale
	 */
	Scene homeIscrittiScene;
	
	/**
	 * Alla prima chiamata della funzione, la scena è null. <br>
	 * Alle successive chiamate, viene usata la stessa istanza.
	 */
	public void initHomeIscritti() {
		
		if (homeIscrittiScene == null) {

			/*
			 * Load di homeIscritti con la chiamata al costruttore personalizzata.
			 */
			AnchorPane homeIscritti = null;
			try {
				homeIscritti = FXMLLoader.<AnchorPane>load(getClass().getResource(homeIscrittiURL), null, null, e -> {
					return new HomeIscritti();
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


}
