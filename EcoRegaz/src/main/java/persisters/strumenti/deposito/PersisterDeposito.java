package main.java.persisters.strumenti.deposito;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import main.java.models.deposito.Deposito;

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


	@Override
	public ResultSet visualizzaDepositi() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean aggiuntaDeposito(Deposito deposito) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rimuoviDeposito(String id_deposito) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean modificaDeposito(Deposito deposito) {
		// TODO Auto-generated method stub
		return false;
	}

}
