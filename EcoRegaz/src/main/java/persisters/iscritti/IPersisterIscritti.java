package main.java.persisters.iscritti;

import java.sql.ResultSet;
import java.sql.SQLException;

import main.java.controllers.interventi.gestioneIscritti.FiltroIscritti;
import main.java.models.iscritto.Iscritto;

/**
 * Interfaccia che definisce i metodi per l'interazione con DB circa gli iscritti
 *
 */
public interface IPersisterIscritti {

	public ResultSet visualizzaIscritti(FiltroIscritti filtro) throws SQLException;
	
	public boolean aggiuntaIscritto(Iscritto iscritto) throws SQLException;
	
	public boolean rimuoviIscritto(String codFisc) throws SQLException;
	
	public boolean modificaIscritto(Iscritto iscritto) throws SQLException;
}
