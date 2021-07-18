package main.java.persisters.strumenti.pinze;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
		statement = connection.createStatement();

		/*
		 * Verifica dell'esistenza della tabella dei Depositi.
		 * Se non esiste, viene creata
		 */
		if (!MainDB.tableExists(connection, "UPDATE PINZE_TABLE SET APPARTENENZA = 'test' WHERE ID_STRUMENTO = -100 ")) {
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
	public ResultSet elencoPinze() {
		// TODO Auto-generated method stub
		return null;
	}
	

	@Override
	public boolean aggiuntaNuovaPinza(Pinza pinza) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rimozionePinza(int id_Pinza) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean modificaDatiPinza(Pinza pinza) {
		// TODO Auto-generated method stub
		return false;
	}

}
