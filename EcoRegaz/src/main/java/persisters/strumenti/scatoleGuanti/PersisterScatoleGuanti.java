package main.java.persisters.strumenti.scatoleGuanti;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import main.java.models.strumenti.scatolaGuanti.ScatolaGuanti;
import main.java.persisters.MainDB;

/**
 * Classe che implementa i metodi per l'interazione con DB circa i guanti
 *
 */
public class PersisterScatoleGuanti implements IPersisterScatoleGuanti {

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
	private static String createTable = "CREATE TABLE GUANTI_TABLE (ID_STRUMENTO INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY, "
				+ " TAGLIA VARCHAR(5) NOT NULL CHECK(TAGLIA = 'XS' OR TAGLIA = 'S' OR TAGLIA = 'M' OR TAGLIA = 'L' OR TAGLIA = 'XL' ), "
				+ " ID_DEPOSITO INTEGER NOT NULL REFERENCES DEPOSITI_TABLE(ID_DEPOSITO))";

	/*
	 * Pattern Singleton
	 */
	private static PersisterScatoleGuanti instance = null;

	/**
	 * Pattern Singleton. <br>
	 * L'eccezione viene lanciata perché nel costruttore qualcosa può andare storto.
	 * 
	 * @throws SQLException
	 */
	public static PersisterScatoleGuanti getInstance() throws SQLException {
		if (instance == null) {
			instance = new PersisterScatoleGuanti();
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
	private PersisterScatoleGuanti() throws SQLException {

		/*
		 * Prelevo la connessione da MainDB, il quale sicuramente è già stato inizializzato
		 */
		connection = MainDB.connection;

		if (connection == null) {
			throw new SQLException("Costruttore PersisterScatoleGuanti: Connessione con DB non stabilita");
		}

		/*
		 * Creazione dello statement che permette di eseguire query SQL
		 */
		statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

		/*
		 * Verifica dell'esistenza della tabella delle Pinze.
		 * Se non esiste, viene creata
		 */
		if (!MainDB.tableExists(connection, "UPDATE GUANTI_TABLE SET TAGLIA = 'S' WHERE ID_STRUMENTO = -100 ")) {
			statement.execute(createTable);
		}

	}

	/**
	 * Metodo che restituisce un RestultSet contenente tutta la tabella dei guanti, insieme alla descrizione del deposito associato.
	 * 
	 * @throws SQLException
	 */
	@Override
	public ResultSet elencoScatole() throws SQLException {
		String selectString = "SELECT 	G.ID_STRUMENTO, G.TAGLIA, D.ID_DEPOSITO, D.DESCRIZIONE " + "FROM 	GUANTI_TABLE G, DEPOSITI_TABLE D " + "WHERE	D.ID_DEPOSITO = G.ID_DEPOSITO";

		ResultSet resultSet = statement.executeQuery(selectString);

		return resultSet;
	}

	
	/**
	 * Metodo che aggiunge una ScatolaGuanti al DB usando direttamente una query INSERT
	 * 
	 * @throws SQLException
	 */
	@Override
	public boolean aggiuntaNuovaScatola(ScatolaGuanti scatola) throws SQLException{
		PreparedStatement psInsert = connection.prepareStatement("INSERT INTO GUANTI_TABLE (TAGLIA, ID_DEPOSITO) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);

		// setto tutti i "?"
		psInsert.setString(1, scatola.getTaglia().toString());
		psInsert.setInt(2, scatola.getId_Deposito());

		// esecuzione effettiva dell'INSERT
		if (psInsert.executeUpdate() != 1) {
			throw new SQLException("ERRORE: l'esecuzione di psInsert.executeUpdate() non è andata a buon fine");
		}

		// rilascio delle risorse
		psInsert.close();

		return true;
	}

	
	/**
	 * Metodo che rimuove una ScatolaGuanti dal DB usando direttamente una query DELETE
	 * 
	 * @throws SQLException
	 */
	@Override
	public boolean rimozioneScatola(int id_ScatolaGuanti) throws SQLException {

		PreparedStatement psDelete = connection.prepareStatement("DELETE FROM GUANTI_TABLE WHERE ID_STRUMENTO = ?");

		// setto tutti i "?"
		psDelete.setInt(1, id_ScatolaGuanti);

		// esecuzione effettiva del DELETE
		if (psDelete.executeUpdate() != 1) {
			throw new SQLException("ERRORE: l'esecuzione di psDelete.executeUpdate() non è andata a buon fine");
		}

		// rilascio delle risorse
		psDelete.close();

		return true;
	}

}
