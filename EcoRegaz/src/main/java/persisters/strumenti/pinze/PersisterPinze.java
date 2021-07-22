package main.java.persisters.strumenti.pinze;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import main.java.controllers.strumenti.pinze.FiltroPinze;
import main.java.models.strumenti.pinza.Pinza;
import main.java.persisters.MainDB;

/**
 * Classe che implementa i metodi per l'interazione con DB circa le pinze
 *
 */
public class PersisterPinze implements IPersisterPinze {

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
	private static String createTable = "CREATE TABLE PINZE_TABLE (ID_STRUMENTO INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY, "
				+ "APPARTENENZA VARCHAR(20) NOT NULL CHECK(APPARTENENZA = 'PROPRIA' OR APPARTENENZA = 'PRESTATA'), "
				+ "CONDIZIONE VARCHAR(20) NOT NULL CHECK(CONDIZIONE = 'FUNZIONANTE' OR CONDIZIONE = 'NONFUNZIONANTE'), " + "ID_DEPOSITO INTEGER NOT NULL REFERENCES DEPOSITI_TABLE(ID_DEPOSITO))";

	/*
	 * Pattern Singleton
	 */
	private static PersisterPinze instance = null;

	/**
	 * Pattern Singleton. <br>
	 * L'eccezione viene lanciata perché nel costruttore qualcosa può andare storto.
	 * 
	 * @throws SQLException
	 */
	public static PersisterPinze getInstance() throws SQLException {
		if (instance == null) {
			instance = new PersisterPinze();
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
	private PersisterPinze() throws SQLException {

		/*
		 * Prelevo la connessione da MainDB, il quale sicuramente è già stato inizializzato
		 */
		connection = MainDB.connection;

		if (connection == null) {
			throw new SQLException("Costruttore PersisterPinze: Connessione con DB non stabilita");
		}

		/*
		 * Creazione dello statement che permette di eseguire query SQL
		 */
		statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

		/*
		 * Verifica dell'esistenza della tabella delle Pinze.
		 * Se non esiste, viene creata
		 */
		if (!MainDB.tableExists(connection, "UPDATE PINZE_TABLE SET APPARTENENZA = 'test' WHERE ID_STRUMENTO = -100 ")) {
			statement.execute(createTable);
		}

	}

	/**
	 * Metodo che restituisce un ResultSet in base al FiltroPinze. <br>
	 * Costruisce man mano la Stringa che costituisce la query SELECT. <br>
	 * La prima Stringa è un "WHERE ..." (che seleziona tutto) e le stringhe successive sono un "AND ..." <br>
	 * oppure una stringa vuota "", in base al FiltroPinze. <br>
	 * 
	 * @throws SQLException
	 */
	@Override
	public ResultSet elencoPinze(FiltroPinze filtro) throws SQLException {
		// Stringa iniziale: è l'unico where, tutti gli altri sono o AND o stringa vuota
		String initial_String = "SELECT * FROM PINZE_TABLE WHERE ID_STRUMENTO != -100 ";

		// id_pinza: AND ID_STRUMENTO = 13
		String idStrumento_String = "";
		if (filtro.getId_Pinza().isPresent()) {
			idStrumento_String = "AND ID_STRUMENTO = " + filtro.getId_Pinza().get();
		}

		// appartenenza: AND APPARTENENZA = PROPRIA
		String appartnenza_String = "";
		if (filtro.getAppartenenza().isPresent()) {
			appartnenza_String = "AND APPARTENENZA = " + filtro.getAppartenenza().get().toString();
		}

		// condizione: AND CONDIZIONE = FUNZIONANTE
		String condizione_String = "";
		if (filtro.getCondizione().isPresent()) {
			condizione_String = "AND CONDIZIONE = " + filtro.getCondizione().get().toString();
		}

		// id_Deposito : AND ID_DEPOSITO = 2
		String idDeposito_String = "";
		if (filtro.getId_Deposito().isPresent()) {
			idDeposito_String = "AND ID_DEPOSITO = " + filtro.getId_Deposito().get();
		}

		String selectString = initial_String + " " + idStrumento_String + " " + appartnenza_String + " " + condizione_String + " " + idDeposito_String;

		// esecuzione effettiva della SELECT
		ResultSet resultSet = statement.executeQuery(selectString);

		return resultSet;
	}

	
	/**
	 * Metodo che aggiunge una Pinza al DB usando direttamente una query INSERT
	 * 
	 * @throws SQLException
	 */
	@Override
	public boolean aggiuntaNuovaPinza(Pinza pinza) throws SQLException {
		
		PreparedStatement psInsert = connection.prepareStatement("INSERT INTO PINZE_TABLE (APPARTENENZA, CONDIZIONE, ID_DEPOSITO) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

		// setto tutti i "?"
		psInsert.setString(1, pinza.getAppartenenza().toString());
		psInsert.setString(2, pinza.getCondizione().toString());
		psInsert.setInt(3, pinza.getId_Deposito());

		// esecuzione effettiva dell'INSERT
		if (psInsert.executeUpdate() != 1) {
			throw new SQLException("ERRORE: l'esecuzione di psInsert.executeUpdate() non è andata a buon fine");
		}

		// rilascio delle risorse
		psInsert.close();

		return true;
	}

	
	/**
	 * Metodo che rimuove una Pinza dal DB usando direttamente una query DELETE
	 * 
	 * @throws SQLException
	 */
	@Override
	public boolean rimozionePinza(int id_Pinza) throws SQLException {
		
		PreparedStatement psDelete = connection.prepareStatement("DELETE FROM PINZE_TABLE WHERE ID_STRUMENTO = ?");

		// setto tutti i "?"
		psDelete.setInt(1, id_Pinza);

		// esecuzione effettiva del DELETE
		if (psDelete.executeUpdate() != 1) {
			throw new SQLException("ERRORE: l'esecuzione di psDelete.executeUpdate() non è andata a buon fine");
		}

		// rilascio delle risorse
		psDelete.close();

		return true;
	}

	
	/**
	 * Metodo che modifica una Pinza al DB usando direttamente una query UPDATE. <br>
	 * Va a sovrascrivere tutti i paramentri.
	 * 
	 * @throws SQLException
	 */
	@Override
	public boolean modificaDatiPinza(Pinza pinza) throws SQLException {
		PreparedStatement psUpdate = connection.prepareStatement("UPDATE PINZE_TABLE SET APPARTENENZA = ?, CONDIZIONE = ?, ID_DEPOSITO = ? WHERE ID_STRUMENTO = ?");

		// setto tutti i "?"
		psUpdate.setString(1, pinza.getAppartenenza().toString());
		psUpdate.setString(2, pinza.getCondizione().toString());
		psUpdate.setInt(3, pinza.getId_Deposito());
		psUpdate.setInt(4, pinza.getId_Pinza());
		
		// esecuzione effettiva dell'UPDATE
		if (psUpdate.executeUpdate() != 1) {
			throw new SQLException("ERRORE: l'esecuzione di psUpdate.executeUpdate() non è andata a buon fine");
		}
		
		// rilascio delle risorse
		psUpdate.close();
				
		return true;
	}

}
