package main.java.persisters.strumenti.pinze;

import java.sql.ResultSet;
import java.sql.SQLException;

import main.java.controllers.strumenti.pinze.FiltroPinze;
import main.java.models.strumenti.pinza.Pinza;

/**
 * Interfaccia che definisce i metodi per l'interazione con DB circa le pinze
 *
 */
public interface IPersisterPinze {
	
	public ResultSet elencoPinze(FiltroPinze filtro) throws SQLException;
	
	public boolean aggiuntaNuovaPinza(Pinza pinza) throws SQLException;
	
	public boolean rimozionePinza(int id_Pinza) throws SQLException;
	
	public boolean modificaDatiPinza(Pinza pinza) throws SQLException;

}
