package main.java.persisters.strumenti.deposito;

import java.sql.ResultSet;
import java.sql.SQLException;

import main.java.models.deposito.Deposito;

/**
 * Interfaccia che definisce i metodi per l'interazione con DB circa i depositi
 *
 */
public interface IPersisterDeposito {
	
	public ResultSet visualizzaDepositi() throws SQLException;
	
	public boolean aggiuntaDeposito(Deposito deposito) throws SQLException;
	
	public boolean rimuoviDeposito(String id_deposito) throws SQLException;
	
	public boolean modificaDeposito(Deposito deposito) throws SQLException;
	
}
