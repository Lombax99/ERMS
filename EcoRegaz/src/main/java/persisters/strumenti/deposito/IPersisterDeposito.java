package main.java.persisters.strumenti.deposito;

import java.sql.ResultSet;

import main.java.models.deposito.Deposito;

/**
 * Interfaccia che definisce i metodi per l'interazione con DB circa i depositi
 *
 */
public interface IPersisterDeposito {
	
	public ResultSet visualizzaDepositi();
	
	public boolean aggiuntaDeposito(Deposito deposito);
	
	public boolean rimuoviDeposito(String id_deposito);
	
	public boolean modificaDeposito(Deposito deposito);
	
}
