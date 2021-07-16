package pattume;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Persister {

	Statement statement;
	Connection connection;

	//////////////////////////////////////////////////////////////////////////////////////////////////
	/*
	 * INIZIALIZZAZIONE del database e creazione se necessario
	 * da fare ogni volta che si accende l'applicazione
	 * magari lo si mette in un'altra classe a parte, oppure lo si mette nel main
	 * OGNI PERSISTER DEVE AVERE connection COME VARIABILE!
	 */
	public boolean openDatabase() throws SQLException {

		/*
		 * Accensione del database
		 */
		String dbName = "ERMS_DB";
		connection = DriverManager.getConnection("jdbc:derby:" + dbName + ";create=true");

		System.out.println("Connected to database " + dbName);// print inutile

		/*
		 * We want to control transactions manually. Autocommit is on by default in JDBC.
		 */
		connection.setAutoCommit(false);
		return true;
	}

	/**
	 * Metodo che chiude la connessione e spegne il database. <br>
	 * Viene invocato quando si chiude lo stage del ricettario. <br>
	 * Avviene tutto correttamente se viene lanciata una precisa eccezione, che se non è quella giusta il metodo lancia a sua volta una SQLException.
	 * 
	 * @throws SQLException
	 */

	public void closeDatabase() throws SQLException {

		/*
		 * Comando che comunica al server il termine delle operazioni
		 */
		connection.commit();

		/*
		 *  Rilascio delle risorse
		 */
		statement.close();
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

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void main(String[] args) throws SQLException {

		/*
		 * 1) PARTE comune ad ogni persisiter (a parte login ovviamente)
		 */

		/*
		 * Creo lo statement per verificare che la tabella sia stata creata, altrimenti la creo
		 */
		statement = connection.createStatement();

		if (!tableExists(connection)) {
			statement.execute("CREATE TABLE INTERVENTI(ID_INTERVENTO INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY," + "		AREAVERDE VARCHAR(254) NOT NULL)");
		}

	}

	/**
	 * Controlla l'esistenza della tabella. Viene eseguita una UPDATE fittizzia (nessuna riga viene selezionata quindi la update non cambia nulla)
	 * 
	 * @param connessione
	 * @return true, se la tabella esiste, false altrimenti
	 * @throws SQLException
	 */
	private boolean tableExists(Connection connessione) throws SQLException {

		try {
			connessione.createStatement().execute("UPDATE RICETTE_TABLE " + "SET NOME_RICETTA = 'TEST ENTRY' " + "WHERE PORTATA = 'test' AND PORZIONI = 0");
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

				System.out.println("Incorrect table definition. Drop RICETTARIO_TABLE and rerun this program");
				throw sqle;
			}

			/*
			 * altrimenti...
			 */
			else {
				System.out.println("Unhandled SQLException");
				throw sqle;
			}
		}

		/*
		 * Se nessuna eccezione viene lanciata, la tabella esiste già.
		 */
		return true;
	}

	/*
	 * 2) SELECT
	 * 
	 */

	public ResultSet select(String selectString) throws SQLException {

		/*
		 * A ResultSet objectis automatically closed by the Statement object that generated it 
		 * when that Statement object is closed, re-executed, or is used to retrieve the next result from asequence of multiple results. 
		 */
		System.out.println(selectString);// TODO print inutile
		ResultSet resultSet = statement.executeQuery(selectString);

		return resultSet;
	}

	/**
	 * 3) INSERT INTO
	 * 
	 */

	public boolean salva(String bho) throws SQLException {

		/* It is recommended to use PreparedStatements when you are
		 * repeating execution of an SQL statement. PreparedStatements also
		 * allows you to parameterize variables. By using PreparedStatements
		 * you may increase performance (because the Derby engine does not
		 * have to recompile the SQL statement each time it is executed) and
		 * improve security (because of Java type checking).
		 */

		PreparedStatement psInsert = connection
					.prepareStatement("INSERT INTO RICETTE_TABLE (NOME_RICETTA, PORTATA, PORZIONI, IS_VEGETARIANA, IS_SENZA_GLUTINE, INGREDIENTI, PREPARAZIONE, TEMPO_PREPARAZIONE, COTTURA) "
								+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

		// setto tutti i "?"
		psInsert.setString(1, bho);

		// esecuzione effettiva dell'INSERT
		if (psInsert.executeUpdate() != 1) {
			throw new SQLException("ERRORE: l'esecuzione di psInsert.executeUpdate() non è andata a buon fine");
		}

		// rilascio delle risorse
		psInsert.close();

		return true;
	}

	/**
	 * 4) UPDATE
	 * 
	 */

	public boolean update(String str) throws SQLException {

		// creo lo statement
		PreparedStatement psUpdate = connection.prepareStatement(
					"UPDATE RICETTE_TABLE SET NOME_RICETTA = ?, PORTATA = ?, PORZIONI = ?, IS_VEGETARIANA = ?, IS_SENZA_GLUTINE = ?, INGREDIENTI = ?, PREPARAZIONE = ?, TEMPO_PREPARAZIONE = ?, COTTURA = ? "
								+ " WHERE ID_RICETTA = ? ");

		// setto tutti i "?"
		psUpdate.setString(1, str);

		// esecuzione
		if (psUpdate.executeUpdate() != 1) {
			throw new SQLException("ERRORE: l'esecuzione di psUpdate.executeUpdate() non è andata a buon fine");
		}
		return true;
	}

	/**
	 * 5) DELETE
	 * 
	 */

	public boolean elimina(Integer ID_da_eliminare) throws SQLException {

		// creo lo statement
		PreparedStatement psDelete = connection.prepareStatement("DELETE FROM RICETTE_TABLE WHERE ID_RICETTA = ?");

		// setto il "?"
		psDelete.setInt(1, ID_da_eliminare);

		// esecuzione
		if (psDelete.executeUpdate() != 1) {
			throw new SQLException("ERRORE: l'esecuzione di psDelete.executeUpdate() non è andata a buon fine");
		}

		// rilascio delle risorse
		psDelete.close();

		return true;
	}

}
