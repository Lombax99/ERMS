package main.java.persisters;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * Di base si fa una select prima di una visualizza. Si può usare il result set per inserire e eliminare e modificare una riga.
 * Bisogna metterlo scrollable e updatable (vedi doc del ResultSet).
 * Tuttavia ogni ResultSet muore quando viene chiuso lo Statement da cui proviene, oppure può essere chiuso manualmente.
 * Quindi è importante che lo Statement rimanga vivo al di fuori del metodo, quindi che sia globale.
 * 
 * Tutte le volte che si vuole modificare un ResultSet, questo deve essere chiuso (così viene fatto un autoCommit, se abilitato).
 * Per visualizzare i dati modificati, bisogna effettuare una seconda select e visualizzare i dati dal nuovo ResultSet.
 * 
 * Nel caso io voglia visualizzare gli iscritti e poi andare da un'altra parte, devo fare la select, caricare in grafica i dati dal ResultSet,
 * 		e poi posso chiudere il resultSet se esco dalla ViewIscritti. In questo modo rimane caricata in memoria solo la grafica.
 * Nel caso in cui poi voglia tornare a vedere gli iscritti e modificarli, poiché non c'è più il ResultSet, posso o chiamare la funzione che direttamente 
 * 		fa UPDATE/INSERT/DELETE al DB e poi chiamare la select per vedere il risultato, oppure chiamare la select per modificare il ResultSet e richiamarla per vedere il risultato.
 * Sinceramente a me piace il secondo caso, perché nel primo bisogna chiamare il metodo per ogni singola modifica, mentre nel secondo solo una volta.
 * 
 * In generale quindi, ogni volta che si esce da una view, si chiude il ResultSet. Quindi è possibile in questo modo usare uno Statement per ogni Persister,
 * 		anche per motivi di estendibilità.
 */



/**
 * Classe che contiene le funzioni di gestione del DB. <br>
 * Fornisce il metodo di apertura e chiusura del DB. <br>
 * Fornisce la variabile di connessione col DB. <br>
 * Fornisce il metodo per verificare l'esistenza di una tabella. <br>
 * E' tutto statico perché non si deve creare un'istanza di tale classe.
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
		connection.setAutoCommit(false);
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

	
	/**
	 * Controlla l'esistenza della tabella. Viene eseguita una UPDATE fittizzia (nessuna riga viene selezionata quindi la update non cambia nulla)
	 * 
	 * @return true se la tabella esiste, false altrimenti
	 * @throws SQLException
	 */
	public static boolean tableExists(Connection connessione, String updateQueryFittizzia) throws SQLException {

		try {
			connessione.createStatement().execute(updateQueryFittizzia);
		} catch (SQLException sqle) {

			/*
			 *  Se lo stato è "42X05" la tabella NON ESISTE
			 */
			if (sqle.getSQLState().equals("42X05")) {

				return false;
			}

			/*
			 *  Se lo stato è "42X14" o "42821", la tabella è definita incorrettamente
			 */
			else if (sqle.getSQLState().equals("42X14") || sqle.getSQLState().equals("42821")) {

				throw new SQLException("Incorrect table definition. Drop table and rerun this program", sqle);
			}

			/*
			 * altrimenti...
			 */
			else {
				throw new SQLException("Unhandled SQLException", sqle);
			}
		}

		/*
		 * Se nessuna eccezione viene lanciata, la tabella esiste già.
		 */
		return true;
	}

}
