package main.java.persisters;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe che contiene le funzioni di gestione del DB. <br>
 * Fornisce il metodo di apertura e chiusura del DB. <br>
 * Fornisce la variabile di connessione col DB. <br>
 * E' tutto statico perché non c'è bisogno di creare un'istanza di tale classe.
 */
public class MainDB {

	/**
	 * Variabile che identifica la connessione con il DB.
	 */
	public static Connection connection;

	
	/**
	 * Metodo che apre la connessione con database, e inizializza la variabile "connection". <br>
	 * 
	 * @throws SQLException
	 */
	public static void openDatabase() throws SQLException {

		/*
		 * Accensione del database
		 */
		final String dbName = "ERMS_DB";
		connection = DriverManager.getConnection("jdbc:derby:" + dbName + ";create=true");

		System.out.println("Connected to database " + dbName);

		/*
		 * We want to control transactions manually. Autocommit is on by default in JDBC.
		 */
		connection.setAutoCommit(true);
	}

	
	/**
	 * Metodo che chiude la connessione e spegne il database. <br>
	 * Viene invocato quando si chiude lo stage principale. <br>
	 * Avviene tutto correttamente se viene lanciata una precisa eccezione, <br>
	 * che se non è quella giusta il metodo lancia a sua volta una SQLException.
	 * 
	 * @throws SQLException
	 */
	public static void closeDatabase() throws SQLException {

		/*
		 * Rende permanenti nel DB tutti i cambiamenti
		 * Dovrebbe essere usato solo se AutoCommit è disabilitato.
		 * Qui viene usato perché è subito prima della close (vedi doc della close)
		 */
		connection.commit();

		/*
		 *  Rilascio delle risorse.
		 *  Vengono chiusi tutti gli Statement e PreparedStatement dei singoli persister (e di conseguenza tutti i ResultSet)
		 */
		connection.close();

		/*
		 * In embedded mode, an application should shut down Derby.
		 * Shutdown throws the XJ015 exception to confirm success.
		 */
		try {
			DriverManager.getConnection("jdbc:derby:;shutdown=true");
		} catch (SQLException se) {

			if (se.getSQLState().equals("XJ015") && se.getErrorCode() == 50000) {

				// success
				System.out.println("Derby shut down normally"); // TODO print inutile
			} else {

				// failure
				throw se;
			}
		}

	}

}
