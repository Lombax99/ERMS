package main.java.persisters.iscritti;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import main.java.controllers.interventi.gestioneIscritti.FiltroIscritti;
import main.java.models.iscritto.Iscritto;
import main.java.persisters.MainDB;

/**
 * Classe che implementa i metodi per l'interazione con DB circa gli iscritti
 */
public class PersisterIscritti implements IPersisterIscritti {

	/**
	 * Variabile che identifica la connessione con il DB.
	 */
	Connection connection;

	/**
	 * Statement unico per tutta la classe, al fine di mantenere vivi i ResultSet
	 */
	Statement statement;

	/**
	 * Stringa che contiene la creazione della tabella
	 */
	private static String createTable = "CREATE TABLE ISCRITTI_TABLE" + "(" + "COD_FISC VARCHAR(20) NOT NULL PRIMARY KEY," + "NOME VARCHAR(20) NOT NULL," + "COGNOME VARCHAR(25) NOT NULL" + ")";

	/*
	 * Pattern Singleton
	 */
	private static PersisterIscritti instance = null;

	/**
	 * Pattern Singleton. <br>
	 * L'eccezione viene lanciata perché nel costruttore qualcosa può andare storto.
	 * 
	 * @throws SQLException
	 */
	public static PersisterIscritti getInstance() throws SQLException {
		if (instance == null) {
			instance = new PersisterIscritti();
		}
		return instance;
	}

	/**
	 * Costruttore: <br>
	 * 1) Preleva la connessione da MainDB <br>
	 * 2) Crea lo Statement <br>
	 * 3) Verifica l'esistenza della tabella <br>
	 * 
	 * @throws SQLException
	 */
	private PersisterIscritti() throws SQLException {

		/*
		 * Prelevo la connessione da MainDB, il quale sicuramente è già stato inizializzato
		 */
		connection = MainDB.connection;

		if (connection == null) {
			throw new SQLException("Costruttore PersisterIscritti: Connessione con DB non stabilita");
		}

		/*
		 * Creazione dello statement che permette di eseguire query SQL
		 */
		statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

		/*
		 * Verifica dell'esistenza della tabella degli Iscritti.
		 * Se non esiste, viene creata
		 */
		if (!MainDB.tableExists(connection, "UPDATE ISCRITTI_TABLE SET NOME = 'test' WHERE COD_FISC = 'test' ")) {
			statement.execute(createTable);
		}

	}

	/**
	 * Metodo che restituisce un ResultSet in base al FiltroIscritti. <br>
	 * Costruisce man mano la Stringa che costituisce la query SELECT. <br>
	 * La prima Stringa è un "WHERE ..." (che seleziona tutto) e le stringhe successive sono un "AND ..." <br>
	 * oppure una stringa vuota "", in base al FiltroIscritti. <br>
	 * 
	 * @throws SQLException
	 */
	@Override
	public ResultSet visualizzaIscritti(FiltroIscritti filtro) throws SQLException {

		// Stringa iniziale: è l'unico where, tutti gli altri sono o AND o stringa vuota
		String initial_String = "SELECT * FROM ISCRITTI_TABLE WHERE COD_FISC != 'test'";

		// codice fiscale: AND COD_FISC = 'VNGRCR00A26A944A'
		String codFisc_String = "";
		if (filtro.getCodFisc().isPresent()) {
			codFisc_String = "AND COD_FISC = '" + filtro.getCodFisc().get() + "'";
		}

		// nome: AND NOME LIKE '%Luca%'
		String nome_String = "";
		if (filtro.getNome().isPresent()) {
			nome_String = "AND NOME LIKE '%" + filtro.getNome().get() + "%'";
		}

		// nome: AND COGNOME LIKE '%Raparrini%'
		String cognome_String = "";
		if (filtro.getCognome().isPresent()) {
			cognome_String = "AND COGNOME LIKE '%" + filtro.getCognome().get() + "%'";
		}

		String selectString = initial_String + " " + codFisc_String + " " + nome_String + " " + cognome_String;

		// esecuzione effettiva della SELECT
		ResultSet resultSet = statement.executeQuery(selectString);

		return resultSet;
	}

	/**
	 * Metodo che aggiunge un Iscritto al DB usando direttamente una query INSERT
	 * 
	 * @throws SQLException
	 */
	@Override
	public boolean aggiuntaIscritto(Iscritto iscritto) throws SQLException {

		PreparedStatement psInsert = connection.prepareStatement("INSERT INTO ISCRITTI_TABLE (COD_FISC, NOME, COGNOME) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

		// setto tutti i "?"
		psInsert.setString(1, iscritto.getCodFisc());
		psInsert.setString(2, iscritto.getNome());
		psInsert.setString(3, iscritto.getCognome());

		// esecuzione effettiva dell'INSERT
		if (psInsert.executeUpdate() != 1) {
			throw new SQLException("ERRORE: l'esecuzione di psInsert.executeUpdate() non è andata a buon fine");
		}

		// rilascio delle risorse
		psInsert.close();

		return true;
	}

	/**
	 * Metodo che rimuove un Iscritto al DB usando direttamente una query DELETE
	 * 
	 * @throws SQLException
	 */
	@Override
	public boolean rimuoviIscritto(String codFisc) throws SQLException {

		PreparedStatement psDelete = connection.prepareStatement("DELETE FROM ISCRITTI_TABLE WHERE COD_FISC = ?");

		// setto tutti i "?"
		psDelete.setString(1, codFisc);

		// esecuzione effettiva del DELETE
		if (psDelete.executeUpdate() != 1) {
			throw new SQLException("ERRORE: l'esecuzione di psDelete.executeUpdate() non è andata a buon fine");
		}

		// rilascio delle risorse
		psDelete.close();

		return true;
	}

	/**
	 * Metodo che modifica un Iscritto al DB usando direttamente una query UPDATE. <br>
	 * Va a sovrascrivere tutti i paramentri.
	 * 
	 * @throws SQLException
	 */
	@Override
	public boolean modificaIscritto(Iscritto iscritto) throws SQLException {

		PreparedStatement psUpdate = connection.prepareStatement("UPDATE ISCRITTI_TABLE SET NOME = ?, COGNOME = ? WHERE COD_FISC = ?");

		// setto tutti i "?"
		psUpdate.setString(1, iscritto.getNome());
		psUpdate.setString(2, iscritto.getCognome());
		psUpdate.setString(3, iscritto.getCodFisc());
		
		// esecuzione effettiva dell'UPDATE
		if (psUpdate.executeUpdate() != 1) {
			throw new SQLException("ERRORE: l'esecuzione di psUpdate.executeUpdate() non è andata a buon fine");
		}
		
		// rilascio delle risorse
		psUpdate.close();
				
		return true;
	}

}
