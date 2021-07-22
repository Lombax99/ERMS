package main.java.persisters.strumenti.rotoliSacchi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import main.java.models.strumenti.rotoloSacchi.RotoloSacchi;
import main.java.persisters.MainDB;

/**
 * Classe che implementa i metodi per l'interazione con DB circa i sacchi
 *
 */
public class PersisterRotoliSacchi implements IPersisterRotoliSacchi {

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
	private static String createTable = "CREATE TABLE SACCHI_TABLE (ID_STRUMENTO INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY, "
				+ " ID_DEPOSITO INTEGER NOT NULL REFERENCES DEPOSITI_TABLE(ID_DEPOSITO))";

	/*
	 * Pattern Singleton
	 */
	private static PersisterRotoliSacchi instance = null;

	/**
	 * Pattern Singleton. <br>
	 * L'eccezione viene lanciata perché nel costruttore qualcosa può andare storto.
	 * 
	 * @throws SQLException
	 */
	public static PersisterRotoliSacchi getInstance() throws SQLException {
		if (instance == null) {
			instance = new PersisterRotoliSacchi();
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
	private PersisterRotoliSacchi() throws SQLException {

		/*
		 * Prelevo la connessione da MainDB, il quale sicuramente è già stato inizializzato
		 */
		connection = MainDB.connection;

		if (connection == null) {
			throw new SQLException("Costruttore PersisterRotoliSacchi: Connessione con DB non stabilita");
		}

		/*
		 * Creazione dello statement che permette di eseguire query SQL
		 */
		statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

		/*
		 * Verifica dell'esistenza della tabella dei Sacchi.
		 * Se non esiste, viene creata
		 */
		if (!MainDB.tableExists(connection, "UPDATE SACCHI_TABLE SET ID_DEPOSITO = 1 WHERE ID_STRUMENTO = -100 ")) {
			statement.execute(createTable);
		}

	}

	/**
	 * Metodo che restituisce un RestultSet contenente tutta la tabella dei sacchi , insieme alla descrizione del deposito associato.
	 * 
	 * @throws SQLException
	 */
	@Override
	public ResultSet elencoRotoli() throws SQLException {
		String selectString = "SELECT 	S.ID_STRUMENTO, D.ID_DEPOSITO, D.DESCRIZIONE "
					+ "FROM 	SACCHI_TABLE S, DEPOSITI_TABLE D "
					+ "WHERE	D.ID_DEPOSITO = S.ID_DEPOSITO";

		ResultSet resultSet = statement.executeQuery(selectString);

		return resultSet;
	}

	/**
	 * Metodo che aggiunge un RotoloSacchi al DB usando direttamente una query INSERT
	 * 
	 * @throws SQLException
	 */
	@Override
	public boolean aggiuntaNuovaRotolo(RotoloSacchi rotolo) throws SQLException {
		PreparedStatement psInsert = connection.prepareStatement("INSERT INTO SACCHI_TABLE (ID_DEPOSITO) VALUES (?)", Statement.RETURN_GENERATED_KEYS);

		// setto tutti i "?"
		psInsert.setInt(1, rotolo.getId_Deposito());

		// esecuzione effettiva dell'INSERT
		if (psInsert.executeUpdate() != 1) {
			throw new SQLException("ERRORE: l'esecuzione di psInsert.executeUpdate() non è andata a buon fine");
		}

		// rilascio delle risorse
		psInsert.close();

		return true;
	}

	/**
	 * Metodo che rimuove un RotoloSacchi dal DB usando direttamente una query DELETE
	 * 
	 * @throws SQLException
	 */
	@Override
	public boolean rimozioneRotolo(int id_Strumento) throws SQLException {
		PreparedStatement psDelete = connection.prepareStatement("DELETE FROM SACCHI_TABLE WHERE ID_STRUMENTO = ?");

		// setto tutti i "?"
		psDelete.setInt(1, id_Strumento);

		// esecuzione effettiva del DELETE
		if (psDelete.executeUpdate() != 1) {
			throw new SQLException("ERRORE: l'esecuzione di psDelete.executeUpdate() non è andata a buon fine");
		}

		// rilascio delle risorse
		psDelete.close();

		return true;
	}

}
