package main.java.persisters.interventi;

import java.sql.ResultSet;
import java.sql.SQLException;

import main.java.controllers.interventi.gestioneInterventi.FiltroInterventi;
import main.java.models.intervento.Intervento;

/**
 * Interfaccia che definisce i metodi per l'interazione col DB circa interventi e aree verdi
 */
public interface IPersisterInterventi {
	
	public ResultSet visualizzaInterventi(FiltroInterventi filtroInterventi) throws SQLException;
	
	public boolean aggiuntaIntervento(Intervento intervento) throws SQLException;
	
	public boolean rimuoviIntervento(int id_Intervento) throws SQLException;
	
	public boolean modificaIntervento(Intervento intervento) throws SQLException;

}
