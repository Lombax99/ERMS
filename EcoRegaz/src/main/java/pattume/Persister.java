package main.java.pattume;

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

}
