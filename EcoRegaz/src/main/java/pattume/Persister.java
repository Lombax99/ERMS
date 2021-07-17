package main.java.pattume;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import main.java.persisters.MainDB;

public class Persister {

	Statement statement;
	Connection connection;
	
	//////////////////////////////////////////////////////////////////////////////////////////////////
	/*
	 * costruttore pattern singleton
	 */
	
	private static Persister instance = null; 
	
	public static Persister getInstance() throws SQLException {
		if(instance == null)
		{
			instance = new Persister();
		}
		return instance;
	}
	
	private Persister() throws SQLException {
		
		/*
		 * 1) PARTE comune ad ogni persisiter (a parte login ovviamente)
		 */

		/*
		 * E' importante che lo Statement della select viva anche oltre il metodo, quindi è necessario che sia una variabile globale.
		 * Creo lo statement per verificare che la tabella sia stata creata, altrimenti la creo
		 */
		statement = connection.createStatement();

		if (!MainDB.tableExists(connection, "queryUpdate")) {
			statement.execute("CREATE TABLE INTERVENTI(ID_INTERVENTO INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY," + "		AREAVERDE VARCHAR(254) NOT NULL)");
		}

	}



	/*
	 * 2) SELECT
	 * 
	 */

	public ResultSet select(String selectString) throws SQLException {

		/*
		 * A ResultSet object is automatically closed by the Statement object that generated it,
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
