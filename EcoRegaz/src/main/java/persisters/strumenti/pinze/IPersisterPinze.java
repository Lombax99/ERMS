package main.java.persisters.strumenti.pinze;

import java.sql.ResultSet;

import main.java.models.strumenti.pinza.Pinza;

/**
 * Interfaccia che definisce i metodi per l'interazione con DB circa le pinze
 *
 */
public interface IPersisterPinze {
	
	public ResultSet elencoPinze();
	
	public boolean aggiuntaNuovaPinza(Pinza pinza);
	
	public boolean rimozionePinza(int id_Pinza);
	
	public boolean modificaDatiPinza(Pinza pinza);

}
