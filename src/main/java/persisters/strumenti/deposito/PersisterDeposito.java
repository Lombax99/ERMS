package main.java.persisters.strumenti.deposito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import main.java.models.deposito.Deposito;
import main.java.persisters.MainDB;
import main.java.persisters.strumenti.pinze.PersisterPinze;
import main.java.persisters.strumenti.rotoliSacchi.PersisterRotoliSacchi;
import main.java.persisters.strumenti.scatoleGuanti.PersisterScatoleGuanti;

/**
 * Classe che implementa i metodi per l'interazione con DB circa i depositi
 */
public class PersisterDeposito implements IPersisterDeposito{
	
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
	private static String createTable = "CREATE TABLE DEPOSITI_TABLE" + "(" + "ID_DEPOSITO INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY," + "DESCRIZIONE VARCHAR(300) NOT NULL," + "STRUMENTI_EXTRA VARCHAR(300) NOT NULL" + ")";

	
	/*
	 * Pattern Singleton
	 */
	private static PersisterDeposito instance = null;

	/**
	 * Pattern Singleton. <br>
	 * L'eccezione viene lanciata perch� nel costruttore qualcosa pu� andare storto.
	 * 
	 * @throws SQLException
	 */
	public static PersisterDeposito getInstance() throws SQLException {
		if (instance == null) {
			instance = new PersisterDeposito();
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
	private PersisterDeposito() throws SQLException {
		
		/*
		 * Prelevo la connessione da MainDB, il quale sicuramente � gi� stato inizializzato
		 */
		connection = MainDB.connection;

		if (connection == null) {
			throw new SQLException("Costruttore PersisterDeposito: Connessione con DB non stabilita");
		}

		/*
		 * Creazione dello statement che permette di eseguire query SQL
		 */
		statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

		/*
		 * Verifica dell'esistenza della tabella dei Depositi.
		 * Se non esiste, viene creata
		 */
		if (!MainDB.tableExists(connection, "UPDATE DEPOSITI_TABLE SET DESCRIZIONE = 'test' WHERE ID_DEPOSITO = -100 ")) {
			statement.execute(createTable);
		}

	}
	
	
	/**
	 * Metodo che restituisce un ResultSet che contiene il join tra le tabelle delgi strumenti.
	 * @throws SQLException 
	 * 
	 */
	@Override
	public ResultSet visualizzaDepositi() throws SQLException {
		
		/*
		 * Faccio creare le tabelle
		 */
		PersisterPinze.getInstance();
		PersisterScatoleGuanti.getInstance();
		PersisterRotoliSacchi.getInstance();
		
		/*
		 * Vi sono una serie di select innestate perch� derby non supporta WITH
		 * 1) Select che preleva la quantit� pinze
		 * 2) Select che preleva la quantit� sacchi
		 * 3) Select che prevela una stringa bellina con le taglie
		 */
		String selectString = "SELECT 	D.ID_DEPOSITO, D.DESCRIZIONE, COALESCE(NUM_PINZE,0) AS NUM_PINZE, COALESCE(NUM_SACCHI,0) AS NUM_SACCHI, COALESCE(T.TAGLIA, 'XS: 0         S: 0         M: 0         L: 0         XL: 0') AS TAGLIA , D.STRUMENTI_EXTRA\n"
					+ "FROM 	DEPOSITI_TABLE D\n"
					+ "LEFT JOIN 	(SELECT		ID_DEPOSITO, COUNT(*) AS NUM_PINZE\n"
					+ "				FROM		PINZE_TABLE P\n"
					+ "				GROUP BY	ID_DEPOSITO) AS COUNT_PINZE(ID_DEPOSITO, NUM_PINZE)\n"
					+ "ON D.ID_DEPOSITO = COUNT_PINZE.ID_DEPOSITO\n"
					+ "LEFT JOIN	(SELECT		ID_DEPOSITO, COUNT(*) AS NUM_SACCHI\n"
					+ "				FROM		SACCHI_TABLE S\n"
					+ "				GROUP BY	ID_DEPOSITO) AS COUNT_SACCHI(ID_DEPOSITO, NUM_SACCHI)\n"
					+ "ON D.ID_DEPOSITO = COUNT_SACCHI.ID_DEPOSITO\n"
					+ "LEFT JOIN 	(SELECT T.ID_DEPOSITO, \n"
					+ "						'XS: ' || CAST(T.TAGLIA_XS AS CHAR(10)) || \n"
					+ "						'S: ' || CAST(T.TAGLIA_S AS CHAR(10)) ||\n"
					+ "						'M: ' || CAST(T.TAGLIA_M AS CHAR(10)) ||\n"
					+ "						'L: ' || CAST(T.TAGLIA_L AS CHAR(10)) ||\n"
					+ "						'XL: ' || CAST(T.TAGLIA_XL AS CHAR(10)) AS TAGLIA\n"
					+ "				FROM (SELECT		G.ID_DEPOSITO,\n"
					+ "									SUM(CASE WHEN G.TAGLIA = 'XS' THEN 1 ELSE 0 END) AS TAGLIA_XS, \n"
					+ "									SUM(CASE WHEN G.TAGLIA = 'S' THEN 1 ELSE 0 END) AS TAGLIA_S,\n"
					+ "									SUM(CASE WHEN G.TAGLIA = 'M' THEN 1 ELSE 0 END) AS TAGLIA_M,\n"
					+ "									SUM(CASE WHEN G.TAGLIA = 'L' THEN 1 ELSE 0 END) AS TAGLIA_L,\n"
					+ "									SUM(CASE WHEN G.TAGLIA = 'XL' THEN 1 ELSE 0 END) AS TAGLIA_XL\n"
					+ "						FROM		GUANTI_TABLE G\n"
					+ "						GROUP BY	ID_DEPOSITO\n"
					+ "						) AS T(ID_DEPOSITO, TAGLIA_XS, TAGLIA_S, TAGLIA_M, TAGLIA_L, TAGLIA_XL)) AS T(ID_DEPOSITO, TAGLIA)\n"
					+ "ON		D.ID_DEPOSITO = T.ID_DEPOSITO";
		
		
		// esecuzione effettiva della SELECT
		ResultSet resultSet = statement.executeQuery(selectString);
		return resultSet;
	}

	
	/**
	 * Metodo che aggiunge un Deposito al DB usando direttamente una query INSERT
	 * 
	 * @throws SQLException
	 */
	@Override
	public boolean aggiuntaDeposito(Deposito deposito) throws SQLException {
		
		PreparedStatement psInsert = connection.prepareStatement("INSERT INTO DEPOSITI_TABLE (DESCRIZIONE, STRUMENTI_EXTRA) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);

		// setto tutti i "?"
		psInsert.setString(1, deposito.getDescrizione());
		psInsert.setString(2, deposito.getStrumentiExtra());

		// esecuzione effettiva dell'INSERT
		if (psInsert.executeUpdate() != 1) {
			throw new SQLException("ERRORE: l'esecuzione di psInsert.executeUpdate() non � andata a buon fine");
		}

		// rilascio delle risorse
		psInsert.close();

		return true;
	}

	
	
	/**
	 * Metodo che rimuove un Deposito al DB usando direttamente una query DELETE
	 * 
	 * @throws SQLException
	 */
	@Override
	public boolean rimuoviDeposito(int id_deposito) throws SQLException {
		
		PreparedStatement psDelete = connection.prepareStatement("DELETE FROM DEPOSITI_TABLE WHERE ID_DEPOSITO = ?");

		// setto tutti i "?"
		psDelete.setInt(1, id_deposito);

		// esecuzione effettiva del DELETE
		if (psDelete.executeUpdate() != 1) {
			throw new SQLException("ERRORE: l'esecuzione di psDelete.executeUpdate() non � andata a buon fine");
		}

		// rilascio delle risorse
		psDelete.close();

		return true;
	}

	
	
	/**
	 * Metodo che modifica un Deposito al DB usando direttamente una query UPDATE. <br>
	 * Va a sovrascrivere tutti i paramentri.
	 * 
	 * @throws SQLException
	 */
	@Override
	public boolean modificaDeposito(Deposito deposito) throws SQLException {
		
		PreparedStatement psUpdate = connection.prepareStatement("UPDATE DEPOSITI_TABLE SET DESCRIZIONE = ?, STRUMENTI_EXTRA = ? WHERE ID_DEPOSITO = ?");

		// setto tutti i "?"
		psUpdate.setString(1, deposito.getDescrizione());
		psUpdate.setString(2, deposito.getStrumentiExtra());
		psUpdate.setInt(3, deposito.getId_Deposito());
		
		// esecuzione effettiva dell'UPDATE
		if (psUpdate.executeUpdate() != 1) {
			throw new SQLException("ERRORE: l'esecuzione di psUpdate.executeUpdate() non � andata a buon fine");
		}
		
		// rilascio delle risorse
		psUpdate.close();
				
		return true;
	}

}
